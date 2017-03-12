package net.halalaboos.mcwrapper.impl.mixin.network;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.event.PacketSendEvent;
import net.halalaboos.mcwrapper.api.network.NetworkHandler;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient implements NetworkHandler {

	@Final
	@Shadow private NetworkManager netManager;

	/**
	 * Publishes the {@link PacketSendEvent}.
	 *
	 * @author b
	 */
	@Overwrite
	public void sendPacket(Packet<?> packet) {
		if (packet == null) return;
		PacketSendEvent event = new PacketSendEvent(((net.halalaboos.mcwrapper.api.network.packet.Packet) packet));
		MCWrapper.getEventManager().publish(event);
		if (event.isCancelled()) return;
		netManager.sendPacket(packet);
	}

	@Shadow public abstract Collection<NetworkPlayerInfo> getPlayerInfoMap();

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PlayerInfo> getPlayers() {
		return (Collection<PlayerInfo>)(Object) getPlayerInfoMap();
	}

	@Override
	public void sendRespawn() {
		sendPacket(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
	}

	@Override
	public void sendUseSwing() {
		CPacketAnimation packetAnimation = new CPacketAnimation(EnumHand.MAIN_HAND);
		CPacketPlayerTryUseItem packetTryUse = new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND);
		sendPacket(packetAnimation);
		sendPacket(packetTryUse);
	}
}
