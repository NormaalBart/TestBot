package me.bartvv.testbot.guild;

import me.bartvv.testbot.guild.channel.Channel;
import me.bartvv.testbot.guild.commands.CommandHandler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

public class GuildHandler {

	private JDA jda;
	private Guild guild;
	private CommandHandler commandHandler;
	private Channel channel;
	private Options options;
	private UserHandler userHandler;

	public GuildHandler( JDA jda, Guild guild ) {
		this.commandHandler = new CommandHandler( this );
		this.jda = jda;
		this.guild = guild;
		this.channel = new Channel();
		this.options = new Options();
		this.userHandler = new UserHandler( this );
	}

	public JDA getJDA() {
		return jda;
	}

	public Guild getGuild() {
		return guild;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public Channel getChannel() {
		return channel;
	}

	public Options getOptions() {
		return options;
	}

	public UserHandler getUserHandler() {
		return userHandler;
	}

}
