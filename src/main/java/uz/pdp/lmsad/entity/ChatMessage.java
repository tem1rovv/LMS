package uz.pdp.lmsad.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;


@Entity
@Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String sender; // store username or userId as string
    private Long recipientId; // nullable
    private Long courseId; // nullable


    @Column(columnDefinition = "text")
    private String content;


    private Instant sentAt;


    private boolean delivered;
    private boolean readed;
}