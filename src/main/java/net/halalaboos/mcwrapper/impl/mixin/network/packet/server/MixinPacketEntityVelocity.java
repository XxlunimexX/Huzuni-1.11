package net.halalaboos.mcwrapper.impl.mixin.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.server.EntityVelocityPacket;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.impl.mixin.network.packet.MixinPacket;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SPacketEntityVelocity.class)
public class MixinPacketEntityVelocity implements MixinPacket, EntityVelocityPacket {

	@Shadow private int motionX;
	@Shadow private int motionY;
	@Shadow private int motionZ;
	@Shadow private int entityID;

	@Override
	public void setVelocity(Vector3d velocity) {
		double x = velocity.getX();
		double y = velocity.getY();
		double z = velocity.getZ();
		this.motionX = (int)(x * 8000.0D);
		this.motionY = (int)(y * 8000.0D);
		this.motionZ = (int)(z * 8000.0D);
	}

	@Override
	public Vector3d getVelocity() {
		return new Vector3d(motionX, motionY, motionZ);
	}

	@Override
	public int getId() {
		return entityID;
	}
}
