let currentPage = 0; // 현재 페이지 번호
const pageSize = 20; // 한 페이지당 게시물 수

// REST API 호출하여 게시물 목록 가져오기
async function fetchPostList(page, title = "") {
    try {
        const response = await fetch(`/postSearch?title=${title}&page=${page}&size=${pageSize}`);
        if (!response.ok) throw new Error("데이터를 가져오는 데 실패했습니다.");

        const data = await response.json();

        // 게시물 리스트 렌더링
        const postList = document.getElementById('postList');
        postList.innerHTML = ""; // 기존 데이터 초기화

        data["검색된 내용"].forEach(post => {
            const li = document.createElement('li');
            li.innerHTML = `
                <a href="/detail/${post.id}">
                    <h3>${post.title}</h3>
                    <p>작성자 이메일: ${post.memberEmail}</p>
                </a>
            `;
            postList.appendChild(li);
        });

        // 페이지 버튼 활성화/비활성화 처리
        document.getElementById('prevPage').disabled = data["현재페이지"] === 0; // 첫 페이지 비활성화
        document.getElementById('nextPage').disabled = data["현재페이지"] + 1 >= data["전체페이지수"]; // 마지막 페이지 비활성화

    } catch (error) {
        console.error("Error fetching post list:", error);
        document.getElementById('postList').innerHTML = "<li>게시물을 불러오는 데 실패했습니다.</li>";
    }
}

// 검색 요청 함수
function searchPosts() {
    const title = document.getElementById('searchInput').value; // 검색어 가져오기
    currentPage = 0; // 검색 시 첫 페이지로 초기화
    fetchPostList(currentPage, title);
}

// 페이지 변경 로직
function changePage(direction) {
    currentPage += direction;

    if (currentPage < 0) currentPage = 0; // 첫 페이지보다 이전으로 이동하지 않음

    const title = document.getElementById('searchInput').value; // 현재 검색어 유지
    fetchPostList(currentPage, title);
}

// 초기 데이터 로드
fetchPostList(currentPage);
