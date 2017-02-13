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

public class Reflection {

	private static Field blockHitDelay;
	private static Field persistantChatGUI;
	private static Field inGround;
	private static Field rightClickDelayTimer;
	private static Field session;
	private static Field timer;

	private static Timer timerObj;
	private static Session sessionObj;

	public static void initFields() {
		blockHitDelay = setAccessible(PlayerControllerMP.class, "field_78781_i", "blockHitDelay");
		persistantChatGUI = setAccessible(GuiIngame.class, "field_73840_e", "persistantChatGUI");
		inGround = setAccessible(EntityArrow.class, "field_70254_i", "inGround");
		rightClickDelayTimer = setAccessible(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");
		session = setAccessible(Minecraft.class, "field_71449_j", "session");
		timer = setAccessible(Minecraft.class, "field_71428_T", "timer");
	}

	private static Field setAccessible(Class clazz, String name, String deobfName) {
		try {
			return ReflectionHelper.findField(clazz, name, deobfName);
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
		if (sessionObj == null) {
			try {
				sessionObj = (Session) Reflection.session.get(Minecraft.getMinecraft().session);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			try {
				session.set(sessionObj, _session);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
