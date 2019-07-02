package ru.otus.homework.services.database;

import ru.otus.homework.models.Account;

import java.sql.SQLException;

public class AccountService implements DbService<Account, Long> {
    private JdbcTemplate jdbcTemplate;

    public AccountService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(Account account) throws SQLException, IllegalAccessException {
        return jdbcTemplate.create(account);
    }

    @Override
    public Account load(Long id) throws SQLException {
        return jdbcTemplate.load(id, Account.class);
    }

    @Override
    public void update(Account account) throws SQLException, IllegalAccessException {
        jdbcTemplate.update(account);
    }
}
