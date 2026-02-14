package com.oriana.challenge.dto;

import java.util.List;

public class RutaMinimaResponse {

    private int totalCost;
    private List<Long> path;

    public RutaMinimaResponse(int totalCost, List<Long> path) {
        this.totalCost = totalCost;
        this.path = path;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<Long> getPath() {
        return path;
    }
}