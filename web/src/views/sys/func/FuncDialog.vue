/*功能新增与修改,作者:高振中,日期:2020-11-15 00:25:11*/
<template>
  <el-dialog :title="title" :visible.sync="show" width="50rem" :close-on-click-modal="false" :close-on-press-escape="false" @close="clear">
    <el-form :model="form" ref="form" :rules="rules" label-width="120px" label-suffix=":">
      <el-row>
        <el-col :span="11">
          <el-form-item label="类型" prop="type">
            <v-select v-model="form.type" placeholder="请选择类型" dictKey="funcType"></v-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input placeholder="请输入名称" v-model="form.name" size="mini" show-word-limit />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="图标" prop="icon">
            <el-input placeholder="请输入图标" v-model="form.icon" show-word-limit size="mini">
              <el-button @click="doAdd" slot="append" icon="el-icon-search" size="mini"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="组件路径" prop="component">
            <el-input style="margin-top: 5px" placeholder="请输入组件路径" v-model="form.component" size="mini"> <template slot="prepend">view /</template> </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="路由参数" prop="param">
            <el-input style="margin-top: 5px" placeholder="请输入路由参数" v-model="form.param" size="mini" type="textarea"> </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态" prop="status">
            <v-select v-model="form.status" placeholder="请选择状态" dictKey="status"></v-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="orders">
            <el-input-number placeholder="请输入排序" v-model="form.orders" size="mini" :min="1" :max="10000.00" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <v-footer :close="() => show = false" :save="save"></v-footer>
    <FuncDialogIcon ref="FuncDialogIcon" @ok="initIcon"></FuncDialogIcon>
  </el-dialog>
</template>
<script>
import { dialogMix } from "@/common/dialog";
import FuncDialogIcon from "./modules/FuncDialogIcon";
export default {
  mixins: [dialogMix],
  props: ["queryTree"],
  components: { FuncDialogIcon },
  data() {
    return {
      rules: {
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        path: [{ required: true, message: "请输入路径", trigger: "blur" }],
        icon: [{ required: false, message: "请输入图标", trigger: "blur" }]
      },
    };
  },
  updated() { this.titles("功能菜单"); },
  methods: {

    initIcon(icon) { this.form.icon = icon; },

    doAdd() { this.$refs.FuncDialogIcon.addDialog(); },
    /*功能菜单-新增*/
    addDialog(parentId) {
      this.title = "新增功能菜单";
      this.dialogMode = "save";
      this.form = { ...this.initForm(), parentId: parentId };
      this.show = true;
    },
    /*功能菜单-保存*/
    save() {
      this.$refs.form.validate((valid) => {
        if (!valid) return;
        this.form.path = this.form.component ? "/" + this.form.component : null;
        this.rq.post("/func/" + this.dialogMode, this.form).then((res) => {
          if (res.code == 200) {
            this.show = false;
            this.$message.success(this.title + "成功!");
            this.queryTree();
          } else this.$message.error(res.msg);
        });
      });
    },
    /*功能菜单-初始数据*/
    initForm() {
      return {
        funcId: null /*主键*/,
        parentId: null /*所属上级*/,
        name: null /*名称*/,
        type: 1 /*类型(1:菜单,2:按钮)*/,
        permissionValue: null /*权限值*/,
        path: null /*路径*/,
        icon: null /*图标*/,
        status: 1 /*状态(0:禁止,1:正常)*/,
        orders: null /*排序*/,
        component: null /*文件路径*/,
        param: null /*参数*/,
        hasChild: 0 /*是否有下级*/,
        leaf: 1 /*叶子*/,
      };
    },
  },
};
</script>
<style scoped lang="scss">
@import "@/styles/index";
</style>
