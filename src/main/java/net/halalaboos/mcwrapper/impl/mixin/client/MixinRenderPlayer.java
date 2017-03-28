package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.MCWrapperHooks;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.renderer.entity.RenderPlayer.class)
public class MixinRenderPlayer {

	@Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
	protected void renderEntityName(AbstractClientPlayer entityIn,
									double x, double y, double z,
									String name, double distanceSq,
									CallbackInfo ci) {
		if (!MCWrapperHooks.renderNames) {
			ci.cancel();
		}
	}
}
