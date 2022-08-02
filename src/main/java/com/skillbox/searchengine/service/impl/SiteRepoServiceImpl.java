package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.entity.SiteEntity;
import com.skillbox.searchengine.repo.SiteRepository;
import com.skillbox.searchengine.service.SiteRepositoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteRepoServiceImpl implements SiteRepositoryService {

    private final SiteRepository siteRepository;

    public SiteRepoServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Override
    public SiteEntity getSite(String url) {
        return siteRepository.findByUrl(url);
    }

    public SiteEntity getSite(int siteId) {
        Optional<SiteEntity> optional = siteRepository.findById(siteId);
        SiteEntity site = null;
        if (optional.isPresent()) {
            site = optional.get();
        }
        return site;
    }

    @Override
    public synchronized void save(SiteEntity site) {
        siteRepository.save(site);
    }

    @Override
    public long siteCount() {
        return siteRepository.count();
    }

    @Override
    public List<SiteEntity> getAllSites() {
        List<SiteEntity> siteList = new ArrayList<>();
        siteRepository.findAll().forEach(siteList::add);
        return siteList;
    }
}
