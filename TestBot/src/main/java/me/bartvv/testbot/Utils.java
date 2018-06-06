package me.bartvv.testbot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Utils {

	private static String token = "NDUyMTY2NzM5NTI2OTQyNzI2.DfMemA.PrWknRmmHtWOgkEsrr_HP6Z64IU";
	private static String command = "!";
	private static long activityChannel = 453992903321190404L;
	private static long botChannel = 452556595461619722L;

	public static String getToken() {
		return token;
	}

	public static String getCommand() {
		return command;
	}

	public static long getActivityChannel() {
		return activityChannel;
	}

	public static long getBotChannel() {
		return botChannel;
	}

	public static boolean checkCommand( String message, String command ) {
		return message.toLowerCase().startsWith( Utils.command + command + " " );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed ) {
		sendMessage( channel, messageEmbed, 10 );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed, int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( messageEmbed ).queue();
		} else {
			channel.sendMessage( messageEmbed ).queue( new Consumer< Message >() {

				@Override
				public void accept( Message message ) {
					message.delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS );
				}
			} );
		}
	}

	public static void sendMessage( MessageChannel channel, String message ) {
		sendMessage( channel, message, 10 );
	}

	public static void sendMessage( MessageChannel channel, String message, int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( message ).queue();
		} else {
			channel.sendMessage( message ).queue( new Consumer< Message >() {

				@Override
				public void accept( Message message ) {
					message.delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS );
				}
			} );
		}
	}

	public static EmbedBuilder createDefaultBuilder() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor( new Color( 51, 204, 255 ) );
		return embedBuilder;
	}
}
