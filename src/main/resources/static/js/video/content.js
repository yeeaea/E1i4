
// 콘텐츠 등록 부분
document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("registerBtn").addEventListener("click", function (event) {
        event.preventDefault();

        // 각 입력 필드에서 데이터 수집
        const contentNo = document.querySelector("#contentNo").value;
        const contentName = document.querySelector("#contentName").value;
        //const contentFileNo = document.querySelector("#contentFileNo").value;
        const ytbUrl = document.querySelector("#ytbUrl").value;
        const contentDesc = document.querySelector("#contentDesc").value;
        const contentUrl = document.querySelector("#contentUrl").value;
        const runTm = document.querySelector("#runTm").value;

        // 입력 필드가 비어 있는지 확인
        if (!contentName || !ytbUrl || !contentDesc || !contentUrl || !runTm) {
            alert("모든 필드를 입력해야 합니다!");
            return; // 입력 필드가 비어 있을 때 아래 코드를 실행하지 않음
        }

        // 수집한 데이터를 객체로 만듦
        const formData = {
            contentNo: contentNo,
            contentName: contentName,
            //contentFileNo: contentFileNo,
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
                alert("등록 했습니다!");
                // 성공 시 콘텐츠 목록 페이지로 리다이렉션
                window.location.href = "/admin/content";
            })
            .catch(error => {
                console.error("에러 발생: " + error);
            });
    });
});

// 콘텐츠 수정 부분
document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("updateBtn").addEventListener("click", function (event) {
        event.preventDefault();

        // 각 입력 필드에서 데이터 수집
        const contentNo = document.querySelector("#contentNo").value;
        const contentName = document.querySelector("#contentName").value;
        //const contentFileNo = document.querySelector("#contentFileNo").value;
        const ytbUrl = document.querySelector("#ytbUrl").value;
        const contentDesc = document.querySelector("#contentDesc").value;
        const contentUrl = document.querySelector("#contentUrl").value;
        const runTm = document.querySelector("#runTm").value;

        // 입력 필드가 비어 있는지 확인
        if (!contentName || !ytbUrl || !contentDesc || !contentUrl || !runTm) {
            alert("모든 필드를 입력해야 합니다!");
            return; // 입력 필드가 비어 있을 때 아래 코드를 실행하지 않음
        }

        // 수집한 데이터를 객체로 만듦
        const formData = {
            contentNo: contentNo,
            contentName: contentName,
            //contentFileNo: contentFileNo,
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
                alert("수정 했습니다!");
                // 성공 시 콘텐츠 목록 페이지로 리다이렉션
                window.location.href = "/admin/content";
            })
            .catch(error => {
                console.error("에러 발생: " + error);
            });
    });
});


// 리스트의 행 클릭하면 값 가져오기
function rowClicked() {
    let table = document.querySelector('table'); // 원하는 테이블을 선택

    if (table !== null) {
        let rows = table.querySelectorAll('tbody tr'); // 모든 행을 선택
        rows.forEach(row => {
            row.addEventListener('click', function () {
                // 클릭된 행에서 데이터를 가져오는 코드
                let contentNo = this.cells[0].innerText;
                let contentName = this.cells[1].innerText;
                let contentDesc = this.cells[2].innerText;
                //let contentFileNo = this.cells[3].innerText;
                let ytbUrl = this.cells[3].innerText;
                let contentUrl = this.cells[4].innerText;
                let runTm = this.cells[5].innerText;

                // 가져온 데이터를 원하는 곳에 넣는 코드
                document.querySelector("#contentNo").value = contentNo;
                document.querySelector("#contentName").value = contentName;
                document.querySelector("#contentDesc").value = contentDesc;
                //document.querySelector("#contentFileNo").value = contentFileNo;
                document.querySelector("#ytbUrl").value = ytbUrl;
                document.querySelector("#contentUrl").value = contentUrl;
                document.querySelector("#runTm").value = runTm;
            });
        });
    } else {
        console.error("테이블이 존재하지 않습니다.");
    }
}
window.onload = rowClicked;

// 콘텐츠 삭제 부분
document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('deleteBtn').addEventListener('click', event => {
        const isConfirmed = confirm('콘텐츠를 삭제하시겠습니까?');
        if (isConfirmed) {

            let contentNo = document.getElementById('contentNo').value;

            fetch(`/api/admin/content/${contentNo}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert("삭제 되었습니다.");
                    location.replace("/admin/content");
                });
        }
    });
});


