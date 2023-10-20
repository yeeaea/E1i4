// 모달창 열기
function openModal() {
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
        const contentName = rows[i].getElementsByTagName('td')[2].innerText.toLowerCase();
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