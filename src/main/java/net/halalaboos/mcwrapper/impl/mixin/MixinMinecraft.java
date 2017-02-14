package net.halalaboos.mcwrapper.impl.mixin;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.OnePointElevenAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.Minecraft.class)
public abstract class MixinMinecraft {

	@Shadow
	public abstract Minecraft getMinecraft();

	@Inject(method = "run()V", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;init()V",
			shift = At.Shift.AFTER))
	public void initWrapper(CallbackInfo ci) {
		MCWrapper.setAdapter(new OnePointElevenAdapter(getMinecraft()));
	}

	@Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
					shift = At.Shift.AFTER))
	public void setWorld(WorldClient world, String loadingMessage, CallbackInfo ci) {
		MCWrapper.setWorld(((World) world));
	}
}
