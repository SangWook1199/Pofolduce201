document.addEventListener("DOMContentLoaded", () => {
	const input = document.getElementById("input");          // 검색창
	const searchBtn = document.getElementById("searchBtn");  // SVG 이미지 버튼
	const hiddenInput = document.getElementById("hiddenInput"); // 숨겨진 input
	const hiddenForm = document.getElementById("hiddenForm");   // 숨겨진 form

	// SVG 클릭 시 검색 실행

	const urlParams = new URLSearchParams(window.location.search);
	const searchValue = urlParams.get('search');
	
	if (searchValue) {
	      // 화면 검색창에도 값 세팅
	      if (input) input.value = decodeURIComponent(searchValue);

	      // hidden input에도 값 세팅
	      if (hiddenInput) hiddenInput.value = decodeURIComponent(searchValue);
	  }

	
	searchBtn.addEventListener("click", () => {
		hiddenInput.value = input.value; // 사용자가 입력한 검색창 값을 hidden input에 복사
		hiddenForm.submit();             // 폼 제출
	});

	// Enter 키 눌러도 검색
	input.addEventListener("keypress", (e) => {
		if (e.key === "Enter") {
			e.preventDefault();          // 기본 엔터 동작 방지
			hiddenInput.value = input.value;
			hiddenForm.submit();
		}
	});
});
