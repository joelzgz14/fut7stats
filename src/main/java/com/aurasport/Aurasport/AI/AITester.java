package com.aurasport.Aurasport.AI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class AITester implements CommandLineRunner {

    private final AIService aiService;

    public AITester(AIService aiService) {
        this.aiService = aiService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==================================================");
        System.out.println("INICIANDO PRUEBA DE CONEXIÓN CON LA IA...");

        aiService.chat("Hola, dime un dato curioso sobre el fútbol.");

        System.out.println("==================================================");
    }
}
