package net.halalaboos.mcwrapper.impl.mixin.entity.liviing.player;

import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.client.entity.EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer implements ClientPlayer {
}
