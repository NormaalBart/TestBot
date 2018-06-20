package me.bartvv.testbot.guild.commands;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;

public class Commandkick implements ICommand {

	private GuildHandler guildHandler;

	public Commandkick( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		MessageChannel channel = e.getChannel();
		if ( !user.hasPermission( Permission.KICK_MEMBERS ) ) {
			return;
		}
		String[] args = e.getMessage().getRawContent().split( " " );
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

		try {
			target.kick();
		} catch ( HierarchyException exc ) {
			Utils.sendMessage( channel, user.getAsMention() + ", failed to kick " + target.getAsMention()
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
