<template>
  <div>
    <div class="bg">
      <div class="logo">
        <a v-if="width < 200" @click="$emit('widthChange', isFold)" class="el-icon-s-unfold is-fold"></a>
        <a v-else @click="$emit('widthChange', 0)" class="el-icon-s-fold is-fold"></a>
      </div>
      <div class="banner">
        <img class="header" src="../../assets/head.jpeg" />
        <el-dropdown trigger="click" @command="handleCommand">
          <span class="el-dropdown-link" style="margin-right: 10px;">
            {{ user.name }}
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="setProxyUser" style="color: #409EFF;font-weight: 600" @click="setProxyUser">设置代理人</el-dropdown-item>
            <el-dropdown-item command="updatePass" style="color: #409EFF;font-weight: 600" @click="updatePass">修改密码</el-dropdown-item>
            <el-dropdown-item command="doRefresh" style="color: #409EFF;font-weight: 600" @click="doRefresh">刷新缓存</el-dropdown-item>
            <el-dropdown-item command="doLogOut" style="color: #409EFF;font-weight: 600" @click="doLogOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <PassDialog ref="passDialog"></PassDialog>
    <SetProxyUser ref="setProxyUser"></SetProxyUser>
  </div>
</template>
<script>
import Vue from "vue";
import PassDialog from "../sys/user/PassDialog";
import SetProxyUser from "../sys/user/SetProxyUser";
import { DICTS,USER } from "@/common/Constant";
export default {
  components: { PassDialog, SetProxyUser },
  data() {
    return {
      isFold: 200,
    };
  },
  props: ["user", "width", "widowsWidth"],

  methods: {
    doRefresh() {
      this.rq.post("/dictItem/all", { schoolIds: this.user.schoolIds.split(",") }).then((res) => {
        if (res.code == 200) {
          Vue.ls.set(DICTS, res.data);
          this.$message.success("成功");
        } else this.$message.error(res.msg);
      });
    },
    handleCommand(command) {
      this[command]();
    },
    /** 修改密码 */
    updatePass() {
      this.$refs.passDialog.editDialog(Vue.ls.get(USER).userId);
    },
    setProxyUser() {
      this.$refs.setProxyUser.openDialog(this.$props.user)
    },
    doLogOut() {
      this.$confirm("你确定要退出吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$message.success("退出成功")
        Vue.ls.clear();
        this.$router.push("/dashboard");
        location.reload()
      }).catch(() => { })
    },
  },
  watch: {
    width: {
      handler(val, oldVal) {
        if (val < 600) {
          this.isFold = 200
        } else {
          this.isFold = 0
        }
      },
      deep: true,
      immediate: true
    }
  }
};
</script>
<style scoped lang="scss">
.banner {
  text-align: right;
  font-size: 14px;
  color: #000;
  flex: 10;
  padding: 4px;
  line-height: 30px;
}

.banner span {
  display: inline-block;
  font-size: 1rem;
  color: #797878;
  font-weight: 600;
}

.banner img {
  vertical-align: middle;
}

.logo {
  text-align: left;
  line-height: 50px;
  flex: 2;
  margin-left: 10px;

  .is-fold {
    font-size: 1.2rem;
    cursor: pointer;
  }
}

.bg {
  background: rgb(255, 255, 255);
  text-align: center;
  height: 40px;
  padding: 0;
  margin: 0;
  display: flex;
  border-bottom: 1px solid rgb(230, 230, 230);
}

.header {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}
</style>
