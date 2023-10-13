// 게시물 등록
document.addEventListener('DOMContentLoaded', function () {
    const createButton = document.getElementById("create-btn");
    if (createButton) {
        createButton.addEventListener("click", (event) => {
            event.preventDefault();

            const formData = new FormData();
            formData.append("postTitle", document.getElementById("postTitle").value);
            formData.append("postContent", document.getElementById("postContent").value);

            // 파일 업로드 input 요소에서 각각의 파일을 가져와 FormData에 추가
            formData.append("file1", document.getElementById("file1").files[0]);

            let title = document.getElementById("postTitle").value;
            let content = document.getElementById("postContent").value;

            if (title == "" && content == "") {
                alert("제목과 내용을 입력해주세요!")
            } else if (content == "") {
                alert("내용을 입력해주세요!");
            } else if (title == "") {
                alert("제목을 입력해주세요!")
            } else {
                fetch("/api/material", {
                    method: "POST",
                    body: formData,
                }).then((response) => {
                    if (response.status === 201) {
                        alert("등록이 완료되었습니다.");
                        location.replace('/lms/materials');
                    } else {
                        alert("로그인 후 작성이 가능합니다.");
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
                let postNo = document.getElementById('material-postNo').value;

                fetch(`/api/material/${postNo}`, {
                    method: 'DELETE'
                })
                    .then(() => {
                        alert('삭제가 완료되었습니다.');
                        location.replace('/lms/materials');
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

            let title = document.getElementById('postTitle').value;
            let content = document.getElementById('postContent').value;

            const formData = new FormData();
            formData.append("file1", document.getElementById("file1").files[0]);
            formData.append("postTitle", title);
            formData.append("postContent", content);

            if (title == "" && content == "") {
                alert("제목과 내용을 입력해주세요!")
            } else if (content == "") {
                alert("내용을 입력해주세요!");
            } else if (title == "") {
                alert("제목을 입력해주세요!")
            } else {
                fetch(`/api/material/${postNo}`, {
                    method: "PUT",
                    body: formData,
                })
                    .then((response) => {
                        if (response.status === 200) {
                            alert("수정을 완료했습니다.");
                            location.replace('/lms/materials');
                        } else {
                            alert("수정을 실패했습니다.");
                        }
                    })
                    .catch((error) => {
                        console.error("Error:", error);
                        alert("수정을 실패했습니다.");
                    });
            }
        });
    }
});

// 첨부파일 삭제
function deleteFile() {
    let params = new URLSearchParams(location.search);
    let postNo = params.get('postNo');

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/deleteFile", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 파일이 삭제되면 화면에서 파일 정보를 제거
            alert("삭제가 완료되었습니다."); // 삭제 완료 팝업 표시
            location.href = location.href; // 페이지 자동으로 다시 로드
        } else if (xhr.status !== 200) {
            alert("파일 삭제 요청 중 오류가 발생했습니다.");
        }
    };

    xhr.send("postNo=" + postNo);
}


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

// 파일 업로드 목록 확인
const fileInput = document.getElementById('files');
const fileList = document.getElementById('file-list');

// 파일이 선택되면 목록에 추가
document.addEventListener('DOMContentLoaded', function () {
    // 파일 선택 input 요소
    const fileInput = document.getElementById('files');
    // 파일 목록을 표시할 ul 요소
    const fileList = document.getElementById('file-list');

    // 파일이 선택되면 목록에 추가
    fileInput.addEventListener('change', (event) => {
        const selectedFiles = event.target.files;

        // 선택한 파일들을 목록에 추가
        for (const file of selectedFiles) {
            const listItem = document.createElement('li');
            listItem.textContent = file.name;
            fileList.appendChild(listItem);
        }
    });
});
