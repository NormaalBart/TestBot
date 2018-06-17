package me.bartvv.testbot.listener;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin( GuildMemberJoinEvent e ) {
		Member member = e.getMember();
		Guild guild = e.getGuild();
		int size = guild.getMembers().size();
		EmbedBuilder builder = Utils.createDefaultBuilder();
		builder.addField( member.getUser().getName() + " welcome to our discord server!",
				"Their are now " + size + " members in this discord.", false );
		TextChannel channel = guild.getTextChannelById( Utils.getJoinChannel() );
		channel.sendMessage( builder.build() ).queue();
	}

}
