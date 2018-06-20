package me.bartvv.testbot.listener;

import me.bartvv.testbot.DiscordBot;
import me.bartvv.testbot.guild.GuildHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived( GuildMessageReceivedEvent e ) {
		User user = e.getAuthor();

		// Make sure it isn't bot spamming
		if ( user.isBot() )
			return;

		Guild guild = e.getGuild();
		GuildHandler handler = DiscordBot.getGuildHandler( guild.getIdLong() );
		handler.getCommandHandler().processCommand( e );
		return;
	}
}
