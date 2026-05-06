/*字典新增与修改,作者:高振中,日期:2021-02-19 17:18:03*/
<template>
  <el-dialog width="50rem" :title="title" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-suffix=":" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="字典名称" prop="name">
            <el-input placeholder="请输入字典名称" v-model="form.name" size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="字典编码" prop="code">
            <el-input placeholder="请输入字典编码" v-model="form.code" size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input placeholder="请输入备注" v-model="form.remark" size="mini" maxlength="200" type="textarea" show-word-limit />
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
  data() {
    return {
      rules: {
        name: [{ required: true, message: "请输入字典名称", trigger: "blur" }],
        code: [{ required: true, message: "请输入字典编码", trigger: "blur" }]
      }
    };
  },
  updated() { this.titles("字典类型"); },
  methods: {
    //字典-保存
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/dict/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    //字典-初始数据
    initForm() { return { id: null, /*主键*/ name: null, /*字典名称*/ code: null, /*字典编码*/ remark: null /*备注*/ }; },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/index";
</style>
