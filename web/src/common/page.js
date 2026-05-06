/**列表页混入方法,不要增加任何与业务有关的代码,如果有相关业务代码,请将在当前列表页重写对应方法即可 */
import {dateFormat, dateShortFormat, dictFormat, multiDictFormat, showField} from "@/common/dicts";
export const pageMix = {
  data() {
    return {
      total: 0, /*记录个数*/
      page: 1, /*当前页*/
      size: 15, /*页大小*/
      dataList: [], /*数据列表*/
      loading: false, /*是否加载列表*/
      selectIds: [], /*选中的记录主键*/
      form: this.initForm(),/*初始化列表页查询条件参数*/
      allShow: false,/*显示全部条件 */
    }
  },
  // activated() {
  //   this.loadDataInit();
  // },
  methods: {
    /*格式化*/
    dateFormat,dictFormat,multiDictFormat,showField,dateShortFormat,
    /*分页--切换页码,执行查询*/
    pageChange(page) { this.page = page; this.loadData(); },
    /*分页--改变页大小,执行查询*/
    sizeChange(size) { this.size = size; this.page=1; this.loadData();},
    /*清空查询条件,执行查询*/
    doReset() { this.form = this.initForm(); this.loadDataInit(); },
    /*选中记录*/
    selectChange(rows, id) { this.selectIds = rows.map((i) => i[id]); },
    /*调用新增记录组件窗口*/
    doAdd() { this.$refs.dialog.addDialog(); },
    /*调用编辑记录组件窗口*/
    doEdit(row) { this.$refs.dialog.editDialog(row); },
    doExamine(row,isShow = true) { this.$refs.dialog.editDialog(row,isShow); },
    downloadFile(res, fileName) {
      let ele = document.createElement("a");
      let href = window.URL.createObjectURL(new Blob([res], { type: res.type })); //创建下载的链接
      ele.href = href;
      ele.download = fileName; //下载后文件名
      document.body.appendChild(ele);
      ele.click(); //点击下载
      document.body.removeChild(ele); //下载完成移除元素
      window.URL.revokeObjectURL(href); //释放blob对象
    },
     /*点击查询按钮回到第1页*/
    loadDataInit(){ this.page=1; this.size=15; this.loadData(); },
    /**
     * 计算表格列宽度
     * @param {String} prop 字段名
     * @param {Object[]} dataList 数据列表
     * @param {Number?} size 字体大小
     * @param {Boolean?} isDict 是否字典格式化
     * @param {String?} dictCode 字典编码
     * @param {String?} labelName 字段中文名
     * @returns {string}
     * */
    computedWidth(prop,dataList,labelName,isDict = false,dictCode,size= 15){
      if (dataList.length > 0) {
        if (isDict) {
          const value =  this.dictFormat(dataList[0],{property:prop}, dictCode)
          const canvas = document.createElement('canvas');
          const cts = canvas.getContext('2d');
          let maxWidth = 0;
          cts.font = `${size}px Microsoft YaHei`;
          maxWidth = cts.measureText(value).width;
          return maxWidth < 100 ? '90' : String(maxWidth + 10);
        } else {
          const canvas = document.createElement('canvas');
          const cts = canvas.getContext('2d');
          cts.font = `${size}px Microsoft YaHei`;
          let maxWidth = 0;
          let labelNameWidth = labelName ? cts.measureText(labelName).width : 0;
          dataList.map(item => {
            if (cts.measureText(item[prop]).width > maxWidth) {
              maxWidth = cts.measureText(item[prop]).width;
            }
          })
          return labelNameWidth > maxWidth ? labelNameWidth : maxWidth < 90 ? '90' : String(maxWidth + 10);
        }
      }
    },
  }
}