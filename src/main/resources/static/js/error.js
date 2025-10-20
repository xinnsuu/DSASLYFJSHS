document.addEventListener('DOMContentLoaded', function() {
    const header = document.getElementById('errorAccordionHeader');
    const content = document.getElementById('errorAccordionContent');
    const icon = document.getElementById('accordionIcon');
    
    if (header && content && icon) {
        header.addEventListener('click', function() {
            content.classList.toggle('is-hidden');
            icon.classList.toggle('fa-chevron-down');
            icon.classList.toggle('fa-chevron-up');
        });
    }
});