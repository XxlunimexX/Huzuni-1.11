package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class) public class MixinNetHandlerPlayClient {

	@Final @Shadow private NetworkManager netManager;

	@Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
	public void onPacketSend(Packet packet, CallbackInfo ci) {
		if (packet == null) return;
		PacketEvent packetEvent = new PacketEvent(PacketEvent.Type.SENT, packet);
		Huzuni.INSTANCE.eventManager.invoke(packetEvent);
		if (packetEvent.isCancelled()) ci.cancel();
		netManager.sendPacket(packetEvent.getPacket());
		ci.cancel();
	}

}
