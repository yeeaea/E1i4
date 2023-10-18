document.addEventListener("DOMContentLoaded", function () {
    // 문항 레벨 구분 선택 상자
    let quesLevelSelect = document.getElementById("quesLevel");
    // 문항 분류 선택 상자
    let parentQuesNoSelect = document.getElementById("parentQuesNo");
    // 답변 유형 선택 상자
    let surveyQuesTypeSelect = document.getElementById("surveyQuesType");

    // 문항 레벨 구분 변경 시 이벤트 처리
    quesLevelSelect.addEventListener("change", function () {
        if (quesLevelSelect.value === "1") {
            // "분류" 선택 시 문항 분류 필드를 비활성화
            parentQuesNoSelect.disabled = true;
            // 답변 유형 선택 상자를 비활성화
            surveyQuesTypeSelect.disabled = true;
        } else {
            // "문항" 선택 시 문항 분류 필드를 활성화
            parentQuesNoSelect.disabled = false;
            // 답변 유형 선택 상자를 활성화
            surveyQuesTypeSelect.disabled = false;
        }
    });

    // "등록" 버튼 클릭 시 데이터 제출
    let addButton = document.getElementById("surveyQuesAdd");
    // 폼 데이터를 가져오기
    const surveyQuestionForm = document.getElementById("surveyQuestionForm");

    // 등록 버튼 클릭 시 실행
    addButton.addEventListener("click", function (event) {
        // 필드 이름을 매핑하는 객체
        const fieldNamesMap = {
            "quesLevel": "문항레벨",
            "surveyQuesName": "문항명",
            "surveyQuesViewNo": "정렬순서",
            "surveyQuesYn": "사용여부"
        };
        // 필수 입력 필드 목록
        const requiredFields = ["quesLevel", "surveyQuesName", "surveyQuesViewNo", "surveyQuesYn"];
        let hasEmptyField = false;
        let emptyFieldNames = [];

        // 필수 입력 필드를 확인하고 빈 필드가 있는지 검사
        for (const fieldName of requiredFields) {
            const field = document.getElementById(fieldName);
            if (!field.value) {
                hasEmptyField = true;
                emptyFieldNames.push(fieldNamesMap[fieldName]); // 필드 이름을 가져오고 사용
            }
        }

        if (hasEmptyField) {
            const emptyFieldNamesString = emptyFieldNames.join(", ");
            alert(`${emptyFieldNamesString} 항목은 필수 입력값입니다.`);
        } else {

            let form = document.getElementById('surveyQuestionForm');
            let formData = new FormData(form);

            // surveyQuesYn 체크박스가 체크되어 있지 않은 경우 'false' 값을 추가
            if (!form.querySelector('[name="surveyQuesYn"]').checked) {
                formData.append('surveyQuesYn', 'false');
            }

            console.log(formData);
            // Fetch API를 사용하여 서버로 데이터 제출
            fetch("/admin/api/survey/add-questions", {
                method: "POST",
                body: formData
            })
                .then(function (response) {
                    if (response.status === 200) {
                        alert("강의 평가 문항 추가 완료")
                        location.reload();
                    } else {
                        alert("강의 평가 문항 추가 실패")
                    }
                })
                .catch(function (error) {
                    console.error("네트워크 에러: " + error);
                });
        }

    });

    // 클릭 이벤트 처리
    const surveyQuestionRows = document.querySelectorAll('.surveyQuestionList');
    surveyQuestionRows.forEach(row => {
        row.addEventListener('click', () => {
            // 다른 행의 선택 상태를 초기화
            surveyQuestionRows.forEach(function (r) {
               r.classList.remove('selected');
            });

            // 클릭한 행을 선택상태로 변경
            row.classList.add('selected');

            // 문항 레벨 필드 비활성화
            quesLevelSelect.disabled = true;
            // 문항 분류 필드를 비활성화
            parentQuesNoSelect.disabled = true;
            // 답변 유형 선택 상자를 비활성화
            surveyQuesTypeSelect.disabled = true;
            //

            // 강의 평가 문항 정보를 폼에 채우는 함수 호출
            populateFormFields(row);
        });
    });

    // 이벤트 핸들러 함수: 테이블 행 클릭 시 실행됨
    function populateFormFields(row) {
        const quesLevelType = row.cells[1].textContent.trim();
        const parentQuesName = row.cells[2].textContent.trim();
        const surveyQuesName = row.cells[3].textContent;
        const surveyQuesType = row.cells[4].textContent.trim();
        const surveyQuesViewNo = row.cells[5].textContent;
        const surveyQuesYn = row.cells[6].querySelector('.survey-checkbox').checked;

        // 선택 목록 요소 가져오기
        const selectElement = document.getElementById('parentQuesNo');
        // surveyQuesNo를 찾기 위한 옵션 요소를 찾기
        const options = selectElement.getElementsByTagName('option');
        let selectedSurveyQuesNo = '';

        for (let i = 0; i < options.length; i++) {
            if (options[i].text === parentQuesName) {
                selectedSurveyQuesNo = options[i].value;
                break;
            }
        }

        // 폼 필드에 데이터 채우기
        document.getElementById('quesLevel').value = quesLevelType === '분류' ? '1' : '2';
        document.getElementById('parentQuesNo').value = selectedSurveyQuesNo;
        document.getElementById('surveyQuesName').value = surveyQuesName;
        document.getElementById('surveyQuesType').value = surveyQuesType === '5문항선택' ? '1' : (surveyQuesType === '직접입력' ? '2' : '');
        document.getElementById('surveyQuesViewNo').value = surveyQuesViewNo;
        document.getElementById('surveyQuesYn').checked = surveyQuesYn;
    }

    // 강의 평가 문항 수정
    const surveyQuesEditButton = document.getElementById('surveyQuesEdit');

    // 수정 버튼 클릭 시 실행되는 함수
    function editButtonClickHandler() {
        // 클릭한 행의 강의 평가 문항 번호 가져오기
        const row = document.querySelector('.surveyQuestionList.selected');
        const surveyQuesNoString = row.getAttribute('data-question-no');


        // Long형으로 변환
        const surveyQuesNo = parseInt(surveyQuesNoString);
        console.log(surveyQuesNo)

        let form = document.getElementById('surveyQuestionForm');
        let formData = new FormData(form);

        // surveyQuesYn 체크박스가 체크되어 있지 않은 경우 'false' 값을 추가
        if (!form.querySelector('[name="surveyQuesYn"]').checked) {
            formData.append('surveyQuesYn', 'false');
        }

        console.log(formData);
        fetch(`/admin/api/survey/update-question/${surveyQuesNo}`, {
            method: "PUT", // PUT 메서드 사용
            body: formData // 데이터를 JSON 문자열로 변환
        })
            .then(function (response) {
                if (response.status === 200) {
                    alert("강의 평가 문항 수정 완료")
                    location.reload();
                } else {
                    alert("강의 평가 문항 수정 실패")
                }
            })
            .catch(function (error) {
                console.error("네트워크 에러: " + error);
            });
    }

    // 수정 버튼 클릭 이벤트 핸들러 등록
    surveyQuesEditButton.addEventListener('click', editButtonClickHandler);


    // ****** 삭제 *******
    // 삭제 버튼 클릭 시 실행
    document.getElementById('surveyQuesDelete').addEventListener('click', function() {
        let selectedQuestions = document.querySelectorAll('.surveyQues-checkbox:checked');
        if (selectedQuestions.length === 0) {
            alert("선택된 강의 평가 문항이 없습니다.");
            return;
        }

        if (confirm("선택한 강의 평가 문항을 삭제하시겠습니까?")) {
            let surveyQuesNos = Array.from(selectedQuestions).map(function (checkbox) {
                return parseInt(checkbox.value);
            });

            fetch("/admin/api/survey/delete-questions", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(surveyQuesNos)
            })
                .then(function (response) {
                    if (response.status === 200) {
                        // 삭제가 성공한 경우
                        selectedQuestions.forEach(function (checkbox) {
                            checkbox.closest("tr").remove();
                        });
                        alert("강의 평가 문항이 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("문항에 사용된 분류는 삭제할 수 없습니다.");
                        location.reload();
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    });

});


