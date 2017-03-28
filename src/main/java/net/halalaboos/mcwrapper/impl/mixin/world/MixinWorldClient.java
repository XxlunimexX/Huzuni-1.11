package net.halalaboos.mcwrapper.impl.mixin.world;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient extends MixinWorld {
	@Shadow public abstract Entity removeEntityFromWorld(int entityID);

	@Shadow public abstract void addEntityToWorld(int entityID, Entity entityToSpawn);

	@Override
	public Player spawnCopiedPlayer(int entityID, Player target) {
		EntityPlayer player = (EntityPlayer)target;
		EntityOtherPlayerMP copiedPlayer = new EntityOtherPlayerMP((World)(Object)this,
				new GameProfile(player.getUniqueID(), player.getName()));
		copiedPlayer.copyLocationAndAnglesFrom(player);
		copiedPlayer.inventory = player.inventory;
		copiedPlayer.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
		copiedPlayer.rotationYawHead = player.rotationYawHead;
		addEntityToWorld(entityID, copiedPlayer);
		return (Player)copiedPlayer;
	}

	@Override
	public void removeEntity(int entityID) {
		removeEntityFromWorld(entityID);
	}
}
