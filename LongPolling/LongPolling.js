function longPollForUpdates() {
    fetch('http://localhost:8080/api/messages/longpoll')
        .then(response => {
            if (response.status === 204) {
                console.log('No new messages, retrying...');
                return [];
            }
            return response.json();
        })
        .then(messages => {
            if (messages.length > 0) {
                console.log('New messages:', messages);
            }
        })
        .catch(error => console.error('Polling error:', error));
}

longPollForUpdates();
