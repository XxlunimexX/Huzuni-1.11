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
	 * Used for Speedmine, to adjust the delay between hitting blocks.
	 *
	 * Field: PlayerControllerMP#blockHitDelay
	 */
	private static Field blockHitDelay;

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

	/**
	 * Used for Fastplace - we could get around this and have it be packet/tick based, but this
	 * is a bit easier.
	 *
	 * Field: Minecraft#rightClickDelayTimer
	 */
	private static Field rightClickDelayTimer;

	/**
	 * Used for the timer mod.
	 *
	 * Field: Minecraft#timer
	 */
	private static Field timer;

	//Timer instance
	private static Timer timerObj;

	static {
		blockHitDelay = setAccessible(PlayerControllerMP.class, "field_78781_i", "blockHitDelay");
		persistantChatGUI = setAccessible(GuiIngame.class, "field_73840_e", "persistantChatGUI");
		inGround = setAccessible(EntityArrow.class, "field_70254_i", "inGround");
		rightClickDelayTimer = setAccessible(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");
		timer = setAccessible(Minecraft.class, "field_71428_T", "timer");
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

	public static int getRightClickDelayTimer() {
		try {
			return rightClickDelayTimer.getInt(Minecraft.getMinecraft());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void setBlockHitDelay(int hitDelay, PlayerControllerMP controllerMP) {
		try {
			blockHitDelay.setInt(controllerMP, hitDelay);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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

	public static void setTimerSpeed(float amount) {
		if (timerObj == null) {
			try {
				timerObj = (net.minecraft.util.Timer) Reflection.timer.get(Minecraft.getMinecraft());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			timerObj.timerSpeed = amount;
		}
	}

	public static void setRightClickDelayTimer(int speed) {
		try {
			rightClickDelayTimer.setInt(Minecraft.getMinecraft(), speed);
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
