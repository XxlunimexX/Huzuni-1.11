package net.halalaboos.mcwrapper.api.network;

import com.mojang.authlib.GameProfile;

public interface PlayerInfo {

	/**
	 * @return The player's {@link GameProfile}
	 */
	GameProfile getProfile();

	/**
	 * @return The player's ping.
	 */
	int getPing();

	String getName(boolean formatted);
}
