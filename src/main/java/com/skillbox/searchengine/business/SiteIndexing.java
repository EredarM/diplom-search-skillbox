package com.skillbox.searchengine.sitemap;

import com.skillbox.searchengine.config.SearchConfig;
import com.skillbox.searchengine.model.dataType.StatusType;
import com.skillbox.searchengine.morphology.MorphologyAnalyzer;
import com.skillbox.searchengine.service.*;
import com.skillbox.searchengine.sitemap.SiteMapBuilder;
import com.skillbox.searchengine.model.entity.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class SiteIndexing extends Thread {
    private final SiteEntity site;
    private final SearchConfig searchConfig;
    private final FieldRepositoryService fieldRepositoryService;
    private final SiteRepositoryService siteRepositoryService;
    private final IndexRepositoryService indexRepositoryService;
    private final PageRepositoryService pageRepositoryService;
    private final LemmaRepositoryService lemmaRepositoryService;
    private final boolean allSite;

    public SiteIndexing(
            SiteEntity site,
                        SearchConfig searchConfig,
                        FieldRepositoryService fieldRepositoryService,
                        SiteRepositoryService siteRepositoryService,
                        IndexRepositoryService indexRepositoryService,
                        PageRepositoryService pageRepositoryService,
                        LemmaRepositoryService lemmaRepositoryService,
                        boolean allSite
    ) {
        this.site = site;
        this.searchConfig = searchConfig;
        this.fieldRepositoryService = fieldRepositoryService;
        this.siteRepositoryService = siteRepositoryService;
        this.indexRepositoryService = indexRepositoryService;
        this.pageRepositoryService = pageRepositoryService;
        this.lemmaRepositoryService = lemmaRepositoryService;
        this.allSite = allSite;
    }


    @Override
    public void run() {
        try {
            if (allSite) {
                runAllIndexing();
            } else {
                runOneSiteIndexing(site.getUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runAllIndexing() {
        site.setStatus(StatusType.INDEXING);
        site.setStatusTime(new Date());
        siteRepositoryService.save(site);
        SiteMapBuilder builder = new SiteMapBuilder(site.getUrl(), this.isInterrupted());
        builder.builtSiteMap();
        List<String> allSiteUrls = builder.getSiteMap();
        for (String url : allSiteUrls) {
            runOneSiteIndexing(url);
        }
    }

    public void runOneSiteIndexing(String searchUrl) {
        site.setStatus(StatusType.INDEXING);
        site.setStatusTime(new Date());
        siteRepositoryService.save(site);
        List<FieldEntity> fieldList = getFieldListFromDB();
        try {
            PageEntity page = getSearchPage(searchUrl, site.getUrl(), site.getId());
            PageEntity checkPage = pageRepositoryService.getPage(searchUrl.replaceAll(site.getUrl(), ""));
            if (checkPage != null) {
                prepareDbToIndexing(checkPage);
            }
            TreeMap<String, Integer> map = new TreeMap<>();
            TreeMap<String, Float> indexing = new TreeMap<>();
            for (FieldEntity field : fieldList) {
                String name = field.getName();
                float weight = field.getWeight();
                String stringByTeg = getStringByTeg(name, page.getContent());
                MorphologyAnalyzer analyzer = new MorphologyAnalyzer();
                TreeMap<String, Integer> tempMap = analyzer.textAnalyzer(stringByTeg);
                map.putAll(tempMap);
                indexing.putAll(indexingLemmas(tempMap, weight));
            }
            lemmaToDB(map, site.getId());
            map.clear();
            pageToDb(page);
            indexingToDb(indexing, page.getPath());
            indexing.clear();
        } catch (UnsupportedMimeTypeException e) {
            site.setLastError("Формат страницы не поддерживается: " + searchUrl);
            site.setStatus(StatusType.FAILED);
        } catch (IOException e) {
            site.setLastError("Ошибка чтения страницы: " + searchUrl + "\n" + e.getMessage());
            site.setStatus(StatusType.FAILED);
        } finally {
            siteRepositoryService.save(site);
        }
        site.setStatus(StatusType.INDEXED);
        siteRepositoryService.save(site);
    }


    private void pageToDb(PageEntity page) {
        pageRepositoryService.save(page);
    }

    private PageEntity getSearchPage(String url, String baseUrl, int siteId) throws IOException {
        PageEntity page = new PageEntity();
        Connection.Response response = Jsoup.connect(url)
                .userAgent(searchConfig.getAgent())
                .referrer("http://www.google.com")
                .execute();

        String content = response.body();
        String path = url.replaceAll(baseUrl, "");
        int code = response.statusCode();
        page.setCode(code);
        page.setPath(path);
        page.setContent(content);
        page.setSiteId(siteId);
        return page;
    }

    private List<FieldEntity> getFieldListFromDB() {
        List<FieldEntity> list = new ArrayList<>();
        Iterable<FieldEntity> iterable = fieldRepositoryService.getAllField();
        iterable.forEach(list::add);
        return list;
    }

    private String getStringByTeg(String teg, String html) {
        String string = "";
        Document document = Jsoup.parse(html);
        Elements elements = document.select(teg);
        StringBuilder builder = new StringBuilder();
        elements.forEach(element -> builder.append(element.text()).append(" "));
        if (!builder.isEmpty()) {
            string = builder.toString();
        }
        return string;
    }

    private void lemmaToDB(TreeMap<String, Integer> lemmaMap, int siteId) {
        for (Map.Entry<String, Integer> lemma : lemmaMap.entrySet()) {
            String lemmaName = lemma.getKey();
            List<LemmaEntity> lemma1 = lemmaRepositoryService.getLemma(lemmaName);
            LemmaEntity lemma2 = lemma1.stream().
                    filter(lemma3 -> lemma3.getSiteId() == siteId).
                    findFirst().
                    orElse(null);
            if (lemma2 == null) {
                LemmaEntity newLemma = new LemmaEntity(lemmaName, 1, siteId);
                lemmaRepositoryService.save(newLemma);
            } else {
                int count = lemma2.getFrequency();
                lemma2.setFrequency(++count);
                lemmaRepositoryService.save(lemma2);
            }
        }
    }

    private TreeMap<String, Float> indexingLemmas(TreeMap<String, Integer> lemmas, float weight) {
        TreeMap<String, Float> map = new TreeMap<>();
        for (Map.Entry<String, Integer> lemma : lemmas.entrySet()) {
            String name = lemma.getKey();
            float w;
            if (!map.containsKey(name)) {
                w = (float) lemma.getValue() * weight;
            } else {
                w = map.get(name) + ((float) lemma.getValue() * weight);
            }
            map.put(name, w);
        }
        return map;
    }

    private void indexingToDb(TreeMap<String, Float> map, String path) {
        PageEntity page = pageRepositoryService.getPage(path);
        int pathId = page.getId();
        int siteId = page.getSiteId();
        for (Map.Entry<String, Float> lemma : map.entrySet()) {

            String lemmaName = lemma.getKey();
            List<LemmaEntity> lemma1 = lemmaRepositoryService.getLemma(lemmaName);
            for (LemmaEntity l : lemma1) {
                if (l.getSiteId() == siteId) {
                    int lemmaId = l.getId();
                    IndexingEntity indexing = new IndexingEntity(pathId, lemmaId, lemma.getValue());
                    indexRepositoryService.save(indexing);
                }
            }
        }
    }

    private void prepareDbToIndexing(PageEntity page) {
        List<IndexingEntity> indexingList = indexRepositoryService.getAllIndexingByPageId(page.getId());
        List<LemmaEntity> allLemmasIdByPage = lemmaRepositoryService.findLemmasByIndexing(indexingList);
        lemmaRepositoryService.deleteAllLemmas(allLemmasIdByPage);
        indexRepositoryService.deleteAllIndexing(indexingList);
        pageRepositoryService.deletePage(page);
    }
}
