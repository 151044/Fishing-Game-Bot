package com.colin.discord.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface Command {
    String invokeName();
    default boolean isHidden(){
        return false;
    }
    void action(TextChannel txt, boolean isPm, List<String> args, User u,List<User> mentions);
    default boolean canPmAct(){
        return true;
    }
    default boolean canGuildAct(){
        return true;
    }
}
