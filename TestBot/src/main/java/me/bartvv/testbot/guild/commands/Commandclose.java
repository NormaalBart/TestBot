package me.bartvv.testbot.guild.commands;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandclose implements ICommand {

	private transient List< String > channelsBeingRemoved;
	private GuildHandler guildHandler;

	public Commandclose( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
		this.channelsBeingRemoved = Lists.newArrayList();
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		TextChannel channel = e.getChannel();
		final String channelName = channel.getName();
		if ( !channelName.startsWith( "support-" ) ) {
			Utils.sendMessage( channel, "You cannot close a non-support ticket" );
			return;
		}
		
		String userName = user.getName();
		if ( !channelName.toLowerCase().startsWith( "support-" + userName.toLowerCase() )
				&& !user.hasPermission( Permission.MANAGE_CHANNEL ) ) {
			Utils.sendMessage( channel,
					"You cannot delete this support channel, " + user.getAsMention() + ". Please ask staff." );
			return;
		}

		if ( this.channelsBeingRemoved.contains( channelName ) ) {
			channel.sendMessage( "This channel is already being removed" ).complete();
			return;
		}
		channelsBeingRemoved.add( channelName );
		channel.sendMessage( "Deleting this channel in 10s" ).queue( new Consumer< Message >() {

			private int countdown = 10;

			@Override
			public void accept( Message message ) {
				Timer timer = new Timer();
				timer.schedule( new TimerTask() {

					@Override
					public void run() {
						if ( editMessage( message ) == 0 ) {
							channelsBeingRemoved.remove( channelName );
							cancel();
							channel.delete().queue();
						}
					}
				}, new Date(), TimeUnit.SECONDS.toMillis( 1 ) );
			}

			private int editMessage( Message message ) {
				message.editMessage( "Deletings this channel in " + countdown + "s" ).complete();
				return countdown--;
			}

		} );
	}

	@Override
	public String getDescription() {
		return "Close a support ticket";
	}

	@Override
	public String getUsage() {
		return "Close";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.SUPPORT_CATEGORY );
	}
}
