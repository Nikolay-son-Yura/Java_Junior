package ru.gb.HomeWork.Lesson3.Task;

public class Person {
    private int ID;
    private String name;
    private int age;
    private boolean active;
    private long departmentID;

    public Person(int ID, String name, int age, boolean active, long departmentID) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.active = active;
        this.departmentID = departmentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(long departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", departmentID=" + departmentID +
                '}';
    }
}
