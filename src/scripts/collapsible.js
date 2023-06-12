document.addEventListener('DOMContentLoaded', function() {
    const collapse = document.querySelectorAll('.collapse')

    for (let i = 0; i < collapse.length; i++) {
        const collapsible = collapse[i].querySelector('.collapsible');
        const content = collapse[i].querySelector('.content');

        collapsible.addEventListener('click', function() {
            this.classList.toggle("active");
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }
});