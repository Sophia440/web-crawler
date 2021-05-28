package com.web.comparator;

import com.web.entity.Link;

import java.util.Comparator;

public class LinkComparator implements Comparator<Link> {

    @Override
    public int compare(Link firstLink, Link secondLink) {
        return Integer.compare(secondLink.getTotalHits(), firstLink.getTotalHits());
    }
}
