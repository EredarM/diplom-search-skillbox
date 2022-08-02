package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.IndexingEntity;

import java.util.List;

public interface IndexRepositoryService {
    List<IndexingEntity> getAllIndexingByLemmaId(int lemmaId);

    List<IndexingEntity> getAllIndexingByPageId(int pageId);

    void deleteAllIndexing(List<IndexingEntity> indexingList);

    IndexingEntity getIndexing(int lemmaId, int pageId);

    void save(IndexingEntity indexing);

}
