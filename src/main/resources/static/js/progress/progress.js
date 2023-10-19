

// 강의 추가 또는 수정 요청
function sendProgressRequest(data, endpoint, successMessage) {
    fetch(endpoint, {
        method: 'POST',
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
            if (data.success) {
                alert('저장 실패');
            } else {
                alert(successMessage);
                location.reload();
            }
        })
        .catch(error => {
            alert('작업 실패');
            console.error('Error:', error);
        });
}

// 폼 데이터를 JSON으로 변환
function formDataToJson(formData) {
    const jsonObject = {};
    formData.forEach((value, key) => {
        jsonObject[key] = value;
    });
    return jsonObject;
}

document.addEventListener("DOMContentLoaded", function () {
    const fieldNamesMap = {
        "lectureNo": "강의 번호",
        "lectureCourse": "과정구분",
        "contentNo": "콘텐츠 관리 번호",
        "nthDuration": "차시순서",
        "contentName": "콘텐츠명",
        "runTm": "차시 학습 시간",
        "ytbUrl": "Youtube 연동 번호",
        "contentUrl": "콘텐츠 호출 URL"
    };

    // 필수 입력 필드가 비어있는지 확인
    function checkRequiredFields(requiredFields) {
        const emptyFieldNames = [];
        for (const fieldName of requiredFields) {
            const field = document.getElementById(fieldName);
            if (!field.value) {
                emptyFieldNames.push(fieldNamesMap[fieldName]);
            }
        }
        return emptyFieldNames;
    }

    document.getElementById("newContent").addEventListener("click", function () {
        const requiredFields = ["lectureCourse", "contentNo", "nthDuration"];
        const emptyFieldNames = checkRequiredFields(requiredFields);

        if (emptyFieldNames.length > 0) {
            alert(`${emptyFieldNames.join(', ')} 항목은 필수 입력값입니다.`);
        } else {
            const progressForm = document.getElementById("progressForm");
            const formData = new FormData(progressForm);
            const data = formDataToJson(formData);

            sendProgressRequest(data, '/admin/api/progress/add', '차시 정보가 성공적으로 저장되었습니다.');
        }
    });
});

/////////////////////////// 차시 정보 수정 ///////////////////////////

// function editContent() {
//     const editButton = document.getElementById('editContent');
//
//     if (editButton) {
//         editButton.addEventListener("click", event => {
//             const lectureNo = document.getElementById('lectureNo').value;
//             const lectureCourse = document.getElementById('lectureCourse').value;
//             const contentNo = document.getElementById('contentNo').value;
//             const nthDuration = document.getElementById('nthDuration').value;
//             const contentName = document.getElementById('contentName').value;
//             const runTm = document.getElementById('runTm').value;
//             const ytbUrl = document.getElementById('ytbUrl').value;
//             const contentUrl = document.getElementById('contentUrl').value;
//
//
//             const data = {
//                 lectureNo,
//                 lectureCourse,
//                 contentNo,
//                 nthDuration,
//                 contentName,
//                 runTm,
//                 ytbUrl,
//                 contentUrl
//             };
//
//             fetch(`/admin/api/progress/update/${nthDuration}`, {
//                 method: 'PUT',
//                 headers: {
//                     'Content-Type': 'application/json'
//                 },
//                 body: JSON.stringify(data)
//             })
//                 .then(response => {
//                     if (!response.ok) {
//                         throw new Error('Network response was not ok');
//                     }
//                     return response.json();
//                 })
//                 .then(data => {
//                     alert('차시 정보가 수정되었습니다.');
//                     location.reload();
//                 })
//                 .catch(error => {
//                     alert('차시 정보 수정에 실패하였습니다.');
//                     console.error('Error:', error);
//                 });
//         });
//     }
// }


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