package me.bartvv.testbot.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandclose implements ICommand {

	private transient List< String > channelsBeingRemoved;

	public Commandclose() {
		this.channelsBeingRemoved = new ArrayList<>();
	}

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		TextChannel channel = e.getChannel();
		final String channelName = channel.getName();
		if ( !channelName.startsWith( "support-" ) ) {
			Utils.sendMessage( channel, "You cannot close a non-support ticket" );
			return;
		}
		Member member = e.getMember();
		String memberName = member.getUser().getName();
		if ( !channelName.toLowerCase().startsWith( "support-" + memberName.toLowerCase() )
				&& !member.hasPermission( Permission.MANAGE_CHANNEL ) ) {
			Utils.sendMessage( channel, "You cannot delete this support channel, " + member.getAsMention() );
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
