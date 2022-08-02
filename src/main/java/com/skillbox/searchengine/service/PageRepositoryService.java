package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.PageEntity;

import java.util.Optional;

public interface PageRepositoryService {
    PageEntity getPage(String pagePath);

    void save(PageEntity page);

    Optional<PageEntity> findPageById(int id);

    Optional<PageEntity> findPageByPageIdAndSiteId(int pageId, int siteId);

    long pageCount();

    long pageCount(long siteId);

    void deletePage(PageEntity page);
}
