package ru.gb.HomeWork.Lesson3;

/*
Создать таблицу Person (скопировать код с семниара)
Создать таблицу Department (id bigint primary key, name varchar(128) not null)
Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
Написать метод, который загружает Имя department по Идентификатору person
Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
** Написать метод, который загружает Map<String, List>, в которой маппинг department.name -> <person.name> Пример: {"department #1", ["person #1", "person #2"]}, {"department #2", ["person #3", "person #4"]}
*** Создать классы-обертки над таблицами, и в пунктах 4, 5, 6 возвращать объекты.
 */

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class JDBC {

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            createTableDepartment(connection);
            insertDataDepartment(connection);
            createTablePerson(connection);
            insertDataPerson(connection);
            updateData(connection);
            selectData(connection);
            departmentNameByPersonID(connection, 2);
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    private static void createTablePerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table person (
                    id bigint primary key,
                    name varchar(256),
                    age int,
                    active boolean,
                    department_id bigint,
                    foreign key (department_id) references department(id)
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void createTableDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table department (
                    id bigint primary key,
                    name varchar(128) not null
                    )
                    """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void insertDataDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder stringBuilder = new StringBuilder("insert into department (id, name) values\n");
            for (int i = 1; i <= 3; i++) {
                stringBuilder.append(String.format("(%s,'%s')", i, "Department #" + i));
                if (i != 3) {
                    stringBuilder.append(",\n");
                }
            }
            int insertCount = statement.executeUpdate(stringBuilder.toString());
            System.out.println("Вставлено строк: " + insertCount);

        }
    }

    private static void insertDataPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            Random random = new Random();
            StringBuilder insertQuery = new StringBuilder("insert into person(id, name, age, active, department_id) values\n");
            for (int i = 1; i <= 10; i++) {
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                ArrayList<Long> department_id = selectDepartmentId(connection);
                long department = department_id.get(ThreadLocalRandom.current().nextInt(department_id.size()));
                insertQuery.append(String.format("(%s, '%s', %s, %s, %s)", i, "Person #" + i, age, active, department));

                if (i != 10) {
                    insertQuery.append(",\n");
                }
            }

            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("Вставлено строк: " + insertCount);
        }
    }

    private static void updateData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int updateCount = statement.executeUpdate("update person set active = true where id > 5");
            System.out.println("Обновлено строк: " + updateCount);
        }
    }


    private static List<String> selectNamesByAge(Connection connection, String age) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("select name from person where age = ?")) {
            statement.setInt(1, Integer.parseInt(age));
            ResultSet resultSet = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
            return names;
        }
    }

    private static void departmentNameByPersonID(Connection connection, long personID) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select department.id, department.name" +
                    "  from department   " +
                    " join person " +
                    " on department.id = person.department_id" +
                    " where person.id = " + personID);

            while (resultSet.next()) {
                String departmentName = resultSet.getString("department.name");
                System.out.println("Найдена строка: [person id = " + personID + ", department name = " + departmentName + "]");
            }
        }
    }

    private static ArrayList<Long> selectDepartmentId(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(" select id from department ");
            ArrayList<Long> department = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                department.add(id);
            }
            return department;
        }
    }

    private static void selectData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    select id, name, age, department_id
                    from person
                    where active is true""");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                long department = resultSet.getLong("department_id");
                System.out.println("Найдена строка: [id = " + id + ", name = " + name + ", age = " + age + ", department_id= " + department + "]");
            }
        }
    }
}
