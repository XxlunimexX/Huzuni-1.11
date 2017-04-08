package net.halalaboos.mcwrapper.impl.mixin.network;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.network.NetworkHandler;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.halalaboos.mcwrapper.api.util.enums.DigAction;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

	@Shadow
	public abstract NetworkPlayerInfo getPlayerInfo(UUID uniqueId);

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

	@Override
	public Optional<PlayerInfo> getInfo(UUID uuid) {
		if (getPlayerInfo(uuid) == null) return Optional.empty();
		return Optional.of((PlayerInfo) getPlayerInfo(uuid));
	}

	@Override
	public void sendDigging(DigAction action, Vector3i pos, int face) {
		sendPacket(new CPacketPlayerDigging(Convert.to(action), Convert.to(pos), EnumFacing.values()[face]));
	}

	@Override
	public void sendSwing(Hand hand) {
		sendPacket(new CPacketAnimation(Convert.to(hand)));
	}

	@Override
	public void sendTryUseItemOnBlock(Vector3i pos, Face face, Hand hand, float faceX, float faceY, float faceZ) {
		sendPacket(new CPacketPlayerTryUseItemOnBlock(Convert.to(pos), Convert.to(face), Convert.to(hand), faceX, faceY, faceZ));
		System.out.println("Attempting to place block!");
		System.out.println(String.format("Pos %s, Face %s, Hand %s", Convert.to(pos), Convert.to(face), Convert.to(hand)));
	}
}
