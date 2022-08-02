package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.entity.IndexingEntity;
import com.skillbox.searchengine.model.entity.LemmaEntity;
import com.skillbox.searchengine.repo.LemmaRepository;
import com.skillbox.searchengine.service.LemmaRepositoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LemmaRepoServiceImpl implements LemmaRepositoryService {

    private final LemmaRepository lemmaRepository;

    public LemmaRepoServiceImpl(LemmaRepository lemmaRepository) {
        this.lemmaRepository = lemmaRepository;
    }

    @Override
    public List<LemmaEntity> getLemma(String lemmaName) {
        List<LemmaEntity> lemmas = null;
        try {
            lemmas = lemmaRepository.findByLemma(lemmaName);
        } catch (Exception e) {
            System.out.println(lemmaName);
            e.printStackTrace();
        }
        return lemmas;
    }

    @Override
    public synchronized void save(LemmaEntity lemma) {
        lemmaRepository.save(lemma);
    }

    @Override
    public long lemmaCount() {
        return lemmaRepository.count();
    }

    @Override
    public long lemmaCount(long siteId) {
        return lemmaRepository.count(siteId);
    }

    @Override
    public synchronized void deleteAllLemmas(List<LemmaEntity> lemmaList) {
        lemmaRepository.deleteAll(lemmaList);
    }

    @Override
    public List<LemmaEntity> findLemmasByIndexing(List<IndexingEntity> indexingList) {
        int[] lemmaIdList = new int[indexingList.size()];
        for (int i = 0; i < indexingList.size(); i++) {
            lemmaIdList[i] = indexingList.get(i).getLemmaId();
        }
        return lemmaRepository.findById(lemmaIdList);
    }
}
