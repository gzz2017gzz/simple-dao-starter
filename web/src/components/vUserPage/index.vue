/*用户管理,作者:高振中,日期:2020-12-20 22:36:10*/
<template>
  <el-dialog title="用户选择" :visible.sync="show" width="60rem" :modal="false">
    <el-form :inline="true" @keyup.enter.native="loadData">
      <el-form-item label="机构"> <el-input placeholder="请输入机构" size="mini" v-model="form.deptName"></el-input>
      </el-form-item>
      <el-form-item label="姓名"> <el-input placeholder="请输入姓名" size="mini" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="mini" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <v-table :data="dataList" @selection-change="(rows) => this.selectRow = rows">
      <el-table-column type="selection" width="40" v-if="type == 1"></el-table-column>
      <el-table-column width="50px" label="选择" v-if="type == 2">
        <template slot-scope="scope">
          <el-radio-group v-model="radioVal" :key="radioKey">
            <el-radio :label="scope.row.userId" v-model="radio" @change.native.prevent="changeRadio(scope.row)">{{ "" }}</el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column prop="deptName" label="机构名称"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="props">
          <el-tag size="mini" type="success" effect="dark" v-if="props.row.status === 1">{{ dictFormatVal("userStatus", props.row.status) }}</el-tag>
          <el-tag size="mini" type="info" effect="dark" v-else>{{ dictFormatVal("userStatus", props.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="posts" label="岗位" show-overflow-tooltip :formatter="(row, col) => multiDictFormat(row, col, 'posts')"></el-table-column>
      <el-table-column prop="schoolIds" label="校区" show-overflow-tooltip :formatter="(row, col) => multiDictFormat(row, col, 'schools')"></el-table-column>
    </v-table>
    <div style="text-align:right;padding:15px;">
      <el-button size="mini" @click="show = false">取消</el-button>
      <el-button size="mini" @click="getSelectValue()" type="primary">确定</el-button>
    </div>
    <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
  </el-dialog>
</template>
<script>
import { pageMix } from "@/common/page";
import { dictFormatVal } from "../../common/dicts";
export default {
  mixins: [pageMix],
  props: ["setValue"],
  data() {
    return { dataList: [], show: false, selectRow: null, radio: "", type: null, radioKey: false, radioVal: '' };
  },
  name: "v-user-page",
  methods: {
    dictFormatVal,
    /*用户-查询参数*/
    initForm() {
      return {
        deptName: null, /*机构ID*/
        name: null, /*姓名*/
        gender: null, /*性别*/
        status: 1 /*状态*/
      };
    },
    showUserPage(type) {
      this.form.name = null;
      this.form.deptName = null;
      this.page = 1;
      this.show = true;
      this.type = type;
      this.loadData();
    },
    queryPage() {
      this.radioVal = null;
      this.rq.post("/user/page", { ...this.form, page: this.page, size: this.size }).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount
        } else this.$message.error(res.msg);
      });
    },
    getSelectValue() {
      if (this.selectRow) {
        this.setValue(this.selectRow);
        this.show = false;
      } else {
        this.$message.error("请选择用户!")
      }
      this.selectRow = null;
    },
    /*用户-请求列表*/
    loadData() {
      this.queryPage();
    },
    changeRadio(row) {
      this.selectRow = row;
    },
  },
};
</script>
<style scoped lang="scss"></style>