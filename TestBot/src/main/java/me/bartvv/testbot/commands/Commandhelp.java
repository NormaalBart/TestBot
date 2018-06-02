package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandhelp implements ICommand {

	CommandHandler commandHandler;

	public Commandhelp( CommandHandler commandHandler ) {
		this.commandHandler = commandHandler;
	}

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		EmbedBuilder embed = Utils.createDefaultBuilder();
		embed.addField( "Help menu", "", false );
		embed.addField( "[] = Optional, <> = Required", "", false );
		embed.addField( "Commands: ", "", false );
		for ( ICommand iCommand : this.commandHandler.getCommands() ) {
			embed.addField( "**" + Utils.getCommand() + iCommand.getUsage() + ":**", iCommand.getDescription(), false );
		}
		TextChannel channel = e.getChannel();
		Utils.sendMessage( channel, embed.build() );
	}

	@Override
	public String getDescription() {
		return "The general help command";
	}

	@Override
	public String getUsage() {
		return "Help";
	}

}
