package com.colin.discord.fishing.api;

import com.colin.utils.Config;
import com.colin.utils.RandomUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Fishes {
    private Fishes() {
        throw new AssertionError();
    }
    private static List<Fish> list = new ArrayList<>();
    static{
        try {
            Config f = Config.read(Paths.get("./data/fishes.txt"));
            f.getAsMap().forEach((name,props) -> {
                String[] arr = props.split(",");
                list.add(new Fish(name,Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Double.parseDouble(arr[2]),Integer.parseInt(arr[3])));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Fish fromLevel(int level){
        if(level < 0 || level > 40){
            throw new IllegalArgumentException("Level too large, given " + level);
        }
        List<String> can = new ArrayList<>();
        for(Fish f : list){
            if(f.minLevel() <= level){
                for(int i = 0; i < f.getWeight(); i++){
                    can.add(f.getName());
                }
            }
        }
        String choice = can.get(RandomUtil.getNextBoundedInt(can.size() - 1));
        return list.stream().filter(f -> f.getName().equals(choice)).findFirst().get();
    }
}
