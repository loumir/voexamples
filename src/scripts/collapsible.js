document.addEventListener('DOMContentLoaded', function() {
    const collapsible = document.querySelector('.collapsible');
    const content = document.querySelector('.content');

    collapsible.addEventListener('click', function() {
        this.classList.toggle("active");
        if (content.style.display === "block") {
            content.style.display = "none";
        } else {
            content.style.display = "block";
        }
    });
});