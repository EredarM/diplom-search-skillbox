package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.exception.StartProccesIndexingException;
import com.skillbox.searchengine.sitemap.Index;
import com.skillbox.searchengine.service.IndexingService;
import org.springframework.stereotype.Service;

@Service
public class IndexingServiceImpl implements IndexingService {
    private final Index index;

    public IndexingServiceImpl(Index index) {
        this.index = index;
    }

    @Override
    public void startIndexingAll() {
        try {
            index.allSiteIndexing();
        } catch (InterruptedException e) {
            throw new StartProccesIndexingException("Ошибка запуска индексации");
        }
    }

    @Override
    public void stopIndexing() {
        index.stopSiteIndexing();
    }

    @Override
    public void startIndexingOne(String url) {
        try {
            index.checkedSiteIndexing(url);
        } catch (InterruptedException e) {
            throw new StartProccesIndexingException("Ошибка запуска индексации");
        }
    }
}
