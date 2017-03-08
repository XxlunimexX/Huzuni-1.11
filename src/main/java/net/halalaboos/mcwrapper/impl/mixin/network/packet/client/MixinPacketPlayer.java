package net.halalaboos.mcwrapper.impl.mixin.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.client.PlayerPacket;
import net.halalaboos.mcwrapper.api.util.math.Rotation;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketPlayer.class)
public class MixinPacketPlayer implements MixinPacket, PlayerPacket {
	@Shadow
	protected double x;

	@Shadow
	protected double y;

	@Shadow
	protected double z;

	@Shadow
	protected float pitch;

	@Shadow
	protected float yaw;

	@Shadow
	protected boolean onGround;

	@Override
	public Vector3d getLocation() {
		return new Vector3d(x, y, z);
	}

	@Override
	public void setLocation(Vector3d location) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
	}

	@Override
	public Rotation getRotation() {
		return new Rotation(pitch, yaw);
	}

	@Override
	public void setRotation(Rotation rotation) {
		this.pitch = rotation.pitch;
		this.yaw = rotation.yaw;
	}

	@Override
	public boolean getOnGround() {
		return onGround;
	}

	@Override
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}
}
