package ru.gb.HomeWork.Lesson3.Task;

/**
 * С помощью JDBC, выполнить следующие пункты:
 * 1. Создать таблицу Person (скопировать код с семниара)
 * 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
 * 3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
 * 4. Написать метод, который загружает Имя department по Идентификатору person
 * 5. * Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name
 *   Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
 * 6. ** Написать метод, который загружает Map<String, List<String>>, в которой маппинг department.name -> <person.name>
 *   Пример:
 *   [
 *     {"department #1", ["person #1", "person #2"]},
 *     {"department #2", ["person #3", "person #4"]}
 *   ]
 *
 *  7. *** Создать классы-обертки над таблицами, и в пунктах 4, 5, 6 возвращать объекты.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:tetst")) {
            Controller controller = new Controller();
            controller.createDepartmentTable(connection);
            controller.createPersonTable(connection);
            controller.updatePersonData(connection);
            controller.selectPerson(connection);
            long personID = 3;
            System.out.println("Person ID=" + personID +" "+ controller.getPersonDepartmentName(connection, personID));
            System.out.println(controller.getPersonDepartments(connection));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
