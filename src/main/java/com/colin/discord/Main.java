package com.colin.discord;

import com.colin.discord.listener.Diverter;
import com.colin.utils.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    private static String prefix;
    private static JDA jda;
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        Config c = Config.read(Paths.get("./data/botData.txt"));
        prefix = c.get("prefix");
        Set<GatewayIntent> intents = GatewayIntent.getIntents(GatewayIntent.DEFAULT);
        jda = JDABuilder.create(c.get("token"), intents)
                .disableCache(CacheFlag.ACTIVITY,CacheFlag.CLIENT_STATUS).setMemberCachePolicy(MemberCachePolicy.DEFAULT)
                .addEventListeners(new Diverter()).build();
        jda.awaitReady();
    }
    public static String prefix(){
        return prefix;
    }
    public static JDA getJDA(){
        return jda;
    }
}
