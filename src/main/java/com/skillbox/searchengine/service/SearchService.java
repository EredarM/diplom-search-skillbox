package com.skillbox.searchengine.service;

import com.skillbox.searchengine.model.entity.RequestEntity;
import com.skillbox.searchengine.model.response.SearchResponse;

public interface SearchService {
    public SearchResponse searchService(RequestEntity request, String url, int offset, int limit);
}
