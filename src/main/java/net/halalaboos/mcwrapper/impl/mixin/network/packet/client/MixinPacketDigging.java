package net.halalaboos.mcwrapper.impl.mixin.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.client.DiggingPacket;
import net.halalaboos.mcwrapper.api.util.DigAction;
import net.halalaboos.mcwrapper.api.util.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketPlayerDigging.class)
@Implements(@Interface(iface = DiggingPacket.class, prefix = "api$"))
public abstract class MixinPacketDigging implements MixinPacket, DiggingPacket {
	@Shadow public abstract BlockPos getPosition();

	@Shadow public abstract CPacketPlayerDigging.Action getAction();

	@Shadow public abstract EnumFacing getFacing();

	@Override
	public Vector3i getLocation() {
		return Convert.from(getPosition());
	}

	@Override
	public DigAction getDigAction() {
		return Convert.from(getAction());
	}

	@Override
	public Face getFace() {
		return Convert.from(getFacing());
	}
}
