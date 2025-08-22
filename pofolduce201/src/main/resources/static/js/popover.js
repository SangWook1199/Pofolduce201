/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
	const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]');

	popoverTriggerList.forEach(el => {
		// Popover 초기화 (manual trigger)
		const popover = new bootstrap.Popover(el, {
			html: true,
			content: '불러오는 중...',
			placement: 'top',
			trigger: 'manual',
			fallbackPlacements: []
		});

		// 닉네임 클릭 시 실행
		el.addEventListener("click", async (e) => {
			e.stopPropagation(); // 클릭 이벤트 버블링 방지
			const userId = el.dataset.userid;

			try {
				const response = await fetch(`/api/user/${userId}`);
				if (!response.ok) throw new Error("서버 응답 오류");

				const data = await response.json();
				const loadImage = new Promise((resolve) => {
					const img = new Image();
					img.onload = () => resolve(`/uploads/profile/${data.userId}.png`); // 이미지 로딩 성공
					img.onerror = () => resolve('/svg/person.svg'); // 이미지 로딩 실패
					img.src = `/uploads/profile/${data.userId}.png`;
				});

				const imageSrc = await loadImage; // 이미지 경로 확정

				const contentHtml = `
				        <div>
							<div class="popover-image-container">
						 		<img src="${imageSrc}" alt="프로필" class="popover-image-inner">
							</div>
				            <p class="popover-nickname">${data.nickname}</p>
				            <hr>
				            <p class="popover-content">획득한 포인트: ${data.point}</p>
				            <p class="popover-content">작성한 이력서: ${data.countPortfolio}</p>
				            <p class="popover-content popover-content-last">작성한 첨삭: ${data.countReview}</p>
				            <div class="popover-button">></div>
				        </div>
				    `;

				popover.setContent({ '.popover-body': contentHtml });
				popover.show();

				// 팝오버 내부 버튼 클릭 시 상세페이지 이동
				const btn = document.querySelector('.popover-button');
				if (btn) {
					btn.addEventListener("click", () => {
						window.location.href = `/userpage/${data.userId}`;
					});
				}

			} catch (err) {
				console.error(err);
				popover.setContent({ '.popover-body': '정보를 불러오지 못했습니다.' });
				popover.show();
			}
		});
	});

	// 문서 클릭 시 팝오버 닫기
	document.addEventListener('click', function(e) {
		popoverTriggerList.forEach(el => {
			const popoverInstance = bootstrap.Popover.getInstance(el);
			if (popoverInstance &&
				!el.contains(e.target) &&
				!document.querySelector('.popover')?.contains(e.target)) {
				popoverInstance.hide();
			}
		});
	});
});