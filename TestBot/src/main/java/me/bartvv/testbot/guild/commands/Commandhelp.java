package me.bartvv.testbot.guild.commands;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandhelp implements ICommand {

	private GuildHandler guildHandler;

	public Commandhelp( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		EmbedBuilder embed = Utils.createDefaultBuilder();
		embed.setDescription( "----- **TestBot** -----" + "\n" + "[] = Optional, <> = Required" + "\n" );
		StringBuilder builder = new StringBuilder();
		for ( ICommand iCommand : this.guildHandler.getCommandHandler().getCommands().values() ) {
			builder.append( "`" + Utils.getCommand() + iCommand.getUsage() + "`: " + iCommand.getDescription() );
			builder.append( "\n" );
		}
		embed.addField( "Commands: ", builder.toString(), false );
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
