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
			window.opener.location.reload(); // 부모창 리스트 새로고침
			window.close(); // 팝업 닫기
		})
		.catch(err => alert(err));
}
