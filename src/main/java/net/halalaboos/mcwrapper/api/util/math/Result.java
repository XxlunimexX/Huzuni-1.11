package net.halalaboos.mcwrapper.api.util.math;

import net.halalaboos.mcwrapper.api.util.enums.Face;

public enum Result {

	MISS, BLOCK, ENTITY;

	private Face face;

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}
}
