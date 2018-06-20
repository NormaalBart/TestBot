package me.bartvv.testbot.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import me.bartvv.testbot.guild.GuildHandler;
import net.dv8tion.jda.core.entities.Guild;

public class JSONStorage implements DataStorage {

	@Override
	public void saveGuildData( GuildHandler guildHandler ) {
		Guild guild = guildHandler.getGuild();
		File directory = new File( File.separator + guild.getId() );

		try {
			Files.createDirectory( directory.toPath() );
		} catch ( FileAlreadyExistsException faee ) {
			// Don't do a shit.
		} catch ( IOException exception ) {
			exception.printStackTrace();
			return;
		}
		
		
	}

	@Override
	public void init( GuildHandler guildHandler ) {
		// TODO Auto-generated method stub

	}

}
