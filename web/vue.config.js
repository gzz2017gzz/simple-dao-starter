const TerserPlugin = require('terser-webpack-plugin');
const fs = require('fs');
const path = require('path');
const version = new Date().getTime().toString();
fs.writeFileSync(path.resolve(__dirname, 'public/version.json'), JSON.stringify({ version }));


module.exports = {
  lintOnSave: 'error', // 设置eslint报错时停止代码编译
  productionSourceMap: false, // 不需要生产环境的 source map（减小dist文件大小，加速构建）
  devServer: {
    open: true, // npm run serve后自动打开页面
    host: '0.0.0.0', // 匹配本机IP地址(默认是0.0.0.0)
    port: 9000, // 开发服务器运行端口号
    proxy: {
      '/api': {
        target: 'http://localhost:8888/', // 代理接口地址
 
        secure: true, // 如果是https接口，需要配置这个参数
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '/api': '' //需要rewrite的, 这里理解成以'/api'开头的接口地址，把/api代替target中的地址
        }
      }
    }
  },
}
