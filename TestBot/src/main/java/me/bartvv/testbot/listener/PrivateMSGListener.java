package me.bartvv.testbot.listener;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateMSGListener extends ListenerAdapter {

	@Override
	public void onPrivateMessageReceived( PrivateMessageReceivedEvent event ) {
		if ( event.getChannelType() != ChannelType.PRIVATE )
			return;

		User user = event.getAuthor();
		if ( user.isBot() )
			return;

		PrivateChannel channel = event.getChannel();
		EmbedBuilder embed = Utils.createDefaultBuilder();
		embed.addField( "Please do not contact this bot private, " + user.getName(),
				"Those messages are **never** read", false );
		Utils.sendMessage( channel, embed.build() );
	}

}
