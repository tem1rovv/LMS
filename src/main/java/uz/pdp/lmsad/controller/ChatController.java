package uz.pdp.lmsad.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lmsad.dto.websocket.ChatMessageDto;
import uz.pdp.lmsad.dto.websocket.OutgoingMessageDto;
import uz.pdp.lmsad.entity.ChatMessage;
import uz.pdp.lmsad.repository.ChatMessageRepository;

import java.security.Principal;
import java.time.Instant;

@RestController
public class ChatController {


    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository messageRepo;


    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageRepository messageRepo) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepo = messageRepo;
    }


    @MessageMapping("/course/{courseId}/send")
    public void sendToCourse(@DestinationVariable Long courseId, ChatMessageDto dto, Principal principal) {
        String sender = principal.getName();
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setCourseId(courseId);
        msg.setContent(dto.getContent());
        msg.setSentAt(Instant.now());

        msg = messageRepo.save(msg);


        OutgoingMessageDto out = new OutgoingMessageDto(msg.getId(), sender, courseId, msg.getContent(), msg.getSentAt());
        messagingTemplate.convertAndSend("/topic/course." + courseId, out);
    }


    @MessageMapping("/user/send")
    public void sendToUser(ChatMessageDto dto, Principal principal) {
        String sender = principal.getName();
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setRecipientId(dto.getRecipientId());
        msg.setContent(dto.getContent());
        msg.setSentAt(Instant.now());
        msg = messageRepo.save(msg);


        OutgoingMessageDto out = new OutgoingMessageDto(msg.getId(), sender, null, msg.getContent(), msg.getSentAt());
        messagingTemplate.convertAndSendToUser(String.valueOf(dto.getRecipientId()), "/queue/messages", out);
    }
}