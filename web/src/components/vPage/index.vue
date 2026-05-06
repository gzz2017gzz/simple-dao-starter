/*分页条,作者:高振中,日期:2020-12-25 01:35:25*/
<template>
  <div style="text-align: right; height: 40px; padding-top: 3px; margin-top: 5px; padding-right: 10px" v-if="total > 0">
    <el-pagination background layout="total, sizes, prev, pager, next, jumper" :current-page="page" :total="total" @current-change="(page) => pageChange(page)"
      :page-sizes="!pageSizes?[15, 25, 30, 50]:pageSizes" @size-change="(size) => sizeChange(size)" :page-size="size" mini></el-pagination>
  </div>
</template>
<script>
export default {
  name: 'v-page',
  props: ['page', 'total', 'size', 'sizeChange', 'pageChange','pageSizes'],
};
</script>
