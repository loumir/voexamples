document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll(".show-image");

    for (let i = 0; i < images.length; i++) {

        const button = images[i].querySelector("#toggle");
        const image = images[i].querySelector("#toggled");

        button.addEventListener("change", function() {
            if (button.checked) {
                image.classList.add("active");
            } else {
                image.classList.remove("active");
            }
        })
    }
});