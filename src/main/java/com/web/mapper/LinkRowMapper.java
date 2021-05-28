package com.web.mapper;

import com.web.entity.Link;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRowMapper implements RowMapper<Link>{
    @Override
    public Link map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Link.ID);
        String url = resultSet.getString(Link.URL);
        return new Link(id, url);
    }
}
