<template>
  <el-card>
    <div style="border: 1px solid #ebeef5;width:380px;float:left;padding: 10px">
      <el-row>
        <el-button type="text" size="mini" @click="queryList({ funcId: 0, children: 'A' })">节点列表</el-button>
        <el-button type="text" size="mini" @click="doAdd(0)">添加节点</el-button>
      </el-row>
      <el-row>
        <el-tree default-expand-all :data="treeList" :props="{ children: 'children', label: 'name' }">
          <span class="custom-tree-node" slot-scope="{ node, data }" @mouseenter="mouseenter(data)" @mouseleave="mouseleave(data)">
            <span>{{ node.label }}</span>
            <span>
              <el-button type="text" size="mini" @click.stop="queryList(data)" :disabled="data.leaf == 1" v-show="data.del">节点列表</el-button>
              <el-button type="text" size="mini" @click.stop="doAdd(data.funcId)" v-show="data.del">添加下级</el-button>
              <el-button type="text" size="mini" @click.stop="doEdit(data)" v-show="data.del">编辑</el-button>
              <el-button type="text" size="mini" @click.stop="doDelete(data)" v-show="data.del" :disabled="data.leaf == 0" style="color: #F56C6C;">删除</el-button>
            </span>
          </span>
        </el-tree>
      </el-row>
    </div>
    <div style="display:grid;padding-left:12px;">
      <v-table :data="dataList">
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="props">
            <el-tag size="mini" type="info" effect="dark" v-if="props.row.status == 0">停用</el-tag>
            <el-tag size="mini" type="success" effect="dark" v-if="props.row.status == 1">启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路径"></el-table-column>
        <el-table-column prop="icon" label="图标"></el-table-column>
        <el-table-column prop="orders" label="排序"></el-table-column>
        <el-table-column prop="type" label="类型" :formatter="(row, col) => dictFormat(row, col, 'funcType')"></el-table-column>
        <el-table-column prop="component" label="组件"></el-table-column>
        <el-table-column label="操作" width="180" align="center" prop="paramName">
          <template slot-scope="props">
            <el-button type="text" size="mini" @click.stop="doAdd(props.row.funcId)">添加下级</el-button><el-divider direction="vertical"></el-divider>
            <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
            <el-button type="text" size="mini" @click="doDelete([props.row.funcId])" style="color: #F56C6C;">删除</el-button>
          </template>
        </el-table-column>
      </v-table>
    </div>
    <FuncDialog ref="dialog" :queryTree="queryTree"> </FuncDialog>
  </el-card>
</template>
<script>
import FuncDialog from "./FuncDialog";
import { treeMix } from "@/common/tree";
export default {
  mixins: [treeMix],
  components: { FuncDialog },
  methods: {
    initForm() { },
    /*功能菜单-树*/
    queryTree() {
      this.rq.post("/func/tree").then((res) => {
        if (res.code == 200) {
          this.treeList = res.data;
        }
        else this.$message.error(res.msg);
      });
    },
    /*功能菜单-列表*/
    queryList(data) {
      this.loading = true;
      if (data.children) {
        let param = { parentId: data.funcId, dr: 0 };
        this.rq.post("/func/list", param).then((res) => {
          if (res.code == 200)
            this.dataList = res.data;
          else
            this.$message.error(res.msg);
        });
      }
      this.loading = false;
    },
    /*功能菜单-删除*/
    doDelete(row) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/func/delete", row).then((res) => {
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
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 20px;
}

::v-deep .el-tree {
  overflow-y: auto;
  height: calc(100vh - 158px);
}
</style>