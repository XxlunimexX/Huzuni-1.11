package net.halalaboos.huzuni.api.util;

import net.halalaboos.mcwrapper.api.entity.living.Living;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Tracks a given entity and increments the player's rotation at a given rate. Used for smoother aiming.
 * */
public class EntityTracker {
	
	private Living entity;
	
	private float currentYaw, currentPitch, rotationRate = 5F;
	
	private boolean hasReached = false;
	
	public EntityTracker() {
		
	}
	
	/**
	 * Updates the fake rotations and calculates whether or not the rotations have fully rotated to meet the given entity.
	 * */
	public void updateRotations() {
		float[] rotations = MinecraftUtils.getRotationsNeeded(entity);
		float[] rotationCaps = MinecraftUtils.getEntityCaps(entity, 6.5F);
		float yawDifference = MinecraftUtils.getYawDifference(currentYaw, rotations[0]), pitchDifference = rotations[1] - currentPitch;
	    float absoluteYawDifference = Math.abs(yawDifference), absolutePitchDifference = Math.abs(pitchDifference);
		this.hasReached = absoluteYawDifference < rotationCaps[0] && absoluteYawDifference > -rotationCaps[0] && absolutePitchDifference < rotationCaps[1] && absolutePitchDifference > -rotationCaps[1];

		if (this.hasReached) {
			float realYawDifference = MinecraftUtils.getYawDifference(getPlayer().getYaw() % 360F, rotations[0]), realPitchDifference = rotations[1] - (getPlayer().getPitch() % 360F);
			if (realYawDifference < rotationCaps[0] && realYawDifference > -rotationCaps[0])
				currentYaw = getPlayer().getYaw() % 360F;
			if (realPitchDifference < rotationCaps[1] && realPitchDifference > -rotationCaps[1])
				currentPitch =  getPlayer().getPitch() % 360F;
		} else {
			if (yawDifference > rotationCaps[0] || yawDifference < -rotationCaps[0]) {
				float yawAdjustment = clamp(yawDifference, rotationRate);
				if (yawAdjustment < 0)
					currentYaw += yawAdjustment;
        		else if (yawAdjustment > 0)
        			currentYaw += yawAdjustment;
			}
			if (pitchDifference > rotationCaps[1] || pitchDifference < -rotationCaps[1]) {
				float pitchAdjustment = clamp(pitchDifference, rotationRate);
				if (pitchAdjustment < 0)
					currentPitch += pitchAdjustment;
        		else if (pitchAdjustment > 0)
        			currentPitch += pitchAdjustment;
			}
		}
	}
	
	/**
	 * Clamps the given input by the max value.
	 * @param input The given input.
	 * @param max The given max.
	 * */
	private float clamp(float input, float max) {
		if (input > max)
			input = max;
		if (input < -max)
			input = -max;
		return input;
	}

	/**
	 * Resets the fake rotation.
	 * */
	public void reset() {
		hasReached = false;
		if (getPlayer() != null) {
			currentYaw = getPlayer().getYaw() % 360;
			currentPitch = getPlayer().getPitch() % 360;
		}
	}
	
 	public void setEntity(Living entity) {
 		if (entity != this.entity) {
 			this.entity = entity;
 			reset();
 		}
 	}
 	
 	public boolean hasReached() {
 		return hasReached;
 	}

	public float getYaw() {
		return currentYaw;
	}
	
	public float getPitch() {
		return currentPitch;
	}

	public float getRotationRate() {
		return rotationRate;
	}

	public void setRotationRate(float rotationRate) {
		this.rotationRate = rotationRate;
	}
	
	public boolean hasEntity() {
		return entity != null;
	}
	
}
