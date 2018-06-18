package me.bartvv.testbot;

import java.awt.Color;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Utils {

	private static String token = "Put-your-token-here";
	private static String command = "!";
	private static long activityChannel = 453992903321190404L;
	private static long botChannel = 452556595461619722L;
	private static long supportCategory = 452541895407697940L;
	private static long supportChannel = 452541677450821642L;
	private static long joinChannel = 454656658661179392L;

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

	public static long getSupportCategory() {
		return supportCategory;
	}

	public static long getSupportChannel() {
		return supportChannel;
	}

	public static long getJoinChannel() {
		return joinChannel;
	}

	public static boolean checkCommand( String message, String command ) {
		return message.toLowerCase().startsWith( Utils.command + command + " " );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed ) {
		sendMessage( channel, messageEmbed, 10 );
	}

	public static void sendMessage( MessageChannel channel, MessageEmbed messageEmbed, final int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( messageEmbed ).queue();
		} else {
			channel.sendMessage( messageEmbed ).queue( new Consumer< Message >() {

				@Override
				public void accept( Message message ) {
					message.delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS, null,
							new Consumer< Throwable >() {

								public void accept( Throwable t ) {
									return;
								}

							} );
				}
			} );
		}
	}

	public static void sendMessage( MessageChannel channel, String message ) {
		sendMessage( channel, message, 10 );
	}

	public static void sendMessage( MessageChannel channel, String message, final int secondsAfterDelete ) {
		if ( secondsAfterDelete < 0 ) {
			channel.sendMessage( message ).queue();
		} else {
			channel.sendMessage( message ).queue( new Consumer< Message >() {

				@Override
				public void accept( Message message ) {
					message.delete().queueAfter( secondsAfterDelete, TimeUnit.SECONDS, null,
							new Consumer< Throwable >() {

								public void accept( Throwable t ) {
									return;
								}

							} );
				}
			} );
		}
	}

	public static boolean isInt( String arg ) {
		try {
			Integer.parseInt( arg );
		} catch ( Exception e ) {
			return false;
		}
		return true;
	}

	public static int getInt( String arg ) {
		if ( isInt( arg ) ) {
			return Integer.parseInt( arg );
		}
		return Integer.MIN_VALUE;
	}

	public static EmbedBuilder createDefaultBuilder() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor( new Color( 51, 204, 255 ) );
		return embedBuilder;
	}
}
