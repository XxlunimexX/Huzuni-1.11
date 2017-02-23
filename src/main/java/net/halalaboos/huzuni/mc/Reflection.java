package net.halalaboos.huzuni.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * This is used to make modifying private/final fields pretty simple.  For a while we were messing
 * around with access transformers, but ran into some issues post-compiling, so for now we will be using
 * reflection for the few fields that we need access to.
 *
 * There will be little to no performance impact from this, especially since we only look for the fields
 * on startup.
 */
public class Reflection {

	/**
	 * Used to change the chat gui to the Huzuni one.
	 *
	 * Field: GuiIngame#persistantChatGUI
	 */
	private static Field persistantChatGUI;

	/**
	 * Used for Projectiles.
	 *
	 * Field: EntityArrow#inGround
	 */
	private static Field inGround;

	static {
		persistantChatGUI = setAccessible(GuiIngame.class, "field_73840_e", "persistantChatGUI");
		inGround = setAccessible(EntityArrow.class, "field_70254_i", "inGround");
	}

	private static Field setAccessible(Class clazz, String name, String deobfName) {
		try {
			Field field = ReflectionHelper.findField(clazz, name, deobfName);
			field.setAccessible(true);
			return field;
		} catch (ReflectionHelper.UnableToFindFieldException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean getInGround(EntityArrow arrow) {
		try {
			return inGround.getBoolean(arrow);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void setPersistantChatGUI(GuiIngame gui, GuiNewChat chatGUI) {
		try {
			persistantChatGUI.set(gui, chatGUI);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setSession(Session _session) {
		try {
			ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), _session, "field_71449_j", "session");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
