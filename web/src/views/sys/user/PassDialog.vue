<!--/*用户新增与修改,作者:高振中,日期:2020-12-20 22:36:10*/-->
<template>
  <el-dialog width="25rem" :title="title" :visible.sync="show">
    <el-form :model="form" ref="form" :rules="rules" label-width="auto" label-suffix=":">
      <el-form-item label="密码" prop="password" label-suffix>
        <el-input placeholder="请输入密码" v-model="form.password" size="mini" maxlength="30"  type="password" />
      </el-form-item>
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
      form: {},
      show: false,
      rules: {
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
      },
    };
  },
  /*点开弹窗清空验证信息*/
  updated() {
    this.$refs.form.clearValidate();
  },
  methods: {
    /*用户-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/user/updatePass", this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
          } else this.$message.error(res.msg);
        });
      });
    },
    /*用户-修改*/
    editDialog(userId) {
      this.title = "修改密码";
      this.form = { ...this.form, userId: userId, password: this.form.password }
      this.show = true;
    },
  },
};
</script>
<style lang="scss" scoped>
@import "@/styles/index";
//::v-deep .el-dialog__footer {
//  border-top: 1px solid #e0e0e5;
//}
</style>
