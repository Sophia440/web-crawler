package com.web.command;

import com.web.entity.Link;
import com.web.entity.Term;
import com.web.service.DatabaseService;
import com.web.writer.CsvFileWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PrintStatisticsCommand implements Command{
    public static final String RESULT_PAGE = "/view/result_page.jsp";
    public static final String PRINT_MESSAGE = "Printing is done!";
    public static final String TOP_HITS_FILENAME = "C:/Users/100nout.by/JWD/web-crawler/src/main/resources/top_hits.csv";
    public static final String STATISTICS_FILENAME = "C:/Users/100nout.by/JWD/web-crawler/src/main/resources/statistics.csv";
    // public static final String TOP_HITS_FILENAME = "src/main/resources/top_hits.csv";
    // public static final String STATISTICS_FILENAME = "src/main/resources/statistics.csv";
    public static final int TOP_COUNT = 10;
    private DatabaseService databaseService;
    private CsvFileWriter writer;

    public PrintStatisticsCommand(DatabaseService databaseService, CsvFileWriter writer) {
        this.databaseService = databaseService;
        this.writer = writer;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<Link> topHits = databaseService.getTop(TOP_COUNT);
        List<Link> allLinks = databaseService.getAllLinks();
        List<Term> allTerms = databaseService.getAllTerms();
        writer.writeTopHits(TOP_HITS_FILENAME, topHits);
        writer.writeAllStatistics(STATISTICS_FILENAME, allTerms, allLinks);
        HttpSession session = request.getSession();
        session.setAttribute("printingMessage", PRINT_MESSAGE);
        return CommandResult.forward(RESULT_PAGE);
    }
}
