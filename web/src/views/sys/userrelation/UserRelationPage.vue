/*用户关系分页列表,作者:高振中,日期:2024-08-15 11:12:18*/
<template>
  <el-card>
    <el-form label-suffix=":" @keyup.enter.native="loadData" :inline="true" label-width="100px">
      <el-row>
        <el-form-item label="上级用户">
          <el-input size="mini" placeholder="请输入上级用户" v-model="form.leaderName" />
        </el-form-item>
        <el-form-item label="用户">
          <el-input size="mini" placeholder="请输入用户" v-model="form.userName" />
        </el-form-item>
        <el-form-item style="padding-left: 40px;">
          <el-button size="mini" @click="loadDataInit" type="primary" plain>查询</el-button>
          <el-button size="mini" @click="doReset" type="primary" plain>重置</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <div class="table-operator">
      <el-button size="mini" @click="doAdd" type="primary">添加</el-button>
    </div>
    <v-table :data="dataList" :loading="loading" height="calc(100vh - 260px)">
      <el-table-column prop="leaderName" label="上级用户"></el-table-column>
      <el-table-column prop="userName" label="用户"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" width="150" fixed="right" align="center">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doDelete([props.row.userRelationId])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
    <UserRelationDialog ref="dialog" :loadData="loadData"></UserRelationDialog>
  </el-card>
</template>
<script>
import UserRelationDialog from './UserRelationDialog';
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  components: { UserRelationDialog },
  data() { return {}; },
  created() { this.loadData(); },
  methods: {
    /**用户关系-查询参数*/
    initForm() {
      return {
        leaderName: null,/*上级用户名*/
        userName: null,/*用户名*/
        dr: 0,/*删除标记*/
      };
    },
    /**用户关系-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/userRelation/page", param).then(res => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /**用户关系-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/userRelation/delete", ids).then(res => {
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
