package com.aurasport.Aurasport.AI.controller;

import com.aurasport.Aurasport.AI.agent.TeamAgent;
import com.aurasport.Aurasport.AI.dto.AiPromptRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final TeamAgent teamAgent;

    public AiController(TeamAgent teamAgent) {
        this.teamAgent = teamAgent;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> handlePrompt(@RequestBody AiPromptRequest request) {
        String agentResponse = teamAgent.chat(request.prompt());
        return ResponseEntity.ok(agentResponse);
    }
}
