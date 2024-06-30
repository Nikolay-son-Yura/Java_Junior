package ru.gb.HomeWork.Lesson3.Task;

//сначало показалась хорошая идея с классом SQL запросов,но увы такое себе
public class SQLRequest {
    public String createPersonTable() {
        return """ 
                create table person (
                id bigint primary key,
                name varchar(256),
                age int,
                active boolean,
                department_id bigint,
                foreign key (department_id) references department(id)
                )""";
    }

    public String createDepartmentTable() {
        return """
                create table department (
                id bigint primary key,
                name varchar(128) not null
                )""";
    }

    public String insertPersonData() {
        return """
                insert into person (id, name, age, active, department_id) values""";
    }

    public String insertDepartmentData() {
        return """
                insert into department (id, name) values""";
    }

    public String selectPersonData() {
        return """
                select id, name, age, department_id
                from person
                where active is true""";
    }

    public String updatePearsonData() {
        return """
                update person
                set active = true
                where id > 5""";
    }

    public String selectDepartmentId() {
        return """
                select id
                from department""";
    }

    public String getPersonDepartmentName() {
        return """
                select department.id, department.name
                from department
                join person
                on department.id = person.department_id
                where person.id =""";
    }

    public String getPersonDepartments() {
        return """
                select id,
                name,
                from person""";
    }
}
