package ru.gb.HomeWork.Lesson1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private int age;
    private double salary;
    private Department depart;

    public Person(String name, int age, double salary, Department depart) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.depart = depart;
    }
// TODO: геттеры, сеттеры

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepart() {
        return depart;
    }

    public void setDepart(Department depart) {
        this.depart = depart;
    }

    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {

        return people.stream()
                .min(Comparator.comparingInt(Person::getAge));

    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {

        return people.stream()
                .max(Comparator.comparingDouble(Person::getSalary))
                .flatMap(x -> Optional.ofNullable(x.getDepart()));
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {

        return people.stream()
                .collect(Collectors.groupingBy(Person::getDepart));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {

        return people.stream()
                .collect(Collectors.groupingBy(x -> x.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(x -> x.getDepart().getName(),
                        Function.identity(),
                        (x, y) -> {
                            if (x.getAge() > y.getAge()) {
                                return x;
                            }
                            return y;
                        }));

    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        Map<Department, List<Person>> arg = people.stream().collect(Collectors.groupingBy(Person::getDepart));
        return arg.values().stream().map(personList -> personList.stream()
                .min(Comparator.comparingDouble(Person::getSalary))).flatMap(Optional::stream).toList();
    }

    @Override
    public String toString() {
        return
                "name= " + name +
                ", age= " + age +
                ", salary= " + salary +
                ", depart= " + depart;
    }
}
