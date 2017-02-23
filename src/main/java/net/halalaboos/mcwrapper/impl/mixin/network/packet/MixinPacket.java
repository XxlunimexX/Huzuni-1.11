package net.halalaboos.mcwrapper.impl.mixin.network.packet;

import net.halalaboos.mcwrapper.api.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.network.Packet.class)
public interface MixinPacket extends Packet {
}
