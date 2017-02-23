package net.halalaboos.mcwrapper.impl.mixin.world;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

@Mixin(net.minecraft.world.World.class) public abstract class MixinWorld implements World {

	@Shadow public abstract boolean setBlockToAir(BlockPos pos);
	@Shadow @Final public List<EntityPlayer> playerEntities;
	@Shadow @Final public List<net.minecraft.entity.Entity> loadedEntityList;

	@Shadow
	public abstract List<net.minecraft.entity.Entity> getEntitiesWithinAABBExcludingEntity(@Nullable net.minecraft.entity.Entity entityIn, AxisAlignedBB bb);

	@Shadow
	@Nullable
	public abstract net.minecraft.entity.Entity getEntityByID(int id);

	@Override
	public void setToAir(Vector3i pos) {
		setBlockToAir(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
	}

	@Override
	public Collection<Player> getPlayers() {
		return ((Collection<Player>)(Object)this.playerEntities);
	}

	@Override
	public Collection<Entity> getEntities() {
		return ((Collection<Entity>)(Object)this.loadedEntityList);
	}

	@Override
	public Entity getEntity(int entityId) {
		return ((Entity) getEntityByID(entityId));
	}
}
