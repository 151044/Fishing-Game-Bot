package com.colin.discord.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedFactory {
    private EmbedFactory(){
        throw new AssertionError();
    }
    public static MessageEmbed from(String title,String desc){
        return new EmbedBuilder().setAuthor("Object extends Object").setTitle(title).setDescription(desc).setColor(Color.BLUE).build();
    }
}
