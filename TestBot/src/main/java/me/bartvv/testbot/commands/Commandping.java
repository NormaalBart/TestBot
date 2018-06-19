package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandping implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
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
