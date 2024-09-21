package com.example.LongPolling.Controller;

import com.example.LongPolling.Entity.Message;
import com.example.LongPolling.Service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/api/messages/")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("longpoll")
    public DeferredResult<ResponseEntity<List<Message>>> longPollForUpdates() {
        long timeout = 30000L;
        DeferredResult<ResponseEntity<List<Message>>> deferredResult = new DeferredResult<>(timeout);

        List<Message> latestMessages = messageService.getLatestMessages();

        if (!latestMessages.isEmpty()) {
            deferredResult.setResult(ResponseEntity.ok(latestMessages));
        } else {
            messageService.registerListener(deferredResult);
        }
        deferredResult.onTimeout(() -> deferredResult.setResult(ResponseEntity.noContent().build()));
        return deferredResult;
    }

    @PostMapping("add")
    public ResponseEntity<String> addMessage(@RequestBody Message message) {
        messageService.addMessage(message);
        return ResponseEntity.ok("Message Added");
    }
}

