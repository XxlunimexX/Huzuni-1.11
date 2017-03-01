package net.halalaboos.mcwrapper.impl.mixin.network;

import net.halalaboos.mcwrapper.api.network.NetworkHandler;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient implements NetworkHandler {

	@Shadow public abstract Collection<NetworkPlayerInfo> getPlayerInfoMap();

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PlayerInfo> getPlayers() {
		return (Collection<PlayerInfo>)(Object) getPlayerInfoMap();
	}
}
