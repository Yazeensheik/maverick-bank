function selectLoan(loanId) {
  document.getElementById("loanId").value = loanId;
}

async function getLoan() {
  const id = document.getElementById("searchId").value;
  const table = document.getElementById("loanTable");

  if (!id) {
    alert("Enter Application ID");
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/loan-applications/" + id);

    if (!response.ok) {
      throw new Error("Loan application not found");
    }

    const data = await response.json();

    table.innerHTML = `
      <tr>
        <td>${data.loanType || data.loanId || "-"}</td>
        <td>${data.amount || "-"}</td>
        <td>${data.purpose || "-"}</td>
        <td>${data.status || "-"}</td>
      </tr>
    `;
  } catch (error) {
    table.innerHTML = `
      <tr>
        <td colspan="4" class="text-center text-danger">No loan application found</td>
      </tr>
    `;
    console.error(error);
  }
}

async function loadLoans() {
  const table = document.getElementById("loanList");

  try {
    const response = await fetch("http://localhost:8080/api/loan-applications/loans");

    if (!response.ok) {
      throw new Error("Failed to load loans");
    }

    const data = await response.json();

    table.innerHTML = "";

    data.forEach(loan => {
      const row = `
        <tr>
          <td>${loan.id}</td>
          <td>${loan.loanType}</td>
          <td>${loan.interestRate}%</td>
          <td>
            <button class="btn btn-success btn-sm" onclick="selectLoan(${loan.id})">
              Apply
            </button>
          </td>
        </tr>
      `;
      table.innerHTML += row;
    });
  } catch (error) {
    table.innerHTML = `
      <tr>
        <td colspan="4" class="text-center text-danger">Failed to load loans</td>
      </tr>
    `;
    console.error(error);
  }
}

async function applyLoan() {
  const loanData = {
    loanId: document.getElementById("loanId").value,
    amount: document.getElementById("amount").value,
    purpose: document.getElementById("purpose").value
  };

  if (!loanData.loanId || !loanData.amount || !loanData.purpose) {
    alert("Fill all fields");
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/loan-applications/apply", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(loanData)
    });

    if (!response.ok) {
      throw new Error("Failed to apply loan");
    }

    const data = await response.json();

    alert("Loan Applied Successfully!");

    document.getElementById("searchId").value = data.applicationId || "";
    document.getElementById("applyMessage").textContent =
      "Loan applied successfully. Application ID: " + (data.applicationId || "");

    document.getElementById("amount").value = "";
    document.getElementById("purpose").value = "";
  } catch (error) {
    console.error(error);
    alert("Loan application failed");
  }
}

document.addEventListener("DOMContentLoaded", function () {
  loadLoans();
});