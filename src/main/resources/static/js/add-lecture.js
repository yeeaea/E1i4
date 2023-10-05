document.addEventListener("DOMContentLoaded", function () {
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
