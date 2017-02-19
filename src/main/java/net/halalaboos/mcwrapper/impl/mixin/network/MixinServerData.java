package net.halalaboos.mcwrapper.impl.mixin.network;

import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerData.class)
public class MixinServerData implements ServerInfo {

	@Shadow public String serverName;
	@Shadow public String serverIP;
	@Shadow public String populationInfo;
	@Shadow public String serverMOTD;
	@Shadow public long pingToServer;
	@Shadow private boolean lanServer;

	@Override
	public String getName() {
		return serverName;
	}

	@Override
	public String getIP() {
		return serverIP;
	}

	@Override
	public String getPopulation() {
		return populationInfo;
	}

	@Override
	public String getMOTD() {
		return serverMOTD;
	}

	@Override
	public long getPing() {
		return pingToServer;
	}

	@Override
	public boolean isLAN() {
		return lanServer;
	}
}
