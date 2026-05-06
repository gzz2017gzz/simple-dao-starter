import Vue from "vue";
import VueRouter from "vue-router";
import dashboard from "@/views/dashboard/dashboard";
 
Vue.use(VueRouter);
const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [ { path: "/dashboard", component: dashboard, meta: { funcId: "1", name: "首页" } }, ]
});
const defaultRoute = { path: "*", redirect: "/dashboard", }
export { router, defaultRoute };
