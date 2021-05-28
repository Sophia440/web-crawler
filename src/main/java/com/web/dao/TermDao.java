package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.entity.Term;
import com.web.exception.DaoException;
import com.web.mapper.TermRowMapper;

import java.util.Optional;

public class TermDao extends AbstractDao<Term> implements Dao<Term>{
    public static final String TABLE_NAME = "term";
    public static final String FIND_TERM_BY_ID = "SELECT * FROM term WHERE id = ?";
    public static final String FIND_TERM_BY_NAME = "SELECT * FROM term WHERE name = ?";
    private static final String CREATE = "INSERT INTO term (name) VALUE (?)";
    private static final String UPDATE = "UPDATE term SET name = ? WHERE id = ?";

    public TermDao(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public void create(Term item) throws DaoException {
        executeUpdate(CREATE, item.getName());
    }

    @Override
    public Optional<Term> update(Term item) throws DaoException {
        Optional<Term> termToUpdate = getById(item.getId());
        if (!termToUpdate.isPresent()) {
            throw new DaoException("Term  " + item.getId() + " not found.");
        }
        executeUpdate(UPDATE, item.getName(), item.getId());
        return termToUpdate;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Term> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_TERM_BY_ID, new TermRowMapper(), id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public Optional<Term> getByName(String name) throws DaoException {
        return executeForSingleResult(FIND_TERM_BY_NAME, new TermRowMapper(), name);
    }
}
