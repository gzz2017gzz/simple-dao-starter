/**弹窗编辑页混入方法,不要增加任何与业务有关的代码,如果有相关业务代码,请将在当前列表页重写对应方法即可 */
import { showField } from "@/common/dicts";
export const dialogMix = {
  data() {
    return {
      title: '',
      form: this.initForm(),
      dialogMode: "save",
      show: false,
      widowsWidth: document.body.clientWidth,
    }
  },
  methods: {
    showField,
    /*修改标题*/
    titles(name) {
      this.title = this.dialogMode == "save" ? "新增" + name : "修改" + name;
    },
    /*清空验证结果*/
    clear() {
      this.$refs.form.clearValidate();
    },
    /*新增*/
    addDialog() {
      this.dialogMode = "save";
      this.form = this.initForm();
      this.show = true;
      this.$nextTick(() => {
        this.clear();
      })
    },
    /*修改*/
    editDialog(row) {
      this.dialogMode = "update";
      this.form = { ...row };
      this.show = true;
    },
    close(){
      this.show = false;
      this.$emit('ok')
      this.$refs.form.clearValidate();
    }
  },
}