document.getElementById("scroll-btn").addEventListener("click", function(event) {
  event.preventDefault();
  
  let target = document.getElementById("ambulance-section");
  let startPosition = window.scrollY;
  let targetPosition = target.getBoundingClientRect().top + startPosition;
  let distance = targetPosition - startPosition;
  let duration = 800; // Adjust this for slower/faster scrolling
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



document.getElementById("ph7oneForm").addEventListener("submit", function(event){
  event.preventDefault();

  let userPhone  = document.getElementById("userPhone").value;

  fetch("https://randomuser.me/api/", {
    method: "POST",
    headers: {
      "Content-Type" : "application/json"
    },
    body: JSON.stringify({phone: userPhone})
  })
  .then(response =>response.json())
  .then(data=> {
    document.getElementById("responseMessage").innerText = data.message;   /* show a popup instead of showng the data on the website*/
  })
  .catch(error => {
    console.error("Error:",error);
    document.getElementById("responseMessage").innerText = "Failed to send data."; /* show a popup instead of showng the data on the website*/
  });
});