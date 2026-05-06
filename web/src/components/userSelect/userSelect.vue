<template>
  <div>
    <el-select :multiple="multiple" size="mini" v-bind="$attrs" :value="value" @input="input" @change="handleChange" filterable clearable>
      <el-option :value="null" label="--请选择--" v-if="addBlank === true"></el-option>
      <el-option v-for="item in options" :key="item.userId" :label="item.name" :value="item.userId"></el-option>
    </el-select>
  </div>
</template>

<script>
import http from "@/common/http";

export default {
  name: "userSelect",
  data() {
    return {
      options:[]
    }
  },
  props:{
    addBlank:{
      type:Boolean,
      default:false
    },
    multiple:{
      type:Boolean,
      default:false
    },
    value:{
      type:[String, Array],
      default:undefined,
    },
    schoolId:{
      type:String ,
      default:undefined,
      required:false
    },
    post:{
      type:String,
      default:undefined,
      required:false
    },
    userIdIn:{
      type: String,
    }
  },
  methods:{
    user(){
      if (!this.post) return
      const data = {
        schoolId:this.schoolId,
        dr:0,
        post:this.post,
        userIdIn:this.userIdIn ? this.userIdIn.split(",") : undefined
      }
      http.post(`/user/list`, data).then(res => {
        if (res.code === 200) {
          this.options = res.data;
          this.handleChange(this.value);
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    input(val){
      this.$emit("input", val);
    },
    handleChange(val){
      this.options.forEach(item=>{
        if (item.userId === val) {
          this.$emit("getManage", item.manage);
        }
      })
      this.$forceUpdate();
    }
  },
  watch:{
    schoolId:{
      immediate:true,
      deep:true,
      handler(value){
        this.user();
      }
    },
    value:{
      immediate:true,
      deep:true,
      handler(value){
        if (value) {
          this.user();
        }
      }
    },
    userIdIn:{
      immediate:true,
      deep:true,
      handler(value){
        if (value) {
          this.user();
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>

</style>
