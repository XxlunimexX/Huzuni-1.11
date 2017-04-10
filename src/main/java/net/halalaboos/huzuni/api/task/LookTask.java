package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Task which allows the players rotations to be faked server-side.
 * */
public class LookTask extends BasicTask {

	protected float yaw, pitch, oldYaw, oldPitch;

	protected boolean reset = true;

	public LookTask(Nameable handler) {
		this(handler, 0F, 0F);
	}
	
	public LookTask(Nameable handler, double x, double y, double z) {
		super(handler);
		addDependency("look");
		float[] rotations = MinecraftUtils.getRotationsNeeded(x, y, z);
		this.yaw = rotations[0];
		this.pitch = rotations[1];
	}
	
	public LookTask(Nameable handler, float yaw, float pitch) {
		super(handler);
		addDependency("look");
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
	public void setRotations(Vector3i position, Face face) {
		float[] rotations = MinecraftUtils.getRotationsNeeded(position.getX() + 0.5F + (float) (face.getDirectionVector().getX()) / 2F, position.getY() + 0.5F + (float) (face.getDirectionVector().getY()) / 2F, position.getZ() + 0.5F + (float) (face.getDirectionVector().getZ()) / 2F);
		this.yaw = rotations[0];
		this.pitch = rotations[1];
	}
	
	public void setRotations(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void onTaskCancelled() {
	}
}
