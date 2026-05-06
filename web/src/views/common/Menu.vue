<template>
  <div>
    <Logo />
    <el-menu class="el-menu-vertical-demo" background-color="#334154" text-color="#fff" active-text-color="#4569e4" router :default-active="$route.path" >
      <el-menu-item style="background: #334154" index="/dashboard">
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span>首页</span>
        </template>
      </el-menu-item>
      <template v-for="item in menus">
        <el-submenu v-if="item.type == 1" :key="item.funcId" class="first_menu" :index="item.funcId" background-color="#2f384b" text-color="#fff">
          <template slot="title">
            <i :class="item.icon"></i><span>{{ item.name }}</span>
          </template>
          <el-menu-item v-for="menu in item.children" :class="menu.icon" :index="menu.path" :key="menu.funcId">
            <span style="display: inline-block;margin-left: 5px;vertical-align: revert;">{{ menu.name }}</span>
          </el-menu-item>
        </el-submenu>
      </template>
    </el-menu>
  </div>
</template>
<script>
import { router, defaultRoute } from "@/router/index";
import { BUTTONS, DICTS, MENUS, TOKEN, USER } from "@/common/Constant";
import Vue from "vue";
import Logo from "@/views/common/Logo";
import http from "@/common/http";
export default {
  name: "Menu",
  components: { Logo },
  props: ["activeMenu"],
  data() {
    return { menus: [], openeds: ["918671421268295680", "1000010"], };
  },
  mounted() {
    this.refresh();
  },
  methods: {
    refresh() {
      http.post(`/sys/refresh`).then(res => {
        if (res.code == 200) {
          Vue.ls.set(USER, res.data.user);
          Vue.ls.set(TOKEN, res.data.token);
          Vue.ls.set(DICTS, res.data.dicts);
          Vue.ls.set(MENUS, res.data.menus);
          Vue.ls.set(BUTTONS, res.data.buttons);
          this.menus = Vue.ls.get(MENUS);
          this.addRouter();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    addRouter() {
      let routes = [];
      this.menus.map((item) => {
        if (item.path)
          routes.push({
            meta: { name: item.name, funcId: item.funcId },
            path: item.path,
            component: (resolve) => require([`@/views/${item.component}`], resolve)
          });
        if (item.children)
          item.children.map((menu) =>
            routes.push({
              meta: { name: menu.name, funcId: menu.funcId, ...menu.param ? JSON.parse(menu.param) : {} },
              path: menu.path,
              component: (resolve) => require([`@/views/${menu.component}`], resolve)
            }))
      });
      routes.map((i) => {
        if (i.path)
          router.addRoute(i)
      });
      // 添加默认路由.因为默认路由为全捕获,因此放到最后.
      router.addRoute(defaultRoute);
    }
  }
};
</script>
<style lang="scss" scoped>
.el-menu-vertical-demo {
  width: 200px;
  height: calc(100vh - 50px);
}
li.el-submenu .el-menu-item {
  height: 36px !important;
  line-height: 36px !important;
}

::v-deep div.el-submenu__title {
  height: 36px;
  line-height: 36px;
}
</style>