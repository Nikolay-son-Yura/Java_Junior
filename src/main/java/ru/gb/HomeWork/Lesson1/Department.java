package ru.gb.HomeWork.Lesson1;

public class Department {
    private String name;

    public Department(String name) {
        this.name = name;
    }
// TODO: геттеры, сеттеры

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
