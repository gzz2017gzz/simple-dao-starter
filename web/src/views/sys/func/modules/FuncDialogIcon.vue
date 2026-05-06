<template>
  <el-dialog title="图标选择" :visible.sync="visible" width="50rem"  append-to-body>
    <span v-for="item in iconFontList" @click="handleClick(item)">
      <i :key="item" :class="item"></i>
    </span>
  </el-dialog>
</template>

<script>
import { iconFontList } from "@/store/icon";
export default {
  data(){
    return {
      visible:false,
      iconFontList
    }
  },
  methods:{
    addDialog(){
      this.visible = true;
    },
    handleClick(item){
      this.$emit('ok',item)
      this.visible = false;
    }
  }
}
</script>

<style lang="scss" scoped>
span {
  width: 20px;
  height: 20px;
  border: 1px solid #a1a3a9;
  display: inline-block;
  margin: 5px;
  text-align: center;
  line-height: 20px;
  cursor: pointer;
}
</style>