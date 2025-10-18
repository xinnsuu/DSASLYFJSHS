document.addEventListener('DOMContentLoaded', function() {
    const accordion = document.getElementById('errorAccordion');
    if (accordion) {
        const header = accordion.querySelector('.accordion-header');
        const content = accordion.querySelector('.accordion-content');
        
        header.addEventListener('click', function() {
            accordion.classList.toggle('active');
            const arrow = header.querySelector('span:last-child');
            if (arrow) {
                arrow.textContent = accordion.classList.contains('active') ? '▲' : '▼';
            }
        });
    }
});