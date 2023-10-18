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
                // 모든 강의평가 버튼을 찾아서 처리
                const surveyButtons = document.querySelectorAll('.survey-button');
                surveyButtons.forEach(surveyButton => {
                    const lectureNoString = surveyButton.getAttribute('data-lecture-no');
                    const lectureNo = parseInt(lectureNoString);

                    // 수료 여부 확인을 위한 요청
                    fetch(`/lms/api/lectures/completion-status?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    })
                        .then(response => response.json())
                        .then(data => {
                            // 서버로부터 받은 데이터에서 수료 여부 확인 (예: data.completionStatus)
                            if (data.completionStatus === 'N') {
                                console.log(data.completionStatus)
                                // 수료 여부가 'N'인 경우 [강의평가] 버튼을 비활성화합니다.
                                surveyButton.disabled = true;
                                surveyButton.textContent = "준비중";
                            } else {
                                // 서버로부터 중복 강의평가 여부를 확인하는 요청
                                fetch(`/lms/api/survey/check-duplicate-survey?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                                    method: 'GET',
                                    headers: {
                                        'Content-Type': 'application/json',
                                    },
                                })
                                    .then(response => response.json())
                                    .then(data => {
                                        if (data.isDuplicate) {
                                            // 이미 강의평가를 진행한 경우
                                            surveyButton.disabled = true;
                                            surveyButton.textContent = "강의평가 완료";
                                        }
                                    })
                                    .catch(error => {
                                        // 서버 오류 메시지
                                        alert("서버 오류가 발생했습니다.");
                                    });
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
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
function surveyForLecture(button) {
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
                const lectureNo = parseInt(lectureNoString);

                // 서버로부터 중복 강의평가 여부를 확인하는 요청
                fetch(`/lms/api/survey/check-duplicate-survey?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.isDuplicate) {
                            // 이미 강의평가를 진행한 경우
                            alert("이미 강의평가를 진행하였습니다.");
                        } else {
                            const surveyData = {
                                lectureNo: lectureNo, // 강의 번호
                                memberNo: memberNo, // 회원 번호 (사용자 식별 정보)
                                surveyAt: new Date().toISOString() // 현재 날짜 및 시간
                            };

                            fetch('/lms/api/survey', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(surveyData)
                            })
                                .then(response => {
                                    if (response.ok) {
                                        alert('강의평가가 성공적으로 저장되었습니다.');
                                        location.reload();
                                    } else {
                                        alert('강의평가 저장 중 오류가 발생했습니다.');
                                    }
                                })
                                .catch(error => {
                                    console.error('Error:', error);
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
