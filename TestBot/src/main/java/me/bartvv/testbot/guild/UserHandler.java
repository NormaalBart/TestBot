package me.bartvv.testbot.guild;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import net.dv8tion.jda.core.entities.Member;

public class UserHandler {

	private Map< Long, User > users;
	private GuildHandler guildHandler;

	public UserHandler( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
		this.users = Maps.newHashMap();

		this.guildHandler.getGuild().getMembers().forEach( member -> addUser( member.getUser() ) );
	}

	public User addUser( net.dv8tion.jda.core.entities.User user ) {
		User userGuild = new User( this.guildHandler.getGuild().getMember( user ) );
		users.put( user.getIdLong(), userGuild );
		return userGuild;
	}

	public void removeUser( net.dv8tion.jda.core.entities.User user ) {
		users.remove( user.getIdLong(), getUser( user.getIdLong() ) );
	}

	public User getUser( net.dv8tion.jda.core.entities.User user ) {
		if ( user == null )
			return null;
		return getUser( user.getIdLong() );
	}

	public User getUser( String name ) {
		if ( name == null )
			return null;
		List< Member > members = this.guildHandler.getGuild().getMembersByName( name, true );
		Member member = members.get( 0 );
		return getUser( member.getUser().getIdLong() );
	}

	public User getUser( long id ) {
		return users.get( id );
	}

}
