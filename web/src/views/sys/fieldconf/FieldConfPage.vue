/*字段配置分页列表,作者:高振中,日期:2023-02-02 15:59:20*/
<template>
  <el-card class="table-page-search-wrapper">
    <el-form :inline="true" label-suffix=":" @keyup.enter.native="loadData">
      <el-row :gutter="24">
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="用户ID" v-show="condition.userId.show">
            <el-input placeholder="请输入用户ID" size="mini" v-model="form.userId"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="组件名" v-show="condition.component.show">
            <el-input placeholder="请输入组件名" size="mini" v-model="form.component">
            </el-input></el-form-item>
        </el-col>
        <el-col :xl="6" :lg="6" :md="8" :sm="12">
          <el-form-item label="备注" v-show="condition.remark.show">
            <el-input placeholder="请输入备注" size="mini" v-model="form.remark"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xl="5" :lg="6" :md="7" :sm="24" style="padding: 7px 0 0 12px">
          <el-button size="mini" @click="loadData" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
          <v-columns :condition="condition" :column="column" component="FieldConfPage"></v-columns>
        </el-col>
      </el-row>
    </el-form>
    <v-table :data="dataList">
      <el-table-column type="expand" v-if="column.detail.show" width="30px">
        <template slot-scope="props">
          <label class="label">自定义条件</label>{{ props.row.cond }}
          <hr class="hr">
          <label class="label">自定义表格</label>{{ props.row.grid }}
        </template>
      </el-table-column>
      <el-table-column prop="userId" label="用户ID" v-if="column.userId.show"></el-table-column>
      <el-table-column prop="component" label="组件名" v-if="column.component.show"></el-table-column>
      <el-table-column prop="cond" label="自定义条件" show-overflow-tooltip v-if="column.cond.show"></el-table-column>
      <el-table-column prop="grid" label="自定义表格" show-overflow-tooltip v-if="column.grid.show"></el-table-column>
      <el-table-column prop="remark" label="备注" v-if="column.remark.show"></el-table-column>
      <el-table-column label="操作" width="60" align="center" v-if="column.operate.show">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doDelete([props.row.id])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
  </el-card>
</template>
<script>
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  data() {
    return {
      column: {
        detail: { show: true, text: "明细列" },
        userId: { show: true, text: "用户ID" },
        component: { show: true, text: "组件名" },
        cond: { show: true, text: "自定义条件" },
        grid: { show: true, text: "自定义表格" },
        remark: { show: true, text: "备注" },
        operate: { show: true, text: "操作列" },
      },
      condition: {
        userId: { show: true, text: "用户ID" },
        component: { show: true, text: "组件名" },
        remark: { show: true, text: "备注" },
      },
    };
  },
  //computed: {}, mounted(){},
  created() { this.loadData(); },
  methods: {
    /**字段配置-查询参数*/
    initForm() {
      return {
        userId: null,/*用户ID*/
        component: null,/*组件名*/
        cond: null,/*自定义条件*/
        grid: null,/*自定义表格*/
        remark: null,/*备注*/
      };
    },
    /**字段配置-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/fieldconf/page", param).then(res => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /**字段配置-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/fieldconf/delete", ids).then(res => {
          if (res.code == 200) { this.$message.success("删除成功"); this.loadData(); } else this.$message.error(res.msg);
        });
      });
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/minxi";
</style>
