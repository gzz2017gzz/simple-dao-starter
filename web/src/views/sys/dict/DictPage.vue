/*字典管理,作者:高振中,日期:2021-02-19 17:18:03*/
<template>
  <el-card>
    <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="80px" class="form-find">
      <el-row>
        <el-form-item label="字典名称">
          <el-input placeholder="请输入字典名称" size="mini" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="字典编码">
          <el-input placeholder="请输入字典编码" size="mini" v-model="form.code"></el-input>
        </el-form-item>
        <el-form-item style="padding-left: 40px;">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
          <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <div class="table-operator">
      <el-button size="mini" type="primary" @click="doAdd">添加</el-button>
      <el-button size="mini" type="primary" @click="doRefresh">刷新客户端缓存</el-button>
      <el-button size="mini" type="primary" @click="doServerRefresh">刷新服务器缓存</el-button>
    </div>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading" height="calc(100vh - 260px)">
      <el-table-column type="selection" align="center"></el-table-column>
      <el-table-column prop="name" label="字典名称"></el-table-column>
      <el-table-column prop="code" label="字典编码" width="200"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createBy" label="创建人" :formatter="(row, col) => dictFormat(row, col, 'persons')"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="140" align="center" :formatter="dateFormat"></el-table-column>
      <el-table-column prop="updateBy" label="修改人" align="center" :formatter="(row, col) => dictFormat(row, col, 'persons')"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" width="140" :formatter="dateFormat"></el-table-column>
      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="queryItemList(props.row)">字典项</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doDelete([props.row.id])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
    <DictDialog ref="dialog" :loadData="loadData"></DictDialog>
    <DictItemList ref="itemList"></DictItemList>
  </el-card>
</template>
<script>
import DictDialog from "./DictDialog";
import DictItemList from "../dictitem/DictItemList";
import { pageMix } from "@/common/page";
import Vue from "vue";
import { DICTS } from "@/common/Constant";

export default {
  mixins: [pageMix],
  components: { DictDialog, DictItemList },
  created() {
    this.loadData();
  },
  data() {
    return {};
  },
  methods: {
    /**刷新localStore字典数据*/
    doRefresh() {
      this.rq.post("/dictItem/all", {}).then((res) => {
        if (res.code == 200) {
          Vue.ls.set(DICTS, res.data);
          this.$message.success("成功");
        } else this.$message.error(res.msg);
      });
    },
    /**刷新localStore字典数据*/
    doServerRefresh() {
      this.rq.post("/dict/refresh", {}).then((res) => {
        if (res.code == 200) {
          this.$message.success(res.data + "成功，多刷几次，毕竟多台服务器呢");
        } else this.$message.error(res.msg);
      });
    },
    /**字典项-列表*/
    queryItemList(row) {
      this.$refs.itemList.showItemList(row.id);
    },
    /**字典-查询参数*/
    initForm() {
      return {
        name: null, /*字典名称*/
        code: null, /*字典编码*/
        remark: null, /*备注*/
        dr: 0,
      };
    },
    /**字典-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/dict/page", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /**字典-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/dict/delete", ids).then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => {
      });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";
</style>
