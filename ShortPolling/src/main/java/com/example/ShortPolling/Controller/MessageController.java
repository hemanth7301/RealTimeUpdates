package com.example.ShortPolling.Controller;

import com.example.ShortPolling.Entity.Message;
import com.example.ShortPolling.Service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/messages/")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/poll")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getMessageStore();
        if(messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMessage(@RequestBody Message message){
        messageService.addMessage(message);
        return ResponseEntity.ok("Message Added");
    }
}
