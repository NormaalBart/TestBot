package me.bartvv.testbot.guild;

import java.time.OffsetDateTime;
import java.util.List;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class User {

	private long lastHere;
	private Member member;

	public User( Member member ) {
		this.member = member;
	}

	public long getLastHere() {
		return lastHere;
	}

	public void setLastHere( long lastHere ) {
		this.lastHere = lastHere;
	}

	public String getName() {
		return this.member.getUser().getName();
	}

	public boolean hasPermission( Permission... permissions ) {
		return this.member.hasPermission( permissions );
	}

	public String getAsMention() {
		return this.member.getAsMention();
	}

	public long getIdLong() {
		return this.member.getUser().getIdLong();
	}

	public String getEffectiveAvatarUrl() {
		return this.member.getUser().getEffectiveAvatarUrl();
	}

	public String getNickname() {
		return this.member.getNickname();
	}

	public OffsetDateTime getJoinDate() {
		return this.member.getJoinDate();
	}

	public OnlineStatus getOnlineStatus() {
		return this.member.getOnlineStatus();
	}

	public Game getGame() {
		return this.member.getGame();
	}

	public List< Role > getRoles() {
		return this.member.getRoles();
	}

	public void kick() {
		kick( null );
	}

	public void kick( String reason ) {
		this.member.getGuild().getController().kick( member, reason );
	}

	public Member getMember() {
		return this.member;
	}

}
