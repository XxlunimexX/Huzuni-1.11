package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SPacketUpdateHealth.class)
public abstract class MixinPacketUpdateHealth implements MixinPacket, HealthUpdatePacket {

	@Shadow public abstract float getHealth();
	@Shadow public abstract int getFoodLevel();
	@Shadow public abstract float getSaturationLevel();

	@Override
	public float getHearts() {
		return getHealth();
	}

	@Override
	public float getHunger() {
		return getFoodLevel();
	}

	@Override
	public float getSaturation() {
		return getSaturationLevel();
	}
}
