package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Task which allows the players rotations to be faked server-side.
 * */
public class LookTask implements Task {
		
	protected final Mod mod;
	
	protected float yaw, pitch, oldYaw, oldPitch;

	protected boolean reset = true, running = false;
	
	public LookTask(Mod mod) {
		this(mod, 0F, 0F);
	}
	
	public LookTask(Mod mod, double x, double y, double z) {
		this.mod = mod;
		float[] rotations = MinecraftUtils.getRotationsNeeded(x, y, z);
		this.yaw = rotations[0];
		this.pitch = rotations[1];
	}
	
	public LookTask(Mod mod, float yaw, float pitch) {
		this.mod = mod;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	@Override
	public void onPreUpdate() {
		setRotation();
	}
	
	@Override
	public void onPostUpdate() {
		resetRotation();
	}

	/**
	 * Sets the player rotations and saves the previous player rotations.
	 * */
	protected void setRotation() {
		oldYaw = getPlayer().getYaw();
		oldPitch = getPlayer().getPitch();
		getPlayer().setPitch(pitch);
		getPlayer().setYaw(yaw);
	}

	/**
	 * Resets the rotations of the player (if reset is true)
	 * */
	protected void resetRotation() {
		if (reset) {
			getPlayer().setPitch(oldPitch);
			getPlayer().setYaw(oldYaw);
		}
	}
	
	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	/**
	 * Sets the rotations to view the given block position at the face given.
	 * */
	public void setRotations(BlockPos position, EnumFacing face) {
		float[] rotations = MinecraftUtils.getRotationsNeeded(position.getX() + 0.5F + (float) (face.getDirectionVec().getX()) / 2F, position.getY() + 0.5F + (float) (face.getDirectionVec().getY()) / 2F, position.getZ() + 0.5F + (float) (face.getDirectionVec().getZ()) / 2F);
		this.yaw = rotations[0];
		this.pitch = rotations[1];
	}
	
	public void setRotations(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public Mod getMod() {
		return mod;
	}

	@Override
	public void onTaskCancelled() {
	}
	
}
