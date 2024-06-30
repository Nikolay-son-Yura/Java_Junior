package ru.gb.HomeWork.Lesson3.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PersonTable {
    static SQLRequest sql = new SQLRequest();

    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql.createPersonTable());
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы Person произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    public void insertData(Connection connection, ArrayList<Long> departmentIds) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder(sql.insertPersonData());
            for (int i = 1; i <= 10; i++) {
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                long department = departmentIds.get(ThreadLocalRandom.current().nextInt(departmentIds.size()));
                insertQuery.append(String.format("(%s, '%s', %s, %s, '%s')", i, "Person #" + i, age, active, department));
                if (i != 10) {
                    insertQuery.append(",\n");
                }
            }
            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Добавлено строк в Person: " + insertCount);
        }
    }

    public void updateData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updateCount = statement.executeUpdate(sql.updatePearsonData());
            System.out.println("Обновлено строк: " + updateCount);
        }
    }

    public void selectData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.selectPersonData());
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                long department_id = resultSet.getLong("department_id");
                System.out.println("Найдена строка: [id = " + id + ", name = " + name + ", age = " + age + ", department_id = " + department_id + "]");
            }
        }
    }
}
