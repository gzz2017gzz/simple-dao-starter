<template>
  <div>
    <el-select size="mini" v-bind="$attrs" :value="value" @input="input" multiple>
      <el-option :value="null" label="--请选择--" v-if="addBlank === true"></el-option>
      <el-option v-for="item in options" :key="item.userId" :label="item.name" :value="item.userId"></el-option>
    </el-select>
  </div>
</template>
<script>
import http from "@/common/http";
export default {
  name: "multiUser",
  data() { return { options: [] } },
  props: ["post", "value", "schoolId", "addBlank"],
  methods: {
    loadData() {
      if (!this.schoolId || !this.post) return
      http.post(`/user/list`, { schoolId: this.schoolId, dr: 0, post: this.post }).then(res => {
        if (res.code === 200) {
          this.options = res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    input(val) {
      this.$emit("input", val);
    },
  },
  watch: {
    schoolId: {
      immediate: true,
      deep: true,
      handler(value) {
        if (value) {
          this.loadData();
        }
      }
    },
  }
}
</script>
<style lang="less" scoped></style>