/*用户管理,作者:高振中,日期:2020-12-20 22:36:10*/
<template>
  <el-card>
    <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="100px" class="form-find"  >
      <el-row>
        <el-form-item label="部门名称"> <el-input placeholder="请输入部门名称" size="mini" v-model="form.deptName"></el-input> </el-form-item>
        <el-form-item label="姓名"> <el-input placeholder="请输入姓名" size="mini" v-model="form.name"></el-input> </el-form-item>
        <el-form-item label="登录名"> <el-input placeholder="请输入登录名" size="mini" v-model="form.loginNameLike"></el-input> </el-form-item>
        <el-form-item label="状态"> <v-select placeholder="请选状态" v-model="form.status" dictKey="userStatus" :addBlank="true"></v-select> </el-form-item>
        <el-form-item label="有代理人"> <v-select placeholder="请选择" v-model="form.proxyUserIdHas" dictKey="yesNo" :addBlank="true"></v-select> </el-form-item>
        <el-form-item label="权限"> <v-select placeholder="请选择" v-model="form.authType" dictKey="userAuthType" :addBlank="true"></v-select> </el-form-item>
        <el-form-item label="岗位"> <v-select placeholder="请选岗位" v-model="form.post" dictKey="posts" :addBlank="true"></v-select> </el-form-item>
        <el-form-item style="padding-left: 40px;">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <div class="table-operator">
      <el-button size="mini" @click="doAdd" type="primary">添加</el-button>
      <el-button size="mini" @click="doSyncUser" type="warning" plain>同步单个用户</el-button>
    </div>
    <v-table :data="dataList" :loading="loading" height="calc(100vh - 306px)">
      <el-table-column prop="deptName" label="部门名称" width="200"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="authType" label="权限类型" :formatter="(row, col) => dictFormat(row, col, 'userAuthType')"></el-table-column>
      <el-table-column prop="proxyUserId" label="代理人" :formatter="(row, col) => dictFormat(row, col, 'persons')"></el-table-column>
      <el-table-column prop="loginName" label="登录名"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="props">
          <el-tag size="mini" type="success" effect="dark" v-if="props.row.status === 1">{{ dictFormatVal("userStatus", props.row.status) }}</el-tag>
          <el-tag size="mini" type="info" effect="dark" v-else>{{ dictFormatVal("userStatus", props.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="posts" label="岗位" show-overflow-tooltip :formatter="(row, col) => multiDictFormat(row, col, 'posts')"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" width="240" align="center">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="showRoles(props.row)">角色列表</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doDelete([props.row.userId])" style="color: #F56C6C;">删除</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="updatePass(props.row.userId)">修改密码</el-button>
        </template>
      </el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
    <UserDialog ref="dialog" :loadData="loadData"></UserDialog>
    <PassDialog ref="passDialog" :loadData="loadData"></PassDialog>
    <RoleList ref="roleList"></RoleList>
  </el-card>
</template>
<script>
import RoleList from "./RoleList";
import UserDialog from "./UserDialog";
import PassDialog from "./PassDialog";
import { pageMix } from "@/common/page";
import http from "@/common/http";
import { dictFormatVal } from "../../../common/dicts";
export default {
  mixins: [pageMix],
  components: { UserDialog, PassDialog, RoleList },
  created() {
    this.loadData();
  },
  methods: {
    /*角色列表*/
    showRoles(row) {
      this.$refs.roleList.openList(row);
    },
    dictFormatVal,
    doSyncUser() {
      this.$prompt("请输入企微用户姓名拼音或邮箱", "同步企微账号到CRM系统", {
        confirmButtonText: "同步",
        cancelButtonText: "取消",
        inputValue: "",
        inputErrorMessage: '请输入企微用户姓名拼音或邮箱',
        inputValidator: (value) => {
          if (!value) return '企微用户姓名拼音不能为空或邮箱'
        },
        type: "warning"
      }).then(({ value }) => {
        http.post("/qwechat/doSyncOneUserByEmail?email=" + value).then((res) => {
          if (res.code == 200) {
            this.$message.success("同步成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => {

      });
    },
    /*用户-查询参数*/
    initForm() {
      return {
        deptName: null, /*机构ID*/
        name: null, /*姓名*/
        proxyUserIdHas: null, /*是否有代理人*/
        authType: null, /*权限类型*/
        password: null, /*密码*/
        loginNameLike: null, /*登录名*/
        schoolId: null, /*校区主键*/
        post: null, /*岗位标识*/
        dr: 0,/*删除标记 */
      };
    },
    /*用户-请求列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/user/page", param).then((res) => {
        this.dataList = res.data.dataList;
        this.total = res.data.rowCount;
        this.loading = false;
      });
    },
    /*用户-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/user/delete", ids).then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => { });
    },
    /** 修改密码 */
    updatePass(userId) {
      this.$refs.passDialog.editDialog(userId);
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";
</style>
