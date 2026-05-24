# LLM-Chat-REST-API
A production-ready REST API built with Spring Boot that integrates with Groq's LLM API to provide AI-powered chat completions. Demonstrates LLM integration patterns in a Java backend — including clean service layering, config-based API key management, and structured error handling.

Project Structure
src/main/java/com/yourname/llmchatapi/
├── controller/
│   └── ChatController.java      # REST endpoint
├── service/
│   └── GroqService.java         # LLM integration logic
├── model/
│   ├── ChatRequest.java         # Incoming DTO
│   └── ChatResponse.java        # Outgoing DTO
└── LlmChatApiApplication.java


Architecture
POST /api/chat
      │
      ▼
ChatController        ← validates + routes request
      │
      ▼
GroqService           ← builds request, calls Groq API
      │
      ▼
Groq API              ← returns LLM completion
      │
      ▼
ChatResponse          ← serialized back as JSON

Author
Aditi Kamath — Backend Engineer
