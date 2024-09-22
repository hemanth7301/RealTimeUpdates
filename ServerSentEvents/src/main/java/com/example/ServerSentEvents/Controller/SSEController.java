package com.example.ServerSentEvents.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class SSEController {

    @GetMapping("/stream-sse")
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(() -> {
            try {
                for (int i = 0; i <= 10; i++) {
                    emitter.send(SseEmitter.event()
                            .id(String.valueOf(i))
                            .name("message")
                            .data("SSE event - " + i));

                    TimeUnit.SECONDS.sleep(2);
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}

