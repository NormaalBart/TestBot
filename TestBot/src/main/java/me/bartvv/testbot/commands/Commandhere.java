package me.bartvv.testbot.commands;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandhere implements ICommand {

	private Map< String, Long > cooldowns;
	private static final long TIME_BETWEEN_HERE = TimeUnit.HOURS.toMillis( 24 );

	public Commandhere() {
		this.cooldowns = Maps.newHashMap();
	}

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		User user = e.getAuthor();
		TextChannel channel = e.getChannel();
		String id = user.getId();

		if ( this.cooldowns.containsKey( id ) ) {
			if ( System.currentTimeMillis() - this.cooldowns.get( id ) < TIME_BETWEEN_HERE ) {
				long lastHere = System.currentTimeMillis() - this.cooldowns.get( id );
				lastHere = TIME_BETWEEN_HERE - lastHere;
				long hours = TimeUnit.MILLISECONDS.toHours( lastHere );
				lastHere = lastHere - TimeUnit.HOURS.toMillis( hours );
				long mins = TimeUnit.MILLISECONDS.toMinutes( lastHere );
				lastHere = lastHere - TimeUnit.MINUTES.toMillis( mins );
				long secs = TimeUnit.MILLISECONDS.toSeconds( lastHere );
				lastHere = lastHere - TimeUnit.SECONDS.toMillis( secs );
				String hour, minute, second;
				hour = hours + ( hours > 1 ? " hours" : " hour" );
				minute = mins + ( mins > 1 ? " minutes" : " minute" );
				second = secs + ( secs > 1 ? " seconds" : " second" );
				EmbedBuilder builder = Utils.createDefaultBuilder();
				builder.addField( "You already marked yourself in the past 24 hours,",
						"Come back in " + hour + ", " + minute + " and " + second, false );
				Utils.sendMessage( channel, builder.build() );
				return;
			}
		}

		EmbedBuilder builder = Utils.createDefaultBuilder();
		builder.addField( "User marked!", user.getAsMention() + " marked himself!", false );
		TextChannel activiyChannel = e.getGuild().getTextChannelById( Utils.getActivityChannel() );
		builder.setTimestamp( Instant.now() );
		Utils.sendMessage( activiyChannel, builder.build(), -1 );
		this.cooldowns.put( id, System.currentTimeMillis() );
	}

	@Override
	public String getDescription() {
		return "Staff command for saying you are here.";
	}

	@Override
	public String getUsage() {
		return "Here";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.BOT_CHANNEL, ChannelType.ACTIVITY_CHANNEL );
	}

}
