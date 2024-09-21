function pollForUpdates() {
  fetch("http://localhost:8080/api/messages/poll")
    .then((response) => {
      if (response.status === 204) {
        console.log("No new messages");
        return [];
      }
      return response.json();
    })
    .then((messages) => {
      if (messages.length > 0) {
        console.log("New messages:", messages);
      }
    })
    .catch((error) => console.error("Polling error:", error));
}

setInterval(pollForUpdates, 5000);
