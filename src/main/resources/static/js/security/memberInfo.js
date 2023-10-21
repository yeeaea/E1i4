// 버튼 클릭 시, 폼 보이기 / 숨기기
// 초기에 modifyForm = style="display: none; 으로 설정되어 있음
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("modifyInfoBtn").addEventListener("click", function () {
        let form1 = document.getElementById("modifyForm");
        let form2 = document.getElementById("originForm");
        let modifyInfoBtn = document.getElementById("modifyInfoBtn");
        let modifyPwBtn = document.getElementById("modifyPwBtn");
        let infoText = document.getElementById("infoText");
        let modifyInfoText = document.getElementById("modifyInfoText");
        if (form1.style.display === "none" || form1.style.display === "") {
            form1.style.display = "block"; // 수정 폼 보이게 설정
            form2.style.display = "none";  // 초기 폼 숨기게 설정
            modifyPwBtn.style.display = "none"; // 비밀번호 변경 버튼 숨기게 설정
            modifyInfoBtn.style.display = "none";
            infoText.style.display = "none";
            modifyInfoText.style.display = "block";
        }
    });

    document.getElementById("saveInfoBtn").addEventListener("click", function(){
        alert("개인 정보가 수정되었습니다.");
    })
});