import html2canvas from "html2canvas";

function download(refs, name) {
    const domObj = refs;
    html2canvas(domObj, {
        allowTaint: true,
        useCORS: true, // 新增跨域支持 
        scale: window.devicePixelRatio > 1 ? 2 : 1, // 动态缩放 
        logging: true,
        backgroundColor: "#ffffff", // 确保安卓背景不透明
        onclone: (clonedDoc) => {
            // 解决安卓样式渲染问题
            const target = clonedDoc.getElementById(domObj.id);
            if (target) {
                target.style.position = "static";
                target.style.transform = "none";
            }
        }
    }).then(canvas => {
        downloadQRImg(canvas, name);
    }).catch(error => {
        console.error(" 截图失败：", error);
        alert("下载失败，请重试");
    });
}

function downloadQRImg(canvas, name) {
    canvas.toBlob((blob) => {
        try {
            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.download = `${name || "bill"}.png`;
            a.href = url;
            a.style.display = "none";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        } catch (error) {
            console.error(" 安卓下载异常：" + error);
            // 旧版本安卓降级方案 
            const imgUrl = canvas.toDataURL("image/png");
            const win = window.open();
            win.document.write(`<img  src="${imgUrl}" onload="this.parentNode.removeChild(this);window.close();">`);
        }
    }, "image/png");
}

export default download;