document.addEventListener('DOMContentLoaded', function() {
    const button = document.querySelector('#top');
    if (!button) return;

    button.addEventListener('click', function() {
        window.scrollTo(0, 0);
    });
});