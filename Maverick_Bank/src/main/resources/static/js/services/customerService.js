window.customerService = {
    async getAll() {
        const session = JSON.parse(localStorage.getItem("mb_session") || "null");

        const response = await fetch("/api/customer-profile/get/all", {
            method: "GET",
            headers: {
                "Authorization": session?.token || ""
            }
        });

        if (!response.ok) {
            const msg = await response.text();
            throw new Error(msg || "Unable to load customers");
        }

        return await response.json();
    }
};