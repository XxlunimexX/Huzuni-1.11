package net.halalaboos.huzuni.api.util;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.getController;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Finds the closest block within a radius from the player position, starting with the blocks the player is facing.
 * */
public abstract class BlockLocator {

	private boolean distanceCheck = true;
	
	private Vector3i position;
	
	private Face face;
	
	/**
	 * @return True if a block could be located within the radius.
	 * @param radius The radius around the player to check for blocks.
	 * @param ignoreFace When true, the {@link Face} given from the {@link getFace( Face )} function will be not be checked if null.
	 * */
	public boolean locateClosest(float radius, boolean ignoreFace) {
		Vector3i closestPosition = null;
		Face closestFace = null;
		double closestDistance = 0;
		Vector3i directionVector = getPlayer().getFace().getDirectionVector();
		int xIncrement = directionVector.getX() != 0 ? directionVector.getX() : 1;
		int zIncrement = directionVector.getZ() != 0 ? directionVector.getZ() : 1;
		
		for (double i = -(radius * xIncrement); check(i, (radius * xIncrement), directionVector.getX() < 0); i += xIncrement) {
			for (double j = -radius; j < radius; j++) {
				for (double k = -(radius * zIncrement); check(k, (radius * zIncrement), directionVector.getZ() < 0); k += zIncrement) {
					if (i == 0 && j >= -1 && j <= getPlayer().getEyeHeight() && k == 0)
						continue;
					Vector3i playerPos = getPlayer().getBlockPosition();
					Vector3i position = new Vector3i(i, j, k).add(playerPos);
					double distance = getPlayer().getDistanceTo(position);
					if (isWithinDistance(distance) && isValidBlock(position)) {
						Face face = getFace(position);
						if (face != null || ignoreFace) {
							if (closestPosition != null) {
								if (distance < closestDistance) {
									closestPosition = position;
									closestFace = face;
									closestDistance = distance;
								}
							} else {
								closestPosition = position;
								closestFace = face;
								closestDistance = distance;
							}
						}
					}
				}
			}
		}
		this.position = closestPosition;
		this.face = closestFace;
		return closestPosition != null;
	}
	
	/**
	 * @return True if the absolute value of {@code index} is within the absolute value of {@code max}.
	 * @param index The double
	 * @param max
	 * @param flip
	 * */
	private boolean check(double index, double max, boolean flip) {
		return flip ? index > max : index < max;
	}
	
	/**
	 * @return True if the {@link Block} located at the {@link Vector3i} is considered 'valid'.
	 * */
	protected abstract boolean isValidBlock(Vector3i position);
	
	/**
	 * @return The {@link Face} needed for the {@link Vector3i}
	 * */
	protected abstract Face getFace(Vector3i position);
	
	/**
	 * @return True if the {@code distance} is less than the block reach distance.
	 * */
	protected boolean isWithinDistance(double distance) {
		return !distanceCheck || distance < getController().getBlockReach();
	}
	
	/**
	 * Resets the position and face found with the block locator
	 * */
	public void reset() {
		position = null;
		face = null;
	}
	
	public boolean isDistanceCheck() {
		return distanceCheck;
	}

	public void setDistanceCheck(boolean distanceCheck) {
		this.distanceCheck = distanceCheck;
	}

	public Vector3i getPosition() {
		return position;
	}

	public Face getFace() {
		return face;
	}

	/**
	 * @return True if the locator has a position and face
	 * */
	public boolean hasPosition() {
		return position != null && face != null;
	}
}
