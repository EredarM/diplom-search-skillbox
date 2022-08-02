package com.skillbox.searchengine.service.impl;

import com.skillbox.searchengine.model.dataType.StatusType;
import com.skillbox.searchengine.model.dto.DetailedDto;
import com.skillbox.searchengine.model.dto.StatisticsDto;
import com.skillbox.searchengine.model.dto.TotalDto;
import com.skillbox.searchengine.model.entity.SiteEntity;
import com.skillbox.searchengine.model.response.StatisticResponse;
import com.skillbox.searchengine.service.LemmaRepositoryService;
import com.skillbox.searchengine.service.PageRepositoryService;
import com.skillbox.searchengine.service.SiteRepositoryService;
import com.skillbox.searchengine.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final SiteRepositoryService siteRepositoryService;
    private final LemmaRepositoryService lemmaRepositoryService;
    private final PageRepositoryService pageRepositoryService;

    public StatisticServiceImpl(
            SiteRepositoryService siteRepositoryService,
            LemmaRepositoryService lemmaRepositoryService,
            PageRepositoryService pageRepositoryService
    ) {
        this.siteRepositoryService = siteRepositoryService;
        this.lemmaRepositoryService = lemmaRepositoryService;
        this.pageRepositoryService = pageRepositoryService;
    }

    public StatisticResponse getStatistic() {
        TotalDto total = getTotal();
        List<SiteEntity> siteList = siteRepositoryService.getAllSites();
        DetailedDto[] detaileds = new DetailedDto[siteList.size()];
        for (int i = 0; i < siteList.size(); i++) {
            detaileds[i] = getDetailed(siteList.get(i));
        }
        return new StatisticResponse(true, new StatisticsDto(total, detaileds));
    }

    private TotalDto getTotal() {
        return new TotalDto(
                siteRepositoryService.siteCount(),
                pageRepositoryService.pageCount(),
                lemmaRepositoryService.lemmaCount(),
                isSitesIndexing()
        );
    }

    private DetailedDto getDetailed(SiteEntity site) {
        return new DetailedDto(
                site.getUrl(),
                site.getName(),
                site.getStatus(),
                site.getStatusTime().getTime(),
                site.getLastError(),
                pageRepositoryService.pageCount(site.getId()),
                lemmaRepositoryService.lemmaCount(site.getId())
        );
    }

    private boolean isSitesIndexing() {
        boolean is = true;
        for (SiteEntity s : siteRepositoryService.getAllSites()) {
            if (!s.getStatus().equals(StatusType.INDEXED)) {
                is = false;
                break;
            }
        }
        return is;
    }
}
