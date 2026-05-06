/* 自定义表格列,作者:高振中,日期:2020-12-25 01:35:25 */
<template>
  <div style="display: inline-block;">
    <el-popover trigger="hover" placement="top">
      <el-button slot="reference" size="mini" type="text" style="margin-left: 10px;">条件选择</el-button>
      <el-checkbox v-model="condShow" :indeterminate="condCheckAll" @change="(val) => conditionAll(val)" size="mini">==全部==</el-checkbox>
      <el-row v-for="(val, key) in condition" :key="key">
        <el-checkbox v-model="val.show" @change="conditions" size="mini">{{ val.text }}</el-checkbox>
      </el-row>
      <el-button size="mini" @click="save()" type="primary">保存</el-button>
    </el-popover>
    <el-popover trigger="hover" placement="top" style="margin: 0 10px;">
      <el-button slot="reference" size="mini" type="text">列选择</el-button>
      <el-checkbox v-model="colShow" :indeterminate="columnCheckAll" @change="(val) => columnAll(val)" size="mini">全部==</el-checkbox>
      <el-row v-for="(val, key) in column" :key="key">
        <el-checkbox v-model="val.show" @change="columns" size="mini">{{ val.text }}</el-checkbox>
      </el-row>
      <el-button size="mini" @click="save()" type="primary">保存</el-button>
    </el-popover>
  </div>
</template>
<script>
import Vue from "vue";
import { USER } from "@/common/Constant";
export default {
  name: 'v-columns',
  props: ['condition', 'column', 'component'],
  data() { return { condShow: true, colShow: true, condCheckAll: false, columnCheckAll: false, user: Vue.ls.get(USER) }; },
  mounted() { this.loadData(); },
  methods: {
    save() {
      let conds = Object.keys(this.condition).filter(i => this.condition[i].show == true);
      let grids = Object.keys(this.column).filter(i => this.column[i].show == true);
      this.rq.post("/fieldconf/save", { userId: this.user.userId, conds: conds, grids: grids, component: this.component }).then(res => {
        if (res.code == 200) {
          this.$message.success("配置保存成功!");
        } else this.$message.error(res.msg);
      });
    },
    /**从后端加载字段配置*/
    loadData() {
      this.rq.post("/fieldconf/list", { userId: this.user.userId, componentEq: this.component }).then(res => {
        if (res.code == 200) {
          Object.keys(this.condition).map(i => this.condition[i].show = (res.data.conds.indexOf(i) > -1));
          Object.keys(this.column).map(i => this.column[i].show = (res.data.grids.indexOf(i) > -1));
        } else this.$message.error(res.msg);
      });
    },
    conditionAll(val) {
      Object.keys(this.condition).map(i => this.condition[i].show = val);
      this.conditions();
    },
    columnAll(val) {
      Object.keys(this.column).map(i => this.column[i].show = val);
      this.columns();
    },
    conditions() {
      let props = Object.values(this.condition);
      let count = props.filter(i => i.show).length;
      this.condShow = props.length === count;
      this.condCheckAll = count > 0 && count < props.length;
    },
    columns() {
      let props = Object.values(this.column);
      let count = props.filter(i => i.show).length;
      this.colShow = props.length === count;
      this.columnCheckAll = count > 0 && count < props.length;
    }
  },
};
</script>
