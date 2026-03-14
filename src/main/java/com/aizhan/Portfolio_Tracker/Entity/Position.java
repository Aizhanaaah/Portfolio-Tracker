package com.aizhan.Portfolio_Tracker.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "positions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal averageBuyPrice;

    private LocalDate purchaseDate = LocalDate.now();
}
