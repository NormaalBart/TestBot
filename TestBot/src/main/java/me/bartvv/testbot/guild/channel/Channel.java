package me.bartvv.testbot.guild.channel;

public class Channel {

	// Text channels
	public long ACTIVITY_CHANNEL = 453992903321190404L;
	public long BOT_CHANNEL = 452556595461619722L;
	public long SUPPORT_CHANNEL = 452556595461619722L;
	public long JOIN_CHANNEL = 454656658661179392L;
	public final long ALL = 0;

	// Categories
	public final long SUPPORT_CATEGORY = 452541895407697940L;

	public ChannelType getChannelType( net.dv8tion.jda.core.entities.Channel channel ) {
		if ( channel.getIdLong() == ACTIVITY_CHANNEL )
			return ChannelType.ACTIVITY_CHANNEL;
		else if ( channel.getIdLong() == BOT_CHANNEL )
			return ChannelType.BOT_CHANNEL;
		else if ( channel.getIdLong() == SUPPORT_CHANNEL )
			return ChannelType.SUPPORT_CHANNEL;
		else if ( channel.getIdLong() == JOIN_CHANNEL )
			return ChannelType.JOIN_CHANNEL;
		else if ( channel.getParent().getIdLong() == SUPPORT_CATEGORY )
			return ChannelType.SUPPORT_CATEGORY;

		// If it is not a specific channel, just return all
		return ChannelType.ALL;
	}

	public enum ChannelType {
		// Text channels
		ACTIVITY_CHANNEL, BOT_CHANNEL, SUPPORT_CHANNEL, JOIN_CHANNEL, ALL,

		// Categories
		SUPPORT_CATEGORY;
	}

}
