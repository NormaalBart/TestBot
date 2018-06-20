package me.bartvv.testbot.guild.commands;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

	public void onCommand( User user, GuildMessageReceivedEvent e );

	public String getDescription();

	public String getUsage();

	public default List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.BOT_CHANNEL );
	}

	public default List< String > getAliases() {
		return Collections.emptyList();
	}

}
