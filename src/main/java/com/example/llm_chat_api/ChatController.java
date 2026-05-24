package com.example.llm_chat_api;

import com.example.llm_chat_api.model.ChatRequest;
import com.example.llm_chat_api.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private GroqService groqService;

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestBody ChatRequest request){
        try {
            String reply = groqService.chat(request.message());
            return ResponseEntity.ok(
                    new ChatResponse(reply, "llama3-8b-8192")
            );
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ChatResponse("Error: " + e.getMessage(), null));
        }
    }

}
