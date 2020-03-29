package com.colin.discord.fishing.api;

import com.colin.utils.RandomUtil;

public class Fish {
    private String name;
    private int range;
    private double multiplier;
    private int weight;
    private int min;
    public Fish(String name,int min,int range,double multiplier,int weight){
        this.name = name;
        this.range = range;
        this.multiplier = multiplier;
        this.weight = weight;
        this.min = min;
    }
    public boolean caught(int guess){
        int lo = RandomUtil.getNextBoundedInt(1000 - range);
        int hi = lo + range;
        System.out.println("Range: " + lo +" to " + hi);
        return guess >= lo && guess <= hi;
    }
    public int minLevel(){
        return min;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
