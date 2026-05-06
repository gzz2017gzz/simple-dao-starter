<template>
  <el-select :placeholder="placeholder ? placeholder : ''" :size="size ? size : 'mini'" :value="value" @input="change($event)" v-bind="$attrs" v-on="$listeners">
    <el-option label="--请选择--" value="" v-if="this.addBlank"></el-option>
    <el-option v-for="item in options" :key="item.userId" :label="item.name" :value="item.userId"></el-option>
  </el-select>
</template>
<script>
export default {
  name: "v-user-select",
  props: ["placeholder", "size", "value", "deptId", "addBlank"],
  created() {
    this.rq.post("/user/list", { "deptId": this.deptId }).then((res) => {
      if (res.code == 200) {
        this.options = res.data;
      } else this.$message.error(res.msg);
    });
  },
  data() {
    return { options: null };
  },
  methods: {
    change: function (val) {
      this.$emit("input", val);
    },
  },
};
</script>