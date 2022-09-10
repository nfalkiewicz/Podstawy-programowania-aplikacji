package com.example.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private Integer id;
    private String lastName;
    private String firstName;
    private String title;
    private String year;
    private String print;
    private String description;

    protected Book(Integer Id, String lastName, String firstName, String title, String year, String print, String description){
        this.id = new Integer(Id);
        this.lastName = new String(lastName);
        this.firstName = new String(firstName);
        this.title = new String(title);
        this.year = new String(year);
        this.print = new String(print);
        this.description = new String(description);

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
