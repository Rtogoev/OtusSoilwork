package ru.otus.homework.services.database;

import ru.otus.homework.services.reflection.ReflectionService;

import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate {

    private ReflectionService reflectionService;
    private DbService dbService;

    public JdbcTemplate(ReflectionService reflectionService, DbService dbService) {
        this.reflectionService = reflectionService;
        this.dbService = dbService;
    }

    public <T> long create(T objectData) throws SQLException, IllegalAccessException {
        String tableName = reflectionService.getClassName(objectData);
        List<Param> params = reflectionService.getFieldsExceptIdAsParams(objectData);
        return dbService.insertRow(tableName,params);
    }

    public <T> void update(T objectData) throws IllegalAccessException {
        String tableName = reflectionService.getClassName(objectData);
        List<Param> params = reflectionService.getFieldsExceptIdAsParams(objectData);
//        dbService.updateRow(tableName, params, );
    }

    public <T> T load(long id, Class clazz) {
//        Optional<User> user = dbService.selectRow("select id, name from user where id  = ?", id, resultSet -> {
//            try {
//                if (resultSet.next()) {
//                    return new User(resultSet.getLong("id"), resultSet.getString("name"));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//        System.out.println("user:" + user);
//        return user;
        return null;
    }
}
