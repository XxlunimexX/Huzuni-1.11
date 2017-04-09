package net.halalaboos.huzuni.mc;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.huzuni.mod.visual.Xray;

public final class Wrapper {

	private static final Huzuni huzuni = Huzuni.INSTANCE;
		
	private Wrapper() {

	}

	public static boolean shouldIgnoreCulling() {
		return Xray.INSTANCE.isEnabled() || Freecam.INSTANCE.isEnabled();
	}
}
