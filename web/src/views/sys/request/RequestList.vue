/*请求时长统计不分页列表,作者:高振中,日期:2021-02-20 14:02:55*/
<template>
  <el-card>
    <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="100px">
      <el-row>
        <el-form-item label="请求路径">
          <el-input placeholder="请输入请求路径" size="mini" v-model="form.path"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <v-select v-model="form.type" placeholder="请选择请求状态" dictKey="requestType" :addBlank="true" />
        </el-form-item>
        <el-form-item style="padding-left: 40px;">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
          <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading" height="calc(100vh - 166px)">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="appName" label="应用名" width="60"></el-table-column>
      <el-table-column prop="path" label="请求路径" width="300" sortable></el-table-column>
      <el-table-column prop="sumCount" label="请求次数" sortable align="right"></el-table-column>
      <el-table-column prop="sumMilliSecond" label="总毫秒" align="right" sortable></el-table-column>
      <el-table-column prop="avgMilliSecond" label="平均毫秒" sortable :formatter="moneyFormat" align="right"></el-table-column>
      <el-table-column prop="maxMilliSecond" label="最大毫秒" sortable align="right"></el-table-column>
      <el-table-column prop="minMilliSecond" label="最小毫秒" sortable align="right"></el-table-column>
      <el-table-column prop="type" label="请求类型" sortable :formatter="(row, col) => dictFormat(row, col, 'requestType')"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center" width="60">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doDelete([props.row.path])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
  </el-card>
</template>
<script>
import { pageMix } from "@/common/page";
import { moneyFormat } from "@/common/system";
export default {
  mixins: [pageMix],
  created() {
    this.loadData();
  },
  methods: {
    moneyFormat,
    /*选中记录*/
    selectChange(rows) {
      this.selectIds = rows.map((item) => item.path);
    },
    /*请求统计-查询参数*/
    initForm() {
      return { path: null, /*应用名*/ type: null /*1后台2前端*/ };
    },
    /*请求统计-列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form };
      this.rq.post("/request/list", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /*请求统计-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/request/delete", ids).then((res) => {
          if (res.code == 200) {
            this.$message.success(res.msg);
            this.loadData();
          } else this.$message.success(res.msg);
        });
      }).catch(() => { });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";
</style>
