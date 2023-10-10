// // JavaScript 강의 차시별 콘텐츠 상세 정보 보여주기
// function showContentDetails(contentNo) {
//     // 해당 콘텐츠의 상세 정보를 서버로부터 받아와 테이블에 표시하는 로직을 구현
//     console.log('Showing details for contentNo:', contentNo);
//     // 여기에 상세 정보 보여주기 로직 추가
//     var contentDetails = `
//                 <table>
//                     <!-- 상세 정보 표시 -->
//                     <!-- 예시로 몇 가지 항목을 표시 -->
//                     <tr>
//                         <th>상세 정보 항목 1</th>
//                         <td>내용 1</td>
//                     </tr>
//                     <tr>
//                         <th>상세 정보 항목 2</th>
//                         <td>내용 2</td>
//                     </tr>
//                 </table>
//                 <button onclick="editContent('${contentNo}')">Edit</button>
//                 <button onclick="deleteContent('${contentNo}')">Delete</button>
//             `;
//     document.getElementById('contentDetails').innerHTML = contentDetails;
// }
//
// function editContent(contentNo) {
//     // 수정 로직 구현: contentNo를 기반으로 해당 콘텐츠 수정 화면으로 이동하거나 필요한 동작을 수행
//     console.log('Editing content with contentNo:', contentNo);
//     // 여기에 수정 로직 추가
// }
//
// function deleteContent(contentNo) {
//     // 삭제 로직 구현: contentNo를 기반으로 해당 콘텐츠 삭제
//     console.log('Deleting content with contentNo:', contentNo);
//     // 여기에 삭제 로직 추가
// }

// JavaScript 강의 차시별 콘텐츠 상세 정보 보여주기
function showContentDetails(nthNo, nthName, contentNo, contentName, contentDesc, contentFileNo, ytbUrl, contentUrl, runTm) {
    // 강의 차시별 콘텐츠 상세 정보를 가져와서 표시
    let nthNoValue = "${progressInfo.nthNo}";
    let nthNameValue = "${progressInfo.nthName}";
    let contentNoValue = "${progressInfo.contentNo.contentNo}";

    console.log('nthNo:', nthNoValue);
    console.log('nthName:', nthNameValue);
    console.log('contentNo:', contentNoValue);

    // 강의 차시별 콘텐츠 상세 정보를 표시
    document.getElementById('nthNo').innerText = nthNoValue;
    document.getElementById('nthName').innerText = nthNameValue;
    document.getElementById('contentNo').innerText = contentNoValue;

    // 강의 차시 번호를 출력
    console.log('Showing details for nthNo:', nthNoValue);
}

// JavaScript 강의 차시별 콘텐츠 상세 정보 보여주기
// function showContentDetails(nthNo, nthName, contentNo, contentName, contentDesc, contentFileNo, ytbUrl, contentUrl, runTm) {
//     document.getElementById('nthNo').value = nthNo;
//     document.getElementById('nthName').value = nthName;
//     document.getElementById('contentNo').value = contentNo;
//     // 필요한 경우 이하의 콘텐츠 관련 정보들도 추가
//
//     // 상세 정보 표시
//     console.log('Showing details for nthNo:', nthNo);
// }