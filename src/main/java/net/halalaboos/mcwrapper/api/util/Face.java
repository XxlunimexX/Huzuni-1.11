package net.halalaboos.mcwrapper.api.util;


import net.halalaboos.mcwrapper.api.util.math.Vector3i;

public enum Face {

	DOWN("down", new Vector3i(0, -1, 0)),
	UP("up", new Vector3i(0, 1, 0)),
	NORTH("north", new Vector3i(0, 0, -1)),
	SOUTH("south", new Vector3i(0, 0, 1)),
	WEST("west", new Vector3i(-1, 0, 0)),
	EAST("east", new Vector3i(1, 0, 0));

	private String name;
	private Vector3i direction;

	Face(String name, Vector3i direction) {
		this.name = name;
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public Vector3i getDirection() {
		return direction;
	}
}
