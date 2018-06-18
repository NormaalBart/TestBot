package me.bartvv.testbot.commands;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandinfo implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();
		Member member = e.getMember();

		Member target;
		if ( args.length == 2 ) {
			User targetUser = e.getMessage().getMentionedUsers().isEmpty() ? null
					: e.getMessage().getMentionedUsers().get( 0 );
			if ( targetUser == null ) {
				Utils.sendMessage( channel, member.getAsMention() + " user not found" );
				return;
			}
			target = e.getGuild().getMember( targetUser );
		} else {
			target = e.getMember();
		}

		EmbedBuilder embed = Utils.createDefaultBuilder();
		embed.setAuthor( target.getUser().getName(), target.getUser().getEffectiveAvatarUrl(),
				target.getUser().getEffectiveAvatarUrl() );
		embed.setTitle( "Information about " + target.getUser().getName() );
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
