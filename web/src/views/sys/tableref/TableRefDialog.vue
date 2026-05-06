/*表引用关系新增与修改,  作者:高振中,  日期:2022-10-12 12:10:59*/
<template>
  <el-dialog :title="title" width="50rem" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px" >
      <el-row>
        <el-col :span="11">
          <el-form-item label='主表表名' prop='mainTable'>
            <el-input placeholder='请输入主表表名' v-model='form.mainTable' size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='主表描述' prop='mainInfo'>
            <el-input placeholder='请输入主表描述' v-model='form.mainInfo' size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='子表表名' prop='subTable'>
            <el-input placeholder='请输入子表表名' v-model='form.subTable' size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='子表描述' prop='subInfo'>
            <el-input placeholder='请输入子表描述' v-model='form.subInfo' size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='外键名称' prop='refId'>
            <el-input placeholder='请输入外键名称' v-model='form.refId' size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label='删除标记' prop='delFlag'>
            <el-input placeholder='请输入删除标记' v-model='form.delFlag' size="mini" maxlength="10" show-word-limit />
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
  components: {},
  data() {
    return {
      rules: {
        mainTable: [{ required: true, message: '请输入主表表名', trigger: 'blur' }],
        mainInfo: [{ required: true, message: '请输入主表描述', trigger: 'blur' }],
        subTable: [{ required: true, message: '请输入子表表名', trigger: 'blur' }],
        subInfo: [{ required: true, message: '请输入子表描述', trigger: 'blur' }],
        refId: [{ required: true, message: '请输入外键名称', trigger: 'blur' }],
      }
    }
  },
  /*点开弹窗清空验证信息*/
  updated() { this.titles("表引用关系"); },
  methods: {
    /*表引用关系-保存*/
    save() {
      this.$refs['form'].validate((valid) => {
        if (!valid) return;
        this.rq.post("/tableRef/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    /*表引用关系-初始数据*/
    initForm() {
      return {
        id: null,/*主键*/
        mainTable: null,/*主表表名*/
        mainInfo: null,/*主表描述*/
        subTable: null,/*子表表名*/
        subInfo: null,/*子表描述*/
        refId: null,/*引表外键名称*/
        delFlag: null,/*删除标记字段名*/
      }
    },
  },
}
</script>
<style scoped lang="scss"></style>
