document.addEventListener('DOMContentLoaded', function() {
    const button = document.querySelector('#top');

    button.addEventListener('click', function() {
        window.scrollTo(0, 0);
    });
});