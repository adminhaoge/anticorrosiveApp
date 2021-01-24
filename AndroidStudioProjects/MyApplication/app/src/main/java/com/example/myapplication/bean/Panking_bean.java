package com.example.myapplication.bean;

import java.util.List;

public class Panking_bean {



    private int Score;
    private int duration;

    public Panking_bean(int score, int duration) {
        Score = score;
        this.duration = duration;
    }



    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
