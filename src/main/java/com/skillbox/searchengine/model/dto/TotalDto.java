package com.skillbox.searchengine.model.dto;

public class TotalDto {
    private long sites;
    private long pages;
    private long lemmas;
    private boolean isIndexing;

    public TotalDto(long sites, long pages, long lemmas, boolean isIndexing) {
        this.sites = sites;
        this.pages = pages;
        this.lemmas = lemmas;
        this.isIndexing = isIndexing;
    }

    public long getSites() {
        return sites;
    }

    public void setSites(int sites) {
        this.sites = sites;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getLemmas() {
        return lemmas;
    }

    public void setLemmas(int lemmas) {
        this.lemmas = lemmas;
    }

    public boolean isIndexing() {
        return isIndexing;
    }

    public void setIndexing(boolean indexing) {
        isIndexing = indexing;
    }
}
