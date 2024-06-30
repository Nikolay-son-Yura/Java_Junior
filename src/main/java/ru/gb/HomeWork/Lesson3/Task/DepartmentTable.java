package ru.gb.HomeWork.Lesson3.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DepartmentTable {
    SQLRequest sql = new SQLRequest();
    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute(sql.createDepartmentTable());
        } catch (SQLException e){
            System.err.println("Во время создания таблицы Department произошла ошибка: " + e.getMessage());
            throw e;
        }
    }
    public void insertData(Connection connection, int depNum) throws SQLException {
        try (Statement statement = connection.createStatement()){
            StringBuilder insertQuery = new StringBuilder(sql.insertDepartmentData());
            for (int i = 1; i <= depNum; i++) {
                insertQuery.append(String.format("(%s, '%s')", i, "Department #" + i));
                if(i != depNum){
                    insertQuery.append(",\n");
                }
            }
            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Добавлено строк в Department: " + insertCount);
        }
    }
    public ArrayList<Long> selectData(Connection connection) throws SQLException{
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql.selectDepartmentId());
            ArrayList<Long> departments = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                departments.add(id);
            }
            return departments;
        }
    }
}
