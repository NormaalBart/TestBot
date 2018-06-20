package me.bartvv.testbot.guild.commands;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandping implements ICommand {
	
	private GuildHandler guildHandler;
	
	public Commandping( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		TextChannel channel = e.getChannel();
		Utils.sendMessage( channel, e.getMember().getAsMention() + ", the connection between the bot and server is "
				+ channel.getJDA().getPing() + "MS" );
	}

	@Override
	public String getDescription() {
		return "Check the ping between the bot and the server";
	}

	@Override
	public String getUsage() {
		return "Ping";
	}
}
