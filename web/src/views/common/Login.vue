<template>
  <div class="loginBg">
    <el-card class="loginCard">
      <h2>SimpleDAO系统</h2>
      <el-form style="margin-top: 3.125rem;" :model="form" ref="form" :rules="rules" @keyup.enter.native="doLogin">
        <el-form-item prop="name">
          <el-input v-model="form.name" placeholder="请输入用户名" suffix-icon="el-icon-user-solid" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" show-password placeholder="请输入密码" type="password" suffix-icon="el-icon-lock" />
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="doLogin">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>

import Vue from "vue";
import { BUTTONS, DICTS, MENUS, TOKEN, USER } from "@/common/Constant";

export default {
  data() {
    return {
      loginType: 1,
      height: null, width: null, activeName: 'first',
      form: { name: undefined, /*用户名*/ password: undefined /*密码*/ },
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      codeShow: false,
      faceShow: true,
      fingerprintShow: true,
      track: null,
      flage: false,
      flagePassword: false,
      inputName: null,///当前选中的输入框(名称),
      qrCodeCleaner: "",
      loginState: null,
    };
  },

  methods: {
    getQueryParam(param) {
      const urlParams = new URLSearchParams(window.location.search);
      return urlParams.get(param);
    },
    showLoginPwd() {
      this.loginType = 1;
    },


    /**登录*/
    doLogin() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/sys/login", this.form).then((res) => {
          if (res.code == 200) {
            this.$message.success("登录成功!");
            Vue.ls.set(USER, res.data.user);
            Vue.ls.set(TOKEN, res.data.token);
            Vue.ls.set(DICTS, res.data.dicts);
            Vue.ls.set(MENUS, res.data.menus);
            Vue.ls.set(BUTTONS, res.data.buttons);
            this.$emit("changeLogin", true);
            if (this.$router.currentRoute.fullPath != "/dashboard") this.$router.push("/dashboard");
          } else this.$message.error(res.msg);
        });
      });
    },


  },
};
</script>
<style scoped lang="scss">
.loginBg {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url("../../assets/login-bg.jpg") no-repeat center right;

  .loginCard {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
    height: auto;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 5%;

    h2 {
      text-align: center;
    }
  }
}
</style>
