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
});


