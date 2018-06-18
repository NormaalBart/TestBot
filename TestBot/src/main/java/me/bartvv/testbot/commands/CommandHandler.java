package me.bartvv.testbot.commands;

import java.util.HashMap;
import java.util.Map;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.commands.support.Commandsupport;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

public class CommandHandler {

	private Map< String, ICommand > commands;

	public Map< String, ICommand > getCommands() {
		return commands;
	}

	public CommandHandler() {
		commands = new HashMap<>();
		commands.put( "help", new Commandhelp( this ) );
		commands.put( "kick", new Commandkick() );
		commands.put( "online", new Commandonline() );
		commands.put( "ping", new Commandping() );
		commands.put( "clear", new Commandclear() );
		commands.put( "here", new Commandhere() );
		commands.put( "support", new Commandsupport( this ) );
		commands.put( "close", new Commandclose() );
		commands.put( "prime", new Commandprime() );

		commands.values().forEach(
				iCommand -> iCommand.getAliases().forEach( alias -> commands.put( alias.toLowerCase(), iCommand ) ) );
	}

	public void processCommand( GuildMessageReceivedEvent e ) {
		Message message = e.getMessage();
		String messageString = message.getRawContent();
		String commandString = messageString.split( " " )[ 0 ].replace( Utils.getCommand(), "" );
		TextChannel channel = e.getChannel();

		ICommand command = commands.get( commandString.toLowerCase() );
		if ( command == null ) {
			User user = e.getAuthor();
			Utils.sendMessage( channel, user.getAsMention() + " this is not a command type `" + Utils.getCommand()
					+ "help` for the commands " );
			return;
		}
		command.onCommand( e );
		try {
			message.delete().complete();
		} catch ( ErrorResponseException ere ) {
			// Do nothing because the message is already deleted :)
		}
	}
}
