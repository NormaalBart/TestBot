package me.bartvv.testbot.guild.commands.support;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import me.bartvv.testbot.guild.channel.Channel.ChannelType;
import me.bartvv.testbot.guild.commands.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandremove implements ICommand {

	private GuildHandler guildHandler;

	public Commandremove( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();
		if ( args.length != 2 ) {
			EmbedBuilder builder = Utils.createDefaultBuilder();
			builder.addField( "Wrong usage", getUsage(), true );
			Utils.sendMessage( channel, builder.build() );
			return;
		}

		User target = this.guildHandler.getUserHandler().getUser(
				e.getMessage().getMentionedUsers().isEmpty() ? null : e.getMessage().getMentionedUsers().get( 0 ) );

		if ( target == null ) {
			Utils.sendMessage( channel, user.getAsMention() + " user not found" );
			return;
		}
		if ( !channel.canTalk( target.getMember() ) ) {
			Utils.sendMessage( channel, "This user isn't in this channel." );
			return;
		}
		PermissionOverride permissionOverrideAction = channel.getPermissionOverride( target.getMember() );
		permissionOverrideAction.delete().queue( new Consumer< Void >() {

			@Override
			public void accept( Void t ) {
				channel.sendMessage( "Removed " + target.getName() + " from the support-ticket" ).queue();
			}
		} );
	}

	@Override
	public String getDescription() {
		return "Removes a person from a ticket";
	}

	@Override
	public String getUsage() {
		return "Remove <user>";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.SUPPORT_CATEGORY );
	}

}
