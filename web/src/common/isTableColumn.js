import { BUTTONS } from "@/common/Constant";
import Vue from "vue";
const buttons = Vue.ls.get(BUTTONS);
function isTableColumn(hasValue) {
    if (!hasValue) return true;
    let isShow = false;
    if (buttons.length === 0) {
        return isShow;
    }
    for (let i = 0; i < buttons.length; i++) {
        if (buttons[i].component === hasValue){
            isShow = true;
            return isShow;
        }
    }
    return isShow;
}

/**
 * @param {String} items
 * @param {Array} itemList
 * */
export function isTableColumnItem(items,itemList) {
    if (!items) {
        this.$nextTick(() => {
            this.$message.warning("参数异常")
            throw new Error("参数异常" + items)
        })
        return true;
    }
    if (!Array.isArray(itemList)) {
        this.$nextTick(() => {
            this.$message.warning("参数异常")
            throw new Error("参数异常" + itemList)
        })
    }
    if (itemList.length === 0) return true;
    let isShow = false;
    for (let i = 0; i < itemList.length; i++) {
        if (itemList[i] === items) {
            isShow = true;
            return isShow;
        } else {
            isShow = false;
        }
    }
    return isShow;
}

export default isTableColumn