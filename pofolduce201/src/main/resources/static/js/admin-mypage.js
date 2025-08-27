let selectedDays = 0;

function openSanctionPanel(userId) {
	document.getElementById("sanctionUserId").value = userId;
	const panel = new bootstrap.Offcanvas(document.getElementById('sanctionPanel'));
	panel.show();
}

function closeSanctionPanel() {
	const panel = bootstrap.Offcanvas.getInstance(document.getElementById('sanctionPanel'));
	panel.hide();
}

function setDays(days) {
	selectedDays = days;
	document.getElementById("customDays").value = "";
}

function toggleCustomInput() {
	document.getElementById("customInputBox").classList.toggle("d-none");
}

// 공통: 팝업 닫고 부모창 새로고침
function closePopupAndRefreshParent() {
	if (window.opener && !window.opener.closed) {
		window.opener.location.reload();
	}
	window.close();
}

function submitSanction() {
	const userId = document.getElementById("sanctionUserId").value;
	const custom = document.getElementById("customDays").value;

	const days = custom && custom > 0 ? custom : selectedDays;
	if (!days || days <= 0) {
		alert("제재 일수를 선택하세요.");
		return;
	}

	fetch(`/admin/sanction`, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ userId: userId, days: days })
	})
		.then(res => {
			if (!res.ok) throw new Error("제재 실패");
			alert("제재 완료!");
			closePopupAndRefreshParent();  // 여기로 바꿈
		})
		.catch(err => alert(err));
}

// 회사 인증 팝업 화면 띄우기
function openCertificationPopup(userId) {
	window.open('/admin/user/certificationpage?userId=' + userId, 'certificationPopup', 'width=800,height=600');
}

// 회사 인증 상태 업데이트 함수 (팝업 내에서 호출)
function updateCertificationStatus(userId, status) {
	fetch(`/admin/user/certification/${userId}/status`, {
		method: "POST",
		headers: { "Content-Type": "application/x-www-form-urlencoded" },
		body: new URLSearchParams({ status: status })
	})
		.then(res => {
			if (!res.ok) throw new Error("상태 변경 실패");
			alert("상태 변경 성공");
			closePopupAndRefreshParent();
		})
		.catch(err => alert(err));
}
