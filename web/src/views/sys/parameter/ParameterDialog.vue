/*系统参数新增与修改, 作者:高振中, 日期:2021-04-11 00:29:26*/
<template>
  <el-dialog :title="title"  width="50rem" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label='组名称' prop='groupName'>
            <el-input placeholder='请输入组名称' v-model='form.groupName' size="mini" maxlength="60" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='参数名称' prop='paramName'>
            <el-input placeholder='请输入参数名称' v-model='form.paramName' size="mini" maxlength="60" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='参数键' prop='paramKey'>
            <el-input placeholder='请输入参数键' v-model='form.paramKey' size="mini" maxlength="60" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='参数值' prop='paramValue'>
            <el-input placeholder='请输入参数值' v-model='form.paramValue' size="mini" maxlength="60" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label='备注' prop='remark'>
            <el-input type="textarea" placeholder='请输入备注' v-model='form.remark' size="mini" maxlength="200" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <v-footer :close="() => show = false" :save="save"></v-footer>
  </el-dialog>
</template>
<script>
import { dialogMix } from "@/common/dialog";
export default {
  mixins: [dialogMix],
  props: ["loadData"],
  updated() { this.titles("系统参数"); },
  data() {
    return {
      rules: {
        paramName: [{ required: true, message: '请输入参数名称', trigger: 'blur' }],
        paramKey: [{ required: true, message: '请输入参数键', trigger: 'blur' }],
        paramValue: [{ required: true, message: '请输入参数值', trigger: 'blur' }],
      }
    }
  },
  methods: {
    /*系统参数-保存*/
    save() {
      this.$refs['form'].validate((valid) => {
        if (!valid) return;
        this.rq.post("/parameter/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    /*系统参数-初始数据*/
    initForm() {
      return {
        id: null,/*主键*/
        groupName: null,/*组名称*/
        paramName: null,/*参数名称*/
        paramKey: null,/*参数键*/
        paramValue: null,/*参数值*/
        remark: null,/*备注*/
      }
    },
  },
}
</script>
<style scoped lang="scss"></style>
