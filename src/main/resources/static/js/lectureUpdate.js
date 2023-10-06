document.addEventListener("DOMContentLoaded", function () {
    // 강의 등록 버튼 클릭 시 토글 처리
    const addLectureButton = document.getElementById("addLectureButton");
    const addSection = document.querySelector(".add-section");

    addLectureButton.addEventListener("click", function () {
        if (addSection.style.display === "none" || addSection.style.display === "") {
            addSection.style.display = "block";
        } else {
            addSection.style.display = "none";
        }
    });

    // 강의 수정 버튼 클릭 시 토글 처리
    const editLectureButton = document.getElementById("editLectureButton");

    editLectureButton.addEventListener("click", function() {
        const lectureNo = editLectureButton.getAttribute("data-lecture-no");
        const editSection = document.getElementById("editSection" + lectureNo);
        const addSection = document.querySelector(".add-section");

        if (editSection.style.display === "none" || editSection.style.display === "") {
            editSection.style.display = "block";
            addSection.style.display = "none"; // 강의 수정을 보여주면 강의 등록을 숨깁니다.
        } else {
            editSection.style.display = "none";
        }
    });

    // 강의 등록 폼 제출 기능
    const addLectureForm = document.querySelector("#addLectureForm");

    addLectureForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // 각 입력 필드에서 데이터 수집
        const lectureYear = document.querySelector("#lectureYear").value;
        const lectureCourse = document.querySelector("#lectureCourse").value;
        const lectureTitle = document.querySelector("#lectureTitle").value;
        const lectureDesc = document.querySelector("#lectureDesc").value;
        const lectureStartAt = document.querySelector("#lectureStartAt").value;
        const lectureEndAt = document.querySelector("#lectureEndAt").value;

        // 수집한 데이터를 객체로 만듦
        const formData = {
            lectureYear: lectureYear,
            lectureCourse: lectureCourse,
            lectureTitle: lectureTitle,
            lectureDesc: lectureDesc,
            lectureStartAt: lectureStartAt,
            lectureEndAt: lectureEndAt
        };

        // 서버로 데이터를 JSON 형식으로 전송
        fetch("/admin/api/lectures/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("서버 응답 오류");
                }
                // POST 요청 후 서버에서 리다이렉션을 수행하면 이 코드는 실행되지 않음
                return response.json();
            })
            .then(data => {
                // 성공 시 강의 목록 페이지로 리다이렉션
                window.location.href = "/admin/online/lecture-all";
            })
            .catch(error => {
                console.error("에러 발생: " + error);
            });
    });
});

// 강의 수정 기능

// 강의 삭제 기능
// 삭제 버튼 클릭 시 실행될 함수
document.getElementById('deleteSelected').addEventListener('click', function () {
    const selectedCheckboxes = document.querySelectorAll('.lecture-checkbox:checked');
    const lectureNos = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);

    if (lectureNos.length === 0) {
        alert('선택된 강의가 없습니다.');
        return;
    }

    // 선택한 강의의 lectureNo 배열을 서버로 전송
    fetch('/admin/api/lectures/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ lectureNos }),
    })
        .then(response => response.json())
        .then(data => {
            alert(data); // 서버에서 반환한 메시지를 표시
            location.reload(); // 페이지 새로 고침
        })
        .catch(error => {
            console.error('삭제 요청 중 오류 발생:', error);
        });
});