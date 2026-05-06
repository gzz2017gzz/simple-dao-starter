/*组织机构树,作者:高振中,日期:2021-02-23 15:04:24*/
<template>
  <el-dialog title="数据权限" :visible.sync="show" width="300px">
    <v-select v-model="roleAuth" placeholder="请选择告警类型" dictKey="roleAuth"></v-select><br>
    <el-tree default-expand-all :data="depts" ref="tree" :props="{ children: 'children', label: 'name' }" show-checkbox node-key="id" v-show="roleAuth == 3">
      <span slot-scope="{ node }">{{ node.label }}</span>
    </el-tree>
    <v-footer :close="() => show = false" :save="save"></v-footer>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return { depts: [], show: false, nodes: null, roleId: null, roleAuth: null };
  },
  methods: {
    /*组织机构树-显示*/
    showDepts(row) {
      this.roleAuth = row.roleAuth
      this.roleId = row.roleId;
      this.show = true;
      this.queryTree(row.roleId);
    },
    /*组织机构-保存授权*/
    save() {
      this.nodes = this.roleAuth === 3 ? this.$refs.tree.getCheckedKeys().map((i) => { return { roleId: this.roleId, deptId: i }; }) : [];
      this.rq.post("/roleDept/save?roleId=" + this.roleId, this.nodes).then((res) => {
        if (res.code != 200) this.$message.error(res.msg);
      });

      this.rq.post("/role/updateAuth", { roleId: this.roleId, roleAuth: this.roleAuth }).then((res) => {
        if (res.code == 200) {
          this.show = false;
          this.$message.success("授权成功");
        } else this.$message.error(res.msg);
        this.$emit('child-event');
      });
    },
    /*组织机构树-请求数据*/
    queryTree(roleId) {
      this.rq.post("/roleDept/tree?roleId=" + roleId).then((res) => {
        if (res.code == 200) {
          this.depts = res.data.depts;
          this.$refs.tree.setCheckedKeys(res.data.ids);
        } else this.$message.error(res.msg);
      });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/index";
::v-deep .el-tree {
  overflow-y: auto;
  height: calc(100vh - 400px);
}

::v-deep .el-dialog__body {
  padding: 10px 20px 20px 20px
}
</style>