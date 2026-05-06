<template>
  <div>
    <div class="table-operator">
      <el-button size="mini" type="primary" @click="handleUserList">添加用户</el-button>
    </div>
    <v-table :data="dataList">
      <el-table-column type="index" width="50" align="center" label="序号"></el-table-column>
      <el-table-column prop="deptName" align="center" label="机构名称"></el-table-column>
      <el-table-column prop="userName" align="center" label="用户名"></el-table-column>
      <el-table-column label="操作" width="60" align="center">
        <template slot-scope="props">
          <el-button v-if="props.row.userRoleId" type="text" size="mini" @click="doDelete([props.row.userRoleId])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <UserListDialog ref="userListDialog" @ok="loadData"></UserListDialog>
  </div>
</template>
<script>
import { pageMix } from "@/common/page";
import UserListDialog from "@/views/sys/role/modules/UserListDialog";
export default {
  mixins: [pageMix],
  components: { UserListDialog },
  props: ["roleId"],
  data() { return {} },
  watch: {
    roleId: {
      handler(val) {
        if (val) this.loadData();
      },
    }
  },
  methods: {
    handleUserList() {
      this.$refs.userListDialog.showUserList(this.roleId);
    },
    loadData() {
      const param = { ...this.form, roleId: this.roleId };
      this.rq.post(`/roleUser/listRoles`, param).then(res => {
        if (res.code === 200) {
          this.dataList = res.data;
        } else {
          this.$message.warning(res.msg);
        }
      })
    },
    /**角色-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/roleUser/delete", ids).then(res => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => { });
    },
    initForm() {
      return {
        roleId: null,/*角色Id*/
      };
    },
  }
}
</script>
<style lang="scss" scoped>
@import "@/styles/minxi";
</style>