package net.halalaboos.mcwrapper.impl.mixin.network.packet.client;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketUseEntity.class)
public abstract class MixinPacketUseEntity implements MixinPacket, UseEntityPacket {

	@Shadow private int entityId;

	@Shadow public abstract EnumHand getHand();

	@Shadow public abstract CPacketUseEntity.Action getAction();

	@Override
	public Entity getEntity(World world) {
		return world.getEntity(entityId);
	}

	@Override
	public int getId() {
		return entityId;
	}

	@Override
	public Hand getUsedHand() {
		return Hand.values()[getHand().ordinal()];
	}

	@Override
	public UseAction getUseAction() {
		return UseAction.values()[getAction().ordinal()];
	}
}
