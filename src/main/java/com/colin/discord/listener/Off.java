package com.colin.discord.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Off implements Command {

    @Override
    public String invokeName() {
        return "off";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u, List<User> mentions) {
        Commands.sendMessage(txt,"Bye!");
        System.exit(0);
    }
}
