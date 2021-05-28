package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.entity.Link;
import com.web.exception.DaoException;
import com.web.mapper.LinkRowMapper;

import java.util.Optional;

public class LinkDao extends AbstractDao<Link> implements Dao<Link> {
    public static final String TABLE_NAME = "link";
    public static final String FIND_LINK_BY_ID = "SELECT * FROM link WHERE id = ?";
    public static final String FIND_LINK_BY_URL = "SELECT * FROM link WHERE url = ?";
    private static final String CREATE = "INSERT INTO link (url) VALUE (?)";
    private static final String UPDATE = "UPDATE link SET url = ? WHERE id = ?";

    public LinkDao(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public void create(Link item) throws DaoException {
        executeUpdate(CREATE, item.getUrl());
    }

    @Override
    public Optional<Link> update(Link item) throws DaoException {
        Optional<Link> linkToUpdate = getById(item.getId());
        if (!linkToUpdate.isPresent()) {
            throw new DaoException("Link  " + item.getId() + " not found.");
        }
        executeUpdate(UPDATE, item.getUrl(), item.getId());
        return linkToUpdate;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Link> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_LINK_BY_ID, new LinkRowMapper(), id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public Optional<Link> getByUrl(String url) throws DaoException {
        System.out.println(url);
        return executeForSingleResult(FIND_LINK_BY_URL, new LinkRowMapper(), url);
    }
}
