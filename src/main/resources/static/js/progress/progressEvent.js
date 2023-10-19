/////////////////////////// 강의년도 및 강의구분 셀렉트 박스 조회 ///////////////////////////
document.addEventListener('DOMContentLoaded', function () {
    // 강의년도 옵션
    const yearOptions = ['2022', '2023', '2024'];
    populateSelectOptions('yearSelect', yearOptions);

    // 과정구분 옵션
    const lectureOptions = ['java', 'C++', 'C#', 'python', 'javascript'];
    populateSelectOptions('lectureSelect', lectureOptions);
});

function populateSelectOptions(selectId, options) {
    const selectElement = document.getElementById(selectId);

    // 기존 옵션 제거
    selectElement.innerHTML = '';

    // 전체 옵션 추가
    const allOption = document.createElement('option');
    allOption.value = '';
    allOption.text = '전체';
    selectElement.add(allOption);

    // 옵션 추가
    options.forEach(optionValue => {
        const option = document.createElement('option');
        option.value = optionValue;
        option.text = optionValue;
        selectElement.add(option);
    });
}


document.addEventListener('DOMContentLoaded', function () {
    const yearSelect = document.getElementById('yearSelect');
    const lectureSelect = document.getElementById('lectureSelect');

    function loadLectureInfo() {
        const selectedYear = yearSelect.value;
        const selectedLecture = lectureSelect.value;

        const lectureList = document.querySelectorAll('#lectureList tr');
        let foundMatchingLectures = false;

        lectureList.forEach(lecture => {
            const lectureYear = lecture.querySelector('#data-lectureYear').innerText;
            const lectureCourse = lecture.querySelector('#data-lectureCourse').innerText;

            if ((selectedYear === '' || selectedYear === lectureYear) &&
                (selectedLecture === '' || selectedLecture === lectureCourse)) {
                lecture.style.display = '';  // 일치하는 데이터면 표시
                foundMatchingLectures = true;
            } else {
                lecture.style.display = 'none';  // 일치하지 않는 경우 숨김
            }
        });

        if (!foundMatchingLectures) {
            alert('일치하는 강의 정보가 없습니다.');
        }
    }

    document.getElementById('loadButton').addEventListener('click', loadLectureInfo);
});

function loadProgressDataFromLectureTable() {
    let lectureTableRows = document.querySelectorAll("#lectureTable tbody tr");
    let progressTable = document.getElementById("progressTable");

    lectureTableRows.forEach(function (row) {
        row.addEventListener("click", function () {
            // 클릭한 행의 데이터 가져오기
            let lectureNo = row.querySelector("#data-lectureNo").textContent;

            // progressTable 첫 번째 행(thead)는 건너뛰고 데이터 행을 찾음
            let progressTableDataRows = progressTable.querySelectorAll("tbody tr");

            // 모든 데이터 행에 hidden-row 클래스를 추가하여 숨깁니다.
            progressTableDataRows.forEach(function (progressRow) {
                progressRow.classList.add("hidden-row");
            });

            // 데이터 행에 해당 데이터 추가
            progressTableDataRows.forEach(function (progressRow) {
                let progressLectureNo = progressRow.querySelector("td.d-none:nth-child(3)").textContent;
                if (progressLectureNo === lectureNo) {
                    // 해당 데이터 행을 찾았을 때
                    let contentCheckbox = progressRow.querySelector(".contentCheckbox");

                    // contentCheckbox 선택 상태로 변경
                    contentCheckbox.checked = true;

                    // hidden-row 클래스를 제거하여 행을 보이게 만듭니다.
                    progressRow.classList.remove("hidden-row");
                }
            });
        });
    });
}

// 페이지 로드 후 함수 실행
window.onload = function () {
    loadProgressDataFromLectureTable();
};

/////////////////////////// 등록된 차시 정보 조회 ///////////////////////////
// 체크박스 전체 선택
function selectAllCheckboxes(checkbox) {
    const checkboxes = document.querySelectorAll('.contentCheckbox');
    checkboxes.forEach(cb => {
        cb.checked = checkbox.checked;
    });
}

// 차시 관리 checkbox 클릭 시 상세 정보 출력
function updateContentDetails(event) {
    const checkbox = event.target;

    let lectureNoValue = checkbox.getAttribute('data-lectureNo');
    let lectureCourseValue = checkbox.getAttribute('data-lectureCourse');
    let contentNoValue = checkbox.getAttribute('data-contentNo');
    let nthDurationValue = checkbox.getAttribute('data-nthDuration');
    let contentNameValue = checkbox.getAttribute('data-contentName');
    let runTmValue = checkbox.getAttribute('data-runTm');
    let ytbUrlValue = checkbox.getAttribute('data-ytbUrl');
    let contentUrlValue = checkbox.getAttribute('data-contentUrl');

    // 강의 차시별 콘텐츠 상세 정보 표시
    document.getElementById('lectureNo').value = lectureNoValue;
    document.getElementById('lectureCourse').value = lectureCourseValue;
    document.getElementById('contentNo').value = contentNoValue;
    document.getElementById('nthDuration').value = nthDurationValue;
    document.getElementById('contentName').value = contentNameValue;
    document.getElementById('runTm').value = runTmValue;
    document.getElementById('ytbUrl').value = ytbUrlValue;
    document.getElementById('contentUrl').value = contentUrlValue;
}

function addCheckboxListeners() {
    const checkboxes = document.querySelectorAll('.contentCheckbox');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('click', updateContentDetails);
    });
}

// 중복 호출 방지
addCheckboxListeners();

/////////////////////////// 강의 과정구분 셀렉트 박스 ///////////////////////////
document.addEventListener('DOMContentLoaded', function () {
    function selectCourse() {
        const selectElement = document.getElementById('lectureCourse');
        const courses = ['java', 'C++', 'C#', 'python', 'javascript'];

        // 기존 옵션 제거
        selectElement.innerHTML = '';

        // 전체 옵션 추가
        const allOption = document.createElement('option');
        allOption.value = '';
        allOption.text = '전체';
        selectElement.add(allOption);

        // 강의 과목별 옵션 추가
        courses.forEach(course => {
            const option = document.createElement('option');
            option.value = course;
            option.text = course;
            selectElement.add(option);
        });
    }

    // 예시: 초기에 호출
    selectCourse();
});

/////////////////////////// 강의 주차수 셀렉트 박스 ///////////////////////////
document.addEventListener('DOMContentLoaded', function () {
    function selectDuration() {
        const selectElement = document.getElementById('nthDuration');
        const totalWeeks = 15;

        // 기존 옵션 제거
        selectElement.innerHTML = '';

        // 전체 옵션 추가
        const allOption = document.createElement('option');
        allOption.value = '';
        allOption.text = '전체';
        selectElement.add(allOption);

        // 주차별 옵션 추가
        for (let i = 1; i <= totalWeeks; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.text = `${i}주차`;
            selectElement.add(option);
        }

        selectElement.addEventListener('change', function () {
            const selectedValue = selectElement.value;
            selectElement.setAttribute('nthDuration', selectedValue);
        });
    }

    selectDuration();
});

