package me.bartvv.testbot.listener;

import me.bartvv.testbot.DiscordBot;
import me.bartvv.testbot.guild.GuildHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemberJoinLeaveListener extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin( GuildMemberJoinEvent e ) {
		Guild guild = e.getGuild();
		GuildHandler guildHandler = DiscordBot.getGuildHandler( guild.getIdLong() );
		guildHandler.getUserHandler().addUser( e.getUser() );
	}

	@Override
	public void onGuildMemberLeave( GuildMemberLeaveEvent e ) {
		Guild guild = e.getGuild();
		GuildHandler guildHandler = DiscordBot.getGuildHandler( guild.getIdLong() );
		guildHandler.getUserHandler().removeUser( e.getUser() );
	}

}
