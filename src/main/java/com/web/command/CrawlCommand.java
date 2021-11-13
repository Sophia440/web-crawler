package com.web.command;

import com.web.crawler.Crawler;
import com.web.dto.LinkDto;
import com.web.entity.Link;
import com.web.exception.ServiceException;
import com.web.parser.Parser;
import com.web.service.DatabaseService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrawlCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CrawlCommand.class);
    public static final String ERROR_MESSAGE = "An error has occurred. See logs for further information.";
    public static final String RESULT_PAGE = "/view/result_page.jsp";
    public static final int TOP_COUNT = 10;
    private DatabaseService databaseService;
    private Parser parser;

    public CrawlCommand(DatabaseService databaseService, Parser parser) {
        this.databaseService = databaseService;
        this.parser = parser;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String seedUrl = request.getParameter("url");
        String terms = request.getParameter("terms");
        String maxDepthString = request.getParameter("maxDepth");
        String maxPagesString = request.getParameter("maxPages");
        List<String> termsList = parser.parse(terms);
        Set<String> termsSet = new HashSet<>(termsList);
        HttpSession session = request.getSession();
        session.setAttribute("termsSet", termsSet);
        try {
            databaseService.initDatabase();
            Crawler crawler = new Crawler(databaseService, termsSet);
            if (!maxDepthString.isEmpty()) {
                int maxDepth = Integer.parseInt(maxDepthString);
                crawler.setMaxDepth(maxDepth);
            }
            if (!maxPagesString.isEmpty()) {
                int maxPages = Integer.parseInt(maxPagesString);
                crawler.setMaxVisitedPages(maxPages);
            }
            crawler.start(seedUrl);
            List<Link> topHits = databaseService.getTop(TOP_COUNT);
            session.setAttribute("topHits", topHits);
            session.setAttribute("link", new LinkDto());
        } catch (ServiceException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
            session.setAttribute("errorMessage", ERROR_MESSAGE);
        }
        return CommandResult.forward(RESULT_PAGE);
    }
}
