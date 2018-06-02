package me.bartvv.testbot;

import javax.security.auth.login.LoginException;

import me.bartvv.testbot.listener.CommandListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DiscordBot {

	private static JDA jda;

	public static void main( String[] args ) {
		JDABuilder builder = new JDABuilder( AccountType.BOT );
		builder.setToken( Utils.getToken() );
		builder.setAutoReconnect( true );
		builder.setStatus( OnlineStatus.ONLINE );

		try {
			jda = builder.buildBlocking();
		} catch ( LoginException | InterruptedException | RateLimitedException e ) {
			e.printStackTrace();
		}
		jda.addEventListener( new CommandListener() );
	}
}
