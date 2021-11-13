package com.web.crawler;

import com.web.service.DatabaseService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    public static final Logger LOGGER = LogManager.getLogger(Crawler.class);
    private static final String LINK_CSS_QUERY = "a[href]";
    private static final String LINK_ATTRIBUTE_KEY = "abs:href";
    public static final int INITIAL_DEPTH = 0;
    private static final int DEFAULT_DEPTH = 8;
    private static final int DEFAULT_VISITED_PAGES = 10000;
    private DatabaseService service;
    private Set<String> terms;
    private int maxDepth;
    private int maxVisitedPages;

    private Map<String, Map<String, Integer>> totalStatistics;
    private Set<String> links;

    public Crawler(DatabaseService service, Set<String> terms) {
        this.terms = terms;
        this.service = service;
        this.links = new HashSet<>();
        this.totalStatistics = new HashMap<>();
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setMaxVisitedPages(int maxVisitedPages) {
        this.maxVisitedPages = maxVisitedPages;
    }

    public void start(String url) {
        if (maxDepth == 0) {
            maxDepth = DEFAULT_DEPTH;
        }
        if (maxVisitedPages == 0) {
            maxVisitedPages = DEFAULT_VISITED_PAGES;
        }
        terms.stream().forEach(term -> service.createTerm(term));
        crawl(url, INITIAL_DEPTH);
        service.insertStatisticsIntoDatabase(totalStatistics);
    }

    private void crawl(String url, int depth) {
        if ((!links.contains(url) && (depth <= maxDepth) && (links.size() <= maxVisitedPages))) {
            Optional<Document> optionalDocument = request(url);
            if (optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                if (document.body() != null) {
                    Map<String, Integer> pageStatistics = getPageStatistics(document);
                    totalStatistics.put(url, pageStatistics);
                }
                links.add(url);
                service.createPage(url);
                Elements linksOnPage = document.select(LINK_CSS_QUERY);
                depth++;
                for (Element page : linksOnPage) {
                    crawl(page.attr(LINK_ATTRIBUTE_KEY), depth);
                }
            }
        }
    }

    Map<String, Integer> getPageStatistics(Set<String> terms, Document document) {
        Element body = document.body();
        String allText = body.text();
        Map<String, Integer> termsStatistics = new HashMap<>();
        terms.forEach(term -> termsStatistics.put(term, findTerm(term, allText)));
        return termsStatistics;
    }

    private Map<String, Integer> getPageStatistics(Document document) {
        Element body = document.body();
        String allText = body.text();
        Map<String, Integer> termsStatistics = new HashMap<>();
        if (allText != null) {
            terms.forEach(term -> termsStatistics.put(term, findTerm(term, allText)));
        }
        return termsStatistics;
    }

    private int findTerm(String term, String allText) {
        int count = 0;
        int startIndex = 0;
        Pattern pattern = Pattern.compile(term, Pattern.LITERAL);
        Matcher matcher = pattern.matcher(allText);
        while (matcher.find(startIndex)) {
            count++;
            startIndex = matcher.start() + 1;
        }
        return count;
    }

    private static Optional<Document> request(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            if (connection.response().statusCode() == 200) {
                return Optional.of(document);
            }
        } catch (IOException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
        }
        return Optional.empty();
    }
}
