package me.bartvv.testbot.commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

	public void onCommand( GuildMessageReceivedEvent e );

	public String getDescription();

	public String getUsage();

}
