<template>
  <div>
    <el-select :placeholder="placeholder ? placeholder : ''" :size="size ? size : 'mini'" :value="value" @input="change($event)" v-bind="$attrs" v-on="$listeners" clearable>
      <el-option :value="null" label="--请选择--" v-if="this.addBlank == true"></el-option>
      <el-option v-for="item in options" :key="item.itemValue" :label="item.text" :value="item.text"></el-option>
    </el-select>
  </div>
</template>
<script>
import { getDict } from "@/common/dicts";
export default {
  name: "t-select",
  props: ["placeholder", "size", "value", "dictKey", "addBlank"],
  data() { return { options: getDict(this.dictKey) }; },
  methods: {
    change(val) { this.$emit("input", val); }
  },
  watch:{
    dictKey:{
      handler(val){ this.options = getDict(val);},
      deep:true,
      immediate:true
    }
  }
};
</script>