function displayToast(caption, body) {
	const toast = window.document.createElement("div");
	toast.classList.add("toast");
	toast.setAttribute("role", "alert");
	toast.setAttribute("aria-live", "polite");
	toast.setAttribute("aria-atomic", "true");
	toast.setAttribute("data-bs-delay", "5000");
	const toastHeader = window.document.createElement("div");
	toastHeader.classList.add("toast-header");
	toastHeader.innerHTML += "<strong class=\"me-auto\">" + caption + "</strong>";
	const toastCloseButton = window.document.createElement("button");
	toastCloseButton.type = "button";
	toastCloseButton.classList.add("btn-close");
	toastCloseButton.setAttribute("data-bs-dismiss", "toast");
	toastCloseButton.setAttribute("aria-label", "Close");
	toastHeader.appendChild(toastCloseButton);
	toast.appendChild(toastHeader);
	const toastBody = window.document.createElement("div");
	toastBody.classList.add("toast-body");
	toastBody.innerHTML += body;
	toast.appendChild(toastBody);

	window.document.querySelector(".toast-container").appendChild(toast);

	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toast);
	toast.addEventListener("hidden.bs.toast", () => {
		toast.remove();
	});
	toastBootstrap.show();
}
