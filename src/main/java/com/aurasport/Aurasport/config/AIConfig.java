package com.aurasport.Aurasport.config;

import com.aurasport.Aurasport.AI.agent.TeamAgent;
import com.aurasport.Aurasport.features.equipo.tool.EquipoTools;
import com.aurasport.Aurasport.features.estadisticaspartido.tools.EstadisticasTools;
import com.aurasport.Aurasport.features.jugador.tools.JugadorTools;
import com.aurasport.Aurasport.features.partido.tools.PartidoTools;
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
                .modelName("gpt-4o")
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
