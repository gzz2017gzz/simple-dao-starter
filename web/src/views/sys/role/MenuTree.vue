/*功能菜单树,作者:高振中,日期:2021-02-23 15:04:24*/
<template>
  <el-dialog title="功能权限" :visible.sync="show" width="25rem">
    <el-tree default-expand-all :data="menus" ref="tree" :props="{ children: 'children', label: 'name' }" show-checkbox node-key="funcId"> <span slot-scope="{ node }">{{ node.label }}</span> </el-tree>
    <v-footer :close="() => show = false" :save="save"></v-footer>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      menus: [],
      show: false,
      nodes: null,
      roleId: null,
    };
  },
  methods: {
    /*功能菜单树-显示*/
    showFuncs(roleId) {
      this.roleId = roleId;
      this.show = true;
      this.queryTree(roleId);
    },
    /*功能菜单-保存授权*/
    save() {
      this.nodes = this.$refs.tree.getCheckedNodes(false, true).map((i) => { return { roleId: this.roleId, funcId: i.funcId } });
      this.rq.post("/roleFunc/save?roleId=" + this.roleId, this.nodes).then((res) => {
        if (res.code == 200) {
          this.show = false;
          this.$message.success("授权成功");
        } else this.$message.error(res.msg);
      });
    },
    /*功能菜单树-请求数据*/
    queryTree(roleId) {
      this.rq.post("/roleFunc/tree?roleId=" + roleId).then((res) => {
        if (res.code == 200) {
          this.menus = res.data.funcs;
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
</style>