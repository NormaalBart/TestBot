package me.bartvv.testbot.commands;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandpurge implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		Member member = e.getMember();
		if ( !member.hasPermission( Permission.MESSAGE_MANAGE ) )
			return;

		TextChannel channel = e.getChannel();
		String[] args = e.getMessage().getRawContent().split( " " );

		if ( args.length != 2 ) {
			EmbedBuilder builder = Utils.createDefaultBuilder();
			builder.addField( "Wrong usage", getUsage(), true );
			Utils.sendMessage( channel, builder.build() );
			return;
		}

		else if ( !Utils.isInt( args[ 1 ] ) ) {
			Utils.sendMessage( channel, member.getAsMention() + ", " + args[ 1 ] + " is not a number" );
			return;
		}

		int amount = Utils.getInt( args[ 1 ] );

		if ( amount < 2 || amount > 100 ) {
			Utils.sendMessage( channel,
					member.getAsMention() + ", messages to delete has to be between the 2 and 100" );
			return;
		}

		e.getMessage().delete().complete();

		channel.getHistory().retrievePast( amount ).queue( new Consumer< List< Message > >() {

			@Override
			public void accept( List< Message > messages ) {
				if ( messages.size() < 2 ) {
					Utils.sendMessage( channel, "Not enough messasges." );
					return;
				}
				channel.deleteMessages( messages ).queue( new Consumer< Void >() {

					@Override
					public void accept( Void t ) {
						Utils.sendMessage( channel, "Deleted " + messages.size() + " messages" );
						return;
					}
				} );
			}
		} );

	}

	@Override
	public String getDescription() {
		return "Purges the amount of messages specified.";
	}

	@Override
	public String getUsage() {
		return "Purge <amount>";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.ALL );
	}

	@Override
	public List< String > getAliases() {
		return Lists.newArrayList( "clear" );
	}
}
