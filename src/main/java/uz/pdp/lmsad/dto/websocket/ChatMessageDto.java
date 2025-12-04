package uz.pdp.lmsad.dto.websocket;

import lombok.Data;


@Data
public class ChatMessageDto {
private Long recipientId; // null for room messages
private Long courseId; // optional: room id
private String content;
private String type; // "CHAT", "JOIN", "LEAVE"
}