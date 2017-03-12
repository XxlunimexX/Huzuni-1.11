package net.halalaboos.mcwrapper.impl.mixin.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

	@Shadow private Channel channel;
	@Shadow private INetHandler packetListener;

	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	void readPacket(ChannelHandlerContext context, Packet packet, CallbackInfo ci) {
		if (channel.isOpen()) {
			try {
				PacketReadEvent event = new PacketReadEvent((net.halalaboos.mcwrapper.api.network.packet.Packet)packet);
				MCWrapper.getEventManager().publish(event);
				if (event.isCancelled()) ci.cancel();
				((Packet<INetHandler>)packet).processPacket(this.packetListener);
			} catch (ThreadQuickExitException ignored) {}
		}
		ci.cancel();
	}
}
