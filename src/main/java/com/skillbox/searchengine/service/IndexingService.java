package com.skillbox.searchengine.service;

public interface IndexingService {
    void startIndexingAll();

    void stopIndexing();

    void startIndexingOne(String url);
}
