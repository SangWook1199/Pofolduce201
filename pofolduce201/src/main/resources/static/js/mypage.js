const modal = document.getElementById("myModal");  // 모달 id 확인
const openBtn = document.getElementById("deleteBtn");  // 모달 열 버튼
const closeBtn = document.getElementById("closeModalBtn");  // 닫기 버튼

openBtn.onclick = () => modal.style.display = "block";
closeBtn.onclick = () => modal.style.display = "none";

window.onclick = (event) => {
    if (event.target === modal) modal.style.display = "none";
};
