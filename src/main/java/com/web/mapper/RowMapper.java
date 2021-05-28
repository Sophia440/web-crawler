package com.web.mapper;

import com.web.entity.Identifiable;
import com.web.entity.Link;
import com.web.entity.PageStatistics;
import com.web.entity.Term;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends Identifiable> {
    T map(ResultSet resultSet) throws SQLException;

    static RowMapper<? extends Identifiable> create(String table) {
        switch (table) {
            case PageStatistics.TABLE:
                return new PageStatisticsRowMapper();
            case Term.TABLE:
                return new TermRowMapper();
            case Link.TABLE:
                return new LinkRowMapper();
            default:
                throw new IllegalArgumentException("Unknown table " + table);
        }
    }
}
