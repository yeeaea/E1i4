document.addEventListener('DOMContentLoaded', function () {
    const accordionButtons = document.querySelectorAll('.accordion-button');

    accordionButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const content = button.nextElementSibling; // 다음 형제 요소인 accordion-content를 가져옴
            content.style.display = content.style.display === 'none' ? 'block' : 'none'; // 토글 표시
        });
    });
});