package com.colin.discord.listener;

import com.colin.discord.fishing.api.Experience;
import com.colin.discord.fishing.api.Fish;
import com.colin.discord.fishing.api.Fishes;
import com.colin.discord.fishing.api.UserStore;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.math.BigDecimal;
import java.util.List;

public class TryFish implements Command {
    @Override
    public String invokeName() {
        return "fish";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u, List<User> mentions) {
        if(args.size() == 0){
            Commands.sendMessage(txt,"Needs a number to guess! The range is 1 to 1000.");
            return;
        }
        int guess = Integer.parseInt(args.get(0));
        if(guess < 0 || guess > 1000){
            Commands.sendMessage(txt,"The range of numbers possible is 1 to 1000.");
            return;
        }
        Fish f = Fishes.fromLevel(Experience.getLevel(u.getIdLong()));
        if(f.caught(guess)){
            Commands.sendMessage(txt,"Successfully caught fish *" + f.getName() +"*.");
            UserStore.addOnce(u.getIdLong(), BigDecimal.valueOf(f.getMultiplier()));
            if(Experience.addXp(u.getIdLong(),(long) f.getMultiplier() * (f.minLevel() + 1))){
                Commands.sendMessage(txt,"Level up to " + Experience.getLevel(u.getIdLong()));
            }
        }else{
            Commands.sendMessage(txt,"Failed to catch *" + f.getName() + "*. Better luck next time!");
        }
    }
}
