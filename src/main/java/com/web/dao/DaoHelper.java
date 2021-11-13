package com.web.dao;

import com.web.connection.ProxyConnection;
import com.web.exception.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {
    private final ProxyConnection connection;

    public DaoHelper(ProxyConnection connection) {
        this.connection = connection;
    }

    public PageStatisticsDao createPageStatisticsDao() {
        return new PageStatisticsDao(connection);
    }

    public LinkDao createLinkDao() {
        return new LinkDao(connection);
    }

    public TermDao createTermDao() {
        return new TermDao(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }
}


