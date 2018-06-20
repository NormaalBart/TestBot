package me.bartvv.testbot.data;

import me.bartvv.testbot.guild.GuildHandler;

public interface DataStorage {

	public void saveGuildData( GuildHandler guildHandler );

	public void init( GuildHandler guildHandler );

}