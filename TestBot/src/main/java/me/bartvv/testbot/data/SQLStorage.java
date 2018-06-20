package me.bartvv.testbot.data;

import me.bartvv.testbot.guild.GuildHandler;

public class SQLStorage implements DataStorage {

	@Override
	public void saveGuildData( GuildHandler guildHandler ) {
		throw new UnsupportedOperationException( "SQLStorage is not yet supported." );
	}

	@Override
	public void init( GuildHandler guildHandler ) {
		throw new UnsupportedOperationException( "SQLStorage is not yet supported." );
	}

}
