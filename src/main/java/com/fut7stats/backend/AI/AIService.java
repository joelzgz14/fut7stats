package com.fut7stats.backend.AI;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatLanguageModel chatModel;

    // Spring inyecta el bean que creamos en AiConfig
    public AIService(ChatLanguageModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String message) {
        System.out.println("Enviando mensaje a la IA: " + message);
        String response = chatModel.generate(message);
        System.out.println("Respuesta recibida de la IA: " + response);
        return response;
    }
}