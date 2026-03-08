package com.oriana.challenge.dto;

import java.util.List;

import org.jspecify.annotations.Nullable;

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

    public @Nullable Integer getCosto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCosto'");
    }

    public @Nullable Object getRuta() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRuta'");
    }
}