package com.aizhan.Portfolio_Tracker.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;           // AAPL, MSFT, BTC

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType type = AssetType.STOCK;

    @Column(precision = 12, scale = 4)
    private BigDecimal currentPrice;
}

