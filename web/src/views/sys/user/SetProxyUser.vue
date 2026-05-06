<!--/*用户新增与修改,作者:高振中,日期:2020-12-20 22:36:10*/-->
<template>
  <el-dialog width="25rem" :title="title" :visible.sync="show">
    <el-form :model="form" ref="form" :rules="rules" label-width="auto" label-suffix=":">
      <v-select-table v-model="form.proxyUserId" table-name="sys_user" id-field="user_id" name-field="name" add-blank="true" filterable ></v-select-table>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" @click="show = false">取 消</el-button>
      <el-button size="mini" type="primary" @click="save">确 定</el-button>
  </span>
  </el-dialog>
</template>
<script>

export default {
  data() {
    return {
      title: "",
      form: {
        proxyUserId: null,
        userId: null,
      },
      show: false,
      rules: {
        // password: [{required: true, message: "请输入密码", trigger: "blur"}],
      },
    };
  },
  /*点开弹窗清空验证信息*/
  updated() {
    // this.$refs.form.clearValidate();
  },
  methods: {
    /*用户-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/user/setProxyUser?proxyUserId="+this.form.proxyUserId).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
          } else this.$message.error(res.msg);
        });
      });
    },
    /*代理人设置*/
    openDialog(user) {
      this.rq.post("/user/view?userId=" + user.userId).then((res) => {
        if (res.code == 200) {
          this.form = res.data
        } else this.$message.error(res.msg);
      });
      this.title = "设置代理人";
      this.show = true;
    },
  },
};
</script>
<style lang="scss" scoped>
@import "@/styles/index";
</style>
