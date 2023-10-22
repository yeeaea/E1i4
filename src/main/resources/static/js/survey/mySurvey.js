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
                                surveyButton.style.backgroundColor= "#6c757d"; // 회색으로 변경
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
                                            surveyButton.style.backgroundColor= "#6c757d"; // 회색으로 변경
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
                            // 모달 열기
                            openModal();
                            // 여기에서 강의 정보를 가져와서 필요한 데이터를 설정할 수 있습니다.
                            let lectureTitle = button.getAttribute("data-lecture-title");
                            // 필요한 데이터 설정 코드 추가

                            // 강의 정보를 모달에 설정하는 예시
                            let modalContent = document.querySelector(".modal-content");
                            let h3 = modalContent.querySelector("h3");
                            h3.innerText = "강의평가 : " + lectureTitle;

                            document.getElementById("surveySubmit").addEventListener("click", function (event) {
                                event.preventDefault(); // 폼 제출 방지

// 필드 이름을 매핑하는 객체
                                // 필수 입력 필드 체크
                                const requiredFields = document.querySelectorAll('[required]');
                                let hasEmptyField = false;

                                requiredFields.forEach(function(field) {
                                    if (field.type === "radio") {
                                        const radioName = field.name;
                                        const radios = document.querySelectorAll(`input[type=radio][name="${radioName}"]`);
                                        if (![...radios].some(radio => radio.checked)) {
                                            hasEmptyField = true;
                                        }
                                    } else {
                                        if (!field.value) {
                                            hasEmptyField = true;
                                        }
                                    }
                                });

                                if (hasEmptyField) {
                                    alert("모든 질문에 답변해주세요.");
                                    return false; // 폼 제출 중지
                                } else {
                                    // 데이터를 수집할 객체를 초기화
                                    let formData = {};

                                    document.querySelectorAll("div[name='questions']").forEach(function (question) {
                                        let surveyQuesNo = question.querySelector("input[name='surveyQuesNo']").value;
                                        let choice = question.querySelector("input[type='radio']:checked");
                                        let directInputAnswer = question.querySelector("input[type='text']");

                                        if (choice) {
                                            choice = choice.value;
                                        }

                                        if (directInputAnswer) {
                                            directInputAnswer = directInputAnswer.value;
                                        }

                                        formData[surveyQuesNo] = {
                                            lectureNo: lectureNo,
                                            memberNo: memberNo,
                                            choice: choice,
                                            directInputAnswer: directInputAnswer
                                        };
                                    });

                                    // 데이터를 JSON 문자열로 변환
                                    let jsonData = JSON.stringify(formData);

                                    // 서버에 POST 요청 보내기
                                    fetch(`/lms/api/survey/submit-answer?lectureNo=${lectureNo}&memberNo=${memberNo}`, {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json'
                                        },
                                        body: jsonData
                                    })
                                        .then(response => response.json())
                                        .then(data => {
                                            // 서버 응답을 처리하는 로직
                                            alert("강의평가가 완료되었습니다.");

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
                                            location.reload();
                                        })
                                        .catch(error => {
                                            alert("error");


                                        });
                                }
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

// 모달 열기
function openModal() {
    let modal = document.getElementById("myModal");
    modal.style.display = "block";
}

// 모달 닫기
function closeModal() {
    let modal = document.getElementById("myModal");
    modal.style.display = "none";
}


