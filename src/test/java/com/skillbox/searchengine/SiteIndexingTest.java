package com.skillbox.searchengine;

import com.skillbox.searchengine.config.SearchConfig;
import com.skillbox.searchengine.model.entity.SiteEntity;
import com.skillbox.searchengine.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SiteIndexingTest {

    @MockBean
    private SearchConfig searchConfig;
    @MockBean
    private FieldRepositoryService fieldRepositoryService;
    @MockBean
    private SiteRepositoryService siteRepositoryService;
    @MockBean
    private IndexRepositoryService indexRepositoryService;
    @MockBean
    private PageRepositoryService pageRepositoryService;
    @MockBean
    private LemmaRepositoryService lemmaRepositoryService;
    SiteEntity site = new SiteEntity();

    @Test
    void runOneSiteIndexing() {
    }
}