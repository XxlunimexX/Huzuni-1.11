package net.halalaboos.mcwrapper.impl.mixin;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.client.gui.TextRenderer;
import net.halalaboos.mcwrapper.api.network.ServerInfo;
import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.OnePointElevenAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinMinecraft implements MinecraftClient {

	@Shadow private int rightClickDelayTimer;
	@Shadow public abstract float getRenderPartialTicks();
	@Shadow @Final private Timer timer;
	@Shadow public EntityPlayerSP player;
	@Shadow public WorldClient world;
	@Shadow public int displayWidth;
	@Shadow public int displayHeight;
	@Shadow public GameSettings gameSettings;
	@Shadow public abstract boolean isSingleplayer();
	@Shadow @Nullable public abstract ServerData getCurrentServerData();
	@Shadow public GuiIngame ingameGUI;
	@Shadow private static int debugFPS;
	@Shadow public PlayerControllerMP playerController;
	@Shadow private RenderManager renderManager;
	@Shadow public FontRenderer fontRenderer;

	@Shadow
	public abstract boolean isUnicode();

	@Inject(method = "run()V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;init()V",
			shift = At.Shift.AFTER))
	public void initWrapper(CallbackInfo ci) {
		MCWrapper.setAdapter(new OnePointElevenAdapter((Minecraft)(Object)this));
	}

	@Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
					shift = At.Shift.AFTER))
	public void setWorld(WorldClient world, String loadingMessage, CallbackInfo ci) {
		MCWrapper.onSetWorld(((World) world));
	}

	@Override
	public int getRightClickDelayTimer() {
		return rightClickDelayTimer;
	}

	@Override
	public void setRightClickDelayTimer(int rightClickDelayTimer) {
		this.rightClickDelayTimer = rightClickDelayTimer;
	}

	@Override
	public float getDelta() {
		return getRenderPartialTicks();
	}

	@Override
	public float getTimerSpeed() {
		return timer.timerSpeed;
	}

	@Override
	public void setTimerSpeed(float timerSpeed) {
		timer.timerSpeed = timerSpeed;
	}

	@Override
	public ClientPlayer getPlayer() {
		return ((ClientPlayer) player);
	}

	@Override
	public World getWorld() {
		return ((World) world);
	}

	@Override
	public boolean isRemote() {
		return !isSingleplayer();
	}

	@Override
	public Resolution getScreenResolution() {
		return new Resolution(displayWidth, displayHeight, gameSettings.guiScale);
	}

	@Override
	public Optional<ServerInfo> getServerInfo() {
		if (getCurrentServerData() == null) {
			return Optional.empty();
		}
		return Optional.of((ServerInfo)getCurrentServerData());
	}

	@Override
	public void clearMessages(boolean sentMessages) {
		ingameGUI.getChatGUI().clearChatMessages(sentMessages);
	}

	@Override
	public int getFPS() {
		return debugFPS;
	}

	@Override
	public Controller getController() {
		return ((Controller) playerController);
	}

	@Override
	public Vector3d getCamera() {
		return new Vector3d(renderManager.viewerPosX, renderManager.viewerPosY, renderManager.viewerPosZ);
	}

	@Override
	public TextRenderer getTextRenderer() {
		return ((TextRenderer) fontRenderer);
	}

	@Override
	public boolean useUnicode() {
		return isUnicode();
	}
}
