// 버튼 클릭 시, 폼 보이기 / 숨기기
// 초기에 modifyForm = style="display: none; 으로 설정되어 있음
document.getElementById("modifyInfoBtn").addEventListener("click", function(){
    let form1 = document.getElementById("modifyForm");
    let form2 = document.getElementById("originForm");
    if(form1.style.display === "none" || form1.style.display === "") {
        form1.style.display = "block"; // 수정 폼 보이게 설정
        form2.style.display = "none";  // 초기 폼 숨기게 설정
    }
})