document.addEventListener('DOMContentLoaded', function() {
    // Find the section selector dropdown
    const sectionSelector = document.getElementById('sectionSelector');

    // If it exists on the page, add an event listener
    if (sectionSelector) {
        sectionSelector.addEventListener('change', function() {
            // Submit the parent form when the selection changes
            this.form.submit();
        });
    }
});
