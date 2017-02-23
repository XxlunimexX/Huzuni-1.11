package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
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
	@Shadow @Final public NetHandlerPlayClient connection;

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
	public void setFlying(boolean flying) {
		capabilities.isFlying = flying;
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

	@Override
	public PlayerInfo getInfo(Player player) {
		return ((PlayerInfo) connection.getPlayerInfo(player.getUUID()));
	}

	@Override
	public boolean isNPC() {
		return false;
	}
}
