package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.entity.PageStatistics;
import com.web.exception.DaoException;
import com.web.mapper.PageStatisticsRowMapper;

import java.util.Optional;

public class PageStatisticsDao extends AbstractDao<PageStatistics> implements Dao<PageStatistics> {
    public static final String TABLE_NAME = "page_statistics";
    public static final String FIND_PAGE_STATISTICS_BY_ID = "SELECT * FROM page_statistics WHERE id = ?";
    private static final String CREATE = "INSERT INTO page_statistics (link_id, term_id, term_count) VALUE (?, ?, ?)";
    private static final String UPDATE = "UPDATE page_statistics SET link_id = ?, term_id = ?, term_count = ? WHERE id = ?";


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
}
