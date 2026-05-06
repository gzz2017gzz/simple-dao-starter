/*用户关系新增与修改, 作者:高振中, 日期:2024-08-15 11:12:18*/
<template>
  <el-dialog :title="title" width="800px" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear" append-to-body>
    <el-form :model="form" ref="form" :rules="rules" label-width="120px" label-suffix=":">
      <el-row>
        <el-col :span="11">
          <el-form-item prop="leaderName" label="上级用户">
            <el-input v-model="form.leaderName" placeholder="请选择上级用户" size="mini">
              <el-button @click="showLeader" slot="append" icon="el-icon-search" size="mini"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="userName" label="用户">
            <el-input v-model="form.userName" placeholder="请选择用户" size="mini">
              <el-button @click="showUser" slot="append" icon="el-icon-search" size="mini"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item prop="remark" label="备注">
            <el-input size="mini" placeholder="请输入备注" v-model="form.remark" maxlength="100" show-word-limit type="textarea" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <v-footer :close="() => show = false" :save="save"></v-footer>
    <v-user-page ref="leaderPage" :setValue="setLeader"></v-user-page>
    <v-user-page ref="userPage" :setValue="setUser"></v-user-page>
  </el-dialog>
</template>
<script>
import { dialogMix } from "@/common/dialog";
export default {
  mixins: [dialogMix],
  props: ["loadData"],
  components: {},
  updated() { this.titles("用户关系"); },
  data() {
    return {
      rules: {
        leaderName: [{ required: true, message: '请选择上级用户', trigger: 'blur' }],
        userName: [{ required: true, message: '请选择用户', trigger: 'blur' }],
      }
    }
  },
  methods: {
    /*返回上级用户信息*/
    setLeader(user) { this.form = { ...this.form, leaderName: user.name, leaderId: user.userId }; },
    /*上级用户选择*/
    showLeader() { this.$refs.leaderPage.showUserPage(2); },
    /*返回用户信息*/
    setUser(user) { this.form = { ...this.form, userName: user.name, userId: user.userId }; },
    /*用户选择*/
    showUser() { this.$refs.userPage.showUserPage(2); },
    /*用户关系-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/userRelation/" + this.dialogMode, this.form).then(res => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    /*用户关系-初始数据*/
    initForm() {
      return {
        leaderId: null,/*leaderId*/
        userId: null,/*user_id*/
        remark: null,/*备注*/
      }
    },
  },
}
</script>
<style scoped lang="scss">
@import "@/styles/index";
</style>
