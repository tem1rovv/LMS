package uz.pdp.lmsad.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class OutgoingMessageDto {
    private Long id;
    private String sender;
    private Long courseId;
    private String content;
    private Instant sentAt;
}
