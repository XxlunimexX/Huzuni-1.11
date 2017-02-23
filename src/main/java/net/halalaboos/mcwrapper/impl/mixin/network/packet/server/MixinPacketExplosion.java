package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.ExplosionPacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SPacketExplosion.class)
public class MixinPacketExplosion implements MixinPacket, ExplosionPacket {
}
