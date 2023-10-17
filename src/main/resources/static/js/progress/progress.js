

// 차시 관리 번호 저장
function newContent() {
    const nthNo = parseInt(document.getElementById('nthNo').value);
    const lectureCourse = document.getElementById('lectureCourse').value;
    const contentNo = parseInt(document.getElementById('contentNo').value);
    const lectureDuration = document.getElementById('lectureDuration').value;
    const contentName = document.getElementById('contentName').value;
    const runTm = document.getElementById('runTm').value;
    const ytbUrl = document.getElementById('ytbUrl').value;
    const contentUrl = document.getElementById('contentUrl').value;

    // text 박스가 비어있는지 확인
    if (!lectureCourse || !contentNo || !lectureDuration) {
        alert('필드를 채워주세요.');
        return;
    }

    const data = {
        nthNo,
        lectureCourse,
        contentNo,
        lectureDuration,
        contentName,
        runTm,
        ytbUrl,
        contentUrl
    };

    // API 호출
    fetch('/admin/api/progress/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            alert('차시 정보가 성공적으로 저장되었습니다.');
        })
        .catch(error => {
            alert('차시 정보 저장에 실패하였습니다.');
            console.error('Error:', error);
        });
}

// 신규 버튼 클릭 이벤트 핸들러
function handleEditButtonClick(event) {
    event.preventDefault();
    newContent();
}


/////////////////////////// 차시 정보 수정 ///////////////////////////

function editContent() {
    const editButton = document.getElementById('editContent');

    if (editButton) {
        editButton.addEventListener("click", event => {
            const nthNo = document.getElementById('nthNo').value;
            const lectureCourse = document.getElementById('lectureCourse').value;
            const contentNo = document.getElementById('contentNo').value;
            const lectureDuration = document.getElementById('lectureDuration').value;
            const contentName = document.getElementById('contentName').value;
            const runTm = document.getElementById('runTm').value;
            const ytbUrl = document.getElementById('ytbUrl').value;
            const contentUrl = document.getElementById('contentUrl').value;


            const data = {
                nthNo,
                lectureCourse,
                contentNo,
                lectureDuration,
                contentName,
                runTm,
                ytbUrl,
                contentUrl
            };

            fetch(`/admin/api/progress/update/${nthNo}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('차시 정보가 수정되었습니다.');
                    location.reload();
                })
                .catch(error => {
                    alert('차시 정보 수정에 실패하였습니다.');
                    console.error('Error:', error);
                });
        });
    }
}


/// 수정 대기...
// function checkDuplicateNthNo() {
//     const nthNoInput = document.getElementById('nthNo');
//     const nthNoValue = nthNoInput.value;
//
//     // AJAX를 사용하여 서버로 데이터 전송
//     const xhr = new XMLHttpRequest();
//     xhr.open('POST', `/check-nthNo/${nthNo}`);  // 서버의 API 엔드포인트
//     xhr.setRequestHeader('Content-Type', 'application/json');
//     xhr.onload = function () {
//         if (xhr.status === 200) {
//             const response = JSON.parse(xhr.responseText);
//             if (response.isDuplicate) {
//                 alert('이미 사용 중인 차시 관리 번호입니다. 다른 번호를 입력해주세요.');
//                 nthNoInput.value = '';
//             }
//         } else {
//             console.error('Error:', xhr.statusText);
//         }
//     };
//     xhr.send(JSON.stringify({nthNo: nthNoValue}));
// }


/////////////////////////// 차시 정보 삭제 ///////////////////////////
function deleteContent() {
    const deleteButton = document.getElementById('deleteContent');

    if (deleteButton) {
        deleteButton.addEventListener("click", event => {
            const checkboxes = document.querySelectorAll('.contentCheckbox:checked');
            const nthNosToDelete = Array.from(checkboxes).map(checkbox => checkbox.getAttribute('data-nthNo'));

            if (nthNosToDelete.length === 0) {
                alert('선택된 항목이 없습니다.');
                return;
            }

            const confirmDelete = confirm('정말로 선택된 항목을 삭제하시겠습니까?');
            if (!confirmDelete) {
                return;
            }

            // API 호출
            Promise.all(nthNosToDelete.map(nthNo =>
                fetch(`/admin/api/progress/delete/${nthNo}`, {
                    method: 'DELETE'
                })
            ))
                .then(responses => {
                    alert('차시 정보가 삭제되었습니다.');
                    location.reload();
                })
                .catch(error => {
                    alert('차시 정보 삭제에 실패하였습니다.');
                    console.error('Error:', error);
                });
        });
    }
}