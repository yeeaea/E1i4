// 페이지가 로드될 때 실행할 함수
document.addEventListener("DOMContentLoaded", function () {
    // ******** 강의 추가 기능 ********
    // 등록 버튼 클릭 시 실행
    document.getElementById("lectureAdd").addEventListener("click", function () {
        // 필수 입력 필드 목록
        const requiredFields = ["lectureCourse", "lectureTitle", "lectureDesc", "lectureStartAt", "lectureEndAt", "lectureDuration"];
        let hasEmptyField = false;
        let emptyFieldNames = [];

        // 필수 입력 필드를 확인하고 빈 필드가 있는지 검사
        for (const fieldName of requiredFields) {
            const field = document.getElementById(fieldName);
            if (!field.value) {
                hasEmptyField = true;
                emptyFieldNames.push(fieldName);
            }
        }

        if (hasEmptyField) {
            const emptyFieldNamesString = emptyFieldNames.join(", ");
            alert(`${emptyFieldNamesString}는 필수 입력값입니다.`);
        } else {
            // 폼 데이터를 가져오기
            const lectureForm = document.getElementById("LectureForm");
            const formData = new FormData(lectureForm);

            // FormData를 JSON으로 변환
            const jsonObject = {};
            formData.forEach((value, key) => {
                jsonObject[key] = value;
            });

            // JSON 데이터를 서버로 전송
            fetch("/admin/api/lectures/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(jsonObject)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 추가 실패 시 필요한 동작 수행
                        alert("강의 추가 실패");
                    } else {
                        // 추가 성공 시 필요한 동작 수행
                        alert("강의 추가 완료");
                        // 페이지 리로딩
                        location.reload();
                    }
                });
        }
    });

    // 강의 시작일과 종료일 입력 필드 가져오기
    const lectureStartInput = document.getElementById("lectureStartAt");
    const lectureEndInput = document.getElementById("lectureEndAt");
    const lectureDurationSelect = document.getElementById("lectureDuration");

    // 강의 종료일 검증 함수
    function validateEndDate() {
        const startDate = new Date(lectureStartInput.value);
        const endDate = new Date(lectureEndInput.value);

        if (endDate < startDate) {
            alert("강의 종료일은 강의 시작일 이후여야 합니다.");
            lectureEndInput.value = ""; // 종료일 초기화
            return; // 함수 종료
        }

        // 강의 주차 계산 및 선택 옵션 업데이트
        const duration = Math.ceil((endDate - startDate) / (7 * 24 * 60 * 60 * 1000)); // 일주일은 7일

        // 강의 주차가 15주를 넘어가면 알림 표시하고 종료일 초기화
        if (duration > 15) {
            alert("강의 주차는 최대 15주까지 가능합니다.");
            lectureEndInput.value = ""; // 종료일 초기화
        } else {
            // 주차 선택 옵션 업데이트
            lectureDurationSelect.value = duration.toString();
        }
    }

    // 강의 종료일 입력 필드에 이벤트 리스너 추가
    lectureEndInput.addEventListener("change", validateEndDate);

    // ******** 강의 수정 기능 ********
// 테이블의 모든 행에 클릭 이벤트 핸들러 추가
    const lectureRows = document.querySelectorAll('.lecture-row');
    lectureRows.forEach(function (row) {
        row.addEventListener('click', function () {
            // 다른 행의 선택 상태를 초기화
            lectureRows.forEach(function (r) {
                r.classList.remove('selected');
            });

            // 클릭한 행을 선택 상태로 변경
            row.classList.add('selected');

            // 강의 정보를 폼에 채우는 함수 호출
            populateFormFields(row);
        });
    });


    // 이벤트 핸들러 함수: 테이블 행 클릭 시 실행됨
    function populateFormFields(row) {
        // 클릭한 행에서 강의 정보를 가져오기
        const lectureYear = row.cells[1].textContent;
        const lectureCourse = row.cells[2].textContent;
        const lectureTitle = row.cells[3].textContent;
        const lectureDesc = row.cells[4].textContent;
        const lectureStartAtText = row.cells[5].textContent;
        const lectureEndAtText = row.cells[6].textContent;
        const lectureDuration = row.cells[7].textContent;

        // 날짜를 Date 객체로 변환
        const lectureStartAtDate = new Date(lectureStartAtText);
        const lectureEndAtDate = new Date(lectureEndAtText);

        // Date 객체를 ISO 형식으로 변환 (yyyy-mm-dd)
        const formattedStartDate = lectureStartAtDate.toISOString().split('T')[0];
        const formattedEndDate = lectureEndAtDate.toISOString().split('T')[0];

        // 강의 등록 섹션의 입력 필드에 정보 설정
        document.getElementById('lectureYear').value = lectureYear;
        document.getElementById('lectureCourse').value = lectureCourse;
        document.getElementById('lectureTitle').value = lectureTitle;
        document.getElementById('lectureDesc').value = lectureDesc;
        document.getElementById('lectureStartAt').value = formattedStartDate;
        document.getElementById('lectureEndAt').value = formattedEndDate;
        document.getElementById('lectureDuration').value = lectureDuration;

        // ******** 강의 수정 기능 ********
        // 강의 클릭한 행의 강의 번호 가져오기 (String)
        const lectureNoString = row.getAttribute('data-lecture-no');
        // Long형으로 변환
        const lectureNo = parseInt(lectureNoString);

        const data = {
            lectureYear,
            lectureCourse,
            lectureTitle,
            lectureDesc,
            formattedStartDate,
            formattedEndDate,
            lectureDuration
        };
        console.log(data);

        // 수정 버튼 클릭 시 실행
        document.getElementById('lectureEdit').addEventListener('click', function () {
            fetch(`/admin/api/lectures/update/${lectureNo}`, {
                method: "PUT", // PUT 메서드 사용
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(data => {
                    if (data) {
                        alert("강의 수정 완료");
                        location.reload();
                    } else {
                        alert("강의 수정 실패");
                    }
                });
        });
    }



    // ******** 강의 삭제 기능 ********
    // 삭제 버튼 클릭 시 실행
    document.getElementById('deleteSelected').addEventListener('click', function () {
        console.log("Delete button clicked."); // 디버깅 메시지
        let selectedLectures = document.querySelectorAll('.lecture-checkbox:checked');
        if (selectedLectures.length === 0) {
            alert("선택된 강의가 없습니다.");
            return;
        }

        // 선택된 강의를 삭제할지 확인하는 팝업을 표시
        if (confirm("선택한 강의를 삭제하시겠습니까?")) {
            // 선택된 강의를 삭제하는 AJAX 요청을 보냅니다.
            let lectureNos = Array.from(selectedLectures).map(function(checkbox) {
                return parseInt(checkbox.value);
            });

            // 서버로 선택된 강의 번호 목록을 JSON 형식으로 변환하여 전송
            let xhr = new XMLHttpRequest();
            xhr.open("POST", "/admin/api/lectures/delete", true);
            xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        // 삭제가 성공한 경우, 화면에서 선택한 강의를 제거합니다.
                        selectedLectures.forEach(function(checkbox) {
                            checkbox.closest("tr").remove();
                        });
                        alert("강의가 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("강의 삭제 중 오류가 발생했습니다.");
                    }
                }
            };

            // 선택된 강의 번호 목록을 JSON 형식으로 변환하여 전송합니다.
            xhr.send(JSON.stringify(lectureNos));
        }
    });
});


