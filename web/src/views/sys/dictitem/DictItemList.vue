/*字典项管理,作者:高振中,日期:2021-02-19 18:15:11*/
<template>
  <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" width="60rem" @close="doReset()">
    <el-form :inline="true" label-suffix=":" @keyup.enter.native="loadData">
      <el-row>
        <el-col :span="8">
          <el-form-item label="字典项文本">
            <el-input placeholder="请输入字典项文本" size="mini" v-model="form.text"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="字典项值">
            <el-input placeholder="请输入字典项值" size="mini" v-model="form.itemValue"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label-width="20px">
            <el-button size="mini" @click="loadData()">查询</el-button>
            <el-button size="mini" @click="doReset()">重置</el-button>
            <el-button size="mini" @click="doDelete(selectIds)" v-if="selectIds.length > 0" type="danger">删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div style="margin-bottom: 10px">
      <el-button size="mini" @click="doAdd(form.dictId)" type="primary">添加</el-button>
    </div>
    <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="text" label="字典项文本" align="center"></el-table-column>
      <el-table-column prop="itemValue" label="字典项值" align="center"></el-table-column>
      <el-table-column prop="orderNum" label="排序编号" align="center"></el-table-column>
      <el-table-column prop="remark" label="备注" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="120">
        <template slot-scope="props">
          <el-button type="text" size="mini" @click="doEdit(props.row)">编辑</el-button><el-divider direction="vertical"></el-divider>
          <el-button type="text" size="mini" @click="doDelete([props.row.id])" style="color: #F56C6C;">删除</el-button>
        </template>
      </el-table-column>
    </v-table>
    <DictItemDialog ref="dialog" :loadData="loadData"></DictItemDialog>
  </el-dialog>
</template>
<script>
import DictItemDialog from "./DictItemDialog";
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  components: { DictItemDialog },
  data() {
    return { title: null, show: false };
  },
  methods: {
    /*字典项列表*/
    showItemList(id) {
      this.title = "字典项列表";
      this.show = true;
      this.form.dictId = id;
      this.loadData();
    },
    /*字典项-新增*/
    doAdd(dictId) {
      this.$refs.dialog.addDialog(dictId);
    },
    /*字典项列表-重置*/
    doReset() {
      this.form = { ...this.initForm(), dictId: this.form.dictId };
      this.loadData();
    },
    /*字典项-查询参数*/
    initForm() {
      return {
        dictId: null, /*字典主键*/
        text: null, /*字典项文本*/
        itemValue: null, /*字典项值*/
        dr: 0,
      };
    },
    /*字典项-列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form };
      this.rq.post("/dictItem/list", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data; /*数据列表*/
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
    /*字典项-删除*/
    doDelete(ids) {
      this.$confirm("你确定要删除吗?", "提示", { confirmButtonText: "确定", cancelButtonText: "取消", type: "warning" }).then(() => {
        this.rq.post("/dictItem/delete", ids).then((res) => {
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
@import "@/styles/index.scss";
</style>