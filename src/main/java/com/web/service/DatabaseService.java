package com.web.service;

import com.web.dao.LinkDao;
import com.web.dao.PageStatisticsDao;
import com.web.dao.TermDao;
import com.web.entity.Link;
import com.web.entity.PageStatistics;
import com.web.entity.Term;
import com.web.exception.DaoException;
import com.web.exception.ServiceException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Map;
import java.util.Optional;

public class DatabaseService {
    public static final Logger LOGGER = LogManager.getLogger(DatabaseService.class);
    private static Driver driver;
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3307/?useSSL=false&serverTimezone=Europe/Minsk";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";
    //private static final String SCRIPT_PATH = "C:/Users/100nout.by/JWD/web-crawler/src/main/resources/init_db.sql";
    private static final String SCRIPT_PATH = "./src/main/resources/init_db.sql";

    private PageStatisticsDao pageStatisticsDao;
    private LinkDao linkDao;
    private TermDao termDao;

    public DatabaseService(PageStatisticsDao pageStatisticsDao, LinkDao linkDao, TermDao termDao) {
        this.pageStatisticsDao = pageStatisticsDao;
        this.linkDao = linkDao;
        this.termDao = termDao;
    }

    public void initDatabase() throws ServiceException {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(SCRIPT_PATH));
            scriptRunner.runScript(reader);
        } catch (SQLException | FileNotFoundException exception) {
            throw new ServiceException(exception.getMessage(), exception);
        }
    }

    public void insertStatisticsIntoDatabase(Map<String, Map<String, Integer>> statistics) {
        statistics.forEach((page, pageStatistics) -> pageStatistics.forEach((term, hits) -> {
            try {
                Optional<Link> optionalLink = linkDao.getByUrl(page);
                Optional<Term> optionalTerm = termDao.getByName(term);
                if (optionalTerm.isPresent() && optionalLink.isPresent()) {
                    Long linkId = optionalLink.get().getId();
                    Long termId = optionalTerm.get().getId();
                    PageStatistics statisticsToInsert = new PageStatistics(linkId, termId, hits);
                    pageStatisticsDao.create(statisticsToInsert);
                }
            } catch (DaoException | NullPointerException exception) {
                LOGGER.fatal(exception.getMessage(), exception);
            }
        }));
    }

    public boolean createPage(String page) {
        Link link = new Link(page);
        try {
            linkDao.create(link);
        } catch (DaoException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
            return false;
        }
        return true;
    }

    private Long getPageId(String page) {
        try {
            Optional<Link> optionalLink = linkDao.getByUrl(page);
            if (optionalLink.isPresent()) {
                return optionalLink.get().getId();
            }
        } catch (DaoException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
            return null;
        }
        return null;
    }

    public boolean createTerm(String name) {
        Term term = new Term(name);
        try {
            termDao.create(term);
        } catch (DaoException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
            return false;
        }
        return true;
    }

    public Long getTermId(String name) {
        try {
            Optional<Term> optionalTerm = termDao.getByName(name);
            if (optionalTerm.isPresent()) {
                return optionalTerm.get().getId();
            }
        } catch (DaoException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
            return null;
        }
        return null;
    }
}
