document.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-bs-toggle='modal']");
    if (!btn) return;

    const modalEl = document.getElementById('globalModal');
    if (!modalEl) return;

    let modal = bootstrap.Modal.getInstance(modalEl);
    if (!modal) {
        modal = new bootstrap.Modal(modalEl, { backdrop: true, keyboard: true });
    }

    modalEl.querySelector(".modal-title").textContent = btn.getAttribute("data-modal-title") || "";
    modalEl.querySelector(".modal-body p").textContent = btn.getAttribute("data-modal-body") || "";
    modalEl.querySelector("#modalConfirmBtn").textContent = btn.getAttribute("data-modal-confirm") || "확인";

    modal.show();
});
