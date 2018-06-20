package me.bartvv.testbot.guild.commands;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandhere implements ICommand {

	private static final long TIME_BETWEEN_HERE = TimeUnit.HOURS.toMillis( 24 );
	private GuildHandler guildHandler;

	public Commandhere( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		TextChannel channel = e.getChannel();
		long cooldown = user.getLastHere();

		if ( System.currentTimeMillis() - cooldown < TIME_BETWEEN_HERE ) {
			cooldown = System.currentTimeMillis() - cooldown;
			cooldown = TIME_BETWEEN_HERE - cooldown;
			long hours = TimeUnit.MILLISECONDS.toHours( cooldown );
			cooldown = cooldown - TimeUnit.HOURS.toMillis( hours );
			long mins = TimeUnit.MILLISECONDS.toMinutes( cooldown );
			cooldown = cooldown - TimeUnit.MINUTES.toMillis( mins );
			long secs = TimeUnit.MILLISECONDS.toSeconds( cooldown );
			cooldown = cooldown - TimeUnit.SECONDS.toMillis( secs );
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

		EmbedBuilder builder = Utils.createDefaultBuilder();
		builder.addField( "User marked!", user.getAsMention() + " marked himself!", false );
		TextChannel activiyChannel = e.getGuild().getTextChannelById( this.guildHandler.getChannel().ACTIVITY_CHANNEL );
		builder.setTimestamp( Instant.now() );
		Utils.sendMessage( activiyChannel, builder.build(), -1 );
		user.setLastHere( System.currentTimeMillis() );
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
