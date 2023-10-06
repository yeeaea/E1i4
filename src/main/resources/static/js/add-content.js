document.addEventListener("DOMContentLoaded", function () {
    const addContentForm = document.querySelector("#addContentForm");

    addContentForm.addEventListener("submit", function (event) {
        event.preventDefault();

        // 각 입력 필드에서 데이터 수집
        const contentNo = document.querySelector("#contentNo").value;
        const contentName = document.querySelector("#contentName").value;
        const contentFileNo = document.querySelector("#contentFileNo").value;
        const ytbUrl = document.querySelector("#ytbUrl").value;
        const contentDesc = document.querySelector("#contentDesc").value;
        const contentUrl = document.querySelector("#contentUrl").value;
        const runTm = document.querySelector("#runTm").value;

        // 수집한 데이터를 객체로 만듦
        const formData = {
            contentNo: contentNo,
            contentName: contentName,
            contentFileNo: contentFileNo,
            ytbUrl: ytbUrl,
            contentDesc: contentDesc,
            contentUrl: contentUrl,
            runTm: runTm
        };

        // 서버로 데이터를 JSON 형식으로 전송
        fetch("/api/admin/content-edit", {
            method: "PUT",
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
                // 성공 시 콘텐츠 목록 페이지로 리다이렉션
                window.location.href = "/admin/content-edit";
            })
            .catch(error => {
                console.error("에러 발생: " + error);
            });
    });
});
