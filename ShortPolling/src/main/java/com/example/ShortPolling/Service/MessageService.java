package com.example.ShortPolling.Service;

import com.example.ShortPolling.Entity.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {
    private final ArrayList<Message> messageStore = new ArrayList<>();

    public ArrayList<Message> getMessageStore() {
        return messageStore;
    }
    public void addMessage(Message message){
        messageStore.add(message);
    }
}
