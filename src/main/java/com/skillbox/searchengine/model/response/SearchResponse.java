package com.skillbox.searchengine.model.response;

import com.skillbox.searchengine.model.dto.SearchDataDto;

public class SearchResponse {
    private boolean result;
    private int count;
    private SearchDataDto[] data;

    public SearchResponse() {
    }

    public SearchResponse(boolean result) {
        this.result = result;
    }

    public SearchResponse(boolean result, int count, SearchDataDto[] data) {
        this.result = result;
        this.count = count;
        this.data = data;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SearchDataDto[] getData() {
        return data;
    }

    public void setData(SearchDataDto[] data) {
        this.data = data;
    }
}
