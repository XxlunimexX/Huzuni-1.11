package net.halalaboos.huzuni.mc;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.event.KeyPressEvent;
import net.halalaboos.huzuni.api.event.LoadWorldEvent;
import net.halalaboos.huzuni.api.event.MouseClickEvent;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.huzuni.mod.visual.Xray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

public final class Wrapper {

	private static final Huzuni huzuni = Huzuni.INSTANCE;
		
	private Wrapper() {
		
	}
	
	public static void onMouseClicked(int buttonId) {
		huzuni.eventManager.invoke(new MouseClickEvent(buttonId));
		if (buttonId == 2) {
			if (Minecraft.getMinecraft().objectMouseOver != null) {
				RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
				if (result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityPlayer) {
					if (huzuni.friendManager.isFriend(result.entityHit.getName())) {
						huzuni.addChatMessage(String.format("Removed %s as a friend.", result.entityHit.getName()));
						huzuni.friendManager.removeFriend(result.entityHit.getName());
						huzuni.friendManager.save();
					} else {
						huzuni.friendManager.addFriend(result.entityHit.getName());
						huzuni.addChatMessage(String.format("Added %s as a friend.", result.entityHit.getName()));
						huzuni.friendManager.save();
					}
				}
			}
		}
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
		huzuni.eventManager.invoke(new LoadWorldEvent(world));
	}
	
	public static void keyTyped(int keyCode) {
		huzuni.guiManager.widgetManager.keyTyped(keyCode);
		huzuni.eventManager.invoke(new KeyPressEvent(keyCode));
	}

	public static boolean shouldIgnoreCulling() {
		return Xray.INSTANCE.isEnabled() || Freecam.INSTANCE.isEnabled();
	}
}
