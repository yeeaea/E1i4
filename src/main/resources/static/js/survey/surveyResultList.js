document.addEventListener("DOMContentLoaded", function () {
    const resultButtons = document.querySelectorAll('.survey-result-button');
    resultButtons.forEach(resultButton => {
        const lectureNoString = resultButton.getAttribute('data-lecture-no');
        const lectureNo = parseInt(lectureNoString);
        console.log(lectureNo)

        // 강의번호로 강의평가 결과 데이터가 있는지 조회
        fetch(`/admin/api/survey/result/check?lectureNo=${lectureNo}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.text())
            .then(data => {
                console.log(data)
                if (data === "ResultExists") {
                } else {
                    // 결과가 없으면 버튼 비활성화
                    resultButton.disabled = true;
                    resultButton.innerText = "준비중";
                    resultButton.style.backgroundColor= "#6c757d"; // 회색으로 변경
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    })
});

function viewSurveyResult(button) {
    const lectureNo = button.getAttribute('data-lecture-no');

    window.location.href = `/admin/survey/result/${lectureNo}`;
}