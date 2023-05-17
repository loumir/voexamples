document.addEventListener('DOMContentLoaded', function() {
    const stepQuery = document.querySelector(".stepquery");
    const prevBtn = stepQuery.querySelector("#prev-btn");
    const nextBtn = stepQuery.querySelector("#next-btn");
    const steps = stepQuery.querySelectorAll(".step");
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
