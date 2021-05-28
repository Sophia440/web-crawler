package com.web.command;

import com.web.dao.DaoHelper;
import com.web.dao.DaoHelperFactory;
import com.web.exception.ConnectionException;
import com.web.parser.TermsParser;
import com.web.service.DatabaseService;
import com.web.writer.CsvFileWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
    private static final String DATA_FORM_PAGE = "/view/data_form.jsp";
    private DaoHelper helper;

    public CommandFactory() {
        DaoHelperFactory factory = new DaoHelperFactory();
        try {
            this.helper = factory.create();
        } catch (ConnectionException exception) {
            LOGGER.fatal(exception.getMessage(), exception);
        }
    }

    public Command create(String type) {
        switch (type) {
            case "start":
                return new ShowPageCommand(DATA_FORM_PAGE);
            case "crawl":
                return new CrawlCommand(new DatabaseService(helper.createPageStatisticsDao(), helper.createLinkDao(), helper.createTermDao()), new TermsParser());
            case "getCsvFiles":
                return new PrintStatisticsCommand(new DatabaseService(helper.createPageStatisticsDao(), helper.createLinkDao(), helper.createTermDao()), new CsvFileWriter(helper.createPageStatisticsDao()));
            default:
                throw new IllegalArgumentException("Unknown type of Command " + type);
        }
    }
}
