package com.colin.discord.listener;

import com.colin.discord.fishing.api.Experience;
import com.colin.discord.fishing.api.UserStore;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Register implements Command {
    @Override
    public String invokeName() {
        return "register";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u,List<User> men) {
        UserStore.registerId(u.getIdLong());
        Experience.register(u.getIdLong());
        Commands.sendMessage(txt,u.getAsMention()+ " successfully registered.");
    }
}
