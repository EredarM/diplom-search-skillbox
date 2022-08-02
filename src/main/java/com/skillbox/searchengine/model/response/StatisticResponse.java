package com.skillbox.searchengine.model.response;


import com.skillbox.searchengine.model.dto.StatisticsDto;

public class StatisticResponse {
    private boolean result;
    private StatisticsDto statistics;

    public StatisticResponse(boolean result, StatisticsDto statistics) {
        this.result = result;
        this.statistics = statistics;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public StatisticsDto getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsDto statistics) {
        this.statistics = statistics;
    }
}
