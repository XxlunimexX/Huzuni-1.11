package net.halalaboos.mcwrapper.api.util;

import net.halalaboos.mcwrapper.api.util.math.Vector3i;

public enum Face {

	DOWN("Down", null, new Vector3i(0, -1, 0), 1),
	UP("Up", null, new Vector3i(0, 1, 0), 0),
	NORTH("North", "Towards negative Z", new Vector3i(0, 0, -1), 3),
	SOUTH("South", "Towards positive Z", new Vector3i(0, 0, 1), 2),
	WEST("West", "Towards negative X", new Vector3i(-1, 0, 0), 5),
	EAST("East", "Towards positive X", new Vector3i(1, 0, 0), 4);

	public static final Face[] VALUES = new Face[6];

	/**
	 * The index of the opposite face in the {@link #VALUES} array.
	 */
	private int oppositeIndex;

	private String info;
	private String direction;

	private Vector3i directionVector;

	Face(String direction, String info, Vector3i directionVector, int oppositeIndex) {
		this.direction = direction;
		this.info = info;
		this.directionVector = directionVector;
		this.oppositeIndex = oppositeIndex;
	}

	/**
	 * The axis info for the face (e.g. 'Towards negative Z' for {@link #NORTH})
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * The name of the direction (e.g. 'North', 'South', etc.)
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @return The opposite face.
	 */
	public Face getOppositeFace() {
		return VALUES[oppositeIndex];
	}

	/**
	 * The {@link Vector3i vector} for the face/direction.
	 */
	public Vector3i getDirectionVector() {
		return directionVector;
	}
}
