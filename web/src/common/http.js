import axios from 'axios'
import { Message } from 'element-ui'
import Vue from 'vue'
import { TOKEN } from "@/common/Constant";
const requests = axios.create({
  baseURL: process.env.NODE_ENV == "development" ? "/api" : "api",
  timeout: 0,
  headers: { 'Content-Type': 'application/json; charset=utf-8' },
})
//console.log("process.env.NODE_ENV=", process.env.NODE_ENV)//这行不要删
const err = (error) => {// 错误处理函数
  if (error.response) {
    if (error.response.status === 403) console.log(403);
    if (error.response.status === 401) console.log(res.msg);
  }
  return Promise.reject(error)
}
// 请求拦截器
requests.interceptors.request.use(config => {
  let file = config.data

  if (config.data instanceof FormData) {
    config.headers['Content-Type'] = 'multipart/form-data';
  }
  if (file instanceof File) {
    let form = new FormData()
    form.append('file', file)
    config.data = form;
    config.headers = { 'Content-Type': 'multipart/form-data' };
  }

  if (Vue.ls.get(TOKEN)) config.headers.token = Vue.ls.get(TOKEN);
  return config;
}, err);
//接收拦截器
requests.interceptors.response.use((response) => {
  const res = response.data
  if (res.code == 201) { //token为空或未登录
    Vue.ls.clear()
    Message.error(res.msg);
    location.reload();
  }
  return res
}, err);
export default requests