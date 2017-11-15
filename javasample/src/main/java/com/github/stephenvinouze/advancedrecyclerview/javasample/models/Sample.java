package com.github.stephenvinouze.advancedrecyclerview.javasample.models;

import java.util.ArrayList;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class Sample {

    private int id;
    private int rate;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Sample> mockItems() {
        ArrayList<Sample> samples = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            Sample sample = new Sample();
            sample.setId(i);
            sample.setRate((int) (Math.random() * 5));
            sample.setName("Sample name for index " + i);
            samples.add(sample);
        }

        return samples;
    }
}
