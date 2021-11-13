package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.entity.Identifiable;
import com.web.exception.DaoException;
import com.web.mapper.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {
    private final ProxyConnection connection;

    protected AbstractDao(ProxyConnection connection) {
        this.connection = connection;
    }

    protected List<T> executeQuery(String query, RowMapper<T> mapper, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    protected int executeQuery(String query, String columnLabel, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int result = resultSet.getInt(columnLabel);
            return result;
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    protected PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i - 1]);
        }
        return statement;
    }

    public List<T> getAll() throws DaoException {
        String table = getTableName();
        RowMapper<T> mapper = (RowMapper<T>) RowMapper.create(table);
        return executeQuery("select * from " + table, mapper);
    }

    protected Optional<T> executeForSingleResult(String query, RowMapper<T> mapper, Object... params) throws DaoException {
        List<T> items = executeQuery(query, mapper, params);
        if (items.size() == 1) {
            if (items.get(0) != null) {
                return Optional.of(items.get(0));
            }
        } else if (items.size() > 1) {
            throw new IllegalArgumentException("More than one record found");
        }
        return Optional.empty();
    }

    protected void executeUpdate(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void save(T item) throws DaoException {
        if (item.getId() == null) {
            create(item);
        } else {
            update(item);
        }
    }

    protected abstract void create(T item) throws DaoException;

    protected abstract Optional<T> update(T item) throws DaoException;

    protected abstract String getTableName();
}
