document.addEventListener("DOMContentLoaded", function() {
    // Scroll to Section
    document.getElementById("scroll-btn").addEventListener("click", function(event) {
        event.preventDefault();

        let target = document.getElementById("services");
        if (!target) return;

        let startPosition = window.scrollY;
        let targetPosition = target.getBoundingClientRect().top + startPosition;
        let distance = targetPosition - startPosition;
        let duration = 800;
        let startTime = null;

        function animation(currentTime) {
            if (startTime === null) startTime = currentTime;
            let timeElapsed = currentTime - startTime;
            let run = ease(timeElapsed, startPosition, distance, duration);
            window.scrollTo(0, run);
            if (timeElapsed < duration) requestAnimationFrame(animation);
        }

        function ease(t, b, c, d) {
            t /= d / 2;
            if (t < 1) return (c / 2) * t * t + b;
            t--;
            return (-c / 2) * (t * (t - 2) - 1) + b;
        }

        requestAnimationFrame(animation);
    });

    // Function to open the modal


    // Auto Scroll for Partners
    const carousel = document.getElementById('carousel');
    function autoScroll() {
        if (carousel.scrollLeft + carousel.clientWidth >= carousel.scrollWidth) {
            carousel.scrollTo({ left: 0, behavior: 'smooth' });
        } else {
            carousel.scrollBy({ left: 150, behavior: 'smooth' });
        }
    }
    setInterval(autoScroll, 2000);

    // Sticky Navbar
    document.addEventListener("scroll", function() {
        const navbar = document.querySelector('.et-hero-tabs-container');
        if (!navbar) return;

        const scrollPosition = window.scrollY;
        if (scrollPosition > 100) {
            navbar.classList.add('sticky');
        } else {
            navbar.classList.remove('sticky');
        }
    });
});
function openModal() {
    console.log("openModal triggered");
    const popup = document.getElementById("popup");
    if (popup) {
        popup.style.display = "flex"; // Ensure 'flex' is supported in CSS
        getLocation(); // Fetch location when modal opens
    } else {
        console.error("Popup element not found.");
    }
}

// Function to close the modal
function closeModal() {
    const popup = document.getElementById("popup");
    if (popup) {
        popup.style.display = "none";
    }
}

// Function to get user's location
function getLocation() {
    alert("Click Ok to allow location access! 📍");
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                document.getElementById("locationText").innerText =
                    `Location: ${position.coords.latitude}, ${position.coords.longitude}`;
            },
            (error) => {
                console.error("Error getting location: ", error);
                document.getElementById("locationText").innerText = "Location not available";
                alert("Failed to get location. Please enable location services.");
            }

        );
    } else {
        document.getElementById("locationText").innerText =
            "Geolocation not supported!";
    }
}

// Form submission handling
document.getElementById("phoneForm").addEventListener("submit", function(event){
    event.preventDefault();

    let userPhone  = document.getElementById("userPhone").value;
    let location = document.getElementById("locationText").innerText;

    if (userPhone.trim() === "") {
        alert("Please enter your phone number.");
        return;
    }

    alert(`Booking confirmed!\nPhone: ${userPhone}\n${location}`);
    closeModal();
});








// Fetch Request
// Fetch Request
function submitBooking() {
    event.preventDefault();  // Prevent default form submission
    const phoneInput = document.getElementById('userPhone');
    const errorText = document.getElementById('errorText');
    const phoneRegex = /^\d{10}$/;

    if (!phoneRegex.test(phoneInput.value)) {
        errorText.style.display = 'block';
     } else {
        console.log("✅ Form submit triggered"); // Debug log
        errorText.style.display = 'none';

        let location = document.getElementById("locationText").innerText;
        let userPhone = document.getElementById("userPhone").value;
        showLoading(true);
        // Validate Phone Number
        if (userPhone.trim() === "") {
            showToast("Please enter your phone number", "error");
            return;
        }

        // Validate Location
        if (location === "Fetching location...") {
            getLocation();
            showToast("Fetching location... Please wait.", "info");
            return;
        }

        let [latitude, longitude] = location.split(",").map(coord => parseFloat(coord.trim()));
        let baseurl = window.baseUrl;
        // Send Data via Fetch
        fetch(baseurl+"/public/book", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                phoneNumber: userPhone,
                latitude: latitude,
                longitude: longitude
            }),
        })
            .then(response => response.json())
            .then(data => {
                if (data.bookingId) {
                    // Show confirmation popup
                    alert(`✅ Request sent successfully!\nBooking ID: ${data.bookingId}\nYou will receive updates on your number: ${data.user.phone}`);
                } else {
                    showToast("⚠️ Something went wrong. Please try again.", "error");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                showToast("⚠️ Failed to send data.", "error");
            }).finally(() => {
            // Hide loading indicator
            showLoading(false);
            closeModal();
        });
    }
}




// Function to show/hide loading indicator
function showLoading(isLoading) {
    const loadingOverlay = document.getElementById("loadingOverlay");
    if (isLoading) {
        loadingOverlay.style.display = "flex"; // Show loading
    } else {
        loadingOverlay.style.display = "none"; // Hide loading
    }
}

// Toast Function
function showToast(message, type) {
    const toast = document.createElement("div");
    toast.innerText = message;
    toast.className = `toast ${type}`;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}

// Basic Toast Styles (Add in your CSS or <style> tag)
const style = document.createElement('style');
style.innerHTML = `
    .toast {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background-color: #333;
        color: white;
        padding: 10px 20px;
        border-radius: 8px;
        font-size: 1rem;
        opacity: 0.9;
        z-index: 1000;
        transition: opacity 0.5s ease-in-out;
    }
    .toast.success { background-color: #4CAF50; }
    .toast.error { background-color: #f44336; }
    .toast.info { background-color: #2196F3; }
`;
document.head.appendChild(style);
