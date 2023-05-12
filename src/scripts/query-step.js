document.addEventListener('DOMContentLoaded', function() {
    const prevBtn = document.getElementById("prev-btn");
    const nextBtn = document.getElementById("next-btn");
    const steps = document.querySelectorAll(".step");
    let currentStep = 0;

    function showStep(step) {
        steps[currentStep].classList.remove("active");
        steps[step].classList.add("active");

        prevBtn.disabled = step === 0;

        nextBtn.disabled = step === steps.length - 1;

        currentStep = step;
    }

    prevBtn.addEventListener("click", () => {
        showStep(currentStep - 1);
    });

    nextBtn.addEventListener("click", () => {
        if (currentStep !== steps.length - 1) {
            showStep(currentStep + 1);
        }
    });

    showStep(currentStep);

});