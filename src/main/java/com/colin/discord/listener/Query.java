package com.colin.discord.listener;

import com.colin.discord.Main;
import com.colin.discord.fishing.api.Experience;
import com.colin.discord.fishing.api.Fish;
import com.colin.discord.fishing.api.Fishes;
import com.colin.discord.fishing.api.UserStore;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class Query implements Command {

    @Override
    public String invokeName() {
        return "query";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u,List<User> men) {
        if(args.size() == 0){
            Commands.sendMessage(txt,"Please specify what to query.");
            return;
        }
        String toQuery = args.get(0);
        if(toQuery.equals("amount")){
            if(args.size() == 1) {
                Commands.sendMessage(txt, EmbedFactory.from("Amount of Fish", String.join("\n", UserStore.allCurrent())));
            }else{
                Commands.sendMessage(txt,EmbedFactory.from("Amount of Fish",UserStore.getStringById(men.get(0).getIdLong())));
            }
        }else if(toQuery.equals("xp")){
            if(args.size() == 1){
                List<Long> it = Experience.levels();
                StringBuilder out = new StringBuilder();
                for(int i = 0; i < it.size(); i++){
                    out.append("Level ").append(i).append("---").append(it.get(i)).append("\n");
                }
                Commands.sendMessage(txt,EmbedFactory.from("Experience chart", out.toString()));
            }else{
                long xp = Experience.getXp(u.getIdLong());
                Commands.sendMessage(txt,EmbedFactory.from("Experience",u.getAsMention() + "---" + Experience.getXp(u.getIdLong()) + " (Level " + Experience.getLevel(u.getIdLong()) + ")"));
            }
        }else if(toQuery.equals("fish")){
            if(args.size() == 1){
                Commands.sendMessage(txt,EmbedFactory.from("All fishes", Fishes.getAll().stream().map(f -> "**" + f.getName() + "**---\nMinimum level:" + f.minLevel() + "\nWeight" + f.getWeight() + "\nMultiplier:" + f.getMultiplier() + "\n").collect(Collectors.joining())));
            }else{
                String s = Fishes.byName(args.get(1)).map(f -> "**" + f.getName() + "**---\nMinimum level:" + f.minLevel() + "\nWeight" + f.getWeight() + "\nMultiplier:" + f.getMultiplier() + "\n").orElse("Cannot find this fish.");
                Commands.sendMessage(txt,EmbedFactory.from("Fish Database",s));
            }
        }
    }
}
