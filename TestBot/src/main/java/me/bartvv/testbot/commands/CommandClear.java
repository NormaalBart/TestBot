package me.bartvv.testbot.commands;

import java.util.List;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandClear implements ICommand {

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

		int amount;
		try {
			amount = Integer.parseInt( args[ 1 ] );
		} catch ( NumberFormatException nfe ) {
			Utils.sendMessage( channel, member.getAsMention() + ", " + args[ 1 ] + " is not a number " );
			return;
		}

		if ( amount < 2 || amount > 100 ) {
			Utils.sendMessage( channel,
					member.getAsMention() + ", number of messages deleted must be between 2 and 100." );
			return;
		}
		e.getMessage().delete();
		List< Message > history;
		history = channel.getHistory().retrievePast( amount ).complete();
		try {
			channel.deleteMessages( history );
		} catch ( IllegalArgumentException iae ) {}

		Utils.sendMessage( channel, "Deleted " + history.size() + " messages" );
		return;
	}

	@Override
	public String getDescription() {
		return "Clear's the amount of messages specified.";
	}

	@Override
	public String getUsage() {
		return "Clear <amount>";
	}

}
