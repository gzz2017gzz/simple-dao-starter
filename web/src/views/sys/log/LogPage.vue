/*系统日志分页列表,作者:高振中,日期:2021-03-12 12:57:38*/
<template>
  <el-card>
    <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="100px">
      <el-row>
        <el-form-item label="日志标题">
          <el-input placeholder="请输入日志标题" size="mini" v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input placeholder="请输入用户名" size="mini" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="java类.方法">
          <el-input placeholder="请输入java类.方法" size="mini" v-model="form.method"></el-input>
        </el-form-item>
        <el-form-item label="请求路径">
          <el-input placeholder="请输入请求路径" size="mini" v-model="form.url"></el-input>
        </el-form-item>
        <el-form-item label="请求参数">
          <el-input placeholder="请输入参数" size="mini" v-model="form.params"></el-input>
        </el-form-item>
        <el-form-item label="响应结果">
          <el-input placeholder="请输响应结果" size="mini" v-model="form.result"></el-input>
        </el-form-item>
 
        <el-form-item prop="startDate" label="开始日期">
          <el-date-picker size="mini" placeholder="请选开始日期" v-model="form.startTime" type="datetime" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 180px !important;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="endDate" label="结束日期">
          <el-date-picker size="mini" placeholder="请选结束日期" v-model="form.endTime" type="datetime" value-format="yyyy-MM-ddTHH:mm:ss" style="width: 180px !important;"></el-date-picker>
        </el-form-item>
        <el-form-item style="padding-left: 40px;">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
          <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading" height="calc(100vh - 260px)">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <label class="label">请求参数</label> {{ props.row.params }}
          <hr class="hr">
          <label class="label">反回结果</label>{{ props.row.result }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="日志标题"></el-table-column>
      <el-table-column prop="userName" label="用户名"></el-table-column>

      <el-table-column prop="method" label="java类.方法"></el-table-column>
      <el-table-column prop="url" label="请求路径"></el-table-column>

      <el-table-column prop="operateTime" label="请求时间" :formatter="dateFormat"></el-table-column>
      <!-- <el-table-column label="操作" align="center" width="60">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doDelete([props.row.id])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column> -->
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
  </el-card>
</template>
<script>
import { pageMix } from "@/common/page";
import { dateFormat } from "@/common/dicts";
export default {
  mixins: [pageMix],
  data() {
    return {};
  },
  created() {
    this.loadData();
  },
  methods: {
    dateFormat,
    /*日志-查询参数*/
    initForm() {
      return {
        title: null, /*日志标题*/
        userId: null, /*用户主键*/
        userName: null, /*用户名*/
        method: null, /*java类.方法*/
        url: null, /*请求路径*/
        params: null,/*请求参数*/
        startTime: null,
        endTime: null,
        result: null,

      };
    },
    /*日志-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/log/page", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /*日志-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/log/delete", ids).then((res) => {
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

::v-deep {
  .el-select {
    width: 180px;
  }

  .el-input--mini {
    width: 180px;
  }
}
</style>
