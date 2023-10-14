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

    // 예: 체크박스가 클릭될 때 editContent 함수 호출
    // editContent();
}

function addCheckboxListeners() {
    const checkboxes = document.querySelectorAll('.contentCheckbox');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('click', updateContentDetails);
    });
}

// 중복 호출 방지
addCheckboxListeners();



// 차시 정보 추가
// 텍스트 박스 내용 비우기
function clearTextBoxes() {
    const inputElements = document.querySelectorAll('input');

    inputElements.forEach(input => {
        input.value = '';
    });
}

// 신규 버튼 클릭 시 텍스트 박스 편집 가능하도록 토글
// function toggleEditing() {
//     const inputElements = document.querySelectorAll('input[readonly]');
//
//     inputElements.forEach(input => {
//         input.readOnly = !input.readOnly;
//     });
// }

// 알림창 띄우기
function showNewLessonInfoAlert() {
    alert('새로운 차시 정보를 입력하세요.');
}

// 신규 버튼 클릭 이벤트 핸들러
function handleEditButtonClick(event) {
    event.preventDefault();
    clearTextBoxes();
//    toggleEditing();
    showNewLessonInfoAlert();
}



/////////////////////////// 차시 정보 수정 ///////////////////////////
const saveProgress = document.getElementById('saveProgress');
function saveContent() {
    const table = document.querySelector('.contentDetailsTable');
    const  nthNoString = table.getAttribute('data-nthNo');

    const nthNo = parseInt(nthNoString);
    const lectureCourse= document.getElementById('lectureCourse').value;
    const contentNo= document.getElementById('contentNo').value;
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
            if(data) {
                alert('작성하신 내용이 저장되었습니다.');
                setTextBoxesReadOnly(true);
            } else {
                alert('저장 실패');
            }
        });
}

// 텍스트 박스의 읽기 전용 상태를 설정하는 함수
function setTextBoxesReadOnly(isReadOnly) {
    const textBoxes = document.querySelectorAll('input[type="text"]');
    textBoxes.forEach(textBox => {
        textBox.readOnly = isReadOnly;
    });
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



// 삭제
function deleteContent() {
    const deleteButton = document.getElementById('deleteContent');
    let nthNo = document.getElementById('nthNo').value;

    if (deleteButton) {
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

// 모달창 선택 버튼
function selectModal() {
    // 선택 기능 구현
    console.log('Select button clicked.');
}

// 모달창 닫기 버튼
function hideModal() {
    let modalElement = document.getElementById("myModal");
    let modal = bootstrap.Modal.getInstance(modalElement);
    modal.hide();
}