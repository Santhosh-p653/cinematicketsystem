document.addEventListener('DOMContentLoaded', function() {
    const moviesContainer = document.querySelector('.movies-container');
    const bookingModal = document.getElementById('bookingModal');
    const closeModal = document.querySelector('.close');
    const bookingForm = document.getElementById('bookingForm');
    const timeSelect = document.getElementById('time');

    // Load movies from backend API
    fetch('/api/movies')
        .then(response => response.json())
        .then(movies => renderMovies(movies))
        .catch(error => console.error('Error fetching movies:', error));

    // Render movies list
    function renderMovies(movies) {
        moviesContainer.innerHTML = '';
        movies.forEach(movie => {
            const movieCard = document.createElement('div');
            movieCard.className = 'movie-card';
            movieCard.innerHTML = `
                <img src="${movie.poster}" alt="${movie.title}" class="movie-poster">
                <div class="movie-info">
                    <h3 class="movie-title">${movie.title}</h3>
                    <p>${movie.genre} • ${movie.duration}</p>
                    <button class="book-btn" data-id="${movie.id}">Book Now</button>
                </div>
            `;
            moviesContainer.appendChild(movieCard);
        });

        // Add event listeners to book buttons
        document.querySelectorAll('.book-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                openBookingModal(this.getAttribute('data-id'));
            });
        });
    }

    // Open booking modal
    function openBookingModal(movieId) {
        bookingModal.style.display = 'block';
        // Set movie ID in form if needed
    }

    // Close modal
    closeModal.onclick = function() {
        bookingModal.style.display = 'none';
    }

    // Handle form submission
    bookingForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            date: document.getElementById('date').value,
            time: document.getElementById('time').value,
            seats: document.getElementById('seats').value,
            movieId: document.getElementById('movieId').value
        };

        fetch('/api/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert('Booking successful!');
            bookingModal.style.display = 'none';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Booking failed');
        });
    });
});