document.addEventListener("DOMContentLoaded", function () {
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
                // 모든 수강신청 버튼을 찾아서 처리
                const applyButtons = document.querySelectorAll('.apply-button');
                applyButtons.forEach(applyButton => {
                    const lectureNoString = applyButton.getAttribute('data-lecture-no');
                    const lectureNo = parseInt(lectureNoString);

                    // 서버로부터 중복 수강 신청 여부를 확인하는 요청
                    fetch(`/lms/api/lectures/check-duplicate-apply?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.isDuplicate) {
                                // 이미 수강 신청한 강의인 경우
                                applyButton.disabled = true;
                                applyButton.textContent = "수강 중";
                                applyButton.style.backgroundColor= "#6c757d"; // 회색으로 변경
                            }
                        })
                        .catch(error => {
                            // 서버 오류 메시지
                            alert("서버 오류가 발생했습니다.");
                        });
                })

            } else {
                // 회원번호를 가져오지 못한 경우 처리
                alert("회원번호를 가져오지 못했습니다.");
            }
        })
        .catch(error => {
            // 서버 오류 메시지
            alert("서버 오류가 발생했습니다.");
        });

});

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
                const lectureNoString = button.getAttribute('data-lecture-no');
                console.log(lectureNoString);

                // Long형으로 변환
                const lectureNo = parseInt(lectureNoString);

                console.log("강의 번호: " + lectureNo);

                // 서버로부터 중복 수강 신청 여부를 확인하는 요청
                fetch(`/lms/api/lectures/check-duplicate-apply?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.isDuplicate) {
                            // 이미 수강 신청한 강의인 경우 알림을 표시
                            alert("이미 수강 신청한 강의입니다.");
                        } else {
                    // 서버에 보낼 데이터 준비
                    let data = JSON.stringify({
                        lectureNo: lectureNo,
                        memberNo: memberNo
                    });

                    // 회원번호와 강의번호를 사용하여 수강신청을 서버로 요청
                    fetch(`/lms/api/lectures/apply-for-lecture`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body:data
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                // 수강신청 성공 메시지
                                alert("수강신청이 완료되었습니다.");
                                location.reload(); // 페이지 리로딩
                            } else {
                                // 수강신청 실패 메시지
                                alert("수강신청에 실패했습니다.");
                            }
                        })
                        .catch(error => {
                            // 서버 오류 메시지
                            alert("서버 오류가 발생했습니다.");
                        });
                    }
                })
                .catch(error => {
                    // 서버 오류 메시지
                    alert("서버 오류가 발생했습니다.");
                });
            } else {
                // 회원번호를 가져오지 못한 경우 처리
                alert("회원번호를 가져오지 못했습니다.");
            }
        })
        .catch(error => {
            // 서버 오류 메시지
            alert("서버 오류가 발생했습니다.");
        });
}