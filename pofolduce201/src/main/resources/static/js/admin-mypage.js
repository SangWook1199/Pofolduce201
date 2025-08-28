let selectedDays = 0;

// 제재 패널 열기
function openSanctionPanel(userId) {
	document.getElementById("sanctionUserId").value = userId;
	const panel = new bootstrap.Offcanvas(document.getElementById('sanctionPanel'));
	panel.show();
}

// 제재 패널 닫기
function closeSanctionPanel() {
	const panel = bootstrap.Offcanvas.getInstance(document.getElementById('sanctionPanel'));
	panel.hide();
}

// 커스텀 입력 토글
function toggleCustomInput() {
	document.getElementById("customInputBox").classList.toggle("d-none");
}

// 팝업 닫고 부모창 새로고침
function closePopupAndRefreshParent() {
	if (window.opener && !window.opener.closed) {
		window.opener.location.reload();
	}
	window.close();
}

// 제재 제출
function submitSanction() {
	const userId = document.getElementById("sanctionUserId").value;
	const custom = document.getElementById("customDays").value;

	const days = custom && custom > 0 ? custom : selectedDays;
	if (!days || days <= 0) {
		alert("제재 일수를 선택해주세요.");
		return;
	}

	fetch(`/admin/sanction/${userId}`, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ days: days })
	})
		.then(res => {
			if (!res.ok) {
				throw new Error(`제재 실패: ${res.statusText}`);
			}
			return res.json(); // 서버에서 JSON 응답을 보낸다고 가정
		})
		.then(data => {
			alert("제재 완료!");
			closePopupAndRefreshParent();
		})
		.catch(err => {
			alert(`오류: ${err.message}`);
		});
}

// 회사 인증 팝업 열기
function openCertificationPopup(userId) {
	window.open('/admin/user/certificationpage?userId=' + userId, 'certificationPopup', 'width=800,height=600');
}

// 회사 인증 상태 업데이트 (팝업 내에서 호출)
function updateCertificationStatus(userId, status) {
	fetch(`/admin/user/certifications/${userId}/status`, { // URL 수정: `certification` -> `certifications`
		method: "POST",
		headers: { "Content-Type": "application/x-www-form-urlencoded" },
		body: new URLSearchParams({ status: status })
	})
		.then(res => {
			if (!res.ok) {
				throw new Error(`상태 변경 실패: ${res.statusText}`);
			}
			alert("상태 변경 성공");
			closePopupAndRefreshParent();
		})
		.catch(err => {
			alert(`오류: ${err.message}`);
		});
}