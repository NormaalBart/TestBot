package me.bartvv.testbot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Utils {

	private static String token = "NDUyMTY2NzM5NTI2OTQyNzI2.DfMemA.PrWknRmmHtWOgkEsrr_HP6Z64IU";
	private static String command = "-";

	public static String getToken() {
		return token;
	}

	public static String getCommand() {
		return command;
	}

	public static boolean checkCommand( String message, String command ) {
		return message.toLowerCase().startsWith( Utils.command + command );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed ) {
		channel.sendMessage( messageEmbed ).complete().delete().queueAfter( 10, TimeUnit.SECONDS );
	}

	public static void sendMessage( MessageChannel channel, String message ) {
		channel.sendMessage( message ).complete().delete().queueAfter( 10, TimeUnit.SECONDS );
	}

	public static EmbedBuilder createDefaultBuilder() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor( new Color( 51, 204, 255 ) );
		return embedBuilder;
	}
}
