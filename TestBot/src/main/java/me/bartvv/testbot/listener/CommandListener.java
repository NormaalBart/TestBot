package me.bartvv.testbot.listener;

import java.util.ArrayList;
import java.util.List;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.commands.CommandHandler;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private CommandHandler commandHandler;
	private List< String > exclusions;

	public CommandListener() {
		this.commandHandler = new CommandHandler();
		this.exclusions = new ArrayList<>();
		this.exclusions.add( Utils.getCommand() + "close" );
		this.exclusions.add( Utils.getCommand() + "support" );
	}

	@Override
	public void onGuildMessageReceived( GuildMessageReceivedEvent e ) {
		Message message = e.getMessage();
		String rawMessage = message.getRawContent();
		TextChannel channel = e.getChannel();
		TextChannel botChannel = e.getGuild().getTextChannelById( Utils.getBotChannel() );
		User user = e.getAuthor();

		// Make sure it isn't bot spamming
		if ( user.isBot() )
			return;

		if ( channel.getIdLong() == Utils.getBotChannel() ) {
			if ( rawMessage.startsWith( Utils.getCommand() ) ) {
				this.commandHandler.processCommand( e );
				return;
			}

			message.delete().complete();
			Utils.sendMessage( channel, "This is not a command, please only use commands in this channel" );
			return;
		} else {
			String[] args = rawMessage.split( " " );
			if ( args.length >= 1 && this.exclusions.contains( args[ 0 ].toLowerCase() ) ) {
				this.commandHandler.processCommand( e );
				return;
			}

			if ( rawMessage.startsWith( Utils.getCommand() ) ) {
				message.delete().complete();
				Utils.sendMessage( channel, "Please refrain from using other channel's then the "
						+ botChannel.getAsMention() + " channel, " + user.getAsMention() );
				return;
			}
		}
	}
}
