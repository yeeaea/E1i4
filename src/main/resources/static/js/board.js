// 게시물 생성
document.addEventListener('DOMContentLoaded', function () {

    const createButton = document.getElementById('create-btn');

    if (createButton) {
        createButton.addEventListener('click', event => {
            event.preventDefault();

            const postTitle = document.getElementById('postTitle').value;
            const postContent = document.getElementById('postContent').value;

            let title = document.getElementById('postTitle').value;
            let content = document.getElementById('postContent').value;

            if (title === "" && content === "") {
                alert("제목과 내용을 입력해주세요!");
            } else if (content === "") {
                alert("내용을 입력해주세요!");
            } else if (title === "") {
                alert("제목을 입력해주세요!");
            } else {
                const requestData = {
                    postTitle: postTitle,
                    postContent: postContent
                };

                fetch("/api/post", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(requestData),
                })
                    .then((response) => {
                        if (response.status === 201) {
                            alert("등록이 완료되었습니다.");
                            location.replace("/lms/questions");
                        } else {
                            alert("등록을 실패했습니다.");
                        }
                    })
                    .catch((error) => {
                        console.error("Error: ", error);
                        alert("등록을 실패했습니다.");
                    });
            }
        });
    }
});

// 게시물 삭제
document.addEventListener('DOMContentLoaded', function () {
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            const isConfirmed = confirm('게시글을 삭제하시겠습니까?');
            if (isConfirmed) {
                let postNo = document.getElementById('question-postNo').value;

                fetch(`/api/post/${postNo}`, {
                    method: 'DELETE'
                })
                    .then(() => {
                        alert('삭제가 완료되었습니다.');
                        location.replace('/lms/questions');
                    });
            }
        });
    }
});

// 게시물 수정
document.addEventListener('DOMContentLoaded', function () {
    const modifyButton = document.getElementById('modify-btn');

    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            let params = new URLSearchParams(location.search);
            let postNo = params.get('postNo');

            // 수정된 부분: postTitle과 postContent 값을 가져온 후 콘솔에 출력
            let title = document.getElementById('postTitle').value;
            let content = document.getElementById('postContent').value;
            console.log('postTitle:', title);
            console.log('postContent:', content);

            fetch(`/api/post/${postNo}`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    postTitle: title,
                    postContent: content
                })
            })
                .then(response => {
                    if (response.status === 200) {
                        alert('수정이 완료되었습니다.');
                        location.replace('/lms/questions');
                    } else {
                        alert('수정에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('수정에 실패했습니다.');
                });
        });
    }
});

// 조회수 정렬
function changeSort(sortBy) {
    // 현재 페이지 URL 가져오기
    const currentUrl = new URL(window.location.href);

    // sortBy 파라미터 설정
    currentUrl.searchParams.set('sortBy', sortBy);

    // sort 파라미터 제거 (기존 버전의 파라미터를 제거함)
    currentUrl.searchParams.delete('sort');

    // 현재 페이지 번호 가져오기
    const currentPage = currentUrl.searchParams.get('page');

    // 페이지 번호가 있는 경우 페이지 번호도 설정
    if (currentPage !== null) {
        currentUrl.searchParams.set('page', currentPage);
    }

    // 새로운 URL로 이동
    window.location.href = currentUrl.toString();
}

// 글 내용 1000자 제한
function countingLength(inputElementId, counterElementId) {
    let wordInputElement = document.getElementById(inputElementId);
    let wordCounter = document.getElementById(counterElementId);

    if (wordInputElement.value.length > 1000) {
        alert('최대 1000자까지 입력 가능합니다.');
        wordInputElement.value = wordInputElement.value.substring(0, 1000);
        wordInputElement.focus();
    }

    wordCounter.innerText = wordInputElement.value.length + '/1000자';
}
