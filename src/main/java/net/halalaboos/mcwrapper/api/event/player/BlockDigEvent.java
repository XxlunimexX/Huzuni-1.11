package net.halalaboos.mcwrapper.api.event.player;

import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.tukio.Event;

public class BlockDigEvent extends Event {

	public final Vector3i position;
	public final int faceOrdinal;

	public float progress;
	public float maxDamage;
	public float multiplier = 1F;

	public BlockDigEvent(Vector3i position, float progress, int faceOrdinal) {
		this.position = position;
		this.progress = progress;
		this.faceOrdinal = faceOrdinal;
	}

}
