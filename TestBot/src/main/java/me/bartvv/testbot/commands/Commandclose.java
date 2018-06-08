package me.bartvv.testbot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
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
		channel.sendMessage( "Deleting this channel in 10s" ).queue();

		channelsBeingRemoved.add( channelName );
		channel.delete().queueAfter( 10, TimeUnit.SECONDS, new Consumer< Void >() {
			@Override
			public void accept( Void t ) {
				channelsBeingRemoved.remove( channelName );
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
}
