/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;

/**
 *
 * @author Mahmoud
 */
public class Score {
    private SimpleIntegerProperty levelNo;
    private SimpleStringProperty date;
    private SimpleIntegerProperty score;

    public Score() {
    }

    public Score(Integer levelNo, String date, Integer score) {
        this.levelNo = new SimpleIntegerProperty(levelNo);
        this.date = new SimpleStringProperty(date);
        this.score = new SimpleIntegerProperty(score);
    }

    public String getDate() {
        return this.date.get();
    }

    public int getLevelNo() {
        return this.levelNo.get();
    }

    public int getScore() {
        return this.score.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = new SimpleIntegerProperty(levelNo);
    }

    public void setScore(int score) {
        this.score = new SimpleIntegerProperty(score);
    }

    /**
     *
     * @return
     * @throws java.io.FileNotFoundException
     */
 
    
}
