<template>
  <el-table ref="table" :max-height="maxHeight" v-bind="$attrs" v-on="$listeners" v-loading="loading ? loading : false" element-loading-text="加载中..." border size="mini" 
    :row-style="rowClass ? rowClass : rowStyle" :cell-style="cellClass ? cellClass : cellStyle" :header-row-style="headerRow"
    :header-cell-style="headerCellStyle ? headerCellStyle : headerCell">
    <slot></slot>
  </el-table>
</template>
<script>
export default {
  name: "v-table",
  props: ['loading', 'rowClass', 'cellClass','headerCellStyle'],
  data() {
    return {
      widowsWidth: document.body.clientWidth,
      maxHeight: 'auto',
    }
  },
  methods: {
    setCurrentRow(row) { this.$refs.table.setCurrentRow(row); },
    clearSelection() { this.$refs.table.clearSelection(); },
    toggleRowSelection(row, selected) { this.$refs.table.toggleRowSelection(row, selected); },
    /*表格行高处理*/
    rowStyle(obj) { return obj.rowIndex % 2 == 0 ? { padding: 0 } : { padding: 0, background: '#FAFAFA' }; },
    cellStyle() { return { padding: '0', height: '36px' }; },
    headerRow() { return { height: '30px' }; },
    headerCell() { return { padding: '0', background: '#F0F0F0', height: '28px' }; },
  },
  watch: {
    widowsWidth: {
      handler(val, oldVal) {
        if (val < 1000) {
          this.maxHeight = '400';
        } else {
          this.maxHeight = 'auto';
        }
      },
      deep: true,
      immediate: true
    }
  }
};
</script>
