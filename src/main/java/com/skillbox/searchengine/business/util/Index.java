package com.skillbox.searchengine.sitemap;

import com.skillbox.searchengine.config.SearchConfig;
import com.skillbox.searchengine.exception.NotFoundPageException;
import com.skillbox.searchengine.exception.ProcessIndexingException;
import com.skillbox.searchengine.exception.StartProccesIndexingException;
import com.skillbox.searchengine.model.dataType.StatusType;
import com.skillbox.searchengine.model.entity.FieldEntity;
import com.skillbox.searchengine.model.entity.SiteEntity;
import com.skillbox.searchengine.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class Index {
    private final static Log log = LogFactory.getLog(Index.class);
    private final SearchConfig searchConfig;

    private final FieldRepositoryService fieldRepositoryService;
    private final SiteRepositoryService siteRepositoryService;
    private final IndexRepositoryService indexRepositoryService;
    private final PageRepositoryService pageRepositoryService;
    private final LemmaRepositoryService lemmaRepositoryService;
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public Index(
            SearchConfig searchConfig,
            FieldRepositoryService fieldRepositoryService,
            SiteRepositoryService siteRepositoryService,
            IndexRepositoryService indexRepositoryService,
            PageRepositoryService pageRepositoryService,
            LemmaRepositoryService lemmaRepositoryService
    ) {
        this.searchConfig = searchConfig;
        this.fieldRepositoryService = fieldRepositoryService;
        this.siteRepositoryService = siteRepositoryService;
        this.indexRepositoryService = indexRepositoryService;
        this.pageRepositoryService = pageRepositoryService;
        this.lemmaRepositoryService = lemmaRepositoryService;
    }

    public void allSiteIndexing() throws InterruptedException {
        fieldInit();
        boolean isIndexing;
        List<SiteEntity> siteList = getSiteListFromConfig();
        for (SiteEntity site : siteList) {
            isIndexing = startSiteIndexing(site);
            if (!isIndexing) {
                stopSiteIndexing();
                throw new ProcessIndexingException("Индексация страницы уже запущена");
            }
        }
    }

    public void checkedSiteIndexing(String url) throws InterruptedException {
        List<SiteEntity> siteList = siteRepositoryService.getAllSites();
        String baseUrl = "";
        for (SiteEntity site : siteList) {
            if (site.getStatus() != StatusType.INDEXED) {
                throw new ProcessIndexingException("Индексация страницы уже запущена");
            }
            if (url.contains(site.getUrl())) {
                baseUrl = site.getUrl();
            }
        }
        if (baseUrl.isEmpty()) {
            throw new NotFoundPageException("Страница находится за пределами сайта, указанных в конфигурационном файле");
        } else {
            SiteEntity site = siteRepositoryService.getSite(baseUrl);
            site.setUrl(url);
            SiteIndexing indexing = new SiteIndexing(
                    site,
                    searchConfig,
                    fieldRepositoryService,
                    siteRepositoryService,
                    indexRepositoryService,
                    pageRepositoryService,
                    lemmaRepositoryService,
                    false);
            executor.execute(indexing);
            site.setUrl(baseUrl);
            siteRepositoryService.save(site);
        }
    }


    private void fieldInit() {
        FieldEntity fieldTitle = new FieldEntity("title", "title", 1.0f);
        FieldEntity fieldBody = new FieldEntity("body", "body", 0.8f);
        if (fieldRepositoryService.getFieldByName("title") == null) {
            fieldRepositoryService.save(fieldTitle);
            fieldRepositoryService.save(fieldBody);
        }
    }

    private boolean startSiteIndexing(SiteEntity site) throws InterruptedException {
        SiteEntity site1 = siteRepositoryService.getSite(site.getUrl());
        if (site1 == null) {
            siteRepositoryService.save(site);
            SiteIndexing indexing = new SiteIndexing(
                    siteRepositoryService.getSite(site.getUrl()),
                    searchConfig,
                    fieldRepositoryService,
                    siteRepositoryService,
                    indexRepositoryService,
                    pageRepositoryService,
                    lemmaRepositoryService,
                    true);
            executor.execute(indexing);
            return true;
        } else {
            if (!site1.getStatus().equals(StatusType.INDEXING)) {
                SiteIndexing indexing = new SiteIndexing(
                        siteRepositoryService.getSite(site.getUrl()),
                        searchConfig,
                        fieldRepositoryService,
                        siteRepositoryService,
                        indexRepositoryService,
                        pageRepositoryService,
                        lemmaRepositoryService,
                        true);
                executor.execute(indexing);
                return true;
            } else {
                return false;
            }
        }
    }

    public void stopSiteIndexing() {
        if (executor.getActiveCount() == 0) {
            throw new StartProccesIndexingException("Остановка индексации не может быть выполнена, потому что процесс индексации не запущен.");
        }

        executor.shutdownNow();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("Ошибка закрытия потоков: " + e);
            throw new RuntimeException();
        }

        List<SiteEntity> siteList = siteRepositoryService.getAllSites();
        for (SiteEntity site : siteList) {
            site.setStatus(StatusType.FAILED);
            siteRepositoryService.save(site);
        }
    }

    private List<SiteEntity> getSiteListFromConfig() {
        List<SiteEntity> siteList = new ArrayList<>();
        List<HashMap<String, String>> sites = searchConfig.getSite();
        for (HashMap<String, String> map : sites) {
            String url = "";
            String name = "";
            for (Map.Entry<String, String> siteInfo : map.entrySet()) {
                if (siteInfo.getKey().equals("name")) {
                    name = siteInfo.getValue();
                }
                if (siteInfo.getKey().equals("url")) {
                    url = siteInfo.getValue();
                }
            }
            SiteEntity site = new SiteEntity();
            site.setUrl(url);
            site.setName(name);
            site.setStatus(StatusType.FAILED);
            siteList.add(site);
        }
        return siteList;
    }
}
