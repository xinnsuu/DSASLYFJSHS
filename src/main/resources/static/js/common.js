document.addEventListener('DOMContentLoaded', function() {
    const aboutButton = document.getElementById('aboutButton');
    const aboutModal = document.getElementById('aboutModal');
    const htmlElement = document.documentElement;

    if (aboutButton && aboutModal) {
        aboutButton.addEventListener('click', function() {
            aboutModal.classList.add('is-active');
            htmlElement.classList.add('is-modal-active');
        });
    }
    
    const modalCloseButtons = document.querySelectorAll('.modal .delete, .modal-background');
    modalCloseButtons.forEach(button => {
        button.addEventListener('click', function() {
            if (aboutModal) {
                aboutModal.classList.remove('is-active');
                htmlElement.classList.remove('is-modal-active');
            }
        });
    });
});