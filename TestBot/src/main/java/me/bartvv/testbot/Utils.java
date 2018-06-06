package me.bartvv.testbot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Utils {

	private static String token = "Your-token-here";
	private static String command = "!";
	private static long activityChannel = 453992903321190404L;

	public static String getToken() {
		return token;
	}

	public static String getCommand() {
		return command;
	}

	public static long getActivityChannel() {
		return activityChannel;
	}

	public static boolean checkCommand( String message, String command ) {
		return message.toLowerCase().startsWith( Utils.command + command + " " );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed ) {
		sendMessage( channel, messageEmbed, 10 );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed, int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( messageEmbed ).complete();
		} else {
			channel.sendMessage( messageEmbed ).complete().delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS );
		}
	}

	public static void sendMessage( MessageChannel channel, String message ) {
		sendMessage( channel, message, 10 );
	}

	public static void sendMessage( MessageChannel channel, String message, int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( message ).complete();
		} else {
			channel.sendMessage( message ).complete().delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS );
		}
	}

	public static EmbedBuilder createDefaultBuilder() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor( new Color( 51, 204, 255 ) );
		return embedBuilder;
	}
}
