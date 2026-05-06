/*对话框底部,作者:高振中,日期:2024-02-19 13:35:25*/
<template>
  <div style="text-align: right">
    <el-button v-if="!isShow" size="mini" @click="save" :disabled="disabled" type="primary">确定</el-button>
    <el-button size="mini" @click="close">取消</el-button>
  </div>
</template>
<script>
export default {
  name: 'v-footer',
  props: ['close', 'save', 'isShow', 'disabled'],
};
</script>
