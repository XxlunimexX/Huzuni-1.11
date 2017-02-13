package net.halalaboos.huzuni.mc.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.NetworkManager.class) public class MixinNetworkManager {

	@Shadow @Final private Channel channel;
	@Shadow @Final private INetHandler packetListener;

	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	void readPacket(ChannelHandlerContext context, Packet packet, CallbackInfo ci) {
		if (channel.isOpen()) {
			try {
				PacketEvent event = new PacketEvent(PacketEvent.Type.READ, packet);
				Huzuni.INSTANCE.eventManager.invoke(event);
				Packet outPacket = event.getPacket();
				if (event.isCancelled()) ci.cancel();
				((Packet<INetHandler>)outPacket).processPacket(this.packetListener);
			} catch (ThreadQuickExitException ignored) {}
		}
		ci.cancel();
	}
}
