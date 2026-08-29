package com.home.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeminiClientService {
    @Value("${gemini.api.key}")
    private String apiKey;

    private RestClient restClient;

    private ObjectMapper objectMapper;

    public GeminiClientService() {
        // Create the HTTP client
        this.restClient = RestClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public String optimizeDescription(String originalDescription) {
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-3.6-flash:generateContent?key="
                + apiKey;

        // 1. Create a clear prompt instructing the AI what to do
        String prompt = "Optimize this home service description for a service professional: '"
                + originalDescription
                + "'. Return only the clean, optimized technical description. Do not include any conversational intro, outro, or quotes.";

        // 2. Build the JSON request body using nested Java Maps (this matches Gemini's
        // API format)
        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> requestBody = Map.of("contents", List.of(content));

        try {
            // 3. Make the POST request to Google's servers
            String jsonResponse = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            // 4. Parse the JSON String into a JsonNode manually using readTree
            JsonNode responseNode = objectMapper.readTree(jsonResponse);

            // 3. Traverse the JsonNode using path methods
            if (responseNode != null) {
                return responseNode.path("candidates")
                        .path(0)
                        .path("content")
                        .path("parts")
                        .path(0)
                        .path("text")
                        .asText()
                        .trim();
            }
        } catch (Exception e) {
            System.err.println("Failed to call Gemini API: " + e.getMessage());
        }
        return originalDescription;
    }
}
