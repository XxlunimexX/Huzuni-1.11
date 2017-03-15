package net.halalaboos.mcwrapper.impl.mixin.client.gui;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.event.render.HUDRenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * NOTE: For non-forge implementations this needs to be pointed to Minecraft's GuiIngame class.
 */
@Mixin(GuiIngameForge.class)
public class MixinGuiIngame {

	@Inject(method = "renderGameOverlay", at = @At(value = "RETURN"))
	public void renderGameOverlay(float partialTicks, CallbackInfo ci) {
		MCWrapper.getEventManager().publish(new HUDRenderEvent(Minecraft.getMinecraft().gameSettings.showDebugInfo, partialTicks));
	}
}
