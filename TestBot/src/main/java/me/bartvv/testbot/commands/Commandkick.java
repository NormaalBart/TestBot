package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;

public class Commandkick implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		Member member = e.getMember();
		MessageChannel channel = e.getChannel();
		if ( !member.hasPermission( Permission.KICK_MEMBERS ) ) {
			return;
		}
		String[] args = e.getMessage().getRawContent().split( " " );
		if ( args.length != 2 ) {
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

		try {
			e.getGuild().getController().kick( e.getGuild().getMember( target ) );
		} catch ( HierarchyException exc ) {
			Utils.sendMessage( channel, member.getAsMention() + ", failed to kick " + target.getAsMention()
					+ " because he has equal or higher powers than me." );
			return;
		}
		Utils.sendMessage( channel, target.getAsMention() + " is kicked from this discord" );
	}

	@Override
	public String getDescription() {
		return "Kick somebody from the server";
	}

	@Override
	public String getUsage() {
		return "Kick <user>";
	}
}
