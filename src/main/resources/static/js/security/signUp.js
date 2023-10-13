<!-- 아이디 중복 체크 : Ajax -->
document.addEventListener("DOMContentLoaded", function(){
    // 중복 체크 버튼, 입력된 아이디, 결과
    let checkLoginIdBtn = document.getElementById("checkLoginIdBtn");
    let loginIdInput = document.getElementById("duplicateId");
    let resultElement = document.getElementById("result");

    checkLoginIdBtn.addEventListener("click", function(){
        // 사용자가 입력한 아이디 값을 가져와 memberId 변수에 저장
        let loginId = loginIdInput.value;
        //alert("로그인 아이디 " + loginId);
        // XMLHttpRequest ajax 객체 생성
        let xhr = new XMLHttpRequest();

        // 요청 설정
        xhr.open("GET", '/signup/checkId?loginId=' + loginId, true);
        // 요청이 완료되면 실행할 함수 설정
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) { // 요청이 완료됨
                if (xhr.status === 200) { // 요청이 성공함
                    // 서버에서 받은 데이터를 결과를 표시할 HTML 요소(#result)의 내용으로 설정

                    // 결과를 화면에 표시
                    resultElement.innerHTML = xhr.responseText;
                } else {
                    console.error(xhr.responseText);
                    resultElement.innerHTML = '오류 발생';
                }
            }
        };
        // 요청 보내기
        xhr.send();
    })
})