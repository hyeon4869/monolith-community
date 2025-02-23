// URL에서 게시물 ID 추출 (예: "/posts/3" → "3")
const postId = window.location.pathname.split('/').pop();

// 게시물 상세 정보 및 댓글 조회
async function fetchPostDetail() {
    try {
        // 게시물 상세 정보 가져오기
        const postResponse = await fetch(`/postDetail/${postId}`);
        if (!postResponse.ok) throw new Error("게시물 데이터를 가져오는 데 실패했습니다.");
        const postData = await postResponse.json();

        // 제목, 날짜, 내용 삽입
        document.getElementById('postTitle').textContent = postData.title || "제목 없음";

        // 날짜 포맷팅
        const formattedDate = new Date(postData.createTime).toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        document.getElementById('postDate').textContent = formattedDate;

        document.getElementById('postContent').textContent = postData.content || "내용 없음";

        // 댓글 데이터 가져오기
        await fetchComments();
    } catch (error) {
        console.error("Error fetching post detail:", error);
        document.getElementById('postContent').textContent = "게시물을 불러오는 데 실패했습니다.";
    }
}

// 댓글 데이터 조회 및 렌더링
async function fetchComments() {
    try {
        const response = await fetch(`/postComment/${postId}`);
        if (!response.ok) throw new Error("댓글 데이터를 가져오는 데 실패했습니다.");

        const data = await response.json();
        const commentList = document.getElementById('commentList');
        commentList.innerHTML = "";

        if (data.comment && data.comment.length > 0) {
            // 댓글 렌더링
            data.comment.forEach(comment => {
                const li = document.createElement('li');

                // 댓글 내용과 작성자 이름 추가
                li.innerHTML = `
                    <p>
                        <span>${comment.content}</span>
                        <span style="float: right; font-size: 0.9em; color: gray;">- ${comment.writer}</span>
                    </p>
                    <span class="comment-time">${formatDate(comment.createTime)}</span>
                    <button class="like-comment-btn" onclick="likeComment(${comment.id})">좋아요</button>
                `;
                commentList.appendChild(li);
            });

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
        commentList.innerHTML = "<li>댓글을 불러오는 데 실패했습니다.</li>";
    }
}

// 게시글 좋아요 요청 함수
async function likePost() {
    try {
        const response = await fetch(`/like/post`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ entityId: postId })
        });

        if (!response.ok) throw new Error("게시글 좋아요 요청에 실패했습니다.");

        alert("게시글에 좋아요를 눌렀습니다!");
    } catch (error) {
        console.error("Error liking post:", error);
        alert("좋아요 요청 중 오류가 발생했습니다.");
    }
}

// 댓글 좋아요 요청 함수
async function likeComment(commentId) {
    try {
        const response = await fetch(`/like/comment`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ entityId: commentId })
        });

        if (!response.ok) throw new Error("댓글 좋아요 요청에 실패했습니다.");

        alert("댓글에 좋아요를 눌렀습니다!");
    } catch (error) {
        console.error("Error liking comment:", error);
        alert("좋아요 요청 중 오류가 발생했습니다.");
    }
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

// 댓글 등록 함수
async function submitComment(event) {
    event.preventDefault(); // 폼 기본 동작 방지

    const commentInput = document.getElementById('commentInput');
    const content = commentInput.value;

    try {
        // 댓글 등록 요청
        const response = await fetch(`/write/${postId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content })
        });

        if (!response.ok) throw new Error("댓글 등록에 실패했습니다.");

        alert("댓글이 등록되었습니다!");

        // 입력 필드 초기화 및 댓글 목록 갱신
        commentInput.value = "";
        await fetchComments();

    } catch (error) {
        console.error("Error submitting comment:", error);
        alert("댓글 등록 중 오류가 발생했습니다.");
    }
}

// 게시물 삭제 함수
function deletePost() {
    if (confirm('정말로 이 게시물을 삭제하시겠습니까?')) {
        fetch(`/delete/${postId}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                window.location.href = '/';
            })
            .catch(error => {
                console.error('Error:', error);
                alert('삭제에 실패했습니다.');
            });
    }
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

// 초기 실행
fetchPostDetail();
