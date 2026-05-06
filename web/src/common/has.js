import { BUTTONS } from "@/common/Constant";

const has= {
    install (Vue, options) {
        Vue.directive('has', {
            update(){},
            bind(){},
            inserted(el, binding) {
                const buttons = Vue.ls.get(BUTTONS);

                if (!buttons || buttons.length === 0) {
                    removeElement(el);
                    return;
                }

                const shouldShow = buttons.some(item => {
                    return item.component === binding.value;
                });

                if (!shouldShow) {
                    removeElement(el);
                }
            },
        })
    }
}

function removeElement(element) {
    if (element.parentNode) {
        element.parentNode.removeChild(element);
    }
}

export default has;