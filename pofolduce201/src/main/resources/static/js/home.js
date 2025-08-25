const iframeWidth = 300;
const iframeHeight = 400;
const scale = 0.38;

function scaleIframe(frame) {
    const doc = frame.contentDocument || frame.contentWindow.document;
    if (!doc || !doc.body) return;

    // DOM 렌더링 후 적용
    requestAnimationFrame(() => {
        doc.body.style.margin = '0';
        doc.body.style.padding = '0';
        doc.body.style.transform = `scale(${scale})`;
        doc.body.style.transformOrigin = 'top left';
        doc.body.style.width = (100 / scale) + '%';
        doc.body.style.height = (100 / scale) + '%';
        doc.body.style.overflow = 'hidden';
    });
}

// 모든 iframe에 기본 너비/높이 적용
document.querySelectorAll('iframe').forEach(frame => {
    frame.style.width = iframeWidth + 'px';
    frame.style.height = iframeHeight + 'px';
    frame.style.border = 'none';

    // iframe onload 후 scale 적용
    frame.onload = () => scaleIframe(frame);
});

// 캐러셀 초기 활성화 아이템과 바로 다음 아이템까지 보장
document.querySelectorAll('#best-box iframe').forEach(frame => scaleIframe(frame));
