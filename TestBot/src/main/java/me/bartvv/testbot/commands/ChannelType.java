package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.entities.TextChannel;

public enum ChannelType {

	BOT_CHANNEL, SUPPORT_CATEGORY, SUPPORT_CHANNEL, ACTIVITY_CHANNEL;

	public static ChannelType getChannelType( long id) {
		if ( id == Utils.getActivityChannel() )
			return ChannelType.ACTIVITY_CHANNEL;
		if ( id == Utils.getBotChannel() )
			return ChannelType.BOT_CHANNEL;
		if ( id == Utils.getSupportCategory() )
			return ChannelType.SUPPORT_CATEGORY;
		if ( id == Utils.getSupportChannel() )
			return ChannelType.SUPPORT_CHANNEL;
		
		// default
		return null;
	}
}
