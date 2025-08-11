document.addEventListener("DOMContentLoaded", () => {
    const ctx = document.getElementById('expensesChart').getContext('2d');
    const yearFilter = document.getElementById("yearFilter");

    // Labels for months
    const monthLabels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
                         'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    // Create chart instance with empty data
    const expensesChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: monthLabels,
            datasets: [{
                label: `Expenses ($) - ${yearFilter.value}`,
                data: [],
                backgroundColor: new Array(12).fill('rgba(75, 192, 192, 0.8)'),
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    // Function to fetch data & update chart
    function updateChart(year) {
        fetch(`/expenses/api/${year}`)
            .then(response => response.json())
            .then(data => {
                expensesChart.data.datasets[0].data = data;
                expensesChart.data.datasets[0].label = `Expenses ($) - ${year}`;
                expensesChart.update();
            })
            .catch(err => console.error("Error fetching data:", err));
    }

    // Initial load
    updateChart(yearFilter.value);

    // On dropdown change
    yearFilter.addEventListener("change", (e) => {
        updateChart(e.target.value);
    });
});

