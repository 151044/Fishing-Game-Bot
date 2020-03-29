package com.colin.discord.fishing.api;

import com.colin.utils.RandomUtil;

public class Fish {
    private String name;
    private int range;
    private double multiplier;
    private int weight;
    private int min;
    private String desc;
    public Fish(String name,int min,int range,double multiplier,int weight) {
        this(name,min,range,multiplier,weight,"No description for this fish found.");
    }
    public Fish(String name,int min,int range,double multiplier,int weight,String desc){
        this.name = name;
        this.range = range;
        this.multiplier = multiplier;
        this.weight = weight;
        this.min = min;
        this.desc = desc;
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
