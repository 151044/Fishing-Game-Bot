package com.colin.discord.fishing.api;

import com.colin.discord.Main;
import com.colin.utils.Config;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UserStore {
    private UserStore(){
        throw new AssertionError();
    }
    private static ScheduledExecutorService exec = Executors.newScheduledThreadPool(7);
    private static Map<Long,BigDecimal> current = new HashMap<>();
    private static Map<Long,Increment> inc = new HashMap<>();
    private static Config points;
    static{
        try {
            points = Config.read(Paths.get("./data/people.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        points.getAsMap().forEach((id,pts) ->{
            current.put(Long.parseLong(id),BigDecimal.valueOf(Double.parseDouble(pts)));
        });
        for(PossibleTimes ps : PossibleTimes.values()){
            exec.scheduleAtFixedRate(() ->{
                inc.values().stream().filter(inc -> inc.time.equals(ps)).forEach((inc) ->{
                    if(current.containsKey(inc.id)){
                        current.put(inc.id,current.get(inc.id).add(inc.getIncrement()));
                    }else{
                        current.put(inc.id,inc.getIncrement());
                    }
                });
            },0,ps.sec(), TimeUnit.SECONDS);
        }
        shutdown();
    }
    public static void addToProd(long id, BigDecimal toSchedule,PossibleTimes time){
        if(!hasIncrement(time,id)){
            inc.put(id,new Increment(time,toSchedule,id));
        }else{
            inc.put(id,inc.get(id).add(toSchedule));
        }
    }
    public static void addOnce(long id,BigDecimal toAdd){
        if(current.containsKey(id)){
            current.put(id,current.get(id).add(toAdd));
        }else{
            current.put(id,toAdd);
        }
    }
    public static BigDecimal current(long id){
        return current.getOrDefault(id,BigDecimal.ZERO);
    }
    public static List<String> allCurrent(){
        return current.entrySet().stream().map((ent) -> Main.getJDA().retrieveUserById(ent.getKey()).complete().getAsMention() + "---" + ent.getValue().toPlainString()).collect(Collectors.toList());
    }
    public static void registerId(long id){
        current.put(id,BigDecimal.ZERO);
    }
    public static String getStringById(long id){
        return Main.getJDA().retrieveUserById(id).complete().getAsMention() + "---" + current.getOrDefault(id,BigDecimal.ZERO);
    }
    private static boolean hasIncrement(PossibleTimes time,long id){
        return inc.containsValue(new Increment(time,id));
    }
    public enum PossibleTimes{
        ONE_SEC(1),FIVE_SEC(5), THIRTY_SEC(30),ONE_MIN(60),FIVE_MIN(300),THIRTY_MIN(1800),ONE_HOUR(3600);
        private int sec;
        PossibleTimes(int sec){
            this.sec = sec;
        }
        public int sec(){
            return sec;
        }
    }
    private static class Increment{
        private PossibleTimes time;
        private BigDecimal inc;
        private long id;
        public Increment(PossibleTimes time,BigDecimal inc,long id){
            this.time = time;
            this.inc = inc;
        }
        public Increment(PossibleTimes time,long id){
            this(time,BigDecimal.ZERO,id);
        }
        public BigDecimal getIncrement(){
            return inc;
        }
        public PossibleTimes getTime(){
            return time;
        }
        public Increment add(BigDecimal bd){
            inc = inc.add(bd);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Increment increment = (Increment) o;
            return id == increment.id &&
                    time == increment.time &&
                    Objects.equals(inc, increment.inc);
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, inc, id);
        }
    }
    private static void shutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            current.forEach((l,bd) ->{
                points.store(l.toString(),Double.toString(bd.doubleValue()));
            });
            try {
                points.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
