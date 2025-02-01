// postDetail.js

// URL에서 게시물 ID 추출 (예: "/posts/3" → "3")
const postId = window.location.pathname.split('/').pop();

// 게시물 상세 정보 조회
async function fetchPostDetail() {
    try {
        const response = await fetch(`/postDetail/${postId}`);
        if (!response.ok) throw new Error("데이터를 가져오는 데 실패했습니다.");

        const data = await response.json();

        // 제목, 날짜, 내용 삽입
        document.getElementById('postTitle').textContent = data.title || "제목 없음";

        // 날짜 포맷팅
        const formattedDate = new Date(data.createTime).toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
        document.getElementById('postDate').textContent = formattedDate;

        document.getElementById('postContent').textContent = data.content || "내용 없음";

        // 댓글 리스트 렌더링
        const commentList = document.getElementById('commentList');
        commentList.innerHTML = "";

        if (data.comment?.length > 0) {
            data.comment.forEach(comment => {
                const li = document.createElement('li');
                li.textContent = comment;
                commentList.appendChild(li);
            });
        } else {
            commentList.innerHTML = "<li>댓글이 없습니다.</li>";
        }

    } catch (error) {
        console.error("Error fetching post detail:", error);
        document.getElementById('postContent').textContent = "게시물을 불러오는 데 실패했습니다.";
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

// 초기 실행
fetchPostDetail();
