// mypage.js
//회원 탈퇴 버튼

document.addEventListener("click", (e) => {
    // modalConfirmBtn 클릭 감지
    const confirmBtn = e.target.closest("#modalConfirmBtn");
    if (!confirmBtn) return;

    const modalEl = document.getElementById("globalModal");
    if (!modalEl) return;

    // trim으로 공백 제거
    const modalTitle = modalEl.querySelector(".modal-title").textContent.trim();

    if (modalTitle === "회원 탈퇴") {

        // alert 띄우기
        alert("탈퇴되었습니다.");
	
        // 모달 닫기
        const modal = bootstrap.Modal.getInstance(modalEl);
        if (modal) modal.hide();

		document.getElementById("deleteForm").submit();
		
    }
});


document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById("btn-box");
    const inputs = document.querySelectorAll(".mypage-main-info-box .input");
    const penIcons = document.querySelectorAll(".pen-svg");
    const deleteBtn = document.getElementById("deleteBtn");
    const userinfo = document.getElementById("userinfo");

    editBtn.addEventListener("click", (e) => {
        const isDisabled = Array.from(inputs).some(input => input.disabled);

        if (isDisabled) {
            // 수정 모드로 전환
            inputs.forEach(input => input.disabled = false);
            editBtn.textContent = "수정완료"; 
            deleteBtn.style.display = "none";
            penIcons.forEach(icon => icon.style.visibility = "visible");
        } else {
            // 수정 완료: AJAX로 전송
            e.preventDefault(); // 기존 submit 막기

            const formData = new FormData(userinfo);
            const params = new URLSearchParams();
            formData.forEach((value, key) => params.append(key, value));

            fetch("/mypage/update", {
                method: "POST",
                body: params,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            })
            .then(response => response.text()) // 서버에서 redirect 없이 메시지만 반환
            .then(msg => {
                alert(msg); // 서버에서 반환한 메시지 alert
                window.location.href = "/mypage"; // mypage로 이동
            })
            .catch(err => console.error(err));

            inputs.forEach(input => input.disabled = true);
            editBtn.textContent = "정보 수정";
            penIcons.forEach(icon => icon.style.visibility = "hidden");
            deleteBtn.style.display = "inline-block";   
        }
    });
});








