package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.entity.PageStatistics;
import com.web.exception.DaoException;
import com.web.mapper.PageStatisticsRowMapper;

import java.util.List;
import java.util.Optional;

public class PageStatisticsDao extends AbstractDao<PageStatistics> implements Dao<PageStatistics> {
    public static final String TABLE_NAME = "page_statistics";
    public static final String FIND_PAGE_STATISTICS_BY_ID = "SELECT * FROM page_statistics WHERE id = ?";
    public static final String FIND_PAGE_STATISTICS_BY_LINK_ID_AND_TERM_ID = "SELECT * FROM page_statistics WHERE term_id = ? AND link_id = ?";
    private static final String CREATE = "INSERT INTO page_statistics (link_id, term_id, term_count) VALUE (?, ?, ?)";
    private static final String UPDATE = "UPDATE page_statistics SET link_id = ?, term_id = ?, term_count = ? WHERE id = ?";
    public static final String SELECT_TOP = "SELECT * FROM page_statistics WHERE term_id = ? ORDER BY term_count LIMIT ?";
    public static final String GET_TOTAL_HITS_BY_LINK_ID = "SELECT SUM(term_count) FROM page_statistics WHERE link_id = ?";
    public static final String GET_TOTAL_HITS_COLUMN_NAME = "SUM(term_count)";
    public static final String COUNT_ENTRIES = "SELECT COUNT(id) FROM page_statistics";
    public static final String COUNT_ENTRIES_COLUMN_NAME = "COUNT(id)";

    public PageStatisticsDao(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public void create(PageStatistics item) throws DaoException {
        executeUpdate(CREATE, item.getLinkId(), item.getTermId(), item.getTermCount());
    }

    @Override
    public Optional<PageStatistics> update(PageStatistics item) throws DaoException {
        Optional<PageStatistics> pageStatisticsToUpdate = getById(item.getId());
        if (!pageStatisticsToUpdate.isPresent()) {
            throw new DaoException("Page statistics  " + item.getId() + " not found.");
        }
        executeUpdate(UPDATE, item.getLinkId(), item.getTermId(), item.getTermCount(), item.getId());
        return pageStatisticsToUpdate;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<PageStatistics> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_PAGE_STATISTICS_BY_ID, new PageStatisticsRowMapper(), id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public List<PageStatistics> getTopPagesByTermId(Long termId, int amount) throws DaoException {
        return executeQuery(SELECT_TOP, new PageStatisticsRowMapper(), termId, amount);
    }

    public Optional<PageStatistics> getPageStatisticsByTermIdAnsLinkId(Long termId, Long linkId) throws DaoException {
        return executeForSingleResult(FIND_PAGE_STATISTICS_BY_LINK_ID_AND_TERM_ID, new PageStatisticsRowMapper(), termId, linkId);
    }

    public int getTotalHitsByLinkId(Long linkId) throws DaoException {
        return executeQuery(GET_TOTAL_HITS_BY_LINK_ID, GET_TOTAL_HITS_COLUMN_NAME, linkId);
    }

    public int countEntries() throws DaoException {
        return executeQuery(COUNT_ENTRIES, COUNT_ENTRIES_COLUMN_NAME);
    }
}
