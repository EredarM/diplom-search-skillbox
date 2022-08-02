package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.SiteEntity;

import java.util.List;

public interface SiteRepositoryService {
    SiteEntity getSite(String url);

    SiteEntity getSite(int siteId);

    void save(SiteEntity site);

    long siteCount();

    List<SiteEntity> getAllSites();
}
