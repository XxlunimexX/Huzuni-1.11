package net.halalaboos.mcwrapper.impl.mixin.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.client.TabCompletePacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.client.CPacketTabComplete;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketTabComplete.class)
public class MixinPacketTabComplete implements MixinPacket, TabCompletePacket {

	@Shadow private String message;

	@Override
	public String getText() {
		return message;
	}

	@Override
	public void setText(String text) {
		this.message = message;
	}
}
