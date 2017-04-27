package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.ServerPlayerAbilitiesPacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import org.spongepowered.asm.mixin.*;

@Mixin(SPacketPlayerAbilities.class)
@Implements(@Interface(iface = ServerPlayerAbilitiesPacket.class, prefix = "api$"))
public abstract class MixinSPacketPlayerAbilities implements MixinPacket, ServerPlayerAbilitiesPacket {

	@Shadow public abstract boolean shadow$isFlying();
	@Shadow public abstract boolean isAllowFlying();

	@Intrinsic
	public boolean api$isFlying() {
		return shadow$isFlying();
	}

	@Override
	public boolean isFlyingAllowed() {
		return isAllowFlying();
	}
}
