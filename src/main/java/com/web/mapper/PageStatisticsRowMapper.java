package com.web.mapper;

import com.web.entity.PageStatistics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PageStatisticsRowMapper implements RowMapper<PageStatistics> {

    @Override
    public PageStatistics map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(PageStatistics.ID);
        Long linkId = resultSet.getLong(PageStatistics.LINK_ID);
        Long termId = resultSet.getLong(PageStatistics.TERM_ID);
        int termCount = resultSet.getInt(PageStatistics.TERM_COUNT);
        return new PageStatistics(id, linkId, termId, termCount);
    }
}
