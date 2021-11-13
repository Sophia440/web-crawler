package com.web.entity;

public class Term implements Identifiable {
    public static final String TABLE = "term";
    public static final String ID = "id";
    public static final String NAME = "name";

    private Long id;
    private String name;

    public Term() {
    }

    public Term(String name) {
        this.name = name;
    }

    public Term(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
