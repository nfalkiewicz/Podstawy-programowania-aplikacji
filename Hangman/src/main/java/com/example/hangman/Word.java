package com.example.hangman;

public class Word {
    private Integer id;
    private String name;
    private String length;
    private String category;

    public Word(Integer id, String name, String length, String category){
        this.id = id;
        this.name = name;
        this.length = length;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
