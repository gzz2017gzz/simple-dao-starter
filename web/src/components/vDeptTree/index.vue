/*组织机构单选,作者:高振中,日期:2020-12-25 01:35:25*/
<template>
  <el-dialog title="机构选择" :visible.sync="show" width="300px" :modal="false">
    <el-tree default-expand-all :data="treeList" :props="{ children: 'children', label: 'name' }" @node-click="(node) => (this.selectNode = node)">
      <span slot-scope="{ node }">{{ node.label }}</span>
    </el-tree>
    <div style="text-align: right">
      <el-button size="mini" @click="show = false">取消</el-button>
      <el-button size="mini" @click="getNodeValue()" type="primary">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  props: ["setValue"],
  data() {
    return { treeList: [], show: false, selectNode: null };
  },
  name: "v-dept-tree",
  methods: {
    showTree() {
      this.show = true;
      this.queryTree();
    },
    getNodeValue() {
      this.setValue(this.selectNode);
      this.show = false;
    },
    queryTree() {
      this.rq.post("/dept/tree", { status: 1 }).then((res) => {
        if (res.code == 200) this.treeList = res.data;
        else this.$message.error(res.msg);
      });
    },
  },
};
</script>
<style scoped lang="scss"></style>