package com.skillbox.searchengine.model.dto;


public class StatisticsDto {
    private TotalDto total;
    private DetailedDto[] detailed;

    public StatisticsDto(TotalDto total, DetailedDto[] detailed) {
        this.total = total;
        this.detailed = detailed;
    }

    public TotalDto getTotal() {
        return total;
    }

    public void setTotal(TotalDto total) {
        this.total = total;
    }

    public DetailedDto[] getDetailed() {
        return detailed;
    }

    public void setDetailed(DetailedDto[] detailed) {
        this.detailed = detailed;
    }
}
