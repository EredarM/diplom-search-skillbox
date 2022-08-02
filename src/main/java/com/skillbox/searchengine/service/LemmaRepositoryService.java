package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.IndexingEntity;
import com.skillbox.searchengine.model.entity.LemmaEntity;

import java.util.List;

public interface LemmaRepositoryService {
    List<LemmaEntity> getLemma(String lemmaName);

    void save(LemmaEntity lemma);

    long lemmaCount();

    long lemmaCount(long siteId);

    void deleteAllLemmas(List<LemmaEntity> lemmaList);

    List<LemmaEntity> findLemmasByIndexing(List<IndexingEntity> indexingList);
}
