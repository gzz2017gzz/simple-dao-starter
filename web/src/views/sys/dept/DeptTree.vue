/*组织机构管理,作者:高振中,日期:2020-11-15 21:33:20*/
<template>
  <el-card>
    <div style="border: 1px solid #ebeef5;width:480px;float:left;">
      <el-row style="padding: 10px">
        <el-button type="text" size="mini" @click="queryList({ id: 0, children: 'A' })">节点列表</el-button>
        <el-button type="text" size="mini" @click="doAdd(0)">添加节点</el-button>
      </el-row>
      <el-row>
        <el-tree default-expand-all :data="treeList" :props="{ children: 'children', label: 'name' }">
          <span class="tree-node" slot-scope="{ node, data }" @mouseenter="mouseenter(data)" @mouseleave="mouseleave(data)">
            <span>{{ node.label }}</span>
            <span v-show="data.del">
              <el-button type="text" size="mini" @click.stop="queryList(data)" :disabled="data.leaf == 1">节点列表</el-button>
              <el-button type="text" size="mini" @click.stop="doAdd(data.id)">添加下级</el-button>
              <el-button type="text" size="mini" @click.stop="doEdit(data)">编辑</el-button>
              <el-button type="text" size="mini" @click.stop="doDelete(data)" :disabled="data.leaf == 0" style="color: #F56C6C;">删除</el-button>
            </span>
          </span>
        </el-tree>
      </el-row>
    </div>
    <div style="display:grid;padding-left:12px;">
      <v-table :data="dataList">
        <el-table-column prop="name" label="机构名称"></el-table-column>
        <el-table-column prop="type" label="机构类型" :formatter="(row, col) => dictFormat(row, col, 'deptType')"></el-table-column>
        <el-table-column prop="code" label="机构编码"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="props">
            <el-tag size="mini" type="info" effect="dark" v-if="props.row.status == 0">停用</el-tag>
            <el-tag size="mini" type="success" effect="dark" v-if="props.row.status == 1">启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="排序编号"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作" width="180" align="center" prop="paramName">
          <template slot-scope="props">
            <el-button type="text" size="mini" @click.stop="doAdd(props.row.funcId)">添加下级</el-button><el-divider direction="vertical"></el-divider>
            <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
            <el-button type="text" size="mini" @click="doDelete([props.row.funcId])" style="color: #F56C6C;">删除</el-button>
          </template>
        </el-table-column>
      </v-table>
    </div>
    <DeptDialog ref="dialog" :queryTree="queryTree"></DeptDialog>
  </el-card>
</template>
<script>
import DeptDialog from "./DeptDialog";
import { treeMix } from "@/common/tree";
export default {
  mixins: [treeMix],
  components: { DeptDialog },
  methods: {
    initForm() { },
    /**组织机构-树查询*/
    queryTree() {
      this.rq.post("/dept/tree", {}).then((res) => {
        if (res.code == 200) this.treeList = res.data;
      });
    },
    /**组织机构-列表*/
    queryList(data) {
      this.loading = true;
      if (data.children) {
        this.rq.post("/dept/list", { parentId: data.id }).then((res) => {
          if (res.code == 200) this.dataList = res.data;
          else this.$message.error(res.msg);
        });
      } else this.dataList = [];
      this.loading = false;
    },
    /**组织机构-树删除*/
    doDelete(row) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/dept/delete", row).then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.queryTree();
          } else this.$message.error(res.msg);
        });
      }).catch(() => { });
    },
  },
};
</script>
<style scoped lang="scss">
.tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.el-tree {
  overflow: auto;
  height: calc(100vh - 158px);
}
</style>