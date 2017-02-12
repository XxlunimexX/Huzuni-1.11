package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.Huzuni;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.*;

@Mixin(net.minecraft.client.renderer.EntityRenderer.class) public class MixinEntityRenderer {

	@Final @Shadow private Minecraft mc;

	@Inject(method = "renderWorldPass", at = @At(value = "FIELD",
			target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z",
			shift = At.Shift.BEFORE))
	private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
		Huzuni.INSTANCE.renderManager.enableGlConstants();
		Huzuni.INSTANCE.renderManager.renderWorld(partialTicks);
		dispatchLineRenderer(partialTicks, pass);
		Huzuni.INSTANCE.renderManager.disableGlConstants();
	}

	@Shadow
	private void setupCameraTransform(float partialTicks, int pass) {}

	private void dispatchLineRenderer(float partialTicks, int pass) {
		boolean oldBobbing = mc.gameSettings.viewBobbing;
		mc.gameSettings.viewBobbing = false;
		glPushMatrix();
		setupCameraTransform(partialTicks, pass);
		Huzuni.INSTANCE.renderManager.renderLines();
		mc.gameSettings.viewBobbing = oldBobbing;
		glPopMatrix();
	}
}
