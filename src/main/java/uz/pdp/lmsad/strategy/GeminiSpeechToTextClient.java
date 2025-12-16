package uz.pdp.lmsad.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Map;


@Service
public class GeminiSpeechToTextClient implements SpeechToTextClient {


    @Value("${gemini.apiKey}")
    private String apiKey;
    @Value("${gemini.url}")
    private String url;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiSpeechToTextClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Model model() {
        return Model.GEMINI;
    }

    @Override
    public String transcribe(MultipartFile file) {
        try {
            byte[] audioBytes = file.getBytes();
            String base64Audio = Base64.getEncoder().encodeToString(audioBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[]{
                            Map.of(
                                    "role", "user",
                                    "parts", new Object[]{
                                            Map.of("text", buildPrompt()),
                                            Map.of(
                                                    "inline_data",
                                                    Map.of(
                                                            "mime_type", file.getContentType(),
                                                            "data", base64Audio
                                                    )
                                            )
                                    }
                            )
                    }
            );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            String fullyUrl = url + "?key=" + apiKey;

            ResponseEntity<String> response =
                    restTemplate.postForEntity(fullyUrl, request, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException("Gemini transcription failed", e);
        }
    }

    /**
     * HIGH-QUALITY ENGLISH PROMPT
     */
    private String buildPrompt() {
        return """
                                You are a professional speech-to-text system.
                
                                Transcribe the given audio accurately with the following rules:
                                - Language: Uzbek
                                - Preserve original meaning and technical terms
                                - Use correct punctuation and paragraph formatting
                                - Do NOT summarize
                                - Do NOT translate
                                - Output only the transcription text
                                - If the audio contains pauses, reflect them naturally
                                - Remove filler sounds (uh, um) unless meaningful
                
                }""";
    }
}