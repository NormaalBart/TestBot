package me.bartvv.testbot;

import java.util.Map;

import javax.security.auth.login.LoginException;

import com.google.common.collect.Maps;

import me.bartvv.testbot.data.DataStorage;
import me.bartvv.testbot.data.JSONStorage;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.listener.CommandListener;
import me.bartvv.testbot.listener.MemberJoinLeaveListener;
import me.bartvv.testbot.listener.PrivateMSGListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordBot {

	/*
	 * On bootup of the bot, this will only do the necesarry things.
	 * All other things will be handled per-guild.
	 */

	private static JDA jda;
	private static DataStorage dataStorage;
	private static Map< Long, GuildHandler > guildHandlers;

	public static void main( String[] args ) {
		JDABuilder builder = new JDABuilder( AccountType.BOT );
		builder.setGame( Game.of( "Creating a bot" ) );
		builder.setToken( Utils.getToken() );
		builder.setAutoReconnect( true );
		builder.setStatus( OnlineStatus.ONLINE );

		try {
			jda = builder.buildBlocking();
		} catch ( LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e ) {
			e.printStackTrace();
			return;
		}

		dataStorage = new JSONStorage();

		guildHandlers = Maps.newHashMap();

		for ( Guild guild : jda.getGuilds() ) {
			guildHandlers.put( guild.getIdLong(), new GuildHandler( jda, guild ) );
		}

		jda.addEventListener( new CommandListener() );
		jda.addEventListener( new PrivateMSGListener() );
		jda.addEventListener( new MemberJoinLeaveListener() );
	}

	public static DataStorage getDataStorage() {
		return dataStorage;
	}

	public static void joinGuild( Guild guild ) {
		guildHandlers.put( guild.getIdLong(), new GuildHandler( jda, guild ) );
	}

	public static void leaveGuild( Guild guild ) {
		guildHandlers.remove( guild.getIdLong(), guildHandlers.get( guild.getIdLong() ) );
	}

	public static GuildHandler getGuildHandler( long id ) {
		return guildHandlers.get( id );
	}
}
