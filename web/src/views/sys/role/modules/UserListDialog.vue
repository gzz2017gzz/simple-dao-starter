<template>
  <el-dialog width="45rem" title="用户列表" :visible.sync="show">
    <el-form :inline="true" label-suffix=":" @keyup.enter.native="loadData">
      <el-form-item size="mini" label="登录名">
        <el-input v-model="form.loginName" placeholder="请输入登录名"></el-input>
      </el-form-item>
      <el-form-item size="mini" label="姓名">
        <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item size="mini">
        <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
        <el-button size="mini" type="primary" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'userId')" :loading="loading">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="loginName" label="登录名" align="center"></el-table-column>
      <el-table-column prop="qwId" label="企业微信ID" align="center"></el-table-column>
      <el-table-column prop="deptName" align="center" label="机构名称"></el-table-column>
      <el-table-column prop="name" align="center" label="用户名"></el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
    <div slot="footer" class="dialog-footer">
      <el-button size="mini" @click="close">取 消</el-button>
      <el-button size="mini" type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  data() {
    return { show: false, roleId: null, }
  },
  methods: {
    showUserList(roleId) {
      this.show = true;
      this.loadData();
      this.roleId = roleId;
    },
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/user/page", param).then((res) => {
        this.dataList = res.data.dataList;
        this.total = res.data.rowCount;
        this.loading = false;
      });
    },
    save() {
      this.rq.post(`/roleUser/save?roleId=${this.roleId}`, this.selectIds).then((res) => {
        if (res.code === 200) {
          this.$message.success("授权成功");
          this.close();
        } else {
          this.$message.warning(res.msg);
        }
      });
    },
    close() {
      this.show = false;
      this.$emit("ok");
    },
    initForm() {
      return {}
    }
  }
}
</script>

<style scoped lang="scss">
@import "@/styles/index";
</style>