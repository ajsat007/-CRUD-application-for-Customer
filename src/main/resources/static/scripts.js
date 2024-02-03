function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Call your authentication API
    fetch('https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            login_id: username,
            password: password,
        }),
    })
        .then(response => response.json())
        .then(data => {
            // Store the token in local storage
            localStorage.setItem('token', data.token);
            // Hide loginScreen and show customerListScreen
            document.getElementById('loginScreen').style.display = 'none';
            document.getElementById('customerListScreen').style.display = 'block';
        })
        .catch(error => console.error('Error during login:', error));
}

function logout() {
    // Remove the token from local storage
    localStorage.removeItem('token');
    // Hide customerListScreen and show loginScreen
    document.getElementById('customerListScreen').style.display = 'none';
    document.getElementById('loginScreen').style.display = 'block';
}

function syncCustomers() {
    const token = localStorage.getItem('token');

    // Call your remote API to fetch customer list
    fetch('https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
        },
    })
        .then(response => response.json())
        .then(data => {
            // Update the customer table with fetched data
            updateCustomerTable(data);
        })
        .catch(error => console.error('Error during sync:', error));
}

function searchCustomers() {
    const token = localStorage.getItem('token');
    const searchInput = document.getElementById('searchInput').value;

    // Call your API to search for customers
    fetch(`YourSearchAPIEndpoint?search=${searchInput}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
        },
    })
        .then(response => response.json())
        .then(data => {
            // Update the customer table with search results
            updateCustomerTable(data);
        })
        .catch(error => console.error('Error during search:', error));
}

function addCustomer() {
    const token = localStorage.getItem('token');
    // Extract customer details from form inputs

    // Call your API to add a new customer
    fetch('YourAddCustomerAPIEndpoint', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token,
        },
        body: JSON.stringify({
            // Pass customer details here
        }),
    })
        .then(response => response.json())
        .then(data => {
            // Optionally, update the customer table or show a success message
        })
        .catch(error => console.error('Error during add customer:', error));
}

function showCustomerList() {
    // Hide addCustomerScreen and show customerListScreen
    document.getElementById('addCustomerScreen').style.display = 'none';
    document.getElementById('customerListScreen').style.display = 'block';
}

function updateCustomerTable(data) {
    // Update the customer table with the provided data
    const tableBody = document.getElementById('customerTableBody');
    tableBody.innerHTML = '';

    data.forEach(customer => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${customer.first_name}</td>
            <td>${customer.last_name}</td>
            <td>${customer.street}</td>
            <td>${customer.address}</td>
            <td>${customer.city}</td>
            <td>${customer.state}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
        `;
    });
}
