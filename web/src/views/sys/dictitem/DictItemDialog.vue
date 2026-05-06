/*字典项新增与修改,作者:高振中,日期:2021-02-19 17:18:03*/
<template>
  <el-dialog :title="title" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" width="50rem" :modal="false"  @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-suffix=":" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="字典项文本" prop="text">
            <el-input placeholder="请输入字典项文本" v-model="form.text" size="mini" maxlength="50" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="字典项值" prop="value">
            <el-input-number placeholder="请输入字典项值" v-model="form.itemValue" size="mini" :min="0" :max="10000" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="排序编号" prop="orderNum">
            <el-input-number placeholder="请输入排序编号" v-model="form.orderNum" size="mini" :min="0" :max="10000" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input placeholder="请输入备注" v-model="form.remark" size="mini" maxlength="200" show-word-limit type="textarea" />
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
        text: [{ required: true, message: "请输入字典项文本", trigger: "blur" }],
        itemValue: [{ required: true, message: "请输入字典项值", trigger: "blur" }]
      },
    };
  },
  updated() { this.titles("字典项"); },
  methods: {
    /**字典项-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/dictItem/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          } else this.$message.error(res.msg);
        });
      });
    },
    //字典项-初始数据
    initForm() {
      return {
        id: null, /*主键*/
        dictId: null, /*字典主键*/
        text: null, /*字典项文本*/
        itemValue: null, /*字典项值*/
        remark: null, /*备注*/
        orderNum: null /*排序编号*/
      };
    },
    //字典项-新增
    addDialog(dictId) {
      this.title = "新增字典项";
      this.dialogMode = "save";
      this.form = this.initForm();
      this.form.dictId = dictId;
      this.show = true;
    },
  },
};
</script>
<style scoped lang="scss">
</style>
