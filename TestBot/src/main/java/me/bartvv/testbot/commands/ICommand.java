package me.bartvv.testbot.commands;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import me.bartvv.testbot.enums.ChannelType;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

	public void onCommand( GuildMessageReceivedEvent e );

	public String getDescription();

	public String getUsage();

	public default List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.BOT_CHANNEL );
	}

	public default List< String > getAliases() {
		return Collections.emptyList();
	}

}
