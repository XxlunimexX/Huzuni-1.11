package net.halalaboos.mcwrapper.impl.mixin.world;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.block.tileentity.TileEntity;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import static net.halalaboos.mcwrapper.impl.Convert.player;

@SuppressWarnings("unchecked")
@Mixin(net.minecraft.world.World.class) public abstract class MixinWorld implements World {

	@Shadow public abstract boolean setBlockToAir(BlockPos pos);
	@Shadow @Final public List<EntityPlayer> playerEntities;
	@Shadow @Final public List<net.minecraft.entity.Entity> loadedEntityList;
	@Shadow public abstract List<net.minecraft.entity.Entity> getEntitiesWithinAABBExcludingEntity(@Nullable net.minecraft.entity.Entity entityIn, AxisAlignedBB bb);
	@Shadow @Nullable public abstract net.minecraft.entity.Entity getEntityByID(int id);
	@Shadow @Final public List<net.minecraft.tileentity.TileEntity> loadedTileEntityList;

	@Shadow
	public abstract IBlockState getBlockState(BlockPos pos);

	@Shadow
	public abstract List<AxisAlignedBB> getCollisionBoxes(@Nullable net.minecraft.entity.Entity entityIn, AxisAlignedBB aabb);

	@Shadow public abstract void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress);

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
	public Collection<TileEntity> getTileEntities() {
		return ((Collection<TileEntity>)(Object)this.loadedTileEntityList);
	}

	@Override
	public Entity getEntity(int entityId) {
		return ((Entity) getEntityByID(entityId));
	}

	@Override
	public Block getBlock(Vector3i pos) {
		return ((Block) getBlockState(Convert.to(pos)).getBlock());
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return getBlock(new Vector3i(x, y, z));
	}

	//this might be broken... lol
	@Override
	public Collection<Entity> getEntitiesInBox(AABB aabb) {
		return ((Collection<Entity>)(Object)getEntitiesWithinAABBExcludingEntity(player(), Convert.to(aabb)));
	}

	@Override
	public boolean isOffsetBBEmpty(Vector3d offset) {
		EntityPlayerSP playerSP = player();
		return getCollisionBoxes(playerSP, playerSP.getEntityBoundingBox().offset(offset.getX(), offset.getY(), offset.getZ())).isEmpty();
	}

	@Override
	public boolean blockExists(Vector3i pos) {
		return getBlockState(Convert.to(pos)).getMaterial() != Material.AIR;
	}

	@Override
	public void sendBreakProgress(Vector3i pos, int progress) {
		sendBlockBreakProgress(player().getEntityId(), Convert.to(pos), progress);
	}

	@Override
	public float getRelativeHardness(Vector3i pos) {
		IBlockState state = getBlockState(Convert.to(pos));
		return state.getPlayerRelativeBlockHardness(player(), (net.minecraft.world.World)(Object)this, Convert.to(pos));
	}
}
