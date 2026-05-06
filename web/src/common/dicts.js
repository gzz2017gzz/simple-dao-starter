/**字典数据读取与翻译(格式化) */
import Vue from "vue";
import { DICTS, BUTTONS } from "@/common/Constant";
import moment from "moment";
import { Notification } from 'element-ui';

let dict;

//过滤单选字典
export const itemValue = (typeCode, val) => {
  if (!dict) {
    dict = Vue.ls.get(DICTS)
    console.log("dict-cache:", typeCode, val)
  }

  if (!typeCode || !dict[typeCode]) {
    console.log("字典不存在：dictCode=", typeCode, "dictVal=", val)
    return val;
  }

  let item = dict[typeCode].filter(item => item.itemValue == val);
  if (item.length == 0) return undefined;
  return item[0].text;
}
//过滤多选字典
function multiItemValue(typeCode, values) {
  let res = "";
  let val = values ? values.split(",") : [];
  val.map(i => res += itemValue(typeCode, i) + ",");
  return res.length > 0 ? res.slice(0, -1) : res;
}
//取字典值
export const getDict = (typeCode) => {
  if (!Vue.ls.get(DICTS)) {
    return setTimeout(() => {
      Notification({
        title: `字典${typeCode}获取失败`,
        message: "请先刷新缓存，如果问题依旧，请联系管理员",
        type: "error"
      });
    })
  }
  if (!Vue.ls.get(DICTS)[typeCode]) return setTimeout(() => {
    Notification({
      title: `字典${typeCode}获取失败`,
      message: "请先刷新缓存，如果问题依旧，请联系管理员",
      type: "error"
    });
  })
  let data = Vue.ls.get(DICTS)[typeCode].map(i => { return { itemValue: parseInt(i.itemValue) > 9999999 ? i.itemValue : parseInt(i.itemValue), text: i.text }; });
  return data
}
//时间格式化
export const dateFormat = (r, c, v) => v == undefined ? "" : moment(v).format("YYYY-MM-DD HH:mm:ss");
export const dateShortFormat = (r, c, v) => v == undefined ? "" : moment(v).format("YYYY-MM-DD");
export const datetimeFormatVal = (val) => val == undefined ? "" : moment(val).format("YYYY-MM-DD HH:mm:ss");
export const dateFormatVal = (val) => val == undefined ? "" : moment(val).format("YYYY-MM-DD");

/**
 * 多选字典格式化
 * @param dictCode 字典编码
 * */
export const multiDictFormat = (row, column, dictCode) => multiItemValue(dictCode, row[column.property]);
/**
 * 单选字典格式化
 * @param dictCode 字典编码
 * */
export const dictFormat = (row, column, dictCode) => itemValue(dictCode, row[column.property]);
export const dictFormatVal = (dictCode, val) => itemValue(dictCode, val);
export const dictFormatDefault = (dictCode, val, def) => {
  const code = itemValue(dictCode, val);
  console.log(dictCode, val, def, code)
  return code === undefined ? def : code
};
/**
  *fieldName 字段名
  *fields 当前场景当前校区可用字段集
  */
export const showField = (fieldName, fields) => fields.filter(item => item.fieldName == fieldName).length > 0;

export const hidden = (key) => Vue.ls.get(BUTTONS).filter(item => item.component == key).length > 0
