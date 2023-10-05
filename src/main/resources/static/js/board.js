// 생성 기능
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

// 삭제

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



