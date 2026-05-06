<template>
  <div>
    <el-select filterable :multiple="multiple" :disabled="disabled" :placeholder="placeholder ? placeholder : ''" :size="size ? size : 'mini'" :value="value" @input="change($event)" v-bind="$attrs" v-on="$listeners" clearable>
      <el-option :value="null" label="--请选择--" v-if="this.addBlank == true"></el-option>
      <el-option v-for="item in fileListOption" :key="item.itemValue" :label="item.text" :value="item.itemValue"></el-option>
    </el-select>
  </div>
</template>
<script>
import { getDict } from "@/common/dicts";
export default {
  name: "v-select",
  props: ["placeholder","multiple", "size", "value", "dictKey", "addBlank","filterList", "disabled"],
  data() { return { options: getDict(this.dictKey) }; },
  computed:{
    fileListOption(){
      if (this.filterList) {
        return this.options.filter(item => this.filterList.includes(item.itemValue))
      } else {
        return this.options
      }
    }
  },
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
