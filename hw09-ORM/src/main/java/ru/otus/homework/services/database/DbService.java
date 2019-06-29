package ru.otus.homework.services.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public interface DbService {

    long insertRow(String sql, List<Param> params) throws SQLException;

    void execute(String sqlExpression) throws SQLException;

    void updateRow(String tableName, List<Param> fields);

    <T> T selectRow(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;

    void close() throws SQLException;
}
