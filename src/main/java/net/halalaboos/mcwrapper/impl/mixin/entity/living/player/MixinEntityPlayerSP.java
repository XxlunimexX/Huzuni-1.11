package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.entity.EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer implements ClientPlayer {

	@Shadow public abstract void swingArm(EnumHand hand);
	@Shadow public abstract void closeScreen();
	@Shadow public MovementInput movementInput;
	@Shadow private String serverBrand;

	@Shadow
	@Final
	public NetHandlerPlayClient connection;

	@Override
	public void swingItem(Hand hand) {
		swingArm(EnumHand.values()[hand.ordinal()]);
	}

	@Override
	public void closeWindow() {
		closeScreen();
	}

	@Override
	public boolean isFlying() {
		return capabilities.isFlying;
	}

	@Override
	public float getForwardMovement() {
		return movementInput.moveForward;
	}

	@Override
	public void setSneak(boolean sneak) {
		this.movementInput.sneak = sneak;
	}

	@Override
	public String getBrand() {
		return serverBrand;
	}

	@Override
	public void sendMessage(String message) {
		connection.sendPacket(new CPacketChatMessage(message));
	}
}
