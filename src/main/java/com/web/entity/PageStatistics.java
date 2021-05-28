package com.web.entity;


public class PageStatistics implements Identifiable {
    public static final String TABLE = "page_statistics";
    public static final String ID = "id";
    public static final String LINK_ID = "link_id";
    public static final String TERM_ID = "term_id";
    public static final String TERM_COUNT = "term_count";

    private Long id;
    private Long linkId;
    private Long termId;
    private int termCount;

    public PageStatistics() {
    }

    public PageStatistics(Long id, Long linkId, Long termId, int termCount) {
        this.id = id;
        this.linkId = linkId;
        this.termId = termId;
        this.termCount = termCount;
    }

    public PageStatistics(Long linkId, Long termId, int termCount) {
        this.linkId = linkId;
        this.termId = termId;
        this.termCount = termCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public int getTermCount() {
        return termCount;
    }

    public void setTermCount(int termCount) {
        this.termCount = termCount;
    }

    @Override
    public Long getId() {
        return id;
    }
}
