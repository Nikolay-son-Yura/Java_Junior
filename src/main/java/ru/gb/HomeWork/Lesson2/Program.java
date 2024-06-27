package ru.gb.HomeWork.Lesson2;
/**
 * В существующий класс ObjectCreator добавить поддержку аннотации RandomDate (по аналогии с Random):
 * 1. Аннотация должна обрабатываться только над полями типа java.util.Date
 * 2. Проверить, что min < max
 * 3. В поле, помеченной аннотацией, нужно вставлять рандомную дату,
 * UNIX-время которой находится в диапазоне [min, max)
 * <p>
 * 4. *** Добавить поддержку для типов Instant, ...
 * 5. *** Добавить атрибут Zone и поддержку для типов LocalDate, LocalDateTime
 */


import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Person person = ObjectCreator.createObj(Person.class);
        System.out.println("date = " + person.date);
        System.out.println("date2= " + person.date2);

    }

    public static class Person {
        @RandomDate
        private Date date;
        @RandomDate(min = 1703067200000L, max = 1725689600000L)
        private Date date2;
    }
}
