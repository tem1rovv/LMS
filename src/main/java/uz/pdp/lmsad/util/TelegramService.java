package uz.pdp.lmsad.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.lmsad.props.AppProps;

@Service
@RequiredArgsConstructor
public class TelegramService {


    private final RestTemplate restTemplate = new RestTemplate();
    private final AppProps appProps;


    public String uploadFileToTelegram(MultipartFile file) {
        try {
            String sendUrl = "https://api.telegram.org/bot" + appProps.getBotToken() + "/sendDocument";

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("chat_id", appProps.getChatId());
            body.add("document", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(sendUrl, HttpMethod.POST, requestEntity, String.class);

            String responseBody = response.getBody();
            String fileId = extractFileId(responseBody);

            // ✅ to‘g‘ri token ishlatamiz (chat_id emas)
            String getFileUrl = "https://api.telegram.org/bot" + appProps.getBotToken() + "/getFile?file_id=" + fileId;
            ResponseEntity<String> fileResponse = restTemplate.getForEntity(getFileUrl, String.class);

            String filePath = extractFilePath(fileResponse.getBody());

            // ✅ to‘g‘ri token ishlatamiz
            return "https://api.telegram.org/file/bot" + appProps.getBotToken() + "/" + filePath;

        } catch (Exception e) {
            throw new RuntimeException("Telegramga fayl yuborishda xatolik: " + e.getMessage());
        }
    }



    public String uploadBytesToTelegram(byte[] fileBytes, String fileName) {
        try {
            String sendUrl = "https://api.telegram.org/bot" + appProps.getBotToken() + "/sendDocument";

            // Faylni multipart requestga tayyorlash
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("chat_id", appProps.getChatId());
            body.add("document", new MultipartInputStreamFileResource(new java.io.ByteArrayInputStream(fileBytes), fileName));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(sendUrl, HttpMethod.POST, requestEntity, String.class);

            String responseBody = response.getBody();
            String fileId = extractFileId(responseBody);

            // ⚠️ BU joyda xato bor edi senga bergan kodingda:
            // getFile API da bot token ishlatiladi, chatId emas!
            String getFileUrl = "https://api.telegram.org/bot" + appProps.getBotToken() + "/getFile?file_id=" + fileId;
            ResponseEntity<String> fileResponse = restTemplate.getForEntity(getFileUrl, String.class);

            String filePath = extractFilePath(fileResponse.getBody());

            return "https://api.telegram.org/file/bot" + appProps.getBotToken() + "/" + filePath;

        } catch (Exception e) {
            throw new RuntimeException("Telegramga byte fayl yuborishda xatolik: " + e.getMessage());
        }
    }




    private String extractFileId(String response) {
        int start = response.indexOf("\"file_id\":\"") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    private String extractFilePath(String response) {
        int start = response.indexOf("\"file_path\":\"") + 13;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}
