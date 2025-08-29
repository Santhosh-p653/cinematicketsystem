// booking.js

// Load movies into dropdown
function loadMovies() {
  const movieSelect = document.getElementById('movieSelect');
  fetch('http://localhost:8080/api/movies')
    .then(response => response.json())
    .then(movies => {
      movieSelect.innerHTML = '';
      movies.forEach(movie => {
        const option = document.createElement('option');
        option.value = movie.id;
        option.textContent = movie.name;
        movieSelect.appendChild(option);
      });
    })
    .catch(err => console.error('Error fetching movies:', err));
}

// Create a new booking
function createBooking() {
  const movieId = document.getElementById('movieSelect').value;
  const customerEmail = document.getElementById('email').value;
  const showTime = document.getElementById('showTime').value;
  const date = document.getElementById('date').value;

  fetch('http://localhost:8080/api/bookings', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ movieId, customerEmail, showTime, date })
  })
  .then(res => res.json())
  .then(data => {
    alert('Booking successful!');
    loadBookings();
  })
  .catch(err => console.error(err));
}

// Load all bookings
function loadBookings() {
  fetch('http://localhost:8080/api/bookings')
    .then(res => res.json())
    .then(bookings => {
      const table = document.getElementById('bookingTable');
      table.innerHTML = '';
      bookings.forEach(b => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${b.id}</td>
          <td>${b.customerEmail}</td>
          <td>${b.movieId}</td>
          <td>${b.date}</td>
          <td>${b.showTime}</td>
          <td>${b.status}</td>
          <td><button onclick="cancelBooking(${b.id})">Cancel</button></td>
        `;
        table.appendChild(row);
      });
    })
    .catch(err => console.error(err));
}

// Cancel a booking
function cancelBooking(id) {
  fetch(`http://localhost:8080/api/bookings/${id}/cancel`, { method: 'POST' })
    .then(res => res.json())
    .then(() => {
      alert('Booking cancelled!');
      loadBookings();
    })
    .catch(err => console.error(err));
}

// Show cancelled bookings
function showCancelled() {
  fetch('http://localhost:8080/api/bookings')
    .then(res => res.json())
    .then(bookings => {
      const cancelled = bookings.filter(b => b.status === 'CANCELLED');
      const table = document.getElementById('bookingTable');
      table.innerHTML = '';
      cancelled.forEach(b => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${b.id}</td>
          <td>${b.customerEmail}</td>
          <td>${b.movieId}</td>
          <td>${b.date}</td>
          <td>${b.showTime}</td>
          <td>${b.status}</td>
        `;
        table.appendChild(row);
      });
    });
}

// Logout
function logout() {
  localStorage.clear();
  window.location.href = 'index.html';
}

// Auto-load movies and bookings on page load
window.addEventListener('DOMContentLoaded', () => {
  loadMovies();
  loadBookings();
});
