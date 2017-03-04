package net.halalaboos.mcwrapper.impl.mixin.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.client.PlayerAbilitiesPacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import org.spongepowered.asm.mixin.*;

@Implements(@Interface(iface = PlayerAbilitiesPacket.class, prefix = "api$"))
@Mixin(CPacketPlayerAbilities.class)
public abstract class MixinPacketPlayerAbilities implements MixinPacket, PlayerAbilitiesPacket {

	@Shadow public abstract void shadow$setFlying(boolean isFlying);

	@Shadow public abstract boolean isAllowFlying();

	@Shadow public abstract boolean shadow$isFlying();

	@Intrinsic
	public void api$setFlying(boolean flying) {
		shadow$setFlying(flying);
	}

	@Override
	public boolean isFlyingAllowed() {
		return isAllowFlying();
	}

	@Intrinsic
	public boolean api$isFlying() {
		return shadow$isFlying();
	}
}
