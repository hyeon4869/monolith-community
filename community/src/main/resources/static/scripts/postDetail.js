// URL에서 게시물 ID 추출
const postId = window.location.pathname.split('/').pop();

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', () => {
    fetchPostDetail();

    // 좋아요 버튼 이벤트 리스너 추가
    const likePostBtn = document.getElementById('likePostBtn');
    if (likePostBtn) {
        likePostBtn.addEventListener('click', likePost);
    }

    // 삭제 버튼 이벤트 리스너 추가
    const deletePostBtn = document.getElementById('deletePostBtn');
    if (deletePostBtn) {
        deletePostBtn.addEventListener('click', deletePost);
    }

    // 댓글 토글 버튼 이벤트 리스너 추가
    const toggleCommentsBtn = document.getElementById('toggleCommentsBtn');
    if (toggleCommentsBtn) {
        toggleCommentsBtn.addEventListener('click', toggleComments);
    }
});

// 게시글 상세 정보 가져오기
async function fetchPostDetail() {
    try {
        const postResponse = await fetch(`/postDetail/${postId}`);
        if (!postResponse.ok) {
            const errorText = await postResponse.text();
            throw new Error(`게시물 데이터를 가져오는 데 실패했습니다. 상태 코드: ${postResponse.status}, 메시지: ${errorText}`);
        }

        const responseData = await postResponse.json();
        console.log("서버 응답:", responseData);

        const postData = responseData.postDetail;

        if (!postData) {
            console.error("postData가 undefined입니다");
            document.getElementById('postTitle').textContent = "게시글 데이터가 없습니다.";
            return;
        }

        // 게시글 정보 표시
        displayPostInfo(postData);

        // 첨부파일 표시
        displayFileAttachment(postData);

        // 댓글 데이터 가져오기
        await fetchComments();
    } catch (error) {
        console.error("Error fetching post detail:", error);
        document.getElementById('postTitle').textContent = "게시물을 불러오는 데 실패했습니다.";
        document.getElementById('postContent').textContent = `오류: ${error.message}`;
    }
}

// 게시글 정보 표시 함수
function displayPostInfo(postData) {
    document.getElementById('postTitle').textContent = postData.title || "제목 없음";

    // 날짜 포맷팅
    const formattedDate = new Date(postData.createTime).toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
    document.getElementById('postDate').textContent = formattedDate;

    document.getElementById('postContent').textContent = postData.content || "내용 없음";
}

// 첨부파일 표시 함수
function displayFileAttachment(postData) {
    const fileArea = document.getElementById('postFileArea');
    fileArea.innerHTML = "";

    if (!postData.postFileDTO) return;

    const fileName = postData.postFileDTO.fileName;
    let filePath = postData.postFileDTO.filePath;

        // 로컬 경로를 웹 접근 가능한 URL로 변환
        if (filePath.includes("D:\\boot\\images")) {
            // 마지막 백슬래시 이후의 모든 문자(파일명)를 가져옴
            const fileNameWithUUID = filePath.substring(filePath.lastIndexOf('\\') + 1);

            // 웹 접근 가능한 URL로 변환
            filePath = "/files/" + encodeURIComponent(fileNameWithUUID);
        }



    // 이미지 파일인지 확인
    const isImage = /\.(jpg|jpeg|png|gif|bmp)$/i.test(fileName);

    fileArea.innerHTML = `
        <div class="file-item">
            <h4>첨부 ${isImage ? '이미지' : '파일'}</h4>
            ${isImage ? `<img src="${filePath}" alt="${fileName}" style="max-width: 400px; display: block; margin: 10px 0;" />` : ''}
            <a href="${filePath}" download="${fileName}" class="download-link">
                <i class="${isImage ? 'download-icon' : 'file-icon'}"></i> ${isImage ? '이미지' : fileName} 다운로드
            </a>
        </div>
    `;
}

// 댓글 데이터 조회 및 렌더링
async function fetchComments() {
    try {
        const response = await fetch(`/postComment/${postId}`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`댓글 데이터를 가져오는 데 실패했습니다. 상태 코드: ${response.status}, 메시지: ${errorText}`);
        }

        const data = await response.json();
        const commentList = document.getElementById('commentList');
        commentList.innerHTML = "";

        if (data.comment && data.comment.length > 0) {
            // 댓글 렌더링
            renderComments(data.comment, commentList);

            // 댓글 수 버튼에 표시
            const toggleCommentsBtn = document.getElementById('toggleCommentsBtn');
            toggleCommentsBtn.textContent = `댓글 보기 (${data.comment.length})`;
        } else {
            commentList.innerHTML = "<li>댓글이 없습니다.</li>";
            document.getElementById('toggleCommentsBtn').textContent = "댓글 보기 (0)";
        }
    } catch (error) {
        console.error("Error fetching comments:", error);
        const commentList = document.getElementById('commentList');
        commentList.innerHTML = `<li>댓글을 불러오는 데 실패했습니다: ${error.message}</li>`;
    }
}

// 댓글 렌더링 함수
function renderComments(comments, commentList) {
    comments.forEach(comment => {
        const li = document.createElement('li');

        li.innerHTML = `
            <p>
                <span>${escapeHTML(comment.content)}</span>
                <span style="float: right; font-size: 0.9em; color: gray;">- ${escapeHTML(comment.writer)}</span>
            </p>
            <span class="comment-time">${formatDate(comment.createTime)}</span>
            <button class="like-comment-btn" onclick="likeComment(${comment.id})">좋아요</button>
            <button class="show-replies-btn" onclick="showReplies(${comment.id})">대댓글 보기</button>
            <button class="write-reply-btn" onclick="showReplyForm(${comment.id})">대댓글 작성</button>

            <!-- 대댓글 목록 -->
            <ul id="replies-${comment.id}" class="replies-list" style="display: none;"></ul>

            <!-- 대댓글 입력 폼 -->
            <form id="reply-form-${comment.id}" class="reply-form" style="display: none;">
                <textarea id="reply-input-${comment.id}" placeholder="대댓글을 입력하세요..." required></textarea>
                <button type="submit" onclick="submitReply(${comment.id}, event)">등록</button>
            </form>
        `;
        commentList.appendChild(li);
    });
}

// HTML 이스케이프 함수 (XSS 방지)
function escapeHTML(str) {
    if (!str) return '';
    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;');
}

// 날짜 포맷팅 함수
function formatDate(dateString) {
    return new Date(dateString).toLocaleString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// 댓글 보이기/숨기기 토글 함수
function toggleComments() {
    const commentSection = document.querySelector('.comments-section');
    const commentList = document.getElementById('commentList');
    const toggleCommentsBtn = document.getElementById('toggleCommentsBtn');

    if (commentList.style.display === "none" || commentList.style.display === "") {
        commentList.style.display = "block";

        // 댓글 입력 폼 추가
        if (!document.querySelector('#commentForm')) {
            const formHtml = `
                <form id="commentForm" onsubmit="submitComment(event)">
                    <textarea id="commentInput" placeholder="댓글을 입력하세요..." required></textarea>
                    <button type="submit">등록</button>
                </form>
            `;
            commentSection.insertAdjacentHTML('afterbegin', formHtml);
        }

        toggleCommentsBtn.textContent = toggleCommentsBtn.textContent.replace("보기", "숨기기");
    } else {
        commentList.style.display = "none";

        // 댓글 입력 폼 제거
        const commentForm = document.querySelector('#commentForm');
        if (commentForm) commentForm.remove();

        toggleCommentsBtn.textContent = toggleCommentsBtn.textContent.replace("숨기기", "보기");
    }
}

// 대댓글 입력 폼 표시 함수
function showReplyForm(commentId) {
    const replyForm = document.getElementById(`reply-form-${commentId}`);
    replyForm.style.display = replyForm.style.display === "block" ? "none" : "block";

    if (replyForm.style.display === "block") {
        document.getElementById(`reply-input-${commentId}`).focus();
    }
}

// 대댓글 목록 표시 함수
async function showReplies(commentId) {
    const repliesList = document.getElementById(`replies-${commentId}`);

    // 이미 표시되어 있으면 토글
    if (repliesList.style.display === "block") {
        repliesList.style.display = "none";
        return;
    }

    try {
        repliesList.innerHTML = "<li>대댓글을 불러오는 중...</li>";
        repliesList.style.display = "block";

        const response = await fetch(`/replicaComment/${commentId}`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`대댓글 데이터를 가져오는 데 실패했습니다. 상태 코드: ${response.status}, 메시지: ${errorText}`);
        }

        const data = await response.json();
        repliesList.innerHTML = "";

        if (data.replies && data.replies.length > 0) {
            // 대댓글 렌더링
            data.replies.forEach(reply => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <p>
                        <span>${escapeHTML(reply.content)}</span>
                        <span style="float: right; font-size: 0.9em; color: gray;">- ${escapeHTML(reply.writer)}</span>
                    </p>
                    <span class="reply-time">${formatDate(reply.createTime)}</span>
                `;
                repliesList.appendChild(li);
            });
        } else {
            repliesList.innerHTML = "<li>대댓글이 없습니다.</li>";
        }

    } catch (error) {
        console.error("Error fetching replies:", error);
        repliesList.innerHTML = `<li>대댓글을 불러오는 데 실패했습니다: ${error.message}</li>`;
    }
}

// 대댓글 제출 함수
async function submitReply(commentId, event) {
    event.preventDefault();

    const replyInput = document.getElementById(`reply-input-${commentId}`);
    const content = replyInput.value.trim();

    if (!content) {
        alert("대댓글 내용을 입력해주세요.");
        replyInput.focus();
        return;
    }

    const submitButton = event.submitter;
    submitButton.disabled = true;
    submitButton.textContent = "등록 중...";

    try {
        const response = await fetch(`/replicaComment/${commentId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "대댓글 등록에 실패했습니다.");
        }

        const data = await response.json();
        alert(data.message || "대댓글이 등록되었습니다!");

        // 입력 필드 초기화 및 대댓글 목록 갱신
        replyInput.value = "";
        await showReplies(commentId);

    } catch (error) {
        console.error("Error submitting reply:", error);
        alert(`대댓글 등록 중 오류가 발생했습니다: ${error.message}`);
    } finally {
        if (submitButton.disabled) {
            submitButton.disabled = false;
            submitButton.textContent = "등록";
        }
    }
}

// 댓글 등록 함수
async function submitComment(event) {
    event.preventDefault();

    const commentInput = document.getElementById('commentInput');
    const content = commentInput.value.trim();

    if (!content) {
        alert("댓글 내용을 입력해주세요.");
        commentInput.focus();
        return;
    }

    const submitButton = event.submitter;
    submitButton.disabled = true;
    submitButton.textContent = "등록 중...";

    try {
        const response = await fetch(`/write/${postId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "댓글 등록에 실패했습니다.");
        }

        const data = await response.json();
        alert(data.message || "댓글이 등록되었습니다!");

        // 입력 필드 초기화 및 댓글 목록 갱신
        commentInput.value = "";
        await fetchComments();

    } catch (error) {
        console.error("Error submitting comment:", error);
        alert(`댓글 등록 중 오류가 발생했습니다: ${error.message}`);
    } finally {
        if (submitButton.disabled) {
            submitButton.disabled = false;
            submitButton.textContent = "등록";
        }
    }
}

// 좋아요 기능 함수 (댓글)
async function likeComment(commentId) {
    try {
        const likeButton = event.target;
        likeButton.disabled = true;
        likeButton.textContent = "처리 중...";

        const response = await fetch(`/like/comment`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ entityId: commentId })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "댓글 좋아요 요청에 실패했습니다.");
        }

        const data = await response.json();
        likeButton.textContent = `좋아요 ${data.likeCount || ''}`;

    } catch (error) {
        console.error("Error liking comment:", error);
        alert(`좋아요 요청 중 오류가 발생했습니다: ${error.message}`);
    } finally {
        if (event.target.disabled) {
            event.target.disabled = false;
            if (event.target.textContent === "처리 중...") {
                event.target.textContent = "좋아요";
            }
        }
    }
}

// 좋아요 기능 함수 (게시글)
async function likePost() {
    try {
        const likeButton = document.getElementById('likePostBtn');
        likeButton.disabled = true;
        likeButton.textContent = "처리 중...";

        const response = await fetch(`/like/post`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ entityId: postId })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "게시글 좋아요 요청에 실패했습니다.");
        }

        const data = await response.json();
        likeButton.textContent = `좋아요 ${data.likeCount || ''}`;

    } catch (error) {
        console.error("Error liking post:", error);
        alert(`좋아요 요청 중 오류가 발생했습니다: ${error.message}`);
    } finally {
        const likeButton = document.getElementById('likePostBtn');
        if (likeButton.disabled) {
            likeButton.disabled = false;
            if (likeButton.textContent === "처리 중...") {
                likeButton.textContent = "좋아요";
            }
        }
    }
}

// 삭제 기능 함수
async function deletePost() {
    if (!confirm('정말로 이 게시물을 삭제하시겠습니까?')) {
        return;
    }

    try {
        const deleteButton = document.getElementById('deletePostBtn');
        if (deleteButton) {
            deleteButton.disabled = true;
            deleteButton.textContent = "삭제 중...";
        }

        const response = await fetch(`/delete/${postId}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "게시글 삭제 요청에 실패했습니다.");
        }

        const data = await response.json();
        alert(data.message || "게시글이 삭제되었습니다.");
        window.location.href = '/';

    } catch (error) {
        console.error('Error:', error);
        alert(`삭제에 실패했습니다: ${error.message}`);

        const deleteButton = document.getElementById('deletePostBtn');
        if (deleteButton && deleteButton.disabled) {
            deleteButton.disabled = false;
            deleteButton.textContent = "삭제";
        }
    }
}
