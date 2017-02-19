package net.halalaboos.mcwrapper.impl.mixin.world;

import com.google.common.collect.ImmutableList;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;

@Mixin(net.minecraft.world.World.class) public abstract class MixinWorld implements World {

	@Shadow
	public abstract boolean setBlockToAir(BlockPos pos);

	@Shadow
	@Final
	public List<EntityPlayer> playerEntities;

	@Override
	public void setToAir(Vector3i pos) {
		setBlockToAir(new BlockPos(pos.x, pos.y, pos.z));
	}

	@Override
	public Collection<Player> getPlayers() {
		return ((Collection<Player>)(Object)this.playerEntities);
	}
}
