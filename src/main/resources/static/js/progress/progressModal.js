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

    // 페이지 이동 링크들에 클릭 이벤트 핸들러를 추가
    const pageLinks = document.querySelectorAll('.page-link');
    pageLinks.forEach(link => {
        link.addEventListener('click', handlePageLinkClick);
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

// 페이지 이동 및 모달 창 유지 처리 함수
function handlePageLinkClick(event) {
    event.preventDefault(); // 기본 동작 방지
    const pageLink = event.target; // 클릭한 페이지 이동 링크
    const pageUrl = pageLink.getAttribute('href'); // 클릭한 페이지 이동의 URL

    // 페이지 번호와 페이지 크기를 추출
    const page = getPageNumberFromUrl(pageUrl);
    const pageSize = 10; // 페이지 크기 (원하는 크기로 설정)

    // AJAX를 사용하여 페이지 내용을 업데이트
    fetch(`/server/api/progress?page=${page}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            // 페이지 내용을 업데이트하는 코드
            const contentContainer = document.querySelector('#contentTable');
            contentContainer.innerHTML = generateTableFromData(data);

            // 다시 데이터를 렌더링한 후에 이벤트 핸들러를 재등록
            const checkboxes = document.querySelectorAll('input.modalContentCheckbox');
            checkboxes.forEach(cb => {
                cb.addEventListener('click', function() {
                    handleCheckboxSelection(this);
                });
            });
        })
        .catch(error => {
            console.error('페이지 이동 실패:', error);
        });
}

function getPageNumberFromUrl(url) {
    // URL에서 page 쿼리 파라미터 추출
    const match = url.match(/[?&]page=(\d+)/);
    if (match) {
        return parseInt(match[1], 10);
    }
    return 1; // 기본 페이지 번호 (첫 번째 페이지)
}

function generateTableFromData(data) {
    // data를 사용하여 테이블 생성
    let tableHTML = '<table class="table" id="contentTable">';
    tableHTML += '<thead><tr><th></th><th>번호</th><th>콘텐츠명</th><th>차시 학습 시간</th></tr></thead>';
    tableHTML += '<tbody>';

    data.forEach(content => {
        tableHTML += `<tr><td><input type="checkbox" class="modalContentCheckbox" ...></td>`;
        tableHTML += `<td>${content.contentNo}</td>`;
        tableHTML += `<td>${content.contentName}</td>`;
        tableHTML += `<td>${content.runTm}</td></tr>`;
    });

    tableHTML += '</tbody></table>';
    return tableHTML;
}

// 페이지 이동 링크들에 클릭 이벤트 핸들러를 추가
const pageLinks = document.querySelectorAll('.page-link');
pageLinks.forEach(link => {
    link.addEventListener('click', handlePageLinkClick);
});

// 모달창 닫기 버튼
function hideModal() {
    let modalElement = document.getElementById("myModal");
    let modal = bootstrap.Modal.getInstance(modalElement);
    modal.hide();
}