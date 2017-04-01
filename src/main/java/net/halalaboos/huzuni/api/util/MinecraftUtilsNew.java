package net.halalaboos.huzuni.api.util;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.mcwrapper.api.block.BlockTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Session;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.net.Proxy;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Porting old MinecraftUtils methods to use MCWrapper.  This will be renamed when it's done
 */
public class MinecraftUtilsNew {

	protected static final Minecraft mc = Minecraft.getMinecraft();


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
			if (getWorld().getBlock(position.getX(), position.getY(), position.getZ()) != BlockTypes.AIR) {
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

}
