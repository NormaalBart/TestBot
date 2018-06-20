package me.bartvv.testbot.listener;

import me.bartvv.testbot.DiscordBot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildJoinLeaveListener extends ListenerAdapter {

	@Override
	public void onGuildJoin( GuildJoinEvent e ) {
		Guild guild = e.getGuild();
		DiscordBot.joinGuild( guild );
	}

	@Override
	public void onGuildLeave( GuildLeaveEvent e ) {
		Guild guild = e.getGuild();
		DiscordBot.leaveGuild( guild );
	}
}
