package me.bartvv.testbot.guild.commands;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import me.bartvv.testbot.guild.commands.support.Commandsupport;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

public class CommandHandler {

	private GuildHandler guildHandler;
	private Map< String, ICommand > commands;

	public Map< String, ICommand > getCommands() {
		return commands;
	}

	public CommandHandler( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
		commands = Maps.newHashMap();

		commands.put( "help", new Commandhelp( this.guildHandler ) );
		commands.put( "kick", new Commandkick( this.guildHandler ) );
		commands.put( "online", new Commandonline( this.guildHandler ) );
		commands.put( "ping", new Commandping( this.guildHandler ) );
		commands.put( "purge", new Commandpurge( this.guildHandler ) );
		commands.put( "here", new Commandhere( this.guildHandler ) );
		commands.put( "support", new Commandsupport( this.guildHandler, this ) );
		commands.put( "close", new Commandclose( this.guildHandler ) );
		commands.put( "prime", new Commandprime( this.guildHandler ) );
		commands.put( "info", new Commandinfo( this.guildHandler ) );

		List< ICommand > iCommands = Lists.newArrayList( this.commands.values() );

		iCommands.stream().forEach( iCommand -> iCommand.getAliases()
				.forEach( alias -> this.commands.put( alias.toLowerCase(), iCommand ) ) );
	}

	public void processCommand( GuildMessageReceivedEvent e ) {
		Message message = e.getMessage();
		String messageString = message.getRawContent();
		TextChannel channel = e.getChannel();
		ChannelType channelType = this.guildHandler.getChannel().getChannelType( channel );

		if ( !messageString.startsWith( Utils.getCommand() ) ) {
			if ( channelType == ChannelType.BOT_CHANNEL && !this.guildHandler.getOptions().isMayChatInBotChannel() ) {
				message.delete().complete();
				Utils.sendMessage( channel, "This is not a command, please only use commands in this channel" );
			}
			return;
		}

		String commandString = messageString.split( " " )[ 0 ].replace( Utils.getCommand(), "" );

		ICommand command = commands.get( commandString.toLowerCase() );
		User user = this.guildHandler.getUserHandler().getUser( e.getAuthor() );

		if ( command == null ) {
			Utils.sendMessage( channel, user.getAsMention() + " this is not a command type `" + Utils.getCommand()
					+ "help` for the commands " );
			message.delete().complete();
			return;
		}

		if ( command.getChannelTypes().contains( channelType )
				|| command.getChannelTypes().contains( ChannelType.ALL ) ) {
			command.onCommand( user, e );
		} else {
			Utils.sendMessage( channel, "This command cannot be used in this channel." );
		}

		try {
			message.delete().complete();
		} catch ( ErrorResponseException ere ) {
			// Do nothing because the message is already deleted :)
		}
	}
}
