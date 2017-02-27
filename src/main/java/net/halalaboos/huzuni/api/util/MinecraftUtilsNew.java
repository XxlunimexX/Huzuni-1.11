package net.halalaboos.huzuni.api.util;

import com.google.common.collect.Multimap;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.Animal;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.Monster;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Session;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.net.Proxy;
import java.util.Collection;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Porting old MinecraftUtils methods to use MCWrapper.  This will be renamed when it's done
 */
public class MinecraftUtilsNew {

	protected static final Minecraft mc = Minecraft.getMinecraft();

	private static final Huzuni huzuni = Huzuni.INSTANCE;

	/**
	 * @return The address the player is currently connected to.
	 * */
	public static String getCurrentServer() {
		return !getMinecraft().getServerInfo().isPresent() ? "localhost" : getMinecraft().getServerInfo().get().getIP();
	}

	/**
	 * Attempts to log into a Minecraft account using the username and password provided.
	 * @return a {@link Session} class with the account's new session.
	 * */
	public static Session loginToMinecraft(String username, String password) throws AuthenticationException {
		YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
		YggdrasilUserAuthentication userAuthentication = (YggdrasilUserAuthentication) authenticationService .createUserAuthentication(Agent.MINECRAFT);
		userAuthentication.setUsername(username);
		userAuthentication.setPassword(password);
		userAuthentication.logIn();
		return new Session(userAuthentication.getSelectedProfile().getName(), userAuthentication.getSelectedProfile().getId().toString(), userAuthentication.getAuthenticatedToken(), "MOJANG" /* we mojang now ;)))*/);
	}

	/**
	 * @return True if the entity type is
	 * */
	public static boolean checkType(Entity entity, boolean invisible, boolean mob,
									boolean animal, boolean player) {
		return !entity.isDead() && !(entity.isInvisible() && !invisible) &&
				(mob && entity instanceof Monster || animal && entity instanceof Animal && !(entity instanceof Monster)
						|| player && entity instanceof Player && !((Player) entity).isNPC());
	}

	/**
	 * @return True if the entity age is greater than or equal to 20
	 * */
	public static boolean checkAge(Entity entity) {
		return entity.getExistedTicks() >= 20;
	}

	/**
	 * @return The closest entity to the player
	 * */
	public static Living getClosestEntityToPlayer(Entity toPlayer, float distance, float extender, boolean invisible, boolean mob, boolean animal, boolean player, boolean ageCheck) {
		Living currentEntity = null;
		for (Entity entity : getWorld().getEntities()) {
			if (entity instanceof Living) {
				Living living = (Living)entity;
				if (isAliveNotUs(living) && checkType(living, invisible, mob, animal, player) && checkTeam(living) && checkProperties(living) && (!ageCheck || checkAge(living))) {
					if (isFriend(living))
						continue;
					if (currentEntity != null) {
						if (toPlayer.getDistanceTo(living) < toPlayer.getDistanceTo(currentEntity))
							currentEntity = living;
					} else {
						if (toPlayer.getDistanceTo(living) < distance + extender)
							currentEntity = living;
					}
				}
			}
		}
		return currentEntity;
	}

	/**
	 * @return The closest entity to the player that requires the least change in yaw and pitch
	 * */
	public static Living getClosestEntity(float distance, float extender, boolean invisible, boolean mob, boolean animal, boolean player, boolean ageCheck) {
		Living currentEntity = null;
		for (Entity entity : getWorld().getEntities()) {
			if (entity instanceof Living) {
				Living living = (Living)entity;
				if (isAliveNotUs(living) && checkType(living, invisible, mob, animal, player) && checkTeam(living) && checkProperties(living) && (!ageCheck || checkAge(living))) {
					if (isFriend(living))
						continue;
					if (currentEntity != null) {
						if (isWithinDistance(living, distance + extender) && isClosestToMouse(currentEntity, living, distance, extender))
							currentEntity = living;
					} else {
						if (isWithinDistance(living, distance + extender))
							currentEntity = living;
					}
				}
			}
		}
		return currentEntity;
	}

	/**
	 * @return The closest entity to our mouse that is within our FOV
	 * */
	public static Living getEntityWithinFOV(float fov, boolean invisible, boolean mob, boolean animal, boolean player, boolean ageCheck) {
		Living currentEntity = null;
		for (Entity entity : getWorld().getEntities()) {
			if (entity instanceof Living) {
				Living living = (Living)entity;
				if (isAliveNotUs(living) && checkType(living, invisible, mob, animal, player) && checkTeam(living) && checkProperties(living) && (!ageCheck || checkAge(living))) {
					if (isFriend(living))
						continue;
					if (currentEntity != null) {
						if (isWithinFOV(living, fov) && isClosestToMouse(currentEntity, living, -1, 0))
							currentEntity = living;
					} else {
						if (isWithinFOV(living, fov))
							currentEntity = living;
					}
				}
			}
		}
		return currentEntity;
	}

	/**
	 * @return True if the entity is another player and does not contain any properties
	 * */
	public static boolean checkProperties(Living entity) {
		return !(entity instanceof Player) || ((Player) entity).getProfile().getProperties().size() > 0;
	}

	/**
	 * @return True if the entity is another player and does not contain any properties
	 * */
	public static boolean checkProperties(Entity entity) {
		return !(entity instanceof Player) || ((Player) entity).getProfile().getProperties().size() > 0;
	}

	/**
	 * @return True if the entity is within the player's friends list
	 * */
	public static boolean isFriend(Living entity) {
		return entity instanceof Player && huzuni.friendManager.isFriend(entity.name());
	}

	/**
	 * @return True if the entity is another player and is on the player's team
	 * */
	public static boolean checkTeam(Living entity) {
		return !huzuni.settings.team.isEnabled() || !huzuni.settings.team.hasSelected() ||
				!(entity instanceof Player) || !huzuni.settings.team.hasTeamColor(entity.getFormattedName());
	}

	/**
	 * @return The closest entity to your mouse
	 */
	public static boolean isClosestToMouse(Living currentEntity, Living otherEntity, float distance, float extender) {

		// If we can't reach our current entity without the extender, but we CAN with our OTHER entity, return true.
		if (!isWithinDistance(currentEntity, distance) && isWithinDistance(otherEntity, distance))
			return true;

		float otherDist = getDistanceFromMouse(otherEntity),
				currentDist = getDistanceFromMouse(currentEntity);
		return (otherDist < currentDist || currentDist == -1) && otherDist != -1;
	}

	/**
	 * @return True if the entity is within the distance specified to the player
	 * */
	public static boolean isWithinDistance(Living entity, float distance) {
		return distance == -1 || getPlayer().getDistanceTo(entity) < distance;
	}

	/**
	 * @return True if the entity is alive and not the player
	 * */
	public static boolean isAliveNotUs(Living entity) {
		return !entity.name().equalsIgnoreCase(getPlayer().name()) && !entity.isDead() && entity != getPlayer();
	}

	/**
	 * @return Distance the entity is from our mouse.
	 */
	public static float getDistanceFromMouse(Living entity) {
		float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			float neededYaw = getYawDifference(getPlayer().getYaw() % 360F, neededRotations[0]), neededPitch = (getPlayer().getPitch() % 360F) - neededRotations[1];
			return net.halalaboos.mcwrapper.api.util.math.MathUtils.sqrt(neededYaw * neededYaw + neededPitch * neededPitch);
		}
		return -1F;
	}

	/**
	 * @return Rotations needed to face the position.
	 */
	public static float[] getRotationsNeeded(double x, double y, double z) {
		double xSize = x - getPlayer().getX();
		double ySize = y - (getPlayer().getY() + getPlayer().getEyeHeight());
		double zSize = z - getPlayer().getZ();

		double theta = (double) net.halalaboos.mcwrapper.api.util.math.MathUtils.sqrt(xSize * xSize + zSize * zSize);
		float yaw = (float) (Math.atan2(zSize, xSize) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (-(Math.atan2(ySize, theta) * 180.0D / Math.PI));
		return new float[] {
				(getPlayer().getYaw() + net.halalaboos.mcwrapper.api.util.math.MathUtils.wrapDegrees(yaw - getPlayer().getYaw())) % 360F,
				(getPlayer().getPitch() + net.halalaboos.mcwrapper.api.util.math.MathUtils.wrapDegrees(pitch - getPlayer().getPitch())) % 360F,
		};
	}

	/**
	 * @return Rotations needed to face the entity.
	 */
	public static float[] getRotationsNeeded(Living entity) {
		if (entity == null)
			return null;
		return getRotationsNeeded(entity.getX(), entity.getY() + ((double) entity.getEyeHeight() / 2F), entity.getZ());
	}

	/**
	 * @return Maximum/minimum rotation leniency allowed to still be considered 'inside' of a given entity.
	 * */
	public static float[] getEntityCaps(Living entity, float distance) {
		float distanceRatio = distance / ((float) getPlayer().getDistanceTo(entity)); /* I honestly do not remember my logic behind this and I don't want to bring out a notebook and figure out why this works, but it seems to work */
		float entitySize = 5F; /* magic number */
		return new float[] { distanceRatio * entity.getWidth() * entitySize, distanceRatio * entity.getHeight() * entitySize };
	}

	/**
	 * @return the difference between two yaw values
	 * */
	public static float getYawDifference(float currentYaw, float neededYaw) {
		float yawDifference = neededYaw - currentYaw;
		if (yawDifference > 180)
			yawDifference = -((360F - neededYaw) + currentYaw);
		else if (yawDifference < -180)
			yawDifference = ((360F - currentYaw) + neededYaw);

		return yawDifference;
	}

	/**
	 * Compares the needed yaw to the player's yaw and returns whether or not it is less than the fov.
	 * @return True if the entity is within the FOV specified.
	 * */
	public static boolean isWithinFOV(Living entity, float fov) {
		float[] rotations = getRotationsNeeded(entity);
		float yawDifference = getYawDifference(getPlayer().getYaw() % 360F, rotations[0]);
		return yawDifference < fov && yawDifference > -fov;
	}

	/**
	 * Raytraces to find a face on the block that can be seen by the player.
	 * */
	public static EnumFacing findFace(BlockPos position) {
		if (mc.world.getBlockState(position).getBlock() != Blocks.AIR) {
			for (EnumFacing face : EnumFacing.values()) {
				Vec3d playerVec = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
				Vec3d blockVec = new Vec3d(position.getX() + 0.5F + (float) (face.getDirectionVec().getX()) / 2F, position.getY() + 0.5F + (float) (face.getDirectionVec().getY()) / 2F, position.getZ() + 0.5F + (float) (face.getDirectionVec().getZ()) / 2F);
				RayTraceResult raytraceResult = mc.world.rayTraceBlocks(playerVec, blockVec);
				if (raytraceResult == null || raytraceResult.typeOfHit == RayTraceResult.Type.MISS) {
					return face;
				}
			}
		}
		return null;
	}

	/**
	 * Finds the face of the first adjacent block that can be seen by the player.
	 * */
	public static EnumFacing getAdjacent(BlockPos position) {
		for (EnumFacing face : EnumFacing.values()) {
			BlockPos otherPosition = position.offset(face);
			if (mc.world.getBlockState(otherPosition).getBlock() != Blocks.AIR) {
				EnumFacing otherFace = face.getOpposite();
				Vec3d playerVec = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
				Vec3d blockVec = new Vec3d(otherPosition.getX() + 0.5F + (float) (otherFace.getDirectionVec().getX()) / 2F, otherPosition.getY() + 0.5F + (float) (otherFace.getDirectionVec().getY()) / 2F, otherPosition.getZ() + 0.5F + (float) (otherFace.getDirectionVec().getZ()) / 2F);
				RayTraceResult raytraceResult = mc.world.rayTraceBlocks(playerVec, blockVec);
				if (raytraceResult == null || raytraceResult.typeOfHit == RayTraceResult.Type.MISS) {
					return face;
				}
			}
		}
		return null;
	}

	public static float calculatePlayerDamage(Living entity, ItemStack item) {
		return calculatePlayerDamage(entity, item, getPlayer().getAttackStrength());
	}

	public static float calculatePlayerDamage(Living entity, ItemStack item, float cooldown) {
		float attackAttribute = (float) mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
//		if (item != null) {
//			Multimap<String, AttributeModifier> attributes = item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
//			Collection<AttributeModifier> attackModifier = attributes.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
//			for (AttributeModifier modifier : attackModifier) {
//				attackAttribute += modifier.getAmount();
//			}
//
//			float enchantModifier = EnchantmentHelper.getModifierForCreature(item, entity.getCreatureAttribute());
//			attackAttribute *= (0.2F + cooldown * cooldown * 0.8F);
//			enchantModifier *= cooldown;
//			if (attackAttribute > 0.0F || enchantModifier > 0.0F) {
//				boolean hasKnockback = false;
//				boolean hasCritical = false;
//				hasCritical = hasKnockback && getPlayer().getFallDistance() > 0.0F && !getPlayer().isOnGround() && !getPlayer().isClimbing() && !getPlayer().isInFluid(Fluid.WATER) && !mc.player.isPotionActive(MobEffects.BLINDNESS) && !getPlayer().isRiding();
//				hasCritical = hasCritical && !mc.player.isSprinting();
//				if (hasCritical) {
//					attackAttribute *= 1.5F;
//				}
//				attackAttribute += enchantModifier;
//
//				attackAttribute = CombatRules.getDamageAfterMagicAbsorb(attackAttribute, (float) entity.getTotalArmorValue());
//				attackAttribute = Math.max(attackAttribute - entity.getAbsorptionAmount(), 0.0F);
//				return attackAttribute;
//			} else
//				return 0F;
//		}
		return attackAttribute;
	}

	public static float calculatePlayerDamageWithAttackSpeed(Living entity, ItemStack item) {
		float attackAttribute = calculatePlayerDamage(entity, item, 1.0F);
		if (item != null) {
			Multimap<String, AttributeModifier> attributes = item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
			Collection<AttributeModifier> speedModifier = attributes.get(SharedMonsterAttributes.ATTACK_SPEED.getName());
			float speedAttribute = (float) mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
			for (AttributeModifier modifier : speedModifier) {
				speedAttribute += (float) modifier.getAmount();
				attackAttribute *= speedAttribute;
			}
		}
		return attackAttribute;
	}

	public static int getPotionY() {
		boolean hidden = true;
		Collection<PotionEffect> effects = getPlayer().getEffects();
		if (!effects.isEmpty()) {
			for (PotionEffect effect : effects) {
				if (effect.showParticles()) hidden = false;
				Potion potion = effect.getEffect();
				if (potion.hasIcon()) {
					if (potion.getType() != Potion.Type.BENEFICIAL) {
						return 52;
					}
				}
			}
		}
		return hidden ? 0 : 26;
	}

	/**
	 * @return True if the item is shift clickable.
	 */
	public static boolean isShiftable(ItemStack preferedItem) {
		if (preferedItem == null || preferedItem.isEmpty())
			return true;
		for (int o = 36; o < 45; o++) {
			if (mc.player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = mc.player.inventoryContainer.getSlot(o).getStack();
				if (Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(preferedItem.getItem())) {
					if (item.getCount() + preferedItem.getCount() <= preferedItem.getMaxStackSize())
						return true;
				}
			} else
				return true;
		}
		return false;
	}
}
