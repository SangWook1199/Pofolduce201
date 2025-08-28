/**review-post.html javascript file */

// 전역변수로 sessionUserId와 reviewPostId 저장
let sessionUserId;
let reviewPostId;
let reviewPostUserId;

// 드래그를 제외하기 위해서 마우스 다운의 위치를 기록, 이후 마우스 업의 위치와 비교한다
let startX = 0;
let startY = 0;

//dom 생성 후 리뷰 로드
document.addEventListener('DOMContentLoaded', async function() {

	sessionUserId = document.getElementById('data-container').dataset.sessionUserid;
	reviewPostId = document.getElementById('data-container').dataset.postId;
	reviewPostUserId = document.getElementById('data-container').dataset.reviewPostUserId;
	userImageLocation = document.getElementById('data-container').dataset.userImageLocation;

	const reviews = await loadReviews(document
		.getElementById('data-container').dataset.postId);
	appendReviews(reviews);
})
document.querySelector("#portfolio-div").addEventListener('mousedown',
	function(event) {
		startX = event.clientX;
		startY = event.clientY;
	});
document.querySelector("#portfolio-div").addEventListener('mouseup',
	createReviewForm);

function createReviewForm(event) {
	// 드래그는 제외하고, 일반 클릭일 때만 review-div를 생성한다
	if (Math.abs(startX - event.clientX)
		+ Math.abs(startY - event.clientY) >= 100) {
		return;
	}
	// new-review-div가 이미 존재한다면, 아무 작업도 하지 않고 함수 즉시 종료.
	if (document.querySelector('.new-review-div')) {
		return;
	}
	// review-div 위에 있는 
	if (event.target.closest('.review-div')) {
		return;
	}
	const portfolioDiv = document.querySelector("#portfolio-div");

	const rect = portfolioDiv.getBoundingClientRect();
	const x = event.clientX - rect.left;
	const y = event.clientY - rect.top;

	const postId = document.getElementById('data-container').dataset.postId;
	const intX = parseInt(x, 10);
	const intY = parseInt(y, 10);

	// 2. 백틱을 사용해 전체 HTML 구조를 하나의 문자열로 만듭니다.
	const formHTML = `
	    <div class="row justify-content-center p-2 new-review-div" 
	         style="position: absolute; z-index: 9998; left: ${x}px; top: ${y}px; transform:translate(-50%, 0);">
			<img class="col-3 p-0 img-fluid rounded-circle bg-white" src="${userImageLocation != null ? userImageLocation.split('static')[1] : '/svg/person.svg'}" style="border: 2px solid #FF4473; width:30px;"/>
			<div class="row">
	        	<form id="review-form" class="col card p-2 text-center bg-light review m-0">
					<div>
	            		<input type="hidden" name="reviewLocationX" value="${intX}">
	            		<input type="hidden" name="reviewLocationY" value="${intY}">
	            		<textarea class="form-control border-0" name="reviewContents"></textarea>
	            		<input type="hidden" name="reviewPost.reviewPostId" value="${postId}">
	            
	            		<button type="submit" class="btn btn-primary m-2">작성</button>
	            		<button type="button" class="btn btn-secondary m-2" 
	                	    onclick="this.closest('.new-review-div').remove()">취소</button>
					</div>
	        	</form>
			</div>
	    </div>
	`;

	portfolioDiv.insertAdjacentHTML('beforeend', formHTML);

	// reviewForm에서 submit시, fetch를 통한 post
	const reviewForm = document.getElementById('review-form');
	reviewForm.addEventListener('submit', async function(event) {
		event.preventDefault();

		const formData = new FormData(reviewForm);
		try {
			const response = await fetch('/review', {
				method: 'post',
				body: formData,
			});
			if (!response.ok) {
				throw new Error(`HTTP error status: ${response.status}`);
			}
		} catch (error) {
			console.error("Fetch Error:", error);
		}

		const reviews = await loadReviews(document
			.getElementById('data-container').dataset.postId);
		appendReviews(reviews);
		portfolioDiv.removeChild(document.querySelector('.new-review-div'));
	})
}

// review를 페이지에 로드
async function loadReviews(reviewPostId) {
	let reviewList = null;
	try {
		response = await fetch(`/review/${reviewPostId}`);
		if (!response.ok) {
			const errorData = await response.json();
			throw new Error(errorData.message);
		}
		reviewList = await response.json();
	} catch (error) {
		console.error("Fetch Error:", error);
	}
	return reviewList;
}

function appendReviews(reviews) {
	document.querySelectorAll('.review-div').forEach(function(reviewDiv) {
		reviewDiv.remove();
	});
	for (const review of reviews) {
		let buttonsHTML = '';
		let reportButton = '';
		let reviewLikeButton = '';
		console.log(reviewPostUserId);
		console.log(sessionUserId);
		if (sessionUserId == reviewPostUserId && review.likeCount == 0) {
			reviewLikeButton = `
				<button class="bg-transparent border-0" onclick="pressReviewLike(${review.reviewId}, ${review.user.userId})"><img id="review-like-button-${review.reviewId}" src="/svg/circle-like-off.svg" style="width:33px; border-color:#FF4473"></button>
			`;
		}
		else if (sessionUserId == reviewPostUserId && review.likeCount == 1) {
			reviewLikeButton = `
				<img id="review-like-button-${review.reviewId}" src="/svg/circle-like-on.svg" style="width:33px; border-color:#FF4473">
			`;
		}
		if (sessionUserId && sessionUserId == review.user.userId) {
			buttonsHTML = `
		            <button type="button" class="btn btn-sm btn-primary" onclick="addUpdateForm(${review.reviewId}, '${review.reviewContents}', ${review.reviewPost.reviewPostId})">수정</button>
		            <button type="button" class="btn btn-sm btn-outline-primary" onclick="deleteReview(${review.reviewId}, ${review.reviewPost.reviewPostId})">삭제</button>
		    `;
		}
		else if (sessionUserId && sessionUserId != review.user.userId) {
			reportButton = `
		            <button type="button" class="btn p-0 border-0 bg-transparent"
		                data-bs-toggle="modal"
		                data-bs-target="#reportPostModal"
		                data-review-id="${reviewPostId}"
		                data-reported-user-id="${review.user.userId}"
		                onclick="prepareReviewReportModal(event)">
		                <img alt="신고 버튼" class="report-img" src="/svg/report-button.svg">
		            </button>
		    `;
		}
		const portfolioDiv = document.getElementById('portfolio-div');
		const reviewHTML = `
		    <div class="row review-div justify-content-center"
				 id="review-div-${review.reviewId}"
		         style="position: absolute; z-index: 9998; left: ${review.reviewLocationX}px; top: ${review.reviewLocationY}px; transform:translate(-50%, 0);">
				 <img class="col-3 p-0 img-fluid rounded-circle bg-white" src="${review.user.userImageLocation != null ? review.user.userImageLocation.split('static')[1] : '/svg/person.svg'}" style="border: 2px solid #FF4473; width:30px;"/>
				 <div class="row">
				 <div class="col card p-2 text-center bg-light review m-0">
					<div class="row m-1 justify-content-center">
						<div class="row">
							<span class="col-12 fs-6 fw-bold">${review.user.nickname}</span>
							<span class="col-12" style="font-size: small;">${review.reviewDate.substring(0, 10)}</span>
						</div>
					</div>
					<div>
						${reviewLikeButton}
						${reportButton}
						${buttonsHTML}
					</div>
					<div class="rounded-2 mt-2 bg-white">
						${review.reviewContents}
					</div>
				</div>
				</div>
		    </div>
		`;
		portfolioDiv.insertAdjacentHTML('beforeend', reviewHTML);

	}
}

async function deleteReview(reviewId, reviewPostId) {
	try {
		const response = await fetch(`/review/${reviewId}`, {
			method: 'delete',
		});
		if (!response.ok) {
			throw new Error(`HTTP error status: ${response.status}`);
		}
	} catch (error) {
		console.error('fetch error:' + error);
	}
	appendReviews(await loadReviews(reviewPostId));
}

async function addUpdateForm(reviewId, reviewContents, reviewPostId) {
	const reviewDiv = document.getElementById(`review-div-${reviewId}`);
	const formHTML = `
		<div class="card p-2 text-center bg-light" id="update-review-div">
			<textarea id='update-review-textarea'></textarea>
			<div>
				<button class="btn btn-sm btn-primary" id="review-update-button">완료</button>
				<button class="btn btn-sm btn-primary" onclick="cancelUpdate(${reviewId})">취소</button>
			</div>
			
		</div>
	`;
	reviewDiv.insertAdjacentHTML('beforeend', formHTML);
	document.getElementById('update-review-textarea').value = reviewContents;

	const updateButton = document.getElementById('review-update-button');
	updateButton.addEventListener('click', async function() {
		try {
			const response = await fetch(`/review/${reviewId}`, {
				method: 'PATCH',
				headers: {
					'Content-Type': 'text/plain' // 순수 텍스트를 보낸다고 명시
				},
				body: document.getElementById('update-review-textarea').value,
			});
			if (!response.ok) {
				throw new Error(`HTTP error status: ${response.status}`);
			}
		} catch (error) {
			console.error("fetch error:" + error);
		}
		appendReviews(await loadReviews(reviewPostId));
	})

}

function cancelUpdate(reviewId) {
	const reviewDiv = document.getElementById(`review-div-${reviewId}`);
	reviewDiv.removeChild(document.getElementById('update-review-div'));
}

async function pressReviewLike(reviewId, reviewUserId) {

	try {
		const response = await fetch(`/review/${reviewId}/like`, {
			method: 'PATCH',
			body: `${reviewUserId}`
		});
		await response.text();
		if (!response.ok) {
			throw new Error(`HTTP error status: ${response.status}`);
		}
	} catch (error) {
		console.log("fetch error: error");
	}
	document.getElementById(`review-like-button-${reviewId}`).src = "/svg/circle-like-on.svg";
}

async function pressLike(reviewPostId) {

	let likeCount = 0;
	try {
		const response = await fetch(`/review-post/${reviewPostId}/like`, {
			method: 'PATCH',
		});
		likeCount = await response.text();
		if (!response.ok) {
			throw new Error(`HTTP error status: ${response.status}`);
		}
	} catch (error) {
		console.log("fetch error: error");
	}
	const likeBtn = document.getElementById('likeBtn');
	const likeIcon = document.getElementById('likeImg');
	const likeCountSpan = document.getElementById('likeCount');

	likeCountSpan.innerText = likeCount;
	likeIcon.src = "/svg/like-on.svg";
	likeBtn.disabled = true;
}

//댓글 수정
function toggleEdit(commentsId) {
	const editBox = document.getElementById(`edit-box-${commentsId}`);
	editBox.setAttribute('style', 'display: block');
	editBox.setAttribute('class', 'm-2');
}

function prepareReviewReportModal(event) {
	// 1. 클릭된 버튼을 찾습니다.
	const button = event.currentTarget;

	// 2. 버튼에 저장된 데이터(dataset)를 읽어옵니다.
	const reviewId = button.dataset.reviewId;
	const reportedUserId = button.dataset.reportedUserId;

	// 3. 메인 HTML에 만들어 둔 모달의 input들을 id로 찾습니다.
	const reviewIdInput = document.getElementById('reportedReviewIdInput');
	const userIdInput = document.getElementById('reportedUserIdInput');

	// 4. 찾은 input에 읽어온 데이터를 값으로 설정합니다.
	reviewIdInput.value = reviewId;
	userIdInput.value = reportedUserId;
}