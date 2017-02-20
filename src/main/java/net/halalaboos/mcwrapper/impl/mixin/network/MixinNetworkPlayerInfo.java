package net.halalaboos.mcwrapper.impl.mixin.network;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetworkPlayerInfo.class)
public abstract class MixinNetworkPlayerInfo implements PlayerInfo {

	@Shadow public abstract GameProfile getGameProfile();
	@Shadow public abstract int getResponseTime();

	@Override
	public GameProfile getProfile() {
		return getGameProfile();
	}

	@Override
	public int getPing() {
		return getResponseTime();
	}
}
