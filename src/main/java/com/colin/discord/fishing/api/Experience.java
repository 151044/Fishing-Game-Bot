package com.colin.discord.fishing.api;

import com.colin.utils.Config;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experience {
    private Experience() {
         throw new AssertionError();
    }
    private static Map<Long, Long> xp = new HashMap<>();
    private static List<Long> exceed = new ArrayList<>();
    private static Config load;
    public static final int MAX_LEVEL = 40;
    static{
        try {
            load = Config.read(Paths.get("./data/xp.txt"));
            load.getAsMap().forEach((id,xpF) ->{
                xp.put(Long.parseLong(id),Long.parseLong(xpF));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        exceed.add(3l);
        for(int i = 1 ; i < MAX_LEVEL; i++){
            exceed.add(new BigDecimal(Math.pow(1.7,i)).add(new BigDecimal(3)).round(new MathContext(3)).longValue());
        }
        write();
    }
    public static boolean addXp(long id,long add){
        if(!xp.containsKey(id)){
            xp.put(id,add);
            return false;
        }
        long before = xp.get(id);
        xp.put(id,before + add);
        int i = 0;
        for(long l : exceed){
            if(before < exceed.get(i)){
                break;
            }
            i++;
        }
        if((before + add) >= exceed.get(i)){
            return true;
        }
        return false;
    }
    public static void register(long id){
        xp.put(id,0l);
    }
    public static int getLevel(long id){
        int i = 0;
        long cur = xp.getOrDefault(id,0l);
        for(long l : exceed){
            if(l > cur){
                break;
            }
            i++;
        }
        return i;
    }
    public static long getXp(long id){
        return xp.getOrDefault(id,0l);
    }
    public static List<Long> levels(){
        return new ArrayList<>(exceed);
    }
    private static void write(){
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            xp.forEach((id,xpS) ->{
                load.store(Long.toString(id),Long.toString(xpS));
            });
            try {
                load.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
