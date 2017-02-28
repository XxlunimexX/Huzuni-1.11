package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.ItemPickupPacket;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketCollectItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SPacketCollectItem.class)
public abstract class MixinPacketCollectItem implements MixinPacket, ItemPickupPacket {

	@Shadow public abstract int getCollectedItemEntityID();
	@Shadow public abstract int getEntityID();
	@Shadow public abstract int getAmount();

	@Override
	public int getCollector() {
		return getEntityID();
	}

	@Override
	public int getQuantity() {
		return getAmount();
	}

	@Override
	public int getCollected() {
		return getCollectedItemEntityID();
	}
}
