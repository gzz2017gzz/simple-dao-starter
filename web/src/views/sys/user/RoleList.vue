/*角色不分页列表,作者:高振中,日期:2024-12-09 12:22:55*/
<template>
  <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" width="25rem">
    <div class="table-operator">
      <el-button size="mini" @click="doAdd" type="primary">添加</el-button>
      <el-button size="mini" type="primary" @click="doSave()">保存</el-button>
      <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
    </div>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'userRoleId')" :loading="loading">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="name" label="角色名称">
        <template slot-scope="props">
          <v-select-table placeholder="请选角色" v-model="props.row.roleId" tableName="sys_role" idField="role_id" nameField="name" where="dr = 0" :addBlank="true" clearable></v-select-table>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="60" align="center">
        <template slot-scope="props">
          <el-button v-if="props.row.userRoleId" type="text" size="mini" @click="doDelete([props.row.userRoleId])" style="color: #F56C6C;">删除</el-button>
          <el-button v-if="!props.row.userRoleId" type="text" size="mini" @click="doDeleteRow(props.$index)">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
  </el-dialog>
</template>
<script>
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  data() {
    return {
      title: null, show: null,
    };
  },
  methods: {
    /**打开角色列表 */
    openList(row) {
      this.title = '【' + row.name + '】角色列表'
      this.form = { ...this.form, userId: row.userId };
      this.loadData();
      this.show = true;
    },
    /**删除未保的行 */
    doDeleteRow(index) {
      this.dataList.splice(index, 1);
    },
    /**科目-批量保存*/
    doSave() {
      if (this.dataList.filter(i => i.roleId == null).length > 0) {
        this.$message.error("【角色名称】不能为空!");
        return
      }
      this.rq.post("/roleUser/saveBatch", this.dataList).then(res => {
        if (res.code == 200) {
          this.$message.success("保存成功!");
          this.loadData();
        } else this.$message.error(res.msg);
      });
    },
    /**加新行*/
    doAdd() {
      this.dataList.push({
        userId: this.form.userId,/*专业主键*/
        roleId: null,/*角色名称*/
      });
    },

    /**角色-查询参数*/
    initForm() {
      return {
        name: null,/*角色名称*/
        roleAuth: null,/*角色权限*/
        orderNum: null,/*排序*/
        remark: null,/*备注*/
        dr: 0,/*删除标记*/
      };
    },
    /**角色-列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form };
      this.rq.post("/roleUser/listRoles", param).then(res => {
        if (res.code == 200) {
          this.dataList = res.data;
          this.loading = false;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    /**角色-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/roleUser/delete", ids).then(res => {
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

::v-deep .el-dialog__body {
  padding-top: 0px;
}
</style>
