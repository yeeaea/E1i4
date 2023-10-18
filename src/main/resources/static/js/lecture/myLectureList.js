document.addEventListener('DOMContentLoaded', function () {
    const accordionButtons = document.querySelectorAll('.accordion-button');

    accordionButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            const content = button.nextElementSibling; // Get the accordion-content

            if (content.style.display === 'none' || content.style.display === '') {
                content.style.display = 'block';
                button.querySelector('.icon i').classList.remove('fa-caret-down');
                button.querySelector('.icon i').classList.add('fa-caret-up');
            } else {
                content.style.display = 'none';
                button.querySelector('.icon i').classList.remove('fa-caret-up');
                button.querySelector('.icon i').classList.add('fa-caret-down');
            }
        });
    });
});
