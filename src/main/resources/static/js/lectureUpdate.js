// 페이지가 로드될 때 실행할 함수
// 페이지가 로드될 때 실행할 함수
document.addEventListener("DOMContentLoaded", function () {
    // 강의 추가 기능
    document.getElementById("lectureAdd").addEventListener("click", function () {
        // 폼 데이터를 가져오기
        const lectureForm = document.getElementById("addLectureForm");
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
                    // 추가 성공 시 필요한 동작 수행
                    alert("강의 추가 완료");
                    // 페이지 리로딩
                    location.reload();
                } else {
                    // 추가 실패 시 필요한 동작 수행
                    alert("강의 추가 실패");
                }
            });
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





    // 강의 삭제 기능
    // 삭제 버튼 클릭 시 실행될 함수
    document.getElementById('deleteSelected').addEventListener('click', function () {
        console.log("Delete button clicked."); // 디버깅 메시지
        let selectedLectures = document.querySelectorAll('.lecture-checkbox:checked');
        if (selectedLectures.length === 0) {
            alert("선택된 강의가 없습니다.");
            return;
        }

        // 선택된 강의를 삭제할지 확인하는 팝업을 표시합니다.
        if (confirm("선택한 강의를 삭제하시겠습니까?")) {
            // 선택된 강의를 삭제하는 AJAX 요청을 보냅니다.
            let lectureNos = Array.from(selectedLectures).map(function(checkbox) {
                return parseInt(checkbox.value);
            });

            // 서버로 선택된 강의 번호 목록을 JSON 형식으로 변환하여 전송합니다.
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

// 날짜 선택할 때 달력에서 선택하는 기능
$(document).ready(function() {
    $('#lectureStartAt').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true
    });

    $('#lectureEndAt').datepicker({
        format: 'yyyy-mm-dd',
        autoclose: true
    });
});