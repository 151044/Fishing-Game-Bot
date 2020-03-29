package com.colin.discord.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class DefaultCommand implements Command {

    @Override
    public String invokeName() {
        return "uninvokablylongandstupidcommandnamedontuseforevermore";
    }

    @Override
    public void action(TextChannel txt, boolean isPm, List<String> args, User u,List<User> mentions) {
        txt.sendMessage("Well, someone actually typed that.").queue();
    }
}
