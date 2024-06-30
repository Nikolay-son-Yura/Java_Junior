package ru.gb.HomeWork.Lesson3.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    PersonTable personTable = new PersonTable();
    DepartmentTable departmentTable = new DepartmentTable();
    static SQLRequest sql = new SQLRequest();

    public void createPersonTable(Connection connection) throws SQLException {
        personTable.createTable(connection);
        personTable.insertData(connection, selectDepartmentIds(connection));
    }

    public void createDepartmentTable(Connection connection) throws SQLException {
        departmentTable.createTable(connection);
        departmentTable.insertData(connection, 3);
    }

    public ArrayList<Long> selectDepartmentIds(Connection connection) throws SQLException {
        return departmentTable.selectData(connection);
    }

    public void selectPerson(Connection connection) throws SQLException {
        personTable.selectData(connection);
    }

    public void selectDepartment(Connection connection) throws SQLException {
        departmentTable.selectData(connection);
    }

    public void updatePersonData(Connection connection) throws SQLException {
        personTable.updateData(connection);
    }

    public Department getPersonDepartmentName(Connection connection, long personID) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.getPersonDepartmentName() + personID);
            String departmentName = "";
            long departmentId = 0;
            while (resultSet.next()) {
                departmentName = resultSet.getString("department.name");
                departmentId = resultSet.getLong("department.id");
            }
            return new Department(departmentId, departmentName);
        }
    }

    /**
     * Пункт 5
     */
    public Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql.getPersonDepartments());

            Map<String, String> map = new HashMap<>();
            while (resultSet.next()) {
                long personId = resultSet.getLong("id");
                String personName=resultSet.getString("name");
                Department department= getPersonDepartmentName(connection, personId);
                String departmentName=department.getName();
                map.put(personName, departmentName);
            }
            return map;
        }

    }

    /**
     * Пункт 6
     */
    private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        throw new UnsupportedOperationException();
    }
}
