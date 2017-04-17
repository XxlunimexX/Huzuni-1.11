package net.halalaboos.mcwrapper.api.util.enums;

import net.halalaboos.mcwrapper.api.util.math.Vector3i;

public enum Face {

	DOWN("Down", Direction.NEGATIVE, Axis.Y, new Vector3i(0, -1, 0), 1),
	UP("Up", Direction.POSITIVE, Axis.Y, new Vector3i(0, 1, 0), 0),
	NORTH("North", Direction.NEGATIVE, Axis.Z, new Vector3i(0, 0, -1), 3),
	SOUTH("South", Direction.POSITIVE, Axis.Z, new Vector3i(0, 0, 1), 2),
	WEST("West", Direction.NEGATIVE, Axis.X, new Vector3i(-1, 0, 0), 5),
	EAST("East", Direction.POSITIVE, Axis.X, new Vector3i(1, 0, 0), 4);

	/**
	 * The index of the opposite face in the {@link #VALUES} array.
	 */
	private int oppositeIndex;

	private String info;
	private String name;

	private Vector3i directionVector;

	private Direction direction;
	private Axis axis;

	Face(String name, Direction direction, Axis axis, Vector3i directionVector, int oppositeIndex) {
		this.name = name;
		this.direction = direction;
		this.axis = axis;
		this.directionVector = directionVector;
		this.oppositeIndex = oppositeIndex;
		this.info = direction.getName() + " " + axis.getName();
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
	public String getName() {
		return name;
	}

	/**
	 * @return The opposite face.
	 */
	public Face getOppositeFace() {
		return values()[oppositeIndex];
	}

	/**
	 * The {@link Vector3i vector} for the face/direction.
	 */
	public Vector3i getDirectionVector() {
		return directionVector;
	}

	public int getOffsetX() {
		return this.axis == Axis.X ? this.direction.getOffset() : 0;
	}

	public int getOffsetY() {
		return this.axis == Axis.Y ? this.direction.getOffset() : 0;
	}

	public int getOffsetZ() {
		return this.axis == Axis.Z ? this.direction.getOffset() : 0;
	}

	public Vector3i getOffsetVector() {
		return new Vector3i(getOffsetX(), getOffsetY(), getOffsetZ());
	}

	public Face rotateY() {
		switch (this) {
			case NORTH: return EAST;
			case EAST: return SOUTH;
			case SOUTH: return WEST;
			case WEST: return NORTH;
		}
		throw new IllegalStateException();
	}

	enum Direction {

		POSITIVE("Towards positive", 1), NEGATIVE("Towards negative", -1);

		private String name;
		private int offset;

		Direction(String name, int offset) {
			this.name = name;
			this.offset = offset;
		}

		public int getOffset() {
			return offset;
		}

		public String getName() {
			return name;
		}
	}

	enum Axis {

		X("x"), Y("y"), Z("z");

		private String name;

		Axis(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
