package com.aizhan.Portfolio_Tracker.Controller;


import com.aizhan.Portfolio_Tracker.dto.*;
import com.aizhan.Portfolio_Tracker.Service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @Valid @RequestBody PortfolioCreateRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(portfolioService.createPortfolio(request, authentication));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getMyPortfolios(Authentication authentication) {
        return ResponseEntity.ok(portfolioService.getMyPortfolios(authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> getPortfolioDetail(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(portfolioService.getPortfolioDetail(id, authentication));
    }

    @PostMapping("/{id}/positions")
    public ResponseEntity<PortfolioResponse> addPosition(
            @PathVariable Long id,
            @Valid @RequestBody PositionAddRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(portfolioService.addPositionToPortfolio(id, request, authentication));
    }
}
