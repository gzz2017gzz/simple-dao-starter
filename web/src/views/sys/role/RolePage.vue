/*角色分页列表,作者:高振中,日期:2021-02-20 11:34:58*/
<template>
  <el-card>
    <el-col :span="24 - leftSpan">
      <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="100px">
        <el-row>
          <el-form-item label="角色名称">
            <el-input placeholder="请输入角色名称" size="mini" v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input placeholder="请输入备注" size="mini" v-model="form.remark"></el-input>
          </el-form-item>
          <el-form-item style="padding-left: 40px;">
            <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
            <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
            <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-operator">
        <el-button size="mini" @click="doAdd" type="primary">添加</el-button>
      </div>
      <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'roleId')" :loading="loading" height="calc(100vh - 250px)">
        <el-table-column type="selection" align="center"></el-table-column>
        <el-table-column prop="name" label="角色名称"></el-table-column>
        <el-table-column prop="orderNum" label="排序编号" align="center"></el-table-column>
        <el-table-column prop="createBy" label="创建人" :formatter="(row, col) => dictFormat(row, col, 'persons')"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" :formatter="dateFormat" align="center"></el-table-column>
        <el-table-column prop="updateBy" label="修改人" :formatter="(row, col) => dictFormat(row, col, 'persons')"></el-table-column>
        <el-table-column prop="updateTime" label="修改时间" width="160" :formatter="dateFormat" align="center"></el-table-column>
        <el-table-column prop="remark" label="备注" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template slot-scope="props">
            <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
            <el-button type="text" size="mini" @click="handleUserRef(props.row.roleId)">用户</el-button><el-divider direction="vertical"></el-divider>
            <el-dropdown trigger="click">
              <el-button type="text" size="mini">
                更多
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <el-button type="text" size="mini" @click="doDelete([props.row.roleId])" style="color: #F56C6C;">删除</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" size="mini" @click="linkUserPc(props.row.roleId)">关联用户(pc)</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" size="mini" @click="linkFunc(props.row.roleId)">功能权限</el-button>
                </el-dropdown-item>
                <!-- <el-dropdown-item >
                    <el-button type="text" size="mini" @click="linkDept(props.row)">数据权限</el-button>
                  </el-dropdown-item> -->
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </v-table>
      <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
      <RoleDialog ref="dialog" :loadData="loadData"></RoleDialog>
      <UserRefPc ref="UserRefPc"></UserRefPc>
      <MenuTree ref="menuTree"></MenuTree>
      <DeptTree ref="deptTree" @child-event="loadData"></DeptTree>
    </el-col>
    <el-col :span="leftSpan" style="padding-left: 20px;">
      <div class="close"><i class="el-icon-circle-close" @click="handleClose"></i></div>
      <RoleUserList :roleId="roleId"></RoleUserList>
    </el-col>
  </el-card>
</template>
<script>
import RoleDialog from "./RoleDialog";
import MenuTree from "./MenuTree";
import DeptTree from "./DeptTree";
import UserRefPc from "./UserRefPc";
import { pageMix } from "@/common/page";
import RoleUserList from "@/views/sys/role/modules/RoleUserList";
export default {
  components: { RoleDialog, MenuTree, DeptTree, UserRefPc, RoleUserList },
  mixins: [pageMix],
  created() {
    this.loadData();
  },
  data() {
    return {
      leftSpan: 0,
      roleId: null
    }
  },
  methods: {
    handleUserRef(roleId) {
      this.leftSpan = 6;
      this.roleId = roleId;
    },
    handleClose() {
      this.leftSpan = 0;
    },
    /*角色-关联用户(mobile)*/
    linkUserPc(roleId) {
      this.$refs.UserRefPc.showUsers(roleId);
    },
    /*角色-授权功能*/
    linkFunc(roleId) {
      this.$refs.menuTree.showFuncs(roleId);
    },
    /*角色-授权部门*/
    linkDept(roleId) {
      this.$refs.deptTree.showDepts(roleId);
    },
    /*角色-查询参数*/
    initForm() {
      return {
        name: null, /*角色名称*/
        remark: null, /*备注*/
        type: null,/*角色类型 */
        dr: 0
      };
    },
    /*角色-列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/role/page", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /*角色-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/role/delete", ids).then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => { });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";

.close {
  text-align: right;
  cursor: pointer;
}
</style>
