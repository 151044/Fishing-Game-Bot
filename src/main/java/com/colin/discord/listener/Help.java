package com.colin.discord.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Help implements Command {
    @Override
    public String invokeName() {
        return "help";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u,List<User> men) {
        txt.sendMessage(EmbedFactory.from("Help","Help")).queue();
    }
    public static void makeHelp(String name, String shortDesc,String syntax,String args,String optionalArgs ){

    }
}
