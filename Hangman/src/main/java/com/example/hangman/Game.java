package com.example.hangman;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private String lives = "*****";
    private Integer livesNumber;
    private ArrayList<Word> wordList;
    private Word word;
    private String letter;
    private String hiddenWord = "_ _ _";
    private Integer length;
    private String name;
    private Boolean lose = false;
    private Boolean win = false;

    public void setLivesNumber(Integer number){
        this.livesNumber = number;
        String stars = new String("");
        if (this.livesNumber == 0){
            this.lose = true;
            stars = "Przegrana";
            this.hiddenWord = this.name;
        }
        else
        {
            for (int i=0; i < number; i++){
                 stars = stars + "*";
            }
        }
        this.setLives(stars);
    }
    public void randomWord(){
        ArrayList<Word> list = this.wordList;
        String hide = new String("");
        Random rand = new Random();
        Word randomWord = list.get(rand.nextInt(list.size()));
        this.setWord(randomWord);
        this.setName(this.word.getName());
        this.setLength(Integer.valueOf(this.word.getLength()));
        for (int i = 0; i < this.length; i++) {
            hide = hide + "_";
        }
        this.setHiddenWord(hide);
        this.setLivesNumber(5);
        this.setLose(false);
        this.setWin(false);
        //System.out.println(this.name);
    }

    public void isLetterInWord(){
        String name = new String();
        name = this.word.getName();
        String hide = new String("");
        Boolean itIs = new Boolean(false);
        for (int i = 0; i < this.length; i++) {
            //System.out.println(String.valueOf(name.charAt(i)) + this.letter);
            if (String.valueOf(name.charAt(i)).equals(this.letter)){
                hide = hide + this.letter;
                itIs = true;
            }
            else{
                hide = hide + this.hiddenWord.charAt(i);
            }
        }
        this.hiddenWord = hide;
        if (!this.hiddenWord.contains("_")){
            this.setWin(true);
        }
        if (itIs == false) {
            this.setLivesNumber(this.livesNumber - 1);
        }
    }

    public String getLives() {
        return lives;
    }

    public void setLives(String lives) {
        this.lives = lives;
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<Word> wordList) {
        this.wordList = wordList;
        this.randomWord();
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
        isLetterInWord();
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public void setHiddenWord(String hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLose() {
        return lose;
    }

    public void setLose(Boolean lose) {
        this.lose = lose;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
        if (this.win == true){
        this.setLives("Wygrana");
    }
    }
}