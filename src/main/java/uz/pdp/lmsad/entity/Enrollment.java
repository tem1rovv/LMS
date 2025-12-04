package uz.pdp.lmsad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.lmsad.entity.base.BaseEntity;
import uz.pdp.lmsad.entity.enums.EnrollmentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Enrollment extends BaseEntity {


    @ManyToOne
    private AuthUser user;

    @ManyToOne
    private Course course;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    private Integer progress;


    /*
    @Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PENDING, COMPLETED, FAILED

    private String provider; // PAYPAL, STRIPE

    private String transactionId;

    // Getters and setters
}

public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}
     */
}
