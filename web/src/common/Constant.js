
export const USER = 'user';     /*localStore中用户信息 */
export const TOKEN = 'token';   /*localStore中令牌 */
export const DICTS = 'dicts';   /*localStore中字典 */
export const MENUS = 'menus';   /*localStore中菜单 */
export const BUTTONS = 'buttons';   /*localStore中按钮 */
// export const serverUrl = (process.env.NODE_ENV === "development" ? "ws://localhost:9998/message" : "wss://api2.huaqinghuashi.net/message"); /*webSocket接入点*/
export const serverUrl = (process.env.NODE_ENV === "development" ? "ws://localhost:9998/message" : "wss://ws.huaqinghuashi.cn/message"); /*webSocket接入点*/


/**移动端页面*/
export const h5Address = (process.env.NODE_ENV === "development" ? "http://192.168.0.101:3001/" : "https://h5.huaqinghuashi.cn/");

export const PAY_NOTIFY_ADDRESS = (process.env.NODE_ENV === "development" ? "http://pay-api.huaqinghuashi.net/crm/notify/" : "https://pay-api.huaqinghuashi.cn/crm/notify/");

export const SZBS = ['922837001055502336', '923851080872230912']

/**打包测试环境 */
// export const h5Address = (process.env.NODE_ENV == "development" ? "http://192.168.0.105:3001/" : "http://h5.huaqinghuashi.net/");
/**打包生产环境 */
// export const h5Address = process.env.NODE_ENV == "development" ? "http://192.168.0.105:3001/": "http://h5.huaqinghuashi.net/";

console.log("[当前环境]", process.env.NODE_ENV)
console.log("[移动端地址]", h5Address)
console.log("[websocket服务地址]", serverUrl)


