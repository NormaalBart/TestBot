package me.bartvv.testbot.enums;

import net.dv8tion.jda.core.entities.Channel;

public enum ChannelType {

	
	// Text channels
	ACTIVITY_CHANNEL( 453992903321190404L ), 
	BOT_CHANNEL( 452556595461619722L ), 
	SUPPORT_CHANNEL( 452556595461619722L ), 
	JOIN_CHANNEL( 454656658661179392L ),
	ALL( 0 ),
	
	// Categories
	SUPPORT_CATEGORY( 452541895407697940L );

	private long channelID;

	private ChannelType( long channelID ) {
		this.channelID = channelID;
	}

	public long getID() {
		return this.channelID;
	}

	public static ChannelType getChannelType( Channel channel ) {
		if ( channel.getIdLong() == ChannelType.ACTIVITY_CHANNEL.getID() )
			return ChannelType.ACTIVITY_CHANNEL;
		else if ( channel.getIdLong() == ChannelType.BOT_CHANNEL.getID() )
			return ChannelType.BOT_CHANNEL;
		else if ( channel.getIdLong() == ChannelType.SUPPORT_CHANNEL.getID() )
			return ChannelType.SUPPORT_CHANNEL;
		else if( channel.getIdLong() == ChannelType.JOIN_CHANNEL.getID()) 
			return ChannelType.JOIN_CHANNEL;
		else if ( channel.getParent().getIdLong() == ChannelType.SUPPORT_CATEGORY.getID() )
			return ChannelType.SUPPORT_CATEGORY;

		// If it is not a specific channel, just return all
		return ChannelType.ALL;
	}

}
