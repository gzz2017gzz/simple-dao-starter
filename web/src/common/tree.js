/**不分页列表页混入方法,不要增加任何与业务有关的代码,如果有相关业务代码,请将在当前列表页重写对应方法即可 */
import { dateFormat,dictFormat,multiDictFormat } from "@/common/dicts";
export const treeMix = {
  data() {
    return {
      treeList: [],
      dataList: [],
      loading: false,
      form: { parentId: null, /*所属上级*/ },
    }
  },
  mounted() { this.queryTree(); },
  methods: {
    dateFormat,dictFormat,multiDictFormat,
    doAdd(parentId) { this.$refs.dialog.addDialog(parentId); },
    doEdit(row) { this.$refs.dialog.editDialog(row); },
    // 鼠标进入移除
    mouseenter(data) {
      this.$set(data, 'del', true)
    },
    mouseleave(data) {
      this.$set(data, 'del', false)
    }
  }
}