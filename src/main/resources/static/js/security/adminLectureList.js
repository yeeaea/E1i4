document.getElementById("lookupStudentBtn").addEventListener("click", function(){

        const lectureNo = this.getAttribute('data-lecture-no');
        console.log("조회된 강의 번호 : " + lectureNo);
        // Ajax 요청을 통해 서버에서 수강생 목록을 가져오는 로직
        const xhr = new XMLHttpRequest();
        xhr.open('GET', '/admin/memberInfo/lectureNo/' + lectureNo,true);
        xhr.onreadystatechange = function(){
            if(xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                // 수강생 데이터를 화면에 출력하는 로직
                console.log(response);
            } else if(xhr.readyState === 4 && xhr.status !== 200){
                alert('수강생 정보를 불러오는 데 실패했습니다.');
            }
        };
        xhr.send();
});