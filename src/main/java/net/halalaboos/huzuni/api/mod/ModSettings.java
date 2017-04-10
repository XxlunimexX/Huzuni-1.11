package net.halalaboos.huzuni.api.mod;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.node.impl.ColorNode;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.huzuni.api.node.impl.StringNode;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.util.gl.GLUtils;

import java.awt.*;

/**
 * Settings node which is applied to all mods. <br/>
 * Each mod settings node contains a color node and a toggleable node for the displayable option. <br/>
 * The display name of each mod is also held within the mod settings.
 * */
public class ModSettings extends Node {

	private final Mod mod;
	
	private final ColorNode displayColor = new ColorNode("Display Color", Color.WHITE, "The color the mod will be displayed with when enabled");
	
	private final Toggleable displayable = new Toggleable("Displayable", "Will allow the mod to be rendered in-game when enabled");
	
	private final StringNode displayName;
	
	public ModSettings(Mod mod) {
		super("settings", "Modify the settings of " + mod.getName());
		displayName = new StringNode("Display Name", mod.getName(), "Adjust the name this mod is displayed with in-game.");
		this.addChildren(displayable, displayColor, displayName);
		displayColor.setColor(GLUtils.getRandomColor());
		displayable.setEnabled(true);
		this.mod = mod;
	}

	@Override
	public boolean hasNode(JsonObject json) {
		return mod.hasNode(json);
	}

	public String getDisplayName() {
		return displayName.getText();
	}

	public void setDisplayName(String displayName) {
		this.displayName.setText(displayName);
	}
	
	public Color getDisplayColor() {
		return displayColor.getColor();
	}
	
	public void setDisplayColor(Color color) {
		this.displayColor.setColor(color);
	}
	
	public boolean isDisplayable() {
		return displayable.isEnabled();
	}

	public void setDisplayable(boolean displayable) {
		this.displayable.setEnabled(displayable);
	}


}
