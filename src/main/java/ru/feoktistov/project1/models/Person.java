package ru.feoktistov.project1.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 1900, message = "Year birth should not be less than 1900")
    @Max(value = 2023, message = "Year birth should not be greater than 2023")
    private int yearBirthday;

    public Person() {

    }

    public Person(int id, String name, int yearBirthday) {
        this.id = id;
        this.name = name;
        this.yearBirthday = yearBirthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearBirthday() {
        return yearBirthday;
    }

    public void setYearBirthday(int yearBirthday) {
        this.yearBirthday = yearBirthday;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearBirthday=" + yearBirthday +
                '}';
    }
}
