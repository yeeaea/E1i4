document.addEventListener("DOMContentLoaded", function() {

    let findIdBtn = document.getElementById("findIdBtn");
    console.log(findIdBtn); // 요소가 제대로 찾아졌는지 확인
    let findIdResult = document.getElementById("findIdResult");
    let toLoginBtn = document.getElementById("toLoginBtn");

    findIdBtn.addEventListener("click", function() {
        console.log("클릭 이벤트가 발생했습니다."); // 클릭 이벤트가 발생하는지 확인
        try {
            let memberName = document.getElementById("memberName").value;
            let memberEmail = document.getElementById("memberEmail").value;
            console.log("사용자 이름 : " + memberName + ", 사용자 이메일 : " + memberEmail);

            // XMLHttpRequest 생성
            let xhr = new XMLHttpRequest();

            // POST 요청 설정
            xhr.open("POST", "/lms/api/members/find-id", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            // 요청 본문 생성
            let data = "memberName=" + encodeURIComponent(memberName) + "&memberEmail=" + encodeURIComponent(memberEmail);
            console.log("data : " + data)
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        findIdResult.textContent = xhr.response;
                        // 아이디 조회 시, '로그인화면으로 이동' 버튼 출력되도록.
                        findIdBtn.style.display = "none";
                        toLoginBtn.style.display = "block";
                        toLoginBtn.addEventListener("click", function(){
                            window.location.href = "/"; // 로그인 페이지로 이동
                        })
                    } else {
                        alert("아이디 찾기 오류");
                    }
                }
            };

            // 요청 전송
            xhr.send(data);
        } catch(error) {
            console.error("클릭 이벤트 핸들러에서 오류 발생 : " + error )
        }
    });
});