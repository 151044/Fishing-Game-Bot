package com.colin.discord.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Shop implements Command {
    @Override
    public String invokeName() {
        return "shop";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u, List<User> mentions) {

    }
}
