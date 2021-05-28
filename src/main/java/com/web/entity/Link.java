package com.web.entity;

public class Link implements Identifiable {
    public static final String TABLE = "link";
    public static final String ID = "id";
    public static final String URL = "url";

    private Long id;
    private String url;
    private int totalHits;

    public Link() {
    }

    public Link(String url) {
        this.url = url;
    }

    public Link(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getTotalHits() {
        return totalHits;
    }
}
