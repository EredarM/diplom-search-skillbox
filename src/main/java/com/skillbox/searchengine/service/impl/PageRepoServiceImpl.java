package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.entity.PageEntity;
import com.skillbox.searchengine.repo.PageRepository;
import com.skillbox.searchengine.service.PageRepositoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageRepoServiceImpl implements PageRepositoryService {

    private final PageRepository pageRepository;

    public PageRepoServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public PageEntity getPage(String pagePath) {
        return pageRepository.findByPath(pagePath);
    }

    @Override
    public synchronized void save(PageEntity page) {
        pageRepository.save(page);
    }

    @Override
    public Optional<PageEntity> findPageById(int id) {
        return pageRepository.findById(id);
    }

    @Override
    public Optional<PageEntity> findPageByPageIdAndSiteId(int pageId, int siteId) {
        return pageRepository.findByIdAndSiteId(pageId, siteId);
    }

    @Override
    public long pageCount() {
        return pageRepository.count();
    }

    @Override
    public long pageCount(long siteId) {
        return pageRepository.count(siteId);
    }

    @Override
    public void deletePage(PageEntity page) {
        pageRepository.delete(page);
    }

}
