document.addEventListener('DOMContentLoaded', function() {
    const stepQuery = document.querySelectorAll(".stepquery");
    if (!stepQuery) return;
    for (let i = 0; i < stepQuery.length; i++) {
        function showStep(step) {
            steps[currentStep].classList.remove("active");
            steps[step].classList.add("active");

            prevBtn.disabled = step === 0;

            nextBtn.disabled = step === steps.length - 1;

            currentStep = step;
        }

        const prevBtn = stepQuery[i].querySelector("#prev-btn");
        const nextBtn = stepQuery[i].querySelector("#next-btn");
        const steps = stepQuery[i].querySelectorAll(".step");
        let currentStep = 0;

        prevBtn.addEventListener("click", () => {
            showStep(currentStep - 1);
        });

        nextBtn.addEventListener("click", () => {
            if (currentStep !== steps.length - 1) {
                showStep(currentStep + 1);
            }
        });

        showStep(currentStep);
    }

});
