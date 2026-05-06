import { BUTTONS } from "@/common/Constant";

const das= {
    install (Vue, options) {
        Vue.directive('das', {
            update(){},
            bind(){},
            inserted(el, binding) {
                const buttons = Vue.ls.get(BUTTONS);

                if (!buttons || buttons.length === 0) {
                    removeElement(el);
                    return;
                }

                // 如果传入的是字符串，则按原逻辑处理
                if (typeof binding.value === 'string') {
                    const shouldShow = buttons.some(item => item.component === binding.value);
                    if (!shouldShow) {
                        removeElement(el);
                    }
                    return;
                }

                // 如果传入的是对象，则检查 component 和额外的 bool 表达式
                if (typeof binding.value === 'object' && binding.value !== null) {
                    const { btn, cond } = binding.value;

                    const shouldShow = buttons.some(item => item.component === btn);
                    console.log('show=',shouldShow);
                    console.log('cond=',cond);

                    // 判断组件是否可见并且满足额外的布尔条件
                    if (!(shouldShow || (typeof cond === 'boolean' && cond))) {
                        removeElement(el);
                    }
                }
            }
        })
    }
}

function removeElement(element) {
    if (element.parentNode) {
        element.parentNode.removeChild(element);
    }
}

export default das;
