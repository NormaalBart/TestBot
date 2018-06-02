package me.bartvv.testbot.listener;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.commands.CommandHandler;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private CommandHandler commandHandler;

	public CommandListener() {
		this.commandHandler = new CommandHandler();
	}

	@Override
	public void onGuildMessageReceived( GuildMessageReceivedEvent event ) {
		Message message = event.getMessage();
		if ( message.getRawContent().startsWith( Utils.getCommand() ) ) {
			this.commandHandler.processCommand( event );
		}
	}
}
