package com.aizhan.Portfolio_Tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PositionAddRequest(
        @NotNull Long assetId,
        @NotNull @Positive BigDecimal quantity,
        @NotNull @Positive BigDecimal buyPrice,
        String notes
) {}
