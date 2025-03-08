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

// 최초 로딩 시 전체 게시물 로드
fetchAllPosts(currentPage);
