package me.bartvv.testbot.commands;

import java.util.List;
import java.util.function.Consumer;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

public class Commandsupport implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		TextChannel textChannel = e.getChannel();
		if ( textChannel.getIdLong() != Utils.getSupportChannel()
				&& textChannel.getIdLong() != Utils.getBotChannel() ) {
			e.getMessage().delete().complete();
			TextChannel botChannel = e.getGuild().getTextChannelById( Utils.getBotChannel() );
			Utils.sendMessage( textChannel, "Please refrain from using other channel's then the " + botChannel
					+ " channel, " + e.getAuthor().getAsMention() );
			return;
		}

		Guild guild = e.getGuild();
		User user = e.getAuthor();
		Category category = guild.getCategoryById( Utils.getSupportCategory() );
		List< TextChannel > channel = guild.getTextChannelsByName( "support-" + user.getName(), true );
		if ( channel == null || channel.isEmpty() ) {
			ChannelAction channelAction = category.createTextChannel( "support-" + user.getName() );
			channelAction.setTopic( "Support channel for " + user.getName() );
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
		Utils.sendMessage( e.getChannel(), "You cannot create more then one support channel." );
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
}
