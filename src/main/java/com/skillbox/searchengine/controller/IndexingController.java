package com.skillbox.searchengine.controller;

import com.skillbox.searchengine.service.IndexingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexingController {

    private final IndexingService index;

    public IndexingController(IndexingService index) {
        this.index = index;
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<HttpStatus> startIndexingAll() {
        index.startIndexingAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<HttpStatus> stopIndexingAll() {
        index.stopIndexing();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/indexPage")
    public ResponseEntity<HttpStatus> startIndexingOne(
            @RequestParam(name = "url", required = false, defaultValue = " ") String url
    ) {
        index.startIndexingOne(url);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
