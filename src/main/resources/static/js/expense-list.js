console.log("expense-list.js loaded");

const categoryLabels = {
    rent: "🏠 Rent / Mortgage",
    groceries: "🍽️ Groceries",
    utilities: "🔌 Utilities",
    transportation: "🚗 Transportation",
    dining: "☕ Dining Out",
    entertainment: "🎬 Entertainment",
    clothing: "👕 Clothing",
    personalCare: "💇 Personal Care",
    loanPayments: "💳 Loan Payments",
    savings: "🏦 Savings",
    subscriptions: "💡 Subscriptions",
    gifts: "🎁 Gifts & Donations",
    other: "❓ Other"
};

document.addEventListener("DOMContentLoaded", () => {
    const categoryCells = document.querySelectorAll("td[data-category]");
    const categoryFilter = document.getElementById("categoryFilter");

    categoryCells.forEach(cell => {
        const rawValue = cell.getAttribute("data-category");
        const label = categoryLabels[rawValue] || rawValue;
        cell.textContent = label;
    });

    categoryFilter.addEventListener("change", (e) => {
        const selectedCategory = e.target.value;
        const expenseRows = document.querySelectorAll(".expense-row");

        expenseRows.forEach(row => {
            const categoryCell = row.querySelector("td[data-category]");
            const categoryValue = categoryCell.getAttribute("data-category");

            if (selectedCategory === "all" || categoryValue === selectedCategory) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    });
});
