package com.web.dto;

public class LinkDto {
    private Long id;
    private String url;
    private int totalHits;

    public Long getId() {
        return id;
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

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public static class Builder {
        private LinkDto newLink;

        public Builder() {
            this.newLink = new LinkDto();
        }

        public Builder id(Long id) {
            newLink.id = id;
            return this;
        }

        public Builder url(String url) {
            newLink.url = url;
            return this;
        }

        public Builder totalHits(int totalHits) {
            newLink.totalHits = totalHits;
            return this;
        }

        public LinkDto build() {
            return newLink;
        }
    }
}
