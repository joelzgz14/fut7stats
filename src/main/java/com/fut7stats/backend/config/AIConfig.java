package com.fut7stats.backend.config;

import com.fut7stats.backend.AI.agent.TeamAgent;
import com.fut7stats.backend.features.equipo.tool.EquipoTools;
import com.fut7stats.backend.features.estadisticaspartido.tools.EstadisticasTools;
import com.fut7stats.backend.features.jugador.tools.JugadorTools;
import com.fut7stats.backend.features.partido.tools.PartidoTools;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration // Le indica a Spring que esta clase contiene configuración de beans
public class AIConfig {

    // Inyecta el valor de la propiedad desde application.properties
    @Value("${langchain4j.openai.api-key}")
    private String openaiApiKey;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName("gpt-3.5-turbo") // Puedes cambiar a "gpt-4" si tienes acceso)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

    @Bean
    public TeamAgent teamAgent(ChatLanguageModel chatLanguageModel,
                               EquipoTools equipoTools,
                               JugadorTools jugadorTools,
                               PartidoTools partidoTools, // <-- Añadimos el nuevo tool
                               EstadisticasTools estadisticasTools) {
        return AiServices.builder(TeamAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .tools(equipoTools, jugadorTools, partidoTools, estadisticasTools) // <-- Y lo registramos
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }
}
