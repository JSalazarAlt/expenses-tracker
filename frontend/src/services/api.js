const API_BASE_URL = 'http://localhost:8080/api';

export const expenseAPI = {
    getAll: () => fetch(`${API_BASE_URL}/expenses`).then(res => res.json()),
    
    create: (expense) => 
        fetch(`${API_BASE_URL}/expenses`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expense)
        }).then(res => res.json()),
    
    update: (id, expense) =>
        fetch(`${API_BASE_URL}/expenses/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expense)
        }).then(res => res.json()),
    
    delete: (id) =>
        fetch(`${API_BASE_URL}/expenses/${id}`, { method: 'DELETE' })
};