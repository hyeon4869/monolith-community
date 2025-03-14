let currentPage = 0; // 현재 페이지 번호
const pageSize = 20; // 한 페이지당 게시물 수

// 기존의 검색 기능 (좋아요 포함)
async function fetchPostList(page, title = "") {
    try {
        const response = await fetch(`/postSearch?title=${title}&page=${page}&size=${pageSize}`);
        if (!response.ok) throw new Error("데이터를 가져오는 데 실패했습니다.");

        const data = await response.json();
        const loginEmail = data["loginEmail"];

        const postList = document.getElementById('postList');
        postList.innerHTML = "";

        data["검색된 내용"].forEach(post => {
            const li = document.createElement('li');
            li.innerHTML = `
                <a href="/detail/${post.id}">
                    <h3>${post.title}</h3>
                    <p>작성자 이메일: ${post.memberEmail}</p>
                    <span class="like-count">좋아요 수: ${post.likeCount}</span>
                </a>
                ${post.memberEmail === loginEmail ? `
                    <button class="delete-btn" onclick="deletePost(${post.id})">삭제</button>
                ` : ''}
            `;
            postList.appendChild(li);
        });

        document.getElementById('prevPage').disabled = data["현재페이지"] === 0;
        document.getElementById('nextPage').disabled = data["현재페이지"] + 1 >= data["전체페이지수"];

    } catch (error) {
        console.error("Error fetching post list:", error);
        document.getElementById('postList').innerHTML = "<li>게시물을 불러오는 데 실패했습니다.</li>";
    }
}

// 전체 게시물 조회 기능 추가 (/postFindAll)
async function fetchAllPosts(page) {
    try {
        const response = await fetch(`/postFindAll?page=${page}&size=${pageSize}`);
        if (!response.ok) throw new Error("전체 게시물을 가져오는 데 실패했습니다.");

        const data = await response.json();

        const postList = document.getElementById('postList');
        postList.innerHTML = "";

        data["게시물"].forEach(post => {
            const li = document.createElement('li');
            li.innerHTML = `
                <a href="/detail/${post.id}">
                    <h3>${post.title}</h3>
                    <p>작성자 이메일: ${post.memberEmail}</p>
                    <span class="like-count">좋아요 수: ${post.likeCount}</span>
                </a>
            `;
            postList.appendChild(li);
        });

        document.getElementById('prevPage').disabled = data["현재페이지"] === 0;
        document.getElementById('nextPage').disabled = data["현재페이지"] + 1 >= data["전체페이지수"];

    } catch (error) {
        console.error("Error fetching all posts:", error);
        document.getElementById('postList').innerHTML = "<li>전체 게시물을 불러오는 데 실패했습니다.</li>";
    }
}

// 게시물 삭제 요청 함수
async function deletePost(postId) {
    if (!confirm("정말로 이 게시물을 삭제하시겠습니까?")) return;

    try {
        const response = await fetch(`/delete/${postId}`, { method: 'PATCH' });
        if (!response.ok) throw new Error("게시물 삭제에 실패했습니다.");

        const result = await response.json();
        alert(result.message);

        fetchPostList(currentPage);
    } catch (error) {
        console.error("Error deleting post:", error);
        alert("게시물 삭제 중 오류가 발생했습니다.");
    }
}

// 게시물 등록 요청 함수
async function registerPost() {
    const titleInput = document.getElementById('postTitle');
    const contentInput = document.getElementById('postContent');

    const postDTO = {
        title: titleInput.value,
        content: contentInput.value,
    };

    try {
        const response = await fetch('/postRegistration', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(postDTO),
        });

        if (!response.ok) throw new Error("게시물 등록에 실패했습니다.");

        const result = await response.json();
        alert(result.message);

        titleInput.value = '';
        contentInput.value = '';

        fetchAllPosts(currentPage); // 등록 후 전체 목록 새로고침
    } catch (error) {
        console.error("Error registering post:", error);
        alert("게시물 등록 중 오류가 발생했습니다.");
    }
}

// 검색 요청 함수
function searchPosts() {
    const title = document.getElementById('searchInput').value;
    currentPage = 0;
    fetchPostList(currentPage, title);
}

// 페이지 변경 로직 (검색용)
function changePage(direction) {
    currentPage += direction;

    if (currentPage < 0) currentPage = 0;

    const title = document.getElementById('searchInput').value;

    if(title.trim() === "") {
      fetchAllPosts(currentPage); // 검색어 없으면 전체조회
    } else {
      fetchPostList(currentPage, title); // 검색어 있을 때만 검색 API 호출
    }
}

// 알림 버튼 클릭 이벤트 처리
const notificationBtn = document.getElementById('notification-btn');
if (notificationBtn) {
    notificationBtn.addEventListener('click', () => {
        const notificationArea = document.getElementById('notification-area');
        notificationArea.style.display = notificationArea.style.display === 'block' ? 'none' : 'block';
    });
}

// SSE를 사용하여 실시간 알림 수신
function setupNotificationListener() {
    const email = localStorage.getItem('userEmail'); // 로컬 스토리지에서 이메일 읽어옴
    if (!email) {
        console.error("이메일이 로컬 스토리지에 저장되어 있지 않습니다.");
        return;
    }

    console.log("알림 연결 설정 중... 이메일:", email);
    const eventSource = new EventSource(`/api/notifications/${email}`);

    eventSource.onmessage = (event) => {
        console.log('알림 수신:', event.data);
        const notificationList = document.getElementById('notifications');
        if (notificationList) {
            const li = document.createElement('li');
            li.textContent = event.data; // 서버에서 전송된 알림 메시지
            notificationList.appendChild(li);

            // 알림이 도착하면 알림 영역을 표시합니다
            const notificationArea = document.getElementById('notification-area');
            if (notificationArea) {
                notificationArea.style.display = 'block';
            }
        }
    };

    eventSource.onerror = (error) => {
        console.error('알림 수신 중 오류 발생:', error);
        // 일정 시간 후 재연결 시도
        setTimeout(() => {
            setupNotificationListener();
        }, 5000);
    };

    eventSource.onopen = () => {
        console.log('알림 연결 설정 완료');
    };
}

// 페이지 로드 시 실행되는 초기화 함수
document.addEventListener('DOMContentLoaded', () => {
    // 알림 기능 설정
    setupNotificationListener();

    // 최초 로딩 시 전체 게시물 로드
    const postList = document.getElementById('postList');
    if (postList) {
        fetchAllPosts(currentPage);
    }
});
