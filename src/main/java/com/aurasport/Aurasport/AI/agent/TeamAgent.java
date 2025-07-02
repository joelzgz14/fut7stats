package com.aurasport.Aurasport.AI.agent;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface TeamAgent {
    String chat(String userMessage);
}
