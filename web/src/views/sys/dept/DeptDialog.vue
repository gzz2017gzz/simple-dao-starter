/*组织机构新增与修改,作者:高振中,日期:2020-11-15 21:33:20*/
<template>
  <el-dialog :title="title" width="50rem" :visible.sync="show" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="机构名称" prop="name">
            <el-input placeholder="请输入机构名称" v-model="form.name" size="mini" maxlength="10" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="机构类型" prop="type">
            <v-select v-model="form.type" placeholder="请选择机构类型" dictKey="deptType"></v-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="机构编码" prop="code">
            <el-input-number placeholder="请输入机构编码" v-model="form.code" size="mini" :min="0" :max="99999" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态" prop="status">
            <v-select v-model="form.status" placeholder="请选择机构类型" dictKey="status"></v-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序编号" prop="orderNum">
            <el-input-number placeholder="请输入排序编号" v-model="form.orderNum" size="mini" :min="0" :max="9999" />
          </el-form-item>
        </el-col>
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
  props: ["queryTree"],
  data() {
    return {
      rules: {
        parentId: [{ required: true, message: "请输入上级ID", trigger: "blur" }],
        name: [{ required: true, message: "请输入机构名称", trigger: "blur" }],
        type: [{ required: true, message: "请输入机构类型", trigger: "blur" }],
        code: [{ required: true, message: "请输入机构编码", trigger: "blur" }],
        status: [{ required: true, message: "请输入状态", trigger: "blur" }],
      },
    };
  },
  updated() { this.titles("组织机构"); },
  methods: {
    /**组织机构-新增*/
    addDialog(parentId) {
      this.title = "新增组织机构";
      this.dialogMode = "save";
      this.form = { ...this.initForm(), parentId: parentId };
      this.show = true;
    },
    /**组织机构-保存*/
    save() {
      if (this.form.parentId == 0 && this.form.type == 2) {
        this.$message.error("第一层不能是部门!");
        return
      }
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.rq.post("/dept/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.queryTree();
          } else this.$message.error(res.msg);
        });
      });
    },
    /**组织机构-初始数据*/
    initForm() {
      return {
        id: null, /*ID*/
        parentId: null, /*上级ID*/
        name: null, /*机构名称*/
        type: 1, /*机构类型 1单位 2部门*/
        code: null, /*机构编码*/
        status: 1, /*状态(1启用，0不启用)*/
        orderNum: null, /*排序编号*/
        leaf: 1 /*叶子*/,
        remark: null
      };
    },
  },
};
</script>
<style scoped lang="scss"></style>
