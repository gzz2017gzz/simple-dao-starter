/*用户不分页列表,作者:高振中,日期:2021-02-23 15:04:24*/
<template>
  <el-dialog title="关联用户" :visible.sync="show" width="155rem" top="20px">
    <el-form label-width="auto" label-suffix=":" :inline="true">
      <el-form-item label="机构名称">
        <el-input placeholder="请输入机构名称" size="mini" v-model="form.deptName"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input placeholder="请输入用户名" size="mini" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="search" @click="loadData()" title="根据输入的条件查询" size="mini">查询</el-button>
        <el-button @click="doReset()" title="重置" size="mini">重置</el-button>
      </el-form-item>
      <el-row>
        <el-checkbox :indeterminate="unsure" v-model="checkAll" @change="handleCheckAll">全选</el-checkbox>
      </el-row>
      <el-row>
        <el-checkbox-group v-model="checkedIds" @change="handleCheckIds">
          <el-col v-for="user in allUsers" :key="user.userId" style="width:220px;" >
            <el-checkbox :label="user.userId" :key="user.userId">{{ user.deptName + "--" + user.name }}</el-checkbox>
          </el-col>
        </el-checkbox-group>
      </el-row>
    </el-form>
    <v-footer :close="() => show = false" :save="save"></v-footer>
  </el-dialog>
</template>
<script>
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  data() {
    return {
      show: false,
      checkAll: false,
      allUsers: [],
      checkedIds: [],
      unsure: true
    };
  },
  methods: {
    /*查询参数*/
    initForm() {
      return {
        deptName: null, /*机构ID*/
        name: null, /*帐号*/
        roleId: null/*角色ID*/
      };
    },
    /*清空查询参数,执行查询*/
    doReset() {
      this.form = { ...this.initForm(), roleId: this.form.roleId };
      this.loadData();
    },
    /*显示窗口*/
    showUsers(roleId) {
      this.form.roleId = roleId;
      this.doReset();
      this.show = true;
    },
    /*人员列表*/
    loadData() {
      this.rq.post("/roleUser/list", this.form).then((res) => {
        if (res.code == 200) {
          this.allUsers = res.data.userList;
          this.checkedIds = res.data.ids;
          this.handleCheckIds(this.checkedIds);
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    /*授权*/
    save() {
      this.rq.post("/roleUser/save?roleId=" + this.form.roleId, this.checkedIds).then((res) => {
        if (res.code == 200) {
          this.$message.success("授权成功");
          this.show = false;
        } else this.$message.error(res.msg);
      });
    },
    handleCheckAll(val) {
      this.checkedIds = (val ? this.allUsers.map(i => i.userId) : []);
      this.unsure = false;
    },
    handleCheckIds(ids) {
      let checkedCount = ids.length;
      this.checkAll = checkedCount === this.allUsers.length;
      this.unsure = checkedCount > 0 && checkedCount < this.allUsers.length;
    }
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/index";
</style>
