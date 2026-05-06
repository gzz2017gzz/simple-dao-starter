/*角色新增与修改,作者:高振中,日期:2021-02-20 11:34:58*/
<template>
  <el-dialog :title="title" width="50rem" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px" label-suffix=":">
      <el-row>
        <el-col :span="11">
          <el-form-item label="角色名称" prop="name">
            <el-input placeholder="请输入角色名称" v-model="form.name" size="mini" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="orderNum">
            <el-input-number placeholder="请输入排序" v-model="form.orderNum" size="mini" :min="0" :max="10000" />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input placeholder="请输入备注" v-model="form.remark" size="mini" show-word-limit type="textarea" />
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
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
      },
    };
  },
  updated() { this.titles("角色"); },
  methods: {
    /*角色-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/role/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.loadData();
          }
          else this.$message.error(res.msg);
        });
      });
    },
    /*角色-初始数据*/
    initForm() {
      return {
        roleId: null, /*主键*/
        name: null, /*角色名称*/
        remark: null, /*备注*/
        orderNum: null, /*排序*/
        createTime: null, /*创建时间*/
      };
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/index";
</style>
