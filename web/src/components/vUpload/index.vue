/*上传组件,作者:高振中,日期:2020-12-25 01:35:25*/
<template>
  <el-upload action="#" :disabled="disabled" :http-request="doImport" :show-file-list="false" style="display: inline-block;margin-left: 10px;">
    <el-button size="mini" :type="type ? type: 'primary'">{{ label }}</el-button>
  </el-upload>
</template>
<script>
export default {
  name: "v-upload",
  props: ["doImport", "label", "type", "disabled"]
};
</script>
