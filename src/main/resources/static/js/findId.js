document.addEventListener("DOMContentLoaded", function() {

    let findIdBtn = document.getElementById("findIdBtn");
    let findIdResult = document.getElementById("findIdResult");
    let modalBackdrop = document.getElementById("staticBackdrop");


    findIdBtn.addEventListener("click", function() {

        let memberName = document.getElementById("memberName").value;
        let memberEmail = document.getElementById("memberEmail").value;


        // XMLHttpRequest 생성
        let xhr = new XMLHttpRequest();

        // POST 요청 설정
        xhr.open("POST", "/lms/find-id", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // 요청 본문 생성
        let data = "memberName=" + encodeURIComponent(memberName) + "&memberEmail=" + encodeURIComponent(memberEmail);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    findIdResult.textContent = xhr.response;
                    // 아이디를 찾았을 때 모달 창 띄우기 ( 그 전까지는 숨기기 - CSS에 적용)
                    modalBackdrop.style.display = "block";
                }else {
                    alert("아이디 찾기 오류");
                }
            }
        };

        // 요청 전송
        xhr.send(data);

    });
});