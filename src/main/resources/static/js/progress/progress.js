/////////////////////////// 강의년도 및 강의구분 셀렉트 박스 조회 ///////////////////////////
document.addEventListener('DOMContentLoaded', function() {
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
    document.getElementById('yearSelect').addEventListener('change', function() {
        const selectedYear = this.value;

        // 예시: 선택된 값이 일치할 때 화면에 보여주기
        const displayElement = document.getElementById('selectedValuesDisplay');
        displayElement.textContent = `선택된 강의년도: ${selectedYear}`;
    });

    // 예시: 과정구분 선택 시 이벤트 핸들러
    document.getElementById('lectureSelect').addEventListener('change', function() {
        const selectedLecture = this.value;

        // 예시: 선택된 값이 일치할 때 화면에 보여주기
        const displayElement = document.getElementById('selectedValuesDisplay');
        displayElement.textContent = `선택된 과정구분: ${selectedLecture}`;
    });
});


function loadLectureInfo() {
    let selectedYear = document.getElementById('yearSelect').value;
    let selectedCourse = document.getElementById('courseSelect').value;

    loadData(selectedYear, selectedCourse, displayFilteredData);
}

function loadData(selectedYear, selectedCourse, callback) {
    const xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);
            callback(responseData, selectedYear, selectedCourse);
        } else {
            console.error('데이터를 불러오는 데 실패했습니다.');
        }
    };

    xhr.open('GET', `/api/progress?year=${selectedYear}&course=${selectedCourse}`);
    xhr.send();
}

function displayFilteredData(data, selectedYear, selectedCourse) {
    const tbody = document.querySelector('#lectureTable tbody');
    tbody.innerHTML = '';

    data.forEach(function (lectureInfo) {
        if ((!selectedYear || lectureInfo.lectureYear === selectedYear) &&
            (!selectedCourse || lectureInfo.lectureCourse === selectedCourse)) {
            let row = tbody.insertRow();
            let cell1 = row.insertCell(0);
            let cell2 = row.insertCell(1);
            let cell3 = row.insertCell(2);
            let cell4 = row.insertCell(3);
            let cell5 = row.insertCell(4);

            cell1.textContent = lectureInfo.lectureNo;
            cell2.textContent = lectureInfo.lectureYear;
            cell3.textContent = lectureInfo.lectureCourse;
            cell4.textContent = lectureInfo.lectureTitle;
            cell5.textContent = lectureInfo.lectureDuration;
        }
    });
}

function handleRowClick(lectureNo, nthNo) {
    console.log('클릭한 행의 lectureDuration:', lectureNo);
    console.log('클릭한 행의 nthNo:', nthNo);

    // 다른 테이블의 데이터 연결
    const progressInfoTable = document.querySelector('.progressInfoTable');

    // progressInfo.nthNo를 특정 위치에 출력
    const nthNoElement = progressInfoTable.querySelector('.nthNo');
    nthNoElement.innerText = nthNo;
}

/////////////////////////// 등록된 차시 정보 조회 ///////////////////////////
// 체크박스 전체 선택
function selectAllCheckboxes(checkbox) {
    const checkboxes = document.querySelectorAll('.contentCheckbox');
    checkboxes.forEach(cb => {
        cb.checked = checkbox.checked;
    });
}

// 차시 관리 checkbox 클릭 시 상세 정보 출력
function updateContentDetails() {
    const checkbox = this;

    let nthNoValue = checkbox.getAttribute('data-nthNo');
    let lectureCourseValue = checkbox.getAttribute('data-lectureCourse');
    let contentNoValue = checkbox.getAttribute('data-contentNo');
    let lectureDurationValue = checkbox.getAttribute('data-lectureDuration');
    let nthNameValue = checkbox.getAttribute('data-nthName');
    let contentNameValue = checkbox.getAttribute('data-contentName');
    let runTmValue = checkbox.getAttribute('data-runTm');
    let contentFileNoValue = checkbox.getAttribute('data-contentFileNo');
    let ytbUrlValue = checkbox.getAttribute('data-ytbUrl');
    let contentUrlValue = checkbox.getAttribute('data-contentUrl');

    // 강의 차시별 콘텐츠 상세 정보 표시
    document.getElementById('nthNo').value = nthNoValue;
    document.getElementById('lectureCourse').value = lectureCourseValue;
    document.getElementById('contentNo').value = contentNoValue;
    document.getElementById('lectureDuration').value = lectureDurationValue;
    document.getElementById('nthName').value = nthNameValue;
    document.getElementById('contentName').value = contentNameValue;
    document.getElementById('runTm').value = runTmValue;
    document.getElementById('contentFileNo').value = contentFileNoValue;
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
document.addEventListener('DOMContentLoaded', function() {
    function lectureCourse() {
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
    lectureCourse();
});

/////////////////////////// 강의 주차수 셀렉트 박스 ///////////////////////////
document.addEventListener('DOMContentLoaded', function() {
function lectureDuration() {
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
    lectureDuration();
});


// 텍스트 박스 내용 비우기
function clearTextBoxes() {
    const inputElements = document.querySelectorAll('input');
    const selectElement = document.querySelectorAll('select');

    inputElements.forEach(input => {
        input.value = '';
    });

    selectElement.forEach(select => {
       select.value = '';
    });
}

// 알림창 띄우기
function showNewLessonInfoAlert() {
    alert('새로운 차시 정보를 입력하세요.');
}

// 신규 버튼 클릭 이벤트 핸들러
function handleEditButtonClick(event) {
    event.preventDefault();
    clearTextBoxes();
    showNewLessonInfoAlert();
}


/////////////////////////// 차시 정보 수정 ///////////////////////////
const saveProgress = document.getElementById('saveContent');

function saveContent() {
    const table = document.querySelector('.contentDetailsTable');
    const nthNoString = table.getAttribute('data-nthNo');

    const nthNo = parseInt(nthNoString);
    const lectureCourse = document.getElementById('lectureCourse').value;
    const contentNo = document.getElementById('contentNo').value;
    const lectureDuration = document.getElementById('lectureDuration').value;
    const nthName = document.getElementById('nthName').value;
    const contentName = document.getElementById('contentName').value;
    const runTm = document.getElementById('runTm').value;
    const contentFileNo = document.getElementById('contentFileNo').value;
    const ytbUrl = document.getElementById('ytbUrl').value;
    const contentUrl = document.getElementById('contentUrl').value;

    const data = {
        lectureCourse,
        contentNo,
        lectureDuration,
        contentName,
        runTm,
        contentFileNo,
        ytbUrl,
        contentUrl
    };

    console.log(data);

    fetch(`/admin/api/progress/update/${nthNo}`, {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('작성하신 내용이 저장되었습니다.');
                location.reload();
            } else {
                alert('저장 실패');
            }
        });
}


/// 수정 대기...
function checkDuplicateNthNo() {
    const nthNoInput = document.getElementById('nthNo');
    const nthNoValue = nthNoInput.value;

    // AJAX를 사용하여 서버로 데이터 전송
    const xhr = new XMLHttpRequest();
    xhr.open('POST', `/check-nthNo/${nthNo}`);  // 서버의 API 엔드포인트
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            if (response.isDuplicate) {
                alert('이미 사용 중인 차시 관리 번호입니다. 다른 번호를 입력해주세요.');
                nthNoInput.value = '';
            }
        } else {
            console.error('Error:', xhr.statusText);
        }
    };
    xhr.send(JSON.stringify({ nthNo: nthNoValue }));
}

// 저장 버튼 클릭 이벤트 핸들러
function handleSaveButtonClick(event) {
    event.preventDefault();
    saveContent();
}

// 초기화 함수
function initialize() {
    const newContentButton = document.getElementById('newContent');
    const saveContentButton = document.getElementById('saveContent');

    if (newContentButton) {
        newContentButton.addEventListener('click', handleEditButtonClick);
    }

    if (saveContentButton) {
        saveContentButton.addEventListener('click', handleSaveButtonClick);
    }
}

// 페이지 로드 시 초기화 함수 호출
window.addEventListener('load', initialize);


/////////////////////////// 차시 정보 삭제 ///////////////////////////
function deleteContent() {
    const deleteButton = document.getElementById('deleteContent');

    if (deleteButton) {
        let nthNo = document.getElementById('nthNo').value;
        deleteButton.addEventListener("click", event => {
            fetch(`/admin/api/progress/delete/${nthNo}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert('차시 정보가 삭제되었습니다.');
                    location.reload();
                });
        });
    }
}

// function fetchDataFromDatabase() {
//     return new Promise((resolve, reject) => {
//         const url = '/admin/contentList';
//         const xhr = new XMLHttpRequest();
//
//         xhr.onload = function () {
//             if (xhr.status === 200) {
//                 resolve(xhr.responseText);
//             } else {
//                 reject(new Error('Failed to fetch data from the database.'));
//             }
//         };
//
//         xhr.onerror = function () {
//             reject(new Error('Failed to make the request.'));
//         };
//
//         xhr.open('GET', url);
//         xhr.send();
//     });
// }

// 모달창 열기
function openModal() {
    // 여기서 타임리프를 사용하여 데이터베이스에서 데이터를 가져옵니다.
    // 실제 데이터베이스에서 데이터를 가져오는 로직을 여기에 추가해주세요.

    let modalElement = document.getElementById("myModal");
    let modal = new bootstrap.Modal(modalElement);
    modal.show();

    // 모달 열 때 체크박스 초기화
    const checkboxes = document.querySelectorAll('input.modalContentCheckbox');
    checkboxes.forEach(cb => {
        cb.checked = false;
        cb.disabled = false;
    });
}

// 모달창 내 검색 버튼
function searchModal() {
    const searchTerm = document.getElementById("searchInput").value.toLowerCase();

    const contentTable = document.getElementById("contentTable");
    const rows = contentTable.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {
        const contentName = rows[i].getElementsByTagName('td')[1].innerText.toLowerCase();
        if (contentName.includes(searchTerm)) {
            rows[i].style.display = '';
        } else {
            rows[i].style.display = 'none';
        }
    }
}

// 모달창 체크박스 체크 수 제한
function handleCheckboxSelection(checkbox) {
    const checkboxes = document.querySelectorAll('.modalContentCheckbox');
    checkboxes.forEach((cb) => {
        if (cb !== checkbox) {
            cb.disabled = checkbox.checked;
        }
    });
}

// 모달창 선택
function selectModal() {
    let checkedInput = document.querySelectorAll('input.modalContentCheckbox:checked');

    if (checkedInput.length === 0) {
        alert("등록할 콘텐츠 정보를 선택하세요.");
        return;
    }

    for (let i = 0; i < checkedInput.length; i++) {
        handleCheckboxSelection(checkedInput[i]);

        // 데이터 값 가져오기
        let contentNo = checkedInput[i].getAttribute('data-modalContentNo');
        let contentName = checkedInput[i].getAttribute('data-modalContentName');
        let runTm = checkedInput[i].getAttribute('data-modalRunTm');
        let ytbUrl = checkedInput[i].getAttribute('data-modalYtbUrl');
        let contentUrl = checkedInput[i].getAttribute('data-modalContentUrl');

        document.getElementById('contentNo').value = contentNo;
        document.getElementById('contentName').value = contentName;
        document.getElementById('runTm').value = runTm;
        document.getElementById('ytbUrl').value = ytbUrl;
        document.getElementById('contentUrl').value = contentUrl;
    }

    // 선택 후 모달창 닫기
    $('#myModal').modal('hide');
}

// 모달창 닫기 버튼
function hideModal() {
    let modalElement = document.getElementById("myModal");
    let modal = bootstrap.Modal.getInstance(modalElement);
    modal.hide();
}