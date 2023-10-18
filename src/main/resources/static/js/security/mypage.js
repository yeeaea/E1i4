window.onload = function(){
    lectureData();
}

function lectureData() {
    console.log("lectureData 함수");
    fetch("/lms/api/members/lectureData")
        .then(response => {
            if (!response.ok) {
                console.log("진입1");
                throw new Error("데이터를 가져오는 중 오류가 발생했습니다.");
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            drawChart(data);
        })
        .catch(error => {
            console.error(error);
        });
}



function drawChart(lectureData) {
    google.charts.load("current", { packages: ["corechart"] });
    google.charts.setOnLoadCallback(function() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', '수강 과정'); // 첫 번째 열에 레이블 추가
        data.addColumn('number', '수강 과정 비율'); // 두 번째 열에 데이터 추가

        var courseCounts = {}; // 각 수강 과정의 등장 횟수를 저장하는 객체

        // 각 수강 과정 등장 횟수 계산
        for (var i = 0; i < lectureData.length; i++) {
            const course = lectureData[i];
            if (courseCounts[course]) {
                courseCounts[course]++;
            } else {
                courseCounts[course] = 1;
            }
        }

        // 데이터 테이블에 데이터 추가
        for (const course in courseCounts) {
            data.addRow([course, courseCounts[course]]);
        }

        var options = {
            title: '수강 과정별 강의 정보 통계',
            titleTextStyle: {
                color: 'black', // 글자 색상
                fontSize: 30,  // 글자 크기 (픽셀)
            },
            legend: {
                position: 'end',
                alignment: 'center',
                textStyle: {
                    fontSize: 16 // 범례 글씨 크기 설정
                }},
            pieHole: 0.4,
        };

        var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
        chart.draw(data, options);
    });
}