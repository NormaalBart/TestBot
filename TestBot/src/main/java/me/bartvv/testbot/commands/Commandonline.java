package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandonline implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		Message message = e.getMessage();
		message.delete();
		MessageChannel channel = e.getChannel();
		int online = ( int ) e.getGuild().getMembers().stream()
				.filter( member -> member.getOnlineStatus() == OnlineStatus.ONLINE && !member.getUser().isBot() )
				.count();
		Utils.sendMessage( channel, "Their " + ( online > 1 ? "are" : "is" ) + " currently " + online + " "
				+ ( online > 1 ? "members" : "member" ) + " online" );
	}

	@Override
	public String getDescription() {
		return "Shows the online count (excludes bots)";
	}

	@Override
	public String getUsage() {
		return "Online";
	}

}
