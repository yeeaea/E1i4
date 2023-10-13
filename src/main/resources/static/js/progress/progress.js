function loadLectureInfo() {
    let selectedYear = document.getElementById('yearSelect').value;
    let selectedCourse = document.getElementById('courseSelect').value;

    loadData(selectedYear, selectedCourse);

    // 가정: 데이터는 강의 정보 객체의 배열로 가정하고, 선택한 강의년도와 과정구분에 따라 데이터를 필터링합니다.
    let filteredData = data.filter(function (lectureInfo) {
        return (!selectedYear || lectureInfo.lectureYear === selectedYear) &&
            (!selectedCourse || lectureInfo.lectureCourse === selectedCourse);
    });

    // TODO: 필터링된 데이터를 HTML에 표시하는 로직을 구현합니다.
    displayFilteredData(filteredData);  // 예시 함수, 실제 데이터 표시 로직으로 대체해야 합니다.
}

function loadData(selectedYear, selectedCourse) {
    const xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);
            // TODO: 데이터를 활용하여 원하는 동작을 수행합니다.
            // 예시: 필요한 동작을 구현하거나 적절한 함수를 호출할 수 있습니다.
        } else {
            console.error('Failed to fetch data.');
        }
    };

    xhr.open('GET', `/api/progress?year=${selectedYear}&course=${selectedCourse}`);
    xhr.send();
}

// 예시 함수: 필터링된 데이터를 HTML에 표시하는 함수
function displayFilteredData(data) {
    // TODO: 실제로 필터링된 데이터를 HTML에 표시하는 로직을 구현합니다.
    // 예시: 간단히 console.log로 데이터를 출력하는 예시
    data.forEach(function (lectureInfo) {
        console.log(lectureInfo);
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
function addContent() {
    const newContentButton = document.getElementById('newContent');
    if (newContentButton) {
        newContentButton.addEventListener('click', event => {
            event.preventDefault();
            clearTextBoxes();
            toggleEditing();
            showNewLessonInfoAlert();
        });
    }

    // 텍스트 박스 내용 비우기
    function clearTextBoxes() {
        const inputElements = document.querySelectorAll('input');

        inputElements.forEach(input => {
            input.value = '';
        });
    }

    // 신규 버튼 클릭 시 텍스트 박스 편집 가능하도록 토글
    function toggleEditing() {
        const inputElements = document.querySelectorAll('input[readonly]');

        inputElements.forEach(input => {
            input.readOnly = !input.readOnly;
        });
    }

    // 알림창 띄우기
    function showNewLessonInfoAlert() {
        alert('새로운 차시 정보를 입력하세요.');
    }
}

// 차시 정보 수정
function editContent() {
    const saveContentButton = document.getElementById('saveContent');
    if (saveContentButton) {
        saveContentButton.addEventListener('click', event => {
            event.preventDefault();
            let params = new URLSearchParams(location.search);
            let nthNo = params.get('nthNo');

            fetch(`/admin/api/progress/update/${nthNo}`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    nthNo: document.getElementById('nthNo').value,
                    lectureCourse: document.getElementById('lectureCourse').value,
                    contentNo: document.getElementById('contentNo').value,
                    lectureDuration: document.getElementById('lectureDuration').value,
                    nthName: document.getElementById('nthName').value,
                    contentName: document.getElementById('contentName').value,
                    runTm: document.getElementById('runTm').value,
                    contentFileNo: document.getElementById('contentFileNo').value,
                    ytbUrl: document.getElementById('ytbUrl').value,
                    contentUrl: document.getElementById('contentUrl').value
                })
            })
                .then(() => {
                    alert('작성하신 내용이 저장되었습니다.');
                    saveChanges();
                });
        });
    }

    // 텍스트 박스 읽기 전용으로 설정
    function saveChanges() {
        document.getElementById('nthNo').readOnly = true;
        document.getElementById('lectureCourse').readOnly = true;
        document.getElementById('contentNo').readOnly = true;
        document.getElementById('lectureDuration').readOnly = true;
        document.getElementById('nthName').readOnly = true;
        document.getElementById('contentName').readOnly = true;
        document.getElementById('runTm').readOnly = true;
        document.getElementById('contentFileNo').readOnly = true;
        document.getElementById('ytbUrl').readOnly = true;
        document.getElementById('contentUrl').readOnly = true;
    }
}

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