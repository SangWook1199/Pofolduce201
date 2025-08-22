/**
 * 
 */
// --- 좋아요 기능 JavaScript 코드 추가 ---
		document.addEventListener("DOMContentLoaded", () => {
			const likeBtn = document.getElementById('likeBtn');
			const likeCountSpan = document.getElementById('likeCount');
			const likeImg = document.getElementById('likeImg');

			if (likeBtn) {
				likeBtn.addEventListener('click', async () => {
					likeBtn.disabled = true;
					
					const studyId = likeBtn.dataset.studyId;
					const url = `/api/study/${studyId}/like`;
					
					try {
						const response = await fetch(url, {
							method: 'POST',
							headers: {
								'Content-Type': 'application/json'
							}
						});
						
						if (response.ok) {
							const newLikeCount = await response.json();
							likeCountSpan.textContent = newLikeCount;
							
							likeImg.src = "/svg/like-on.svg";
							
						} else {
							console.error('Failed to update like count:', response.status);
							alert('좋아요 업데이트에 실패했습니다.');
						}
					} catch (error) {
						console.error('Error:', error);
						alert('네트워크 오류가 발생했습니다.');
					} finally {
						likeBtn.disabled = true;
					}
				});
			}
		});