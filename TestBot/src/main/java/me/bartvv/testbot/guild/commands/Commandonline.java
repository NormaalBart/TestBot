package me.bartvv.testbot.guild.commands;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandonline implements ICommand {

	private GuildHandler guildHandler;

	public Commandonline( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		TextChannel channel = e.getChannel();
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
