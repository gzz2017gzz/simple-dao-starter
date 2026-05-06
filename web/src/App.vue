<template>
  <div id="app">
    <el-container v-if="isLogin" style="height: 100vh">
      <el-aside class="menuHeight" :style="menuHide">
        <Menu v-if="widowsWidth > 1000" :menus="menus" :active-menu="active"></Menu>
        <el-drawer v-else size="200px" title="" :visible.sync="drawer" direction="ltr" :with-header="false">
          <Menu :menus="menus" :active-menu="active"></Menu>
        </el-drawer>
      </el-aside>
      <el-container>
        <el-header style="border-bottom: 1px solid #e5dcdc" :style="{ height: height + 'px' }">
          <Header :width="width" :widowsWidth="widowsWidth" :user="user" @widthChange="widthChange"></Header>
          <menu-tag v-if="widowsWidth > 1000" :widowsWidth="widowsWidth" @activeMenu="handleActiveMenu" />
        </el-header>
        <el-main class="el-main">
          <information></information>
          <div class="content">
            <keep-alive>
              <router-view />
            </keep-alive>
          </div>
        </el-main>
        <el-footer class="el-footer">
          <Footer></Footer>
        </el-footer>
      </el-container>
    </el-container>
    <login v-if="!isLogin" @changeLogin="changeLogin"></login>
  </div>
</template>
<script>
import Vue from "vue";
import Menu from "@/views/common/Menu";
import Header from "@/views/common/Header";
import Footer from "@/views/common/Footer";
import Login from "@/views/common/Login";
import MenuTag from "@/views/common/MenuTag";
import { USER, MENUS } from "@/common/Constant";


// 读取本地版本号
import localVersion from '../public/version.json';

export default {
  components: { MenuTag, Menu, Header, Footer, Login },
  data() {
    return {
      menus: [], active: {}, user: {}, isLogin: Vue.ls.get(USER) != null, roleType: 2, height: 76, width: 200,
      windowHeight: document.body.clientHeight,
      widowsWidth: document.body.clientWidth,
      drawer: true,
      direction: false
    };
  },
  computed: {
    menuHide() { return 'width:' + this.width + 'px;'; },
  },
  mounted() {
    //每隔半小时检测一最新版
    setInterval(() => {
      this.rq.post("/parameter/findByKey?key=version", { "localVersion": localVersion.version }).then((res) => {
        if (res.code === 200) {
          if (res.data !== localVersion.version) {
            window.location.reload();
          } else {
            console.log("当前版本:" + localVersion.version + ",已是最新版本!");
          }
        } else {
          console.log("检测最新版本错误：", res.msg, "localVersion:", localVersion.version);
        }
      }).catch((e) => {
        console.log("检测最新版本异常:", e, "localVersion:", localVersion.version);
      });
    }, 1800000);//1800000=半小时

    this.changeLogin(Vue.ls.get(USER))
    window.onresize = () => {
      this.windowHeight = document.body.clientHeight;
      this.widowsWidth = document.body.clientWidth;
      if (this.widowsWidth < 1000) {
        this.width = 0;
      } else {
        this.width = 200;
      }
    };
  },

  methods: {
    handleActiveMenu(menu) {
      this.activeMenu = menu && menu.meta ? menu : { path: '', meta: {} }
    },
    changeLogin(status) {
      this.isLogin = status;
      if (status) {
        this.menus = Vue.ls.get(MENUS);
        this.user = Vue.ls.get(USER);
        this.roleType = this.user.roleType;
        if (this.roleType == 2 && this.$router.currentRoute.path !== '/dashboard') {
          this.$router.replace("/dashboard");
        }
      }
    },

    widthChange(value) {
      this.width = value;
      if (this.widowsWidth < 1000) {
        this.drawer = true;
        this.width = 0
      } else {
        this.drawer = false;
      }
    },
  },

  watch: {
    widowsWidth: {
      handler(val, oldVal) {
        if (val < 1000) {
          this.width = 0;
          this.height = 50
        } else {
          this.width = 200;
          this.height = 76
        }
      },
      deep: true,
      immediate: true
    }
  }
};
</script>
<style lang="scss">
// 解决 elselect iphone上 要点两次
.el-scrollbar .el-scrollbar__bar {
  opacity: 1 !important;
}

.menuHeight {
  height: calc(100vh);
  transition: all 0.3s ease-in-out;
}

.el-header {
  padding: 0;
}

.el-card__body,
.el-main {
  padding: 10px;
}

.el-footer {
  text-align: center;
  height: 20px !important;
  font-size: small;
  line-height: 20px !important;
}

.el-aside {
  overflow-x: hidden;
  background-color: #334154
}

.el-main {
  background-color: #e9ebee;
  padding: 0px;

  .content {
    border-radius: 5px;
    background: #fff;
  }
}

.el-container {
  margin: 0;
}

body,
html {
  padding: 0;
  margin: 0;
  overflow: hidden;
}

* //滚动条的宽度 */

::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

* //滚动条的滑块 */

::-webkit-scrollbar-thumb {
  background-color: #a1a3a9;
  border-radius: 4px;
}

/* 表格行鼠标经过颜色 */
.el-table__body tr:hover>td {
  background-color: pink !important;
}
</style>
