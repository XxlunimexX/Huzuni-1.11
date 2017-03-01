package net.halalaboos.mcwrapper.impl.mixin.network;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(NetworkPlayerInfo.class)
public abstract class MixinNetworkPlayerInfo implements PlayerInfo {

	@Shadow public abstract GameProfile getGameProfile();
	@Shadow public abstract int getResponseTime();

	@Shadow
	@Nullable
	public abstract ITextComponent getDisplayName();

	@Override
	public GameProfile getProfile() {
		return getGameProfile();
	}

	@Override
	public int getPing() {
		return getResponseTime();
	}

	@Override
	public String getName(boolean formatted) {
		if (getDisplayName() == null) return getProfile().getName();
		return formatted ? getDisplayName().getFormattedText() : getDisplayName().getUnformattedText();
	}
}
