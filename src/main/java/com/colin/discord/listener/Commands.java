package com.colin.discord.listener;

import com.colin.utils.ReflectUtils;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Commands {
    private Commands(){
        throw new AssertionError();
    }
    private static final Map<String,Command> lookup = new HashMap<>();
    static{
        try {
            ReflectUtils.loadConcreteSubclasses(Paths.get("./data/"),Command.class).stream().map(cls -> ReflectUtils.getNoArgConstructor(cls)).filter(opt -> opt.isPresent()).map(cons -> {
                try {
                    return cons.get().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                throw new IllegalStateException("Unable to load modules");
            }).forEach(cmd -> lookup.put(cmd.invokeName(),cmd));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Command lookup(String s){
        System.out.println(s);
        return lookup.getOrDefault(s,new DefaultCommand());
    }
    public static void set(String s, Command cmd){
        lookup.put(s,cmd);
    }
    public static void sendMessage(MessageReceivedEvent evt,String put){
        evt.getChannel().sendMessage(new MessageBuilder(put).build()).queue();
    }
    public static void sendMessage(GuildMessageReceivedEvent evt,String put){
        evt.getChannel().sendMessage(new MessageBuilder(put).build()).queue();
    }
    public static boolean hasCommand(String s){
        return lookup.containsKey(s);
    }
    public static void sendMessage(TextChannel txt, String put){
        txt.sendMessage(put).queue();
    }
    public static void sendMessage(TextChannel txt, MessageEmbed em){
        txt.sendMessage(em).queue();
    }
}
