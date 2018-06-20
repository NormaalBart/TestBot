package me.bartvv.testbot.guild.commands;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import me.bartvv.testbot.Utils;
import me.bartvv.testbot.guild.GuildHandler;
import me.bartvv.testbot.guild.User;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandinfo implements ICommand {

	private GuildHandler guildHandler;

	public Commandinfo( GuildHandler guildHandler ) {
		this.guildHandler = guildHandler;
	}

	@Override
	public void onCommand( User user, GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();

		User target;
		if ( args.length == 2 ) {
			target = this.guildHandler.getUserHandler().getUser(
					e.getMessage().getMentionedUsers().isEmpty() ? null : e.getMessage().getMentionedUsers().get( 0 ) );

			if ( target == null ) {
				Utils.sendMessage( channel, user.getAsMention() + " user not found" );
				return;
			}
		} else {
			target = user;
		}

		EmbedBuilder embed = Utils.createDefaultBuilder();
		embed.setAuthor( target.getName(), target.getEffectiveAvatarUrl(), target.getEffectiveAvatarUrl() );
		embed.setTitle( "Information about " + target.getName() );
		StringBuilder builder = new StringBuilder();
		builder.append( "Nickname: " + ( target.getNickname() == null ? "none" : target.getNickname() ) );
		builder.append( "\n" );
		OffsetDateTime offSet = target.getJoinDate();
		builder.append( "Joindate: " + formatData( offSet ) );
		builder.append( "\n" );
		builder.append( "Status: " + target.getOnlineStatus().toString().toLowerCase() );
		builder.append( "\n" );
		builder.append( "Game: " + ( target.getGame() == null ? "none" : target.getGame().getName() ) );
		builder.append( "\n" );
		builder.append( "Role" + ( target.getRoles().isEmpty() || target.getRoles().size() == 1 ? "" : "s" )
				+ getRoles( target.getRoles() ) );
		embed.setDescription( builder.toString() );
		embed.setTimestamp( Instant.now() );
		Utils.sendMessage( channel, embed.build(), 30 );
	}

	@Override
	public String getDescription() {
		return "Return's the information of a member of the guild";
	}

	@Override
	public String getUsage() {
		return "Info [user]";
	}

	private String getRoles( List< Role > roles ) {
		StringBuilder builder = new StringBuilder();
		builder.append( "\n" );
		if ( roles.isEmpty() ) {
			builder.append( " - None" );
		} else {
			roles.forEach( role -> builder.append( " - " + role.getName() ).append( "\n" ) );
		}
		return builder.toString();
	}

	private String formatData( OffsetDateTime format ) {
		String year = "" + format.getYear();
		String month = "" + ( format.getMonthValue() > 10 ? format.getMonthValue() : "0" + format.getMonthValue() );
		String day = "" + ( format.getDayOfMonth() > 10 ? format.getDayOfMonth() : "0" + format.getDayOfMonth() );
		String hour = "" + ( format.getHour() > 10 ? format.getHour() : "0" + format.getHour() );
		String minute = "" + ( format.getMinute() > 10 ? format.getMinute() : "0" + format.getMinute() );
		String second = "" + ( format.getSecond() > 10 ? format.getSecond() : "0" + format.getSecond() );
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + " seconds";
	}

}
