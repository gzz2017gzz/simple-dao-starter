<template>
  <div>
    <el-select :tableName="tableName" :idField="idField" filterable :multiple="isMultiple" :nameField="nameField" :where="where" :order="order" :placeholder="placeholder ? placeholder : ''" :size="size ? size : 'mini'" :value="value" @input="change($event)" v-bind="$attrs" v-on="$listeners" clearable>
      <el-option :value="null" label="--请选择--"></el-option>
      <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id"></el-option>
    </el-select>
  </div>
</template>
<script>
export default {
  name: "v-select-table",
  props: ["placeholder", "size", "value", "dictKey", "addBlank", "tableName", "idField", "nameField", "where", "order","isMultiple"],
  data() { return { options: null }; },
  mounted() {
    this.dataList();
  },
  methods: {
    change(val) { this.$emit("input", val); },
    /**校区-分页列表*/
    dataList() {
      if (!this.tableName) {
        console.error("下拉框的表名必须填写");
        return;
      }

      this.idField = this.idField ? this.idField : "id";
      this.nameField = this.nameField ? this.nameField : "name";
      this.rq.post("/select/list", { tableName: this.tableName, idField: this.idField, nameField: this.nameField, where: this.where, order: this.order }).then(res => {
        if (res.code == 200) {
          this.options = res.data;
        } else this.$message.error(res.msg);
      });
    }
  }
};

</script>