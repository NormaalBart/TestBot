package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.entities.Channel;

public enum ChannelType {

	BOT_CHANNEL, SUPPORT_CATEGORY, SUPPORT_CHANNEL, ACTIVITY_CHANNEL;

	public static ChannelType getChannelType( Channel channel ) {
		if ( channel.getIdLong() == Utils.getActivityChannel() )
			return ChannelType.ACTIVITY_CHANNEL;
		if ( channel.getIdLong() == Utils.getBotChannel() )
			return ChannelType.BOT_CHANNEL;
		if ( channel.getParent().getIdLong() == Utils.getSupportCategory() )
			return ChannelType.SUPPORT_CATEGORY;
		if ( channel.getIdLong() == Utils.getSupportChannel() )
			return ChannelType.SUPPORT_CHANNEL;

		// default
		return null;
	}
}
