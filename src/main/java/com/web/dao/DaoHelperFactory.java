package com.web.dao;

import com.web.exception.ConnectionException;
import com.web.connection.ConnectionPool;

public class DaoHelperFactory {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    public DaoHelper create() throws ConnectionException {
        return new DaoHelper(pool.getConnection());
    }
}
