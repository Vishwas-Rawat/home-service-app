package com.home.model.ai;

import com.home.model.booking.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_logs", schema = "ai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ai_logs_id")
    private Integer aiLogsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "prompt_type", nullable = false, length = 100)
    private String promptType;

    @Column(name = "input_text", nullable = false)
    private String inputText;

    @Column(name = "output_text", nullable = false)
    private String outputText;

    @Column(name = "model_name", length = 100)
    private String modelName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
