document.addEventListener('DOMContentLoaded', function() {
    // Add event listener to the about button
    const aboutButton = document.getElementById('aboutButton');
    if (aboutButton) {
        aboutButton.addEventListener('click', function() {
            document.getElementById('aboutModal').classList.add('is-active');
        });
    }
    
    // Add event listener for the modal close functionality
    const modalCloseButtons = document.querySelectorAll('.modal .delete, .modal-background');
    modalCloseButtons.forEach(button => {
        button.addEventListener('click', function() {
            document.getElementById('aboutModal').classList.remove('is-active');
        });
    });
});