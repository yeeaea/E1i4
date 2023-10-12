function applyForLecture(button) {
    // 현재 로그인한 회원의 회원번호를 서버로 요청
    fetch('/lms/api/members/current-memberNo', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => response.json())
        .then(memberData => {
            if (memberData.success) {
                const memberNo = memberData.memberNo;
                // 강의 번호 추출
                const lectureNoString = button.getAttribute("data-lecture-no");

                // Long형으로 변환
                const lectureNo = parseInt(lectureNoString);
                const completionYn = false;

                console.log("강의 번호: " + lectureNo);
                // Long형으로 변환하여 서버에 보낼 데이터 준비
                let data = JSON.stringify({
                    lectureNo: lectureNo,
                    memberNo: memberNo,
                    completionYn: completionYn
                });

                // 회원번호와 강의번호를 사용하여 수강신청을 서버로 요청
                fetch('/lms/api/lectures/apply-for-lecture', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body:data
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            // 수강신청 성공 메시지를 표시하거나 리디렉션을 수행합니다.
                            alert("수강신청이 완료되었습니다.");
                        } else {
                            // 수강신청 실패 메시지를 표시합니다.
                            alert("수강신청에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        // 서버 오류 메시지를 표시합니다.
                        alert("서버 오류가 발생했습니다.");
                    });
            } else {
                // 회원번호를 가져오지 못한 경우 처리
                alert("회원번호를 가져오지 못했습니다.");
            }
        })
        .catch(error => {
            // 서버 오류 메시지를 표시합니다.
            alert("서버 오류가 발생했습니다.");
        });
}