
  
  function openModal() {
    document.getElementById("popup").style.display = "flex";
    getLocation(); // Request location only when modal opens
  }
  
  function closeModal() {
    document.getElementById("popup").style.display = "none";
  }
  
  function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                document.getElementById("locationText").innerText = 
                    `Location: ${position.coords.latitude}, ${position.coords.longitude}`;
            },
            (error) => {
                document.getElementById("locationText").innerText = 
                    "Location access denied!";
            }
        );
    } else {
        document.getElementById("locationText").innerText = 
            "Geolocation not supported!";
    }
  }
  
  function submitBooking() {
    let phone = document.getElementById("userPhone").value;
    let location = document.getElementById("locationText").innerText;
  
    if (phone.trim() === "") {
        alert("Please enter your phone number.");
        return;
    }
  
    alert(`Booking confirmed!\nPhone: ${phone}\n${location}`);
    closeModal();
  }
  
  
  
 