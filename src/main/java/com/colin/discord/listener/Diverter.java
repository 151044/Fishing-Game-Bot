package com.colin.discord.listener;

import com.colin.discord.Main;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class Diverter extends ListenerAdapter {
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if(event.isFromGuild()){
            return;
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message m = event.getMessage();
        String con = m.getContentRaw();
        if(m.getContentRaw().equals("Eric...")){
            Commands.sendMessage(event,"Poor Eric, getting ganged upon by all the bots!");
        }
        if(!con.startsWith(Main.prefix())){
            return;
        }
        String[] args = con.split(" ");
        if(!Commands.hasCommand(args[0].substring(1))){
            Commands.sendMessage(event,"No such command.");
            return;
        }
        String actual = args[0].substring(1);
        Command get = Commands.lookup(actual);
        List<String> wrapped = Arrays.asList(args);
        get.action(m.getTextChannel(),false,wrapped.subList(1,wrapped.size()),m.getAuthor(),m.getMentionedUsers());
    }
}
