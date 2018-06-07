package me.bartvv.testbot.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandHandler {

	private Map< String, ICommand > commands;

	public Collection< ICommand > getCommands() {
		return commands.values();
	}

	public CommandHandler() {
		commands = new HashMap<>();
		commands.put( "help", new Commandhelp( this ) );
		commands.put( "kick", new Commandkick() );
		commands.put( "online", new Commandonline() );
		commands.put( "ping", new Commandping() );
		commands.put( "clear", new CommandClear() );
		commands.put( "here", new Commandhere() );
		commands.put( "support", new Commandsupport() );
	}

	public void processCommand( GuildMessageReceivedEvent e ) {
		Message message = e.getMessage();
		String messageString = message.getRawContent();
		String commandString = messageString.split( " " )[ 0 ].replace( Utils.getCommand(), "" );
		TextChannel channel = e.getChannel();

		ICommand command = commands.get( commandString.toLowerCase() );
		message.delete().complete();
		if ( command == null ) {
			User user = e.getAuthor();
			Utils.sendMessage( channel, user.getAsMention() + " this is not a command type `" + Utils.getCommand()
					+ "help` for the commands " );
			return;
		}
		command.onCommand( e );
	}
}
