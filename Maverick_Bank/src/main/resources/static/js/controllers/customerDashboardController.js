document.addEventListener("DOMContentLoaded", async () => {
    const tbody = document.getElementById("customerTableBody");
    const alertBox = document.getElementById("globalAlert");

    try {
        const customers = await window.customerService.getAll();

        if (!tbody) return;

        tbody.innerHTML = "";

        customers.forEach(c => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${c.id ?? ""}</td>
                <td>${c.fullName ?? ""}</td>
                <td>${c.email ?? ""}</td>
                <td>${c.phone ?? ""}</td>
                <td>${c.userId ?? ""}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary">View</button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (e) {
        if (alertBox) {
            alertBox.innerHTML = `
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Unable to load customers.
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            `;
        }
        console.error(e);
    }
});