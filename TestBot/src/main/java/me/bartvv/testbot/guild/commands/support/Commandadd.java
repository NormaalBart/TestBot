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
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.PermissionOverrideAction;

public class Commandadd implements ICommand {

	private GuildHandler guildHandler;

	public Commandadd( GuildHandler guildHandler ) {
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

		String targetName = e.getMessage().getRawContent().split( " " )[ 1 ];
		List< Member > targetMembers = e.getGuild().getMembersByName( targetName, true );
		if ( targetMembers == null || targetMembers.isEmpty() ) {
			Utils.sendMessage( channel, user.getAsMention() + " user not found" );
			return;
		}
		Member targetMember = targetMembers.get( 0 );
		if ( channel.canTalk( targetMember ) ) {
			Utils.sendMessage( channel, "This user is already in this channel" );
			return;
		}
		List< Permission > permissions = Lists.newArrayList();
		permissions.add( Permission.VIEW_CHANNEL );
		permissions.add( Permission.MESSAGE_READ );
		permissions.add( Permission.MESSAGE_ADD_REACTION );
		permissions.add( Permission.MESSAGE_WRITE );
		permissions.add( Permission.MESSAGE_ATTACH_FILES );
		permissions.add( Permission.MESSAGE_HISTORY );
		PermissionOverrideAction permissionOverrideAction = channel.createPermissionOverride( targetMember );
		permissionOverrideAction.setPermissions( permissions, Lists.newArrayList() );
		permissionOverrideAction.queue( new Consumer< PermissionOverride >() {

			@Override
			public void accept( PermissionOverride t ) {
				channel.sendMessage( "Added " + targetMember.getUser().getName() + " to the support-ticket" ).queue();
			}
		} );
	}

	@Override
	public String getDescription() {
		return "Adds a person to a ticket";
	}

	@Override
	public String getUsage() {
		return "Add <user>";
	}

	@Override
	public List< ChannelType > getChannelTypes() {
		return Lists.newArrayList( ChannelType.SUPPORT_CATEGORY );
	}
}
