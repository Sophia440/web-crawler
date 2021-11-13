package com.web.writer;

import au.com.bytecode.opencsv.CSVWriter;
import com.web.dao.LinkDao;
import com.web.dao.PageStatisticsDao;
import com.web.dao.TermDao;
import com.web.entity.Link;
import com.web.entity.PageStatistics;
import com.web.entity.Term;
import com.web.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CsvFileWriter {
    private static final Logger LOGGER = LogManager.getLogger(CsvFileWriter.class);

    private PageStatisticsDao pageStatisticsDao;

    public CsvFileWriter(PageStatisticsDao pageStatisticsDao) {
        this.pageStatisticsDao = pageStatisticsDao;
    }

    public void writeAllStatistics(String filename, List<Term> terms, List<Link> links) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename));
            int termsCount = terms.size();
            String[] termsArray = new String[termsCount + 1];
            termsArray[0] = "url";
            for (int i = 1; i <= termsCount; i++) {
                termsArray[i] = terms.get(i - 1).getName();
            }
            writer.writeNext(termsArray);
            List<String[]> statistics = new ArrayList<>();
            links.forEach(link -> {
                Long linkId = link.getId();
                String[] record = new String[termsCount + 1];
                record[0] = link.getUrl();
                for (int i = 1; i <= termsCount; i++) {
                    Long termId = terms.get(i - 1).getId();
                    Optional<PageStatistics> optionalPageStatistics = Optional.empty();
                    try {
                        optionalPageStatistics = pageStatisticsDao.getPageStatisticsByTermIdAnsLinkId(termId, linkId);
                    } catch (DaoException exception) {
                        LOGGER.fatal(exception.getMessage(), exception);
                    }
                    if (optionalPageStatistics.isPresent()) {
                        record[i] = String.valueOf(optionalPageStatistics.get().getTermCount());
                    } else {
                        record[i] = "0";
                    }
                }
                statistics.add(record);
            });
            writer.writeAll(statistics);
            writer.close();
        } catch (IOException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
        }
    }

    public void writeTopHits(String filename, List<Link> topHits) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename));
            writer.writeNext(new String[]{"link", "hits"});
            for (Link link : topHits) {
                writer.writeNext(new String[]{link.getUrl(), String.valueOf(link.getTotalHits())});
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
