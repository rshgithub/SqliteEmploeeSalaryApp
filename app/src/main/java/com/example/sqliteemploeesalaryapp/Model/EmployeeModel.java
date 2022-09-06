package com.example.sqliteemploeesalaryapp.Model;

public class EmployeeModel {
    private String name;
    private int id , salary;

    // with id to get / select Post
    public EmployeeModel(String name, int id, int salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }


    // without id to insert new post
    public EmployeeModel(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}