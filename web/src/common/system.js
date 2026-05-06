import moment from "moment";
import { BUTTONS } from "@/common/Constant";
import Vue from 'vue';
//身份证脱敏
export const idMask = (str) => {
    if (!str) return '';
    return str.length >= 14 ? str.slice(0, 6) + '*'.repeat(8) + str.slice(14) : str.slice(0, 6) + '*'.repeat(str.length - 6);
};
//手机脱敏
export const phoneMask = (str) => str.replace(/(\d{3})\d{4}(\d)/, '$1****$2');
export const FORMAT = 'YYYY-MM-DDT00:00:00'; /*查询时日期格式 */
//格式化开始日期
export const formatS = (date) => {
    return date ? moment(date).format(FORMAT) : date;
};
//格式化结束日期
export const formatE = (date) => {
    return date ? moment(date).add(1, 'days').format(FORMAT) : date;
};

export const moneyFormat = (r, c, v) => {
    if (v === 0) return null;
    if (v) return v.toFixed(2);
    return v;
};

export const moneyFormatZero = (r, c, v) => {
    if (v) return v.toFixed(2);
    return v;
};

export const intFormat = (r, c, v) => {
    if (v === 0) return null;
    if (v) return v;
};

export const has = (key) => Vue.ls.get(BUTTONS).some(item => item.component === key);

