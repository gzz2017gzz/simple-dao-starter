<template>
  <el-button v-if="isDisabled" v-bind="$attrs" v-on="$listeners">
    <slot></slot>
  </el-button>
</template>
<script>
import Vue from "vue";
import { BUTTONS } from "@/common/Constant";
export default {
  name: "v-button",
  props: ["buttonKey", "label"],
  computed: {
    isDisabled() {
      return Vue.ls.get(BUTTONS).some(item => item.component === this.buttonKey);
    }
  },
};
</script>