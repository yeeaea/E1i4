document.addEventListener("DOMContentLoaded", function(){
    const checkEmailBtn = document.getElementById("checkEmail");
    checkEmailBtn.addEventListener("click", checkEmail);

    function checkEmail(){
        const memberEmail = document.getElementById("memberEmail").value;

        if( !memberEmail || memberEmail.trim() === "") {
            // 사용자의 이메일이 입력되지 않은 경우, 알림창 출력
            alert("이메일을 입력하세요.");
        } else {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", '/lms/find-pw/check?memberEmail=' + encodeURIComponent(memberEmail), true);

            xhr.onload = function() {
                if(xhr.status == 200) {
                    const result = xhr.responseText;
                    console.log("result : " + result);
                    if(result === "true"){
                        sendEmail();
                        //alert("이메일이 존재합니다.");
                    } else if(result === "false"){
                        alert("가입하지 않은 이메일입니다.");
                    }
                } else {
                    alert("서버 오류: " + xhr.status);
                }
            };

            xhr.onerror = function(){
                alert("요청 실패");
            };

            // 요청 전송
            xhr.send();
        }
    }

    function sendEmail(){
        const memberEmail = document.getElementById("memberEmail");
        const xhr = new XMLHttpRequest();

        xhr.open("POST", '/lms/find-pw/send', true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        let data = "memberEmail=" + memberEmail.value;

        xhr.onreadystatechange = function(){
            if(xhr.readyState === XMLHttpRequest.DONE){
                if(xhr.status === 200){
                    // prompt 창에 임시 비밀번호 출력됨
                    prompt(`임시로 발급한 비밀번호는 "${xhr.response}"입니다.` + "\n"
                                    + `확인 버튼을 누르면 자동으로 로그아웃됩니다.` + "\n"
                                    + `아래 임시 비밀번호를 복사 후 다시 로그인해주세요.`, xhr.response);
                    // 로그인 화면으로 이동
                    window.location.href = "/";
                } else {
                    alert("요청 실패");
                }
            }
        };

        // 요청 전송
        xhr.send(data);
    }
});