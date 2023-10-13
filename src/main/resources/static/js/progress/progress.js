// 차시 관리 checkbox 클릭 시 상세 정보 출력
function updateContentDetails(checkbox) {
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
        checkbox.addEventListener('click', () => {
            if (checkbox.checked) {
                updateContentDetails(checkbox);
            }
        });
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
