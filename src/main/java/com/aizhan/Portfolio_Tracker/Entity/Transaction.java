package com.aizhan.Portfolio_Tracker.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String type; // BUY or SELL

    @Column(precision = 12, scale = 6)
    private BigDecimal quantity;

    @Column(precision = 12, scale = 4)
    private BigDecimal price;

    private LocalDateTime timestamp = LocalDateTime.now();
}
