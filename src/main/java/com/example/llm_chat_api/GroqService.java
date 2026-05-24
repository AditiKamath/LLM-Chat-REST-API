package com.example.llm_chat_api;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {
    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final OkHttpClient http = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String chat(String userMessage) throws Exception {
        // Build the JSON body Groq expects
        String body = mapper.writeValueAsString(Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", userMessage)
                )
        ));
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body,
                        MediaType.get("application/json")))
                .build();
        try (Response response = http.newCall(request).execute()) {
            String json = response.body().string();
            // Navigate: choices[0].message.content
            var choices = mapper.readTree(json).path("choices");
            if (choices.isEmpty()) throw new RuntimeException("Groq error: " + json);
            return choices.get(0).path("message").path("content").asText();
        }
    }
}
