/////////////////////////// 강의년도 및 강의구분 셀렉트 박스 조회 ///////////////////////////
document.addEventListener('DOMContentLoaded', function () {
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

    // 강의년도 옵션
    const yearOptions = ['2022', '2023', '2024', '2025'];
    populateSelectOptions('yearSelect', yearOptions);

    // 과정구분 옵션
    const lectureOptions = ['java', 'C++', 'C#', 'python', 'javascript'];
    populateSelectOptions('lectureSelect', lectureOptions);

    // 예시: 강의년도 선택 시 이벤트 핸들러
    document.getElementById('yearSelect').addEventListener('change', function () {
        const selectedYear = this.value;

        // 예시: 선택된 값이 일치할 때 화면에 보여주기
        const displayElement = document.getElementById('selectedValuesDisplay');
        displayElement.textContent = `선택된 강의년도: ${selectedYear}`;
    });

    // 예시: 과정구분 선택 시 이벤트 핸들러
    document.getElementById('lectureSelect').addEventListener('change', function () {
        const selectedLecture = this.value;

        // 예시: 선택된 값이 일치할 때 화면에 보여주기
        const displayElement = document.getElementById('selectedValuesDisplay');
        displayElement.textContent = `선택된 과정구분: ${selectedLecture}`;
    });
});


// function loadLectureInfo() {
//     let selectedYear = document.getElementById('yearSelect').value;
//     let selectedCourse = document.getElementById('courseSelect').value;
//
//     loadData(selectedYear, selectedCourse, displayFilteredData);
// }
//
// function loadData(selectedYear, selectedCourse, callback) {
//     const xhr = new XMLHttpRequest();
//
//     xhr.onload = function () {
//         if (xhr.status === 200) {
//             const responseData = JSON.parse(xhr.responseText);
//             callback(responseData, selectedYear, selectedCourse);
//         } else {
//             console.error('데이터를 불러오는 데 실패했습니다.');
//         }
//     };
//
//     xhr.open('GET', `/api/progress?year=${selectedYear}&course=${selectedCourse}`);
//     xhr.send();
// }

// 차시 정보 체크박스 클릭하면 등록된 콘텐츠 정보 출력
// function displayFilteredData(data, selectedYear, selectedCourse) {
//     const tbody = document.querySelector('#lectureTable tbody');
//     tbody.innerHTML = '';
//
//     data.forEach(function (lectureInfo) {
//         if ((!selectedYear || lectureInfo.lectureYear === selectedYear) &&
//             (!selectedCourse || lectureInfo.lectureCourse === selectedCourse)) {
//             let row = tbody.insertRow();
//             let cell1 = row.insertCell(0);
//             let cell2 = row.insertCell(1);
//             let cell3 = row.insertCell(2);
//             let cell4 = row.insertCell(3);
//             let cell5 = row.insertCell(4);
//
//             cell1.textContent = lectureInfo.lectureNo;
//             cell2.textContent = lectureInfo.lectureYear;
//             cell3.textContent = lectureInfo.lectureCourse;
//             cell4.textContent = lectureInfo.lectureTitle;
//             cell5.textContent = lectureInfo.lectureDuration;
//         }
//     });
// }

// 강의 번호 조회 (수정중)
// function handleRowClick(lectureNo, nthNo) {
//     console.log('클릭한 행의 lectureDuration:', lectureNo);
//     console.log('클릭한 행의 nthNo:', nthNo);
//
//     // 다른 테이블의 데이터 연결
//     const progressInfoTable = document.querySelector('.progressInfoTable');
//
//     // progressInfo.nthNo를 특정 위치에 출력
//     const nthNoElement = progressInfoTable.querySelector('.nthNo');
//     nthNoElement.innerText = nthNo;
// }

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

    let nthNoValue = checkbox.getAttribute('data-nthNo');
    let lectureCourseValue = checkbox.getAttribute('data-lectureCourse');
    let contentNoValue = checkbox.getAttribute('data-contentNo');
    let lectureDurationValue = checkbox.getAttribute('data-lectureDuration');
    let contentNameValue = checkbox.getAttribute('data-contentName');
    let runTmValue = checkbox.getAttribute('data-runTm');
    let ytbUrlValue = checkbox.getAttribute('data-ytbUrl');
    let contentUrlValue = checkbox.getAttribute('data-contentUrl');

    // 강의 차시별 콘텐츠 상세 정보 표시
    document.getElementById('nthNo').value = nthNoValue;
    document.getElementById('lectureCourse').value = lectureCourseValue;
    document.getElementById('contentNo').value = contentNoValue;
    document.getElementById('lectureDuration').value = lectureDurationValue;
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
        const selectElement = document.getElementById('lectureDuration');
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
    }

    selectDuration();

    const newContentButton = document.getElementById('newContent');
    newContentButton.addEventListener('click', handleEditButtonClick);
});