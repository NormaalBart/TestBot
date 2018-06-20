package me.bartvv.testbot.guild.commands.support;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import me.bartvv.testbot.guild.commands.CommandHandler;
import me.bartvv.testbot.guild.commands.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

public class Commandsupport implements ICommand {

	private GuildHandler guildHandler;

	public Commandsupport( GuildHandler guildHandler, CommandHandler commandHandler ) {
		this.guildHandler = guildHandler;
		commandHandler.getCommands().put( "add", new Commandadd( this.guildHandler ) );
		commandHandler.getCommands().put( "remove", new Commandremove( this.guildHandler ) );

	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		Guild guild = e.getGuild();
		Category category = guild.getCategoryById( this.guildHandler.getChannel().SUPPORT_CATEGORY );
		List< TextChannel > channels = category.getTextChannels().stream().filter(
				channel -> channel.getName().toLowerCase().startsWith( "support-" + user.getName().toLowerCase() ) )
				.collect( Collectors.toList() );

		String channelName = "support-" + user.getName() + "-"
				+ ( ( channels == null || channels.isEmpty() ) ? 1 : ( channels.size() + 1 ) );

		ChannelAction channelAction = category.createTextChannel( channelName );
		channelAction.setTopic( "Support channel for " + user.getName() );
		List< Permission > permissions = Lists.newArrayList();
		permissions.add( Permission.VIEW_CHANNEL );
		permissions.add( Permission.MESSAGE_READ );
		permissions.add( Permission.MESSAGE_ADD_REACTION );
		permissions.add( Permission.MESSAGE_WRITE );
		permissions.add( Permission.MESSAGE_ATTACH_FILES );
		permissions.add( Permission.MESSAGE_HISTORY );
		channelAction.addPermissionOverride( e.getMember(), permissions, Lists.newArrayList() );
		channelAction.queue( new Consumer< Channel >() {

			@Override
			public void accept( Channel channel ) {
				TextChannel textChannel = ( TextChannel ) channel;
				textChannel.sendMessage( "Please ask the question so Support can help you, " + user.getAsMention() )
						.queue();
			}
		} );
		return;
	}

	@Override
	public String getDescription() {
		return "Creates a channel for you to get support";
	}

	@Override
	public String getUsage() {
		return "Support";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.BOT_CHANNEL, ChannelType.SUPPORT_CHANNEL );
	}
}
