package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.mc.HuzuniEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.multiplayer.PlayerControllerMP.class) public class MixinPlayerController {

	@Shadow @Final private Minecraft mc;
	@Shadow @Final private NetHandlerPlayClient connection;

	@Inject(method = "createClientPlayer", at = @At("HEAD"), cancellable = true)
	public void createClientPlayer(World world, StatisticsManager statisticsManager,
								   CallbackInfoReturnable<EntityPlayerSP> ci) {
		ci.setReturnValue(new HuzuniEntityPlayer(mc, world, connection, statisticsManager));
		ci.cancel();
	}
}
