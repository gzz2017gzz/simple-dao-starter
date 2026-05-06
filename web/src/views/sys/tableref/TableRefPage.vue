/*表引用关系分页列表,作者:高振中,日期:2022-10-12 14:42:25*/
<template>
  <el-card class="table-page-search-wrapper">
    <el-form :inline="true" label-suffix=":" @keyup.enter.native="loadData">
      <el-row :gutter="24">
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="主表表名"><el-input placeholder="请输入主表表名" size="mini" v-model="form.mainTable"></el-input></el-form-item>
        </el-col>
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="主表描述"><el-input placeholder="请输入主表描述" size="mini" v-model="form.mainInfo"></el-input></el-form-item>
        </el-col>
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="子表表名"><el-input placeholder="请输入子表表名" size="mini" v-model="form.subTable"></el-input></el-form-item>
        </el-col>
        <el-col :xl="5" :lg="6" :md="7" :sm="24" style="padding: 7px 0 0 12px">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain >重置</el-button>
          <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger" >删除</el-button>
          <el-button size="mini" @click="() => allShow = !allShow" type="text">{{ allShow ? "▲" : "▼" }}</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="24" v-if="allShow">
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="子表描述"><el-input placeholder="请输入子表描述" size="mini" v-model="form.subInfo"></el-input></el-form-item>
        </el-col>
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="外键名称"><el-input placeholder="请输入外键名称" size="mini" v-model="form.refId"></el-input></el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="table-operator">
      <el-button size="mini" @click="doAdd" type="primary">添加</el-button>
    </div>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="mainTable" label="主表表名"></el-table-column>
      <el-table-column prop="mainInfo" label="主表描述"></el-table-column>
      <el-table-column prop="subTable" label="子表表名"></el-table-column>
      <el-table-column prop="subInfo" label="子表描述"></el-table-column>
      <el-table-column prop="refId" label="外键名称"></el-table-column>
      <el-table-column prop="delFlag" label="删除标记"></el-table-column>
      <el-table-column label="操作" width="120" align="center">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doDelete([props.row.id])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
    <TableRefDialog ref="dialog" :loadData="loadData"></TableRefDialog>
  </el-card>
</template>
<script>
import TableRefDialog from './TableRefDialog';
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  components: { TableRefDialog },
  data() { return {} },
  //computed: {}, mounted(){},
  created() { this.loadData(); },
  methods: {
    /**表引用关系-查询参数*/
    initForm() {
      return {
        mainTable: null,/*主表表名*/
        mainInfo: null,/*主表描述*/
        subTable: null,/*子表表名*/
        subInfo: null,/*子表描述*/
        refId: null,/*外键名称*/
        delFlag: null,/*删除标记*/
      };
    },
    /**表引用关系-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/tableRef/page", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else
          this.$message.error(res.msg);
      });
    },
    /**表引用关系-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/tableRef/delete", ids).then((res) => {
          if (res.code == 200) {
            this.$message.success("删除成功");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      }).catch(() => { });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";
</style>
