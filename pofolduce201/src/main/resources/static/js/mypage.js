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

        // 실제 탈퇴 AJAX 호출 가능
        // 예: axios.post('/user/delete', { id: userId })
    }
});


//수정하기 버튼
document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById("btn-box");
    const inputs = document.querySelectorAll(".mypage-main-info-box .input");
	const penIcons = document.querySelectorAll(".pen-svg");

    editBtn.addEventListener("click", () => {
        // input 중 하나라도 disabled인지 확인
        const isDisabled = Array.from(inputs).some(input => input.disabled);

        if (isDisabled) {
            // 수정 모드로 전환
            inputs.forEach(input => input.disabled = false);
            editBtn.textContent = "수정하기"; // 버튼 텍스트 변경(optional)
			deleteBtn.style.display = "none";   // 회원 탈퇴 버튼 숨기기
			// 펜 보이게 하기 
			penIcons.forEach(icon => icon.style.visibility = "visible");
        } else {
            // 수정 완료 처리
			// 여기서 서버로 수정 데이터 보내기
			
			
			userinfo.submit();  // <-- 여기서 서버로 전송
			
            inputs.forEach(input => input.disabled = true);
            alert("수정이 완료되었습니다!");
            editBtn.textContent = "정보 수정"; // 버튼 텍스트 원복(optional)
            
			// 펜 숨기기
			penIcons.forEach(icon => icon.style.visibility = "hidden");
			// 회원 탈퇴 버튼 나타내기
			deleteBtn.style.display = "inline-block";   
			
        }
    });
});


//수정 데이터 전달하기 (ajax)







