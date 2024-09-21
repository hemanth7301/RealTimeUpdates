package com.example.LongPolling.Service;

import com.example.LongPolling.Entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final List<Message> messageStore = new ArrayList<>();

    private final List<DeferredResult<ResponseEntity<List<Message>>>> listeners = new ArrayList<>();

    public List<Message> getLatestMessages() {
        return messageStore;
    }

    public void registerListener(DeferredResult<ResponseEntity<List<Message>>> deferredResult) {
        synchronized (listeners) {
            listeners.add(deferredResult);
        }
    }

    public void addMessage(Message message) {
        synchronized (messageStore) {
            messageStore.add(message);
        }
        notifyListeners();
    }

    private void notifyListeners() {
        List<Message> latestMessages = getLatestMessages();

        synchronized (listeners) {
            for (DeferredResult<ResponseEntity<List<Message>>> listener : listeners) {
                listener.setResult(ResponseEntity.ok(latestMessages));
            }
            listeners.clear();
        }
    }
}
