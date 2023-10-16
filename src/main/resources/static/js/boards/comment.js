// 댓글 등록
document.addEventListener('DOMContentLoaded', function () {
    // Submit 버튼 클릭 이벤트 핸들러
    const submitButton = document.getElementById("submit-comment");
    submitButton.addEventListener("click", () => {
        // 댓글 내용과 질문 번호 가져오기
        const commentContent = document.getElementById("commentContent").value;
        const postNo = document.getElementById("postNo").value;

        // 댓글 데이터 생성
        const commentData = {
            commentContent: commentContent,
            postNo: postNo
        };

        if (commentContent.trim() === "") {
            alert("댓글 내용을 입력해주세요!");
            return;
        } else {
            // 서버로 댓글 데이터 전송
            fetch("/api/comments", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(commentData)
            })
                .then(response => response.json())
                .then(data => {
                    // 페이지 새로 고침
                    location.reload();
                })
                .catch(error => {
                    console.error("Error:", error);
                });
        }
    });
});

// 댓글 삭제
document.addEventListener('DOMContentLoaded', function () {
    const commentList = document.getElementById('comment-list');

    commentList.addEventListener('click', (event) => {
        const deleteButton = event.target;
        if (deleteButton.classList.contains('cmt-delete-btn')) {
            // 삭제 버튼의 부모 요소인 댓글 요소를 찾습니다.
            const commentItem = deleteButton.closest('li'); // 또는 댓글 요소를 감싸는 부모 요소의 셀렉터를 사용

            if (commentItem) { // 댓글 요소를 찾은 경우에만 실행
                const confirmation = confirm('댓글을 삭제하시겠습니까?');

                if (confirmation) {
                    const commentNoElement = commentItem.querySelector('.comment-no');
                    const commentNo = parseInt(commentNoElement.textContent);

                    if (!isNaN(commentNo)) {
                        fetch(`/api/comments/${commentNo}`, {
                            method: "DELETE"
                        })
                            .then(response => {
                                if (response.status === 200) {
                                    // 삭제 요청이 성공하면 화면에서 해당 댓글을 제거합니다.
                                    commentItem.remove();
                                    console.log(`${commentNo}번 댓글 삭제`);

                                    // 페이지 새로고침
                                    location.reload();
                                } else {
                                    console.error("댓글 삭제 실패");
                                }
                            })
                            .catch(error => {
                                console.error("Error:", error);
                            });
                    }
                }
            }
        }
    });
});



// 댓글 수정
document.addEventListener("DOMContentLoaded", function () {
    let editingCommentNo = null;

    // 공통 함수: 수정 폼을 보이거나 숨기고, 내용을 설정하는 함수
    function toggleEditFormAndContent(commentItem, updatedContent) {
        const commentContent = commentItem.querySelector('.comment-content');
        const editForm = commentItem.querySelector('.edit-comment-form');
        const editTextarea = editForm.querySelector('.edit-comment-textarea');

        editTextarea.value = commentContent.textContent;
        updatedContent.value = commentContent.textContent;

        commentContent.style.display = 'none';
        editForm.style.display = 'block';
    }

    // "수정" 버튼 클릭 시
    document.querySelectorAll('.edit-comment-btn').forEach(button => {
        button.addEventListener('click', () => {
            const commentItem = button.closest('.list-group-item');
            const editTextarea = commentItem.querySelector('.edit-comment-textarea');

            toggleEditFormAndContent(commentItem, editTextarea);

            // 클릭된 "수정" 버튼의 부모 요소인 댓글 요소에서 댓글 번호 추출
            const commentNoElement = commentItem.querySelector('.comment-no');
            const commentNo = parseInt(commentNoElement.textContent);

            if (!isNaN(commentNo)) { // 파싱한 결과가 NaN이 아닌 경우에만 사용
                editingCommentNo = commentNo;
            }
        });
    });

    // "저장" 및 "취소" 버튼 클릭 시
    document.querySelectorAll('.save-comment-btn, .cancel-comment-btn').forEach(button => {
        button.addEventListener('click', () => {
            const editForm = button.closest('.edit-comment-form');
            const editTextarea = editForm.querySelector('.edit-comment-textarea');
            const commentContent = editForm.parentElement.querySelector('.comment-content');
            const commentDate = editForm.parentElement.querySelector('.comment-date');

            if (button.classList.contains('save-comment-btn') && editingCommentNo !== null) {
                const updatedContent = editTextarea.value;

                // 댓글 내용이 없으면 alert
                if (updatedContent.trim() === "") {
                    alert("수정할 댓글 내용을 입력해주세요!");
                    return;
                }

                // 서버에 업데이트 요청 보내기
                fetch(`/api/comments/${editingCommentNo}`, { // 수정 중인 댓글 번호 사용
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({commentContent: updatedContent})
                })
                    .then(response => {
                        if (response.status === 200) {
                            // 성공적으로 업데이트되면 화면에 수정된 내용과 시간 반영
                            commentContent.textContent = updatedContent;
                            const now = new Date();
                            commentDate.textContent = now.toLocaleString();
                            alert("수정을 완료했습니다.");
                            location.reload();
                        } else {
                            console.error("댓글 수정 실패");
                        }
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            }

            // 수정 폼을 숨기고 댓글 내용을 다시 보이도록 설정
            editForm.style.display = 'none';
            commentContent.style.display = 'block';

            // 수정할 댓글 번호 초기화
            editingCommentNo = null;
        });
    });
});

// 댓글 길이 카운팅
function countingLength(inputElementId, counterElementId) {
    let inputElement = document.getElementById(inputElementId);
    let counter = document.getElementById(counterElementId);

    if (inputElement.value.length > 200) {
        alert('최대 200자까지 입력 가능합니다.');
        inputElement.value = inputElement.value.substring(0, 200);
        inputElement.focus();
    }

    counter.innerText = inputElement.value.length + '/200자';
}


document.addEventListener("DOMContentLoaded", function () {
    // 호출할 때 함수 이름을 다르게 지정하여 각각의 입력 필드에 적용
    let commentContent = document.getElementById('commentContent');
    let counterComment = document.getElementById('counterComment');
    commentContent.addEventListener('input', function () {
        countingLength('commentContent', 'counterComment');
    });

    let editCommentContent = document.getElementById('editCommentContent');
    let counterEdit = document.getElementById('counterEdit');
    editCommentContent.addEventListener('input', function () {
        countingLength('editCommentContent', 'counterEdit');
    });
});
