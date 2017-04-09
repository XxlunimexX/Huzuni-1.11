package net.halalaboos.huzuni.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * This is used to make modifying private/final fields pretty simple.  For a while we were messing
 * around with access transformers, but ran into some issues post-compiling, so for now we will be using
 * reflection for the few fields that we need access to.
 */
public class Reflection {
	public static void setSession(Session _session) {
		try {
			ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), _session, "field_71449_j", "session");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
