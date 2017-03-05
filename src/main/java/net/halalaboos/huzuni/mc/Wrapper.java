package net.halalaboos.huzuni.mc;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.huzuni.mod.visual.Xray;
import net.minecraft.client.multiplayer.WorldClient;

public final class Wrapper {

	private static final Huzuni huzuni = Huzuni.INSTANCE;
		
	private Wrapper() {

	}
	
	public static void loadWorld(WorldClient world) {
		if (world != null) {
			if (huzuni.settings.firstUse.isEnabled()) {
				huzuni.addChatMessage("Welcome to huzuni!");
				huzuni.addChatMessage("Press right shift to open up the settings menu!");
				huzuni.addChatMessage("Type \".help\" for a list of commands!");
				huzuni.settings.firstUse.setEnabled(false);
			}
		}
		huzuni.lookManager.cancelTask();
	}

	public static boolean shouldIgnoreCulling() {
		return Xray.INSTANCE.isEnabled() || Freecam.INSTANCE.isEnabled();
	}
}
