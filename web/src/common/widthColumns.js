import Vue from "vue";
export function adjustColumnWidth(table) {
    const colgroup = table.querySelector("colgroup");
    const colDefs = [...colgroup.querySelectorAll("col")];
    colDefs.forEach((col) => {
        let clsName = col.getAttribute("name");
        const cells = [
            ...table.querySelectorAll(`td.${clsName}`),
            ...table.querySelectorAll(`th.${clsName}`),
        ];
        // 忽略加了"leave-alone"类的列
        if (cells[0]?.classList?.contains?.("leave-alone")) {
            return;
        }
        const widthList = cells.map((el) => {
            return el.querySelector(".cell")?.scrollWidth || 0;
        });
        let max = Math.max(...widthList);
        // if (clsName !== 'gutter') {
            table.querySelectorAll(`col[name=${clsName}]`).forEach((el) => {
                el.removeAttribute("width");
                el.style.width = `${max}px`;
                el.setAttribute("width", max);
            });
        // }
    });
    new Vue().$forceUpdate()
}

const widthColumns = {
    install(Vue) {
        Vue.directive('widthColumns', {
            update() {},
            bind() {},
            inserted(el) {
                setTimeout(() => {
                    adjustColumnWidth(el);
                }, 0);
            },
            componentUpdated(el) {
                // el.classList.add("r-table");
                setTimeout(() => {
                    adjustColumnWidth(el);
                }, 0);
            },
            unbind() {},
        })
    }
}

export default widthColumns