document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('surveyQuestionNo').addEventListener('change', function() {
        let selectedQuestionNo = this.value;
        let lectureNo = getLectureNoFromURL(); // URL에서 강의 번호를 추출하는 함수

        console.log(selectedQuestionNo)
        console.log(lectureNo)

        // AJAX 요청 대신 fetch API 사용
        fetch('/admin/api/survey/result/getSurveyData?lectureNo=' + lectureNo + '&surveyQuesNo=' + selectedQuestionNo)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                // 서버에서 받은 데이터를 사용하여 구글 차트 업데이트
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(function() {
                    drawChart(data);
                });
            })
            .catch(error => {
                console.error('데이터를 불러오지 못했습니다:', error);
            });
    });

    // URL에서 강의 번호를 추출하는 함수
    function getLectureNoFromURL() {
        let url = window.location.href;
        let lectureNo = url.match(/admin\/survey\/result\/(\d+)/);
        if (lectureNo && lectureNo[1]) {
            return lectureNo[1];
        }
        return null;
    }

    // 구글 차트 그리기 함수
    function drawChart(surveyAnswerData) {
        google.charts.load("current", { packages: ["corechart"] });
        google.charts.setOnLoadCallback(function() {
            let data = new google.visualization.DataTable();
            data.addColumn('string', '답변명'); // 첫 번째 열에 레이블 추가
            data.addColumn('number', '답변명 비율'); // 두 번째 열에 데이터 추가

            let surveyAnsNameCounts = {}; // 각 답변명의 등장 횟수를 저장하는 객체

            // 각 답변명 등장 횟수 계산
            for (let i = 0; i < surveyAnswerData.length; i++) {
                const surveyAnsName = surveyAnswerData[i];
                if (surveyAnsNameCounts[surveyAnsName]) {
                    surveyAnsNameCounts[surveyAnsName]++;
                } else {
                    surveyAnsNameCounts[surveyAnsName] = 1;
                }
            }
            // 데이터 테이블에 데이터 추가
            for (const surveyAnsName in surveyAnsNameCounts) {
                data.addRow([surveyAnsName, surveyAnsNameCounts[surveyAnsName]]);
            }

            let options = {
                title: '평가 문항 별 답변 통계',
                titleTextStyle: {
                    color: 'black', // 글자 색상
                    fontSize: 23,  // 글자 크기 (픽셀)
                },
                legend: {
                    position: 'end',
                    alignment: 'center',
                    textStyle: {
                        fontSize: 16 // 범례 글씨 크기 설정
                    }},
                pieHole: 0.4,
            };

            let chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            chart.draw(data, options);
            console.log(data)
        });

    }
    let selectBox = document.getElementById('surveyQuesNo');
    let tableRows = document.querySelectorAll('table tbody tr');

    // 페이지 로딩 시 테이블 행을 숨김
    tableRows.forEach(function(row) {
        row.style.display = 'none';
    });

    selectBox.addEventListener('change', function() {
        let selectedValue = this.value;

        // 테이블의 각 행을 순회하면서 선택한 값과 일치하는 행만 보이도록 처리
        tableRows.forEach(function(row) {
            let questionNo = row.getAttribute('data-survey-question-no');
            if (questionNo === selectedValue) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });

});

