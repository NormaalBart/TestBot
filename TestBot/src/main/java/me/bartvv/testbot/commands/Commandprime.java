package me.bartvv.testbot.commands;

import me.bartvv.testbot.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Commandprime implements ICommand {

	@Override
	public void onCommand( GuildMessageReceivedEvent e ) {
		String[] args = e.getMessage().getRawContent().split( " " );
		TextChannel channel = e.getChannel();
		long number;
		try {
			number = Long.parseLong( args[ 1 ] );
		} catch ( Exception exc ) {
			EmbedBuilder builder = Utils.createDefaultBuilder();
			builder.addField( "Wrong usage", getUsage(), true );
			Utils.sendMessage( channel, builder.build() );
			return;
		}

		Number prime = isPrime( number );
		Utils.sendMessage( channel, "The number " + args[ 1 ] + " is " + prime.name().toLowerCase() );
		return;
	}

	@Override
	public String getDescription() {
		return "Checks if the number is prime or not";
	}

	@Override
	public String getUsage() {
		return "Prime (number)";
	}

	public Number isPrime( long number ) {
		if ( number < 1 )
			return Number.COMPOSITE;

		if ( number > 2 && ( number & 1 ) == 0 )
			return Number.COMPOSITE;

		for ( int i = 3; i * i <= number; i += 2 ) {
			if ( number % i == 0 )
				return Number.COMPOSITE;
		}
		return Number.PRIME;
	}

	private enum Number {
		PRIME, COMPOSITE;
	}
}
