package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.entity.IndexingEntity;
import com.skillbox.searchengine.repo.IndexRepository;
import com.skillbox.searchengine.service.IndexRepositoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexRepoServiceImpl implements IndexRepositoryService {

    private final IndexRepository indexRepository;

    public IndexRepoServiceImpl(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    @Override
    public List<IndexingEntity> getAllIndexingByLemmaId(int lemmaId) {
        return indexRepository.findByLemmaId(lemmaId);
    }

    @Override
    public List<IndexingEntity> getAllIndexingByPageId(int pageId) {
        return indexRepository.findByPageId(pageId);
    }

    @Override
    public synchronized void deleteAllIndexing(List<IndexingEntity> indexingList) {
        indexRepository.deleteAll(indexingList);
    }

    @Override
    public IndexingEntity getIndexing(int lemmaId, int pageId) {
        IndexingEntity indexing = null;
        try {
            indexing = indexRepository.findByLemmaIdAndPageId(lemmaId, pageId);
        } catch (Exception e) {
            System.out.println("lemmaId: " + lemmaId + " + pageId: " + pageId + " not unique");
            e.printStackTrace();
        }
        return indexing;
    }

    @Override
    public synchronized void save(IndexingEntity indexing) {
        indexRepository.save(indexing);
    }

}
