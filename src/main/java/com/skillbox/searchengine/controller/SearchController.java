package com.skillbox.searchengine.controller;

import com.skillbox.searchengine.model.entity.RequestEntity;
import com.skillbox.searchengine.model.response.SearchResponse;
import com.skillbox.searchengine.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SearchController {

    private final SearchService search;

    public SearchController(SearchService search) {
        this.search = search;
    }

    @GetMapping("/search")
    public SearchResponse search(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "site", required = false, defaultValue = "") String site,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = false, defaultValue = "0") int limit
    ) throws IOException {
        return search.searchService(new RequestEntity(query), site.equals("") ? null : site, offset, limit);
    }
}
