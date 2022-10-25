package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }
}
