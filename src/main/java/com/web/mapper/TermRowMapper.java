package com.web.mapper;

import com.web.entity.Term;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TermRowMapper implements RowMapper<Term>{
    @Override
    public Term map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Term.ID);
        String name = resultSet.getString(Term.NAME);
        return new Term(id, name);
    }
}
