package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.gui.screen.HuzuniMainMenu;
import net.halalaboos.huzuni.mc.Wrapper;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(net.minecraft.client.Minecraft.class) public abstract class MixinMinecraft {

	@Shadow public WorldClient world;
	@Shadow public GuiScreen currentScreen;

	@Shadow
	public boolean skipRenderWorld;

	@Shadow
	public abstract void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	@Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
	public void onLoadWorld(@Nullable WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
		if (worldClientIn != null) {
			Wrapper.loadWorld(worldClientIn);
		}
	}

	@Inject(method = "runTick()V", at = @At("RETURN"))
	public void runTick(CallbackInfo callbackInfo) {
		if (this.currentScreen instanceof GuiMainMenu) {
			displayGuiScreen(new HuzuniMainMenu());
		}
	}
}
