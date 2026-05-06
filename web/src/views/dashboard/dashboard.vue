<template>
  <div style="background: #e9ebee">
    <el-card shadow="always">
      <div class="user">
        <div style="line-height: 60px;display: flex">
          <div style="width: 60px;height: 60px;margin-right: 20px"><img style="width:100%;height:100%;border-radius: 50%" class="header" src="../../assets/head.jpeg" /></div>
          <div>您好，{{user.name}}</div>
        </div>
      </div>
    </el-card>

    <el-card shadow="always" style="margin-top: 50px;width: 50%">
      <div style="font-weight: 800">快捷入口</div>
      <div>
        <router-link to="/base/classenroll/ClassEnrollPage">
          <v-button buttonKey="ClassEnrollPage" type="primary" plain style="margin-right: 20px;margin-top: 20px">
            <div style="font-size: 30px" class="el-icon-s-cooperation"></div>
            <div style="margin-top: 10px">入学班型</div>
          </v-button>
        </router-link>
<!--        base/plate/PlatePage-->

        <router-link to="base/notebook/NoteBookPage">
          <v-button buttonKey="ClassEnrollPage" type="primary" plain style="margin-right: 20px;margin-top: 20px">
            <div style="font-size: 30px" class="el-icon-s-grid"></div>
            <div style="margin-top: 10px">需求管理</div>
          </v-button>
        </router-link>

      </div>
    </el-card>
  </div>
</template>

<style>
body {
  margin: 0;
}
</style>

<script>
import Vue from "vue"
import {USER} from "@/common/Constant";

export default {
  data() {
    return {

    };
  },
  computed: {
    user() {
      return Vue.ls.get(USER);
    }
  }
};
</script>

<style lang="scss" scoped>
.user {
  display: flex;
}
</style>