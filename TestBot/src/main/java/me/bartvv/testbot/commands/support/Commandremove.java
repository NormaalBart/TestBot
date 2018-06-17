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

public class Commandremove implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();
		Member member = e.getMember();
		if ( args.length != 2 ) {
			EmbedBuilder builder = Utils.createDefaultBuilder();
			builder.addField( "Wrong usage", getUsage(), true );
			Utils.sendMessage( channel, builder.build() );
			return;
		}

		List< User > users = e.getMessage().getMentionedUsers();
		if ( users == null || users.isEmpty() ) {
			Utils.sendMessage( channel, member.getAsMention() + " user not found" );
			return;
		}
		Member targetMember = e.getGuild().getMember( users.get( 0 ) );
		if ( !channel.canTalk( targetMember ) ) {
			Utils.sendMessage( channel, "This user isn't in this channel." );
			return;
		}
		PermissionOverride permissionOverrideAction = channel.getPermissionOverride( targetMember );
		permissionOverrideAction.delete().queue( new Consumer< Void >() {

			@Override
			public void accept( Void t ) {
				EmbedBuilder builder = Utils.createDefaultBuilder();
				builder.addField( "Removed " + targetMember.getUser().getName() + " from the support-ticket", "",
						false );
				channel.sendMessage( builder.build() ).queue();
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
