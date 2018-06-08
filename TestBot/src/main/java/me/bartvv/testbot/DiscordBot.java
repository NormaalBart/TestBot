package me.bartvv.testbot;

import javax.security.auth.login.LoginException;

import me.bartvv.testbot.listener.CommandListener;
import me.bartvv.testbot.listener.GuildJoin;
import me.bartvv.testbot.listener.PrivateMSGListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordBot {

	private static JDA jda;

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
		}
		jda.addEventListener( new CommandListener() );
		jda.addEventListener( new PrivateMSGListener() );
		jda.addEventListener( new GuildJoin() );
	}
}
