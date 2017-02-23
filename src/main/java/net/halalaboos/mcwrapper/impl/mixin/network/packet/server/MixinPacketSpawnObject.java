package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.SpawnObjectPacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketSpawnObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SPacketSpawnObject.class)
public abstract class MixinPacketSpawnObject implements MixinPacket, SpawnObjectPacket {

	@Shadow public abstract int getEntityID();
	@Shadow public abstract int getData();

	@Override
	public int getSpawnedId() {
		return getEntityID();
	}

	@Override
	public int getSourceId() {
		return getData();
	}
}
