package uz.pdp.lmsad.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.pdp.lmsad.dto.assignment.GradingResultDto;
import uz.pdp.lmsad.props.AppProps;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AiGraderClient {

    private final WebClient webClient;
    private final AppProps appProps;

    public GradingResultDto gradeAssignment(String assignmentText, String studentAnswer) {
        // Prompt: AI ga 1–5 baholashni aniq so‘raymiz
        String prompt = buildPrompt(assignmentText, studentAnswer);

        Map<String, Object> payload = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 200
        );

        Map<String, Object> response = webClient.post()
                .uri(appProps.getOpenaiUrl())
                .header("Authorization", "Bearer " + appProps.getOpenaiApiKey())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block(Duration.ofSeconds(20));

        String aiText = extractTextFromResponse(response);

        return parseAiOutput(aiText);
    }

    private String buildPrompt(String assignmentText, String studentAnswer) {
        return """
        You are a teacher AI that grades student work.

        You will be given:
        - Assignment text
        - Student's answer

        Your task:
        1. Detect automatically the language of the student's answer.
        2. Give feedback in the same language as the student's answer.
        3. Grade the answer on a scale from 1 to 5:
           1 - Very poor (incorrect or incomplete)
           2 - Many mistakes or missing key points
           3 - Fair, but has some issues
           4 - Good, mostly correct and complete
           5 - Excellent, fully correct and well-explained

        Always return a valid JSON object with these two fields:
        {"grade": 4, "feedback": "Good work, but missing examples."}

        Assignment: %s
        Student answer: %s
        """.formatted(assignmentText, studentAnswer);
    }



    private String extractTextFromResponse(Map<String, Object> response) {
        try {
            var choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                var message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        } catch (Exception ignored) {}
        return response.toString();
    }

    private GradingResultDto parseAiOutput(String aiText) {
        GradingResultDto dto = new GradingResultDto();
        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(aiText.trim());
            dto.setGrade(node.has("grade") ? node.get("grade").asInt() : null);
            dto.setFeedback(node.has("feedback") ? node.get("feedback").asText() : aiText);
            if (dto.getGrade() == null) {
                Matcher m = Pattern.compile("(\\d)").matcher(aiText);
                if (m.find()) dto.setGrade(Integer.parseInt(m.group(1)));
            }
        } catch (Exception e) {
            Matcher m = Pattern.compile("(\\d)").matcher(aiText);
            if (m.find()) dto.setGrade(Integer.parseInt(m.group(1)));
            dto.setFeedback(aiText);
        }

        // Validatsiya: 1 dan 5 oralig‘ida bo‘lishi kerak
        if (dto.getGrade() == null) dto.setGrade(1);
        if (dto.getGrade() < 1) dto.setGrade(1);
        if (dto.getGrade() > 5) dto.setGrade(5);

        return dto;
    }
}
