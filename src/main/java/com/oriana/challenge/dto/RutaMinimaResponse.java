package com.oriana.challenge.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class RutaMinimaResponse {

    private final int totalCost;
    private final List<Long> path;

    public RutaMinimaResponse(int totalCost, List<Long> path) {
        this.totalCost = totalCost;
        this.path = path;
    }


}