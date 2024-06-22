package ru.gb.HomeWork.Lesson1;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        Department urbanEconomyAndTransport = new Department("Департамент городского хозяйства и транспорта");
        Department informationPolicy = new Department("Департамент информационной политики");


        Person personP = new Person("Петр", 60, 150000.00, urbanEconomyAndTransport);
        Person personN = new Person("Николай", 36, 80000.00, urbanEconomyAndTransport);
        Person personAl = new Person("Александр", 40, 70000.00, informationPolicy);
        Person personS = new Person("Семен", 42, 60000.00, informationPolicy);
        Person personA = new Person("Александра", 32, 95000.00, informationPolicy);
        Person personV = new Person("Василий", 35, 100000.00, urbanEconomyAndTransport);

        List<Person> list = new ArrayList<>();
        list.add(personV);
        list.add(personP);
        list.add(personN);
        list.add(personAl);
        list.add(personS);
        list.add(personA);


//
//        System.out.println(Person.findMostYoungestPerson(list));//Найти самого молодого сотрудника
//        System.out.println(Person.findMostExpensiveDepartment(list));//Найти департамент, в котором работает сотрудник с самой большой зарплатой
//        System.out.println(Person.groupByDepartment(list));//Сгруппировать сотрудников по департаментам
//        System.out.println(Person.groupByDepartmentName(list));//Сгруппировать сотрудников по названиям департаментов
//        System.out.println(Person.getDepartmentOldestPerson(list));//В каждом департаменте найти самого старшего сотрудника
        System.out.println(Person.cheapPersonsInDepartment(list));//Найти сотрудников с минимальными зарплатами в своем отделе
    }

}
