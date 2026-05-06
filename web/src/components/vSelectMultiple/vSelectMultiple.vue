<template>
  <div>
    <el-select multiple :size="size" :value="arrValue" v-bind="$attrs" v-on="$listeners" clearable @change="change">
      <el-option :value="null" label="--请选择--" v-if="addBlank"></el-option>
      <el-option v-for="item in options" :key="item.itemValue" :label="item.text" :value="item.itemValue.toString()"></el-option>
    </el-select>
  </div>
</template>
<script>
import {getDict} from "@/common/dicts";

export default {
  name: "v-select-multiple",
  props: {
    size: {
      default: 'mini'
    },
    dictKey: {
      type: String,
      default: ''
    },
    addBlank: {
      type: Boolean,
      default: false
    },
    value: {
      default: null,
    }
  },
  data() {
    return {
      options: getDict(this.dictKey),
      arrValue: []
    };
  },
  methods:{
    change(value) {
      this.arrValue = value;
    },

    setValue(value) {
      if (Array.isArray(value)) {
        return  value
      } else {
        if (value && value != 0) {
          return value.split(',')
        } else {
          return []
        }
      }
    }
  },
  watch:{
    value:{
      handler(newValue, oldValue) {
        this.arrValue = this.setValue(newValue)
      },
      immediate: true
    }
  }
};
</script>