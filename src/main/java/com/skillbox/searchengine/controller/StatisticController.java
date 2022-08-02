package com.skillbox.searchengine.controller;

import com.skillbox.searchengine.service.StatisticService;
import com.skillbox.searchengine.model.response.StatisticResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    private final StatisticService statistic;

    public StatisticController(StatisticService statistic) {
        this.statistic = statistic;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getStatistics() {
        StatisticResponse stat = statistic.getStatistic();
        return ResponseEntity.ok(stat);
    }
}
