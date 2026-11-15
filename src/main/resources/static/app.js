async function loadUsers() {
    const url = "http://localhost:8080/user";
    try {
        const response = await fetch(url, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        const users = await response.json();
        const select = document.getElementById('userSelect');
        select.innerHTML = '';
        users.forEach((user) => {
            select.innerHTML += `<option value="${user.id}">${user.name}</option>`;
        })
    } catch (error) {
        console.log(error.message);
    }
}

loadUsers();

async function loadRides() {
    const url = "http://localhost:8080/ride";
    try {
        const response = await fetch(url, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        const rides = await response.json();
        renderRiders(rides);
    } catch (error) {
        console.log(error.message);
    }
}

loadRides();

async function searchRides() {
    const fromCity = document.getElementById('fromCity').value.trimEnd();
    const toCity = document.getElementById('toCity').value.trimEnd();
    const url = `http://localhost:8080/ride/search?fromCity=${fromCity}&toCity=${toCity}`;

    try {
        const response = await fetch(url, {
            method: 'GET'
        });
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        const rides = await response.json();
        renderRiders(rides);
    } catch (error) {
        console.log(error.message);
    }
}

function renderRiders(rides) {
    const container = document.getElementById('ridesContainer');
    container.innerHTML = '';
    rides.forEach((ride) => {
        const viaCities = (ride.intermediateCities && ride.intermediateCities.length > 0)
            ? `<div class="text-sm text-gray-500">Via: ${ride.intermediateCities.join(', ')}</div>` : '';

        const rideCard = `
            <div class="bg-white p-5 rounded-lg shadow mb-4 flex justify-between item-center border-1-4 border-blue-500">
                <div>
                    <div class="font-bold">${ride.fromCity} -> ${ride.toCity} | Price: ${ride.price} | Available seats: ${ride.availableSeats}</div>                
                    <div class="text-sm text-gray-600">Driver: ${ride.driver.name}, Car: ${ride.vehicleName}, Depart: ${ride.departureTime.replace('T', ' ')}</div>
                    ${viaCities}                
                </div> 
                <div>
                    <button onclick="bookRide(${ride.id})" class="bg-green-600 hover:bg-green-700 text-white px-3 py-1 rounded mr-2 text-sm">Book Ride</button>
                    <button onclick="cancelRide(${ride.id})" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded text-sm">Cancel Ride</button>
                </div>
        `;
        container.innerHTML += rideCard;
    })
}

async function bookRide(rideId) {
    const userId = document.getElementById('userSelect').value;
    const url = `http://localhost:8080/ride/${rideId}/book/${userId}`;
    try {
        const response = await fetch(url, {
            method: 'POST'
        });
        if (!response.ok) {
            alert('Failed to book ride.');
            return;
        }
        alert('Ride booked successfully!');
        loadRides();
    } catch (error) {
        console.log(error.message);
    }
}

async function cancelRide(rideId) {
    const userId = document.getElementById('userSelect').value;
    const url = `http://localhost:8080/ride/${rideId}/cancel/${userId}`;
    try {
        const response = await fetch(url, {
            method: 'POST'
        });
        if (!response.ok) {
            alert('Failed to cancel ride.');
            return;
        }
        alert('Ride canceled successfully!');
        loadRides();
    } catch (error) {
        console.log(error.message);
    }
}

loadRides();

async function createRide() {
    const driverId = document.getElementById('userSelect').value;
    const fromCity = document.getElementById('newFromCity').value.trimEnd();
    const toCity = document.getElementById('newToCity').value.trimEnd();
    let intermediateCities = document.getElementById('newIntermediateCities').value;
    const departureTime = document.getElementById('newDepartureTime').value;
    const totalSeats = document.getElementById('newSeats').value;
    const price = document.getElementById('newPrice').value;
    const url = `http://localhost:8080/ride/driver/${driverId}`;

    if (intermediateCities.length > 0) {
        intermediateCities = intermediateCities.split(',').map(city => city.trim());
    } else {
        intermediateCities = [];
    }

    const rideData = {
        fromCity: fromCity,
        toCity: toCity,
        departureTime: departureTime,
        totalSeats: Number(totalSeats),
        price: Number(price),
        intermediateCities: intermediateCities,
    }

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(rideData)
        })
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        alert('Ride created successfully!');
        await loadRides();
        await loadUsers();
    } catch (error) {
        console.log(error.message);
    }
}
