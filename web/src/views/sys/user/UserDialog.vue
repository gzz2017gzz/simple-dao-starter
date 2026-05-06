/*用户新增与修改,作者:高振中,日期:2020-12-20 22:36:10*/
<template>
  <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" width="50rem">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="机构" prop="deptName">
            <el-input placeholder="请选择机构" readonly v-model="form.deptName" size="mini">
              <el-button @click="showDeptTree" slot="append" icon="el-icon-search" size="mini"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="姓名" prop="name">
            <el-input placeholder="请输入姓名" v-model="form.name" size="mini" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11" v-if="dialogMode == 'save'">
          <el-form-item label="密码" prop="password">
            <el-input placeholder="请输入密码" v-model="form.password" size="mini" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="登录名" prop="loginName">
            <el-input placeholder="请输入登录名" v-model="form.loginName" size="mini" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="企业微信ID" prop="qwId">
            <el-input placeholder="请输入企业微信ID" disabled v-model="form.qwId" size="mini" maxlength="20" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态" prop="status">
            <v-select v-model="form.status" placeholder="请选状态" dictKey="userStatus"></v-select>
          </el-form-item>
        </el-col>
         <el-col :span="22">
          <el-form-item label="岗位" prop="posts">
            <v-select v-model="posts" placeholder="请选岗位" dictKey="posts" multiple :add-blank="true" filterable></v-select>
          </el-form-item>
        </el-col>
         <el-col :span="11">
          <el-form-item label="权限类型" prop="schoolIds">
            <v-select placeholder="请选择权限类型" v-model="form.authType" dictKey="userAuthType"></v-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="代理人" prop="proxyUserId">
            <el-input v-model="form.proxyName" placeholder="请选择代理人" size="mini">
              <el-button @click="clearProxy()" slot="append" icon="el-icon-close" size="mini"></el-button>
              <el-button @click="showUserPage(2)" slot="append" icon="el-icon-search" size="mini"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" placeholder="请输入备注" v-model="form.remark" size="mini" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <v-footer :close="() => show = false" :save="save"></v-footer>
    <v-dept-tree ref="deptTree" :setValue="setDeptValue"></v-dept-tree>
    <v-user-page ref="vuserpage" :setValue="setUserValue"></v-user-page>
  </el-dialog>
</template>
<script>
export default {
  props: ["loadData"],
  data() {
    return {
      flage: false, title: "", form: this.initForm(), dialogMode: "save", show: false, schoolIds: [], posts: [],excludeSchoolIds:[],
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        loginName: [{ required: true, message: "请输入登录名", trigger: "blur" }],
        number: [{ required: true, message: '请输入编号', trigger: 'blur' }],
        status: [{ required: true, message: '请选状态', trigger: 'blur' }],
      },
    };
  },
  watch: {
    "form.password"() {
      if (this.form.password) this.form.password = this.form.password.replace(/[\W]/g, '');
    },
    "form.loginName"() {
      if (this.form.loginName) this.form.loginName = this.form.loginName.replace(/[\W]/g, '');
    },
  },
  /*点开弹窗清空验证信息*/
  updated() {
    this.$refs.form.clearValidate();
  },
  methods: {
    /*清除代理人信息*/
    clearProxy() {
      this.form = { ...this.form, proxyName: null, proxyUserId: null };
    },
    /*返回用户信息*/
    setUserValue(user) {
      if (user != null) {
        this.form = { ...this.form, proxyName: user.name, proxyUserId: user.userId };
      }
    },
    /*用户选择页*/
    showUserPage(type) {
      this.$refs.vuserpage.showUserPage(type);
    },
    /*返回机构节点*/
    setDeptValue(node) {
      this.form = { ...this.form, deptName: node.name, deptId: node.id };
    },
    /*显示机构树*/
    showDeptTree() {
      this.$refs.deptTree.showTree();
    },
    /*用户-保存*/
    save() {
      this.form.posts = this.posts.toString();
      this.form.schoolIds = this.schoolIds.toString();
      this.form.excludeSchoolIds = this.excludeSchoolIds.toString();
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.form.proxyUserId = this.form.proxyUserId == "" || this.form.proxyUserId == null ? 0 : this.form.proxyUserId;

        this.rq.post("/user/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    /*用户-初始数据*/
    initForm() {
      return {
        userName: null, /*用户名*/
        proxyUserId: null, /*用户主键*/
        deptName: null, /*机构名称*/
        userId: null, /*主键*/
        deptId: null, /*机构ID*/
        name: null, /*姓名*/
        authType: null, /*姓名*/
        password: null, /*密码*/
        loginName: null, /*登录名*/
        remark: null,/*备注*/
        status: 1,/*状态:1启用0禁用*/
        manage: 1,
      };
    },
    /*用户-新增*/
    addDialog() {
      this.title = "新增用户";
      this.dialogMode = "save";
      this.form = this.initForm();
      this.show = true;
      this.posts = [];
    },
    /*用户-修改*/
    editDialog(row) {
      this.title = "修改用户";
      this.dialogMode = "update";
      this.form = { ...row };
      this.posts = row.posts ? row.posts.split(",").map(parseFloat) : [];
      this.show = true;
    },
  },
};
</script>
<style scoped>
::v-deep .el-input__icon {
  color: black !important;
}
</style>
