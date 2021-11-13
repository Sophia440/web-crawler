package com.web.crawler;

import com.web.dao.LinkDao;
import com.web.dao.PageStatisticsDao;
import com.web.dao.TermDao;
import com.web.service.DatabaseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.mock;

public class CrawlerTest {
    private static final Set<String> TERMS = new HashSet<>(Arrays.asList("Term1", "Term2"));
    private static final DatabaseService SERVICE = new DatabaseService(mock(PageStatisticsDao.class), mock(LinkDao.class), mock(TermDao.class));
    private static final Crawler CRAWLER = new Crawler(SERVICE, TERMS);
    private static final String STATISTICS_DOCUMENT_PATH = "./src/test/resources/test_count_terms.html";
    private static final Map<String, Integer> EXPECTED_STATISTICS = new HashMap<String, Integer>() {{
        put("Term1", 4);
        put("Term2", 2);
    }};

    @Test
    public void testGetPageStatisticsShouldSucceed() throws IOException {
        Document statisticsDocument = Jsoup.parse(new File(STATISTICS_DOCUMENT_PATH), "UTF-8");
        Map<String, Integer> actualStatistics = CRAWLER.getPageStatistics(TERMS, statisticsDocument);
        Assert.assertEquals(EXPECTED_STATISTICS, actualStatistics);
    }
}
