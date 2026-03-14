package com.aizhan.Portfolio_Tracker.Service;


import com.aizhan.Portfolio_Tracker.dto.*;
import com.aizhan.Portfolio_Tracker.Entity.*;
import com.aizhan.Portfolio_Tracker.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PositionRepository positionRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    @Transactional
    public PortfolioResponse createPortfolio(PortfolioCreateRequest request, Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.name());
        portfolio.setDescription(request.description());
        portfolio.setUser(user);
        portfolio.setCreatedAt(LocalDateTime.now());

        portfolio = portfolioRepository.save(portfolio);

        return toResponse(portfolio);
    }

    public List<PortfolioResponse> getMyPortfolios(Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return portfolioRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PortfolioResponse addPositionToPortfolio(Long portfolioId, PositionAddRequest request, Authentication auth) {
        Portfolio portfolio = getPortfolioOwnedByUser(portfolioId, auth);

        Asset asset = assetRepository.findById(request.assetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Position position = new Position();
        position.setPortfolio(portfolio);
        position.setAsset(asset);
        position.setQuantity(request.quantity());
        position.setAverageBuyPrice(request.buyPrice());
        position.setPurchaseDate(LocalDateTime.now().toLocalDate());

        positionRepository.save(position);

        return toResponse(portfolioRepository.findById(portfolioId).orElseThrow());
    }

    public PortfolioResponse getPortfolioDetail(Long portfolioId, Authentication auth) {
        Portfolio portfolio = getPortfolioOwnedByUser(portfolioId, auth);
        return toResponse(portfolio);
    }

    // Helper: ownership check
    private Portfolio getPortfolioOwnedByUser(Long portfolioId, Authentication auth) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        String username = auth.getName();
        if (!portfolio.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You do not own this portfolio");
        }
        return portfolio;
    }


    private PortfolioResponse toResponse(Portfolio p) {
        List<PositionSummary> posSummaries = p.getPositions().stream()
                .map(pos -> {
                    BigDecimal currentPrice = pos.getAsset().getCurrentPrice() != null
                            ? pos.getAsset().getCurrentPrice()
                            : pos.getAverageBuyPrice();

                    BigDecimal currentValue = pos.getQuantity().multiply(currentPrice);
                    BigDecimal invested = pos.getQuantity().multiply(pos.getAverageBuyPrice());
                    BigDecimal pnl = currentValue.subtract(invested);
                    BigDecimal pct = invested.compareTo(BigDecimal.ZERO) > 0
                            ? pnl.multiply(BigDecimal.valueOf(100)).divide(invested, 2, BigDecimal.ROUND_HALF_UP)
                            : BigDecimal.ZERO;

                    return new PositionSummary(
                            pos.getId(),
                            pos.getAsset().getTicker(),
                            pos.getAsset().getName(),
                            pos.getQuantity(),
                            pos.getAverageBuyPrice(),
                            currentPrice,
                            currentValue,
                            pnl,
                            pct
                    );
                })
                .collect(Collectors.toList());

        BigDecimal totalInvested = posSummaries.stream()
                .map(s -> s.quantity().multiply(s.averageBuyPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalValue = posSummaries.stream()
                .map(PositionSummary::currentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPnl = totalValue.subtract(totalInvested);
        BigDecimal totalPct = totalInvested.compareTo(BigDecimal.ZERO) > 0
                ? totalPnl.multiply(BigDecimal.valueOf(100)).divide(totalInvested, 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        PortfolioSummary summary = new PortfolioSummary(
                totalInvested,
                totalValue,
                totalPnl,
                totalPct,
                posSummaries.size()
        );

        return new PortfolioResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getUser().getId(),
                p.getCreatedAt(),
                posSummaries,
                summary
        );
    }
}
