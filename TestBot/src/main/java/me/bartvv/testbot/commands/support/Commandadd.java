package me.bartvv.testbot.commands.support;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.commands.ChannelType;
import me.bartvv.testbot.commands.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.PermissionOverrideAction;

public class Commandadd implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();
		Member member = e.getMember();
		ChannelType channelType = ChannelType.getChannelType( channel );
		if ( channelType == null || channelType != ChannelType.SUPPORT_CATEGORY ) {
			Utils.sendMessage( channel, "Cannot add a member to this channel" );
			return;
		} else if ( args.length != 2 ) {
			EmbedBuilder builder = Utils.createDefaultBuilder();
			builder.addField( "Wrong usage", getUsage(), true );
			Utils.sendMessage( channel, builder.build() );
			return;
		}

		User target = e.getMessage().getMentionedUsers().isEmpty() ? null : e.getMessage().getMentionedUsers().get( 0 );
		if ( target == null ) {
			Utils.sendMessage( channel, member.getAsMention() + " user not found" );
			return;
		}
		Member targetMember = e.getGuild().getMember( target );
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
				EmbedBuilder builder = Utils.createDefaultBuilder();
				builder.addField( "Added " + target.getName() + " to the support-ticket", "", false );
				channel.sendMessage( builder.build() ).queue();
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
