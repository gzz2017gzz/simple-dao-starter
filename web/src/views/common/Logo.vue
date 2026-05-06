<template>
  <div class="logo">
    <div><img src="@/assets/logo.png" alt="" /></div>
    <div class="title">{{ title }}</div>
  </div>
</template>

<script lang="ts" setup>
const title = "SimpleDAO系统";
</script>

<style scoped>
.logo {
  width: 200px;
  color: #fff;
  font-weight: 700;
  padding: 0.5rem;
  background: #2b2f3f;
  height: 50px;
  box-sizing: border-box;
  line-height: 50px;
  /* border-right: 1px solid #ffffff; */
  display: flex;

}

img {
  width: 1.875rem;
  height: 1.875rem;
}

.title {
  margin-left: 0.7rem;
  font-size: 1rem;
  line-height: 2rem;
}
</style>
