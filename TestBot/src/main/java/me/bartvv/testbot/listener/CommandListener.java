package me.bartvv.testbot.listener;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.commands.ChannelType;
import me.bartvv.testbot.commands.CommandHandler;
import me.bartvv.testbot.commands.ICommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private CommandHandler commandHandler;

	public CommandListener() {
		this.commandHandler = new CommandHandler();
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
			String commandString = rawMessage.split( " " )[ 0 ].replace( Utils.getCommand(), "" );

			ICommand command = this.commandHandler.getCommands().get( commandString.toLowerCase() );
			ChannelType channelType = ChannelType.getChannelType( channel.getIdLong() );

			if ( command != null && command.getChannelTypes().contains( channelType ) ) {
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
