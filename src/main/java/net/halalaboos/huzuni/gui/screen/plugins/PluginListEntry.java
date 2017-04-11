package net.halalaboos.huzuni.gui.screen.plugins;

import net.halalaboos.huzuni.api.plugin.PluginData;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

public class PluginListEntry implements GuiListExtended.IGuiListEntry {
		
	private final PluginSelectionList list;
	
	private final PluginData pluginData;
	
	public PluginListEntry(PluginSelectionList list, PluginData pluginData) {
		this.list = list;
		this.pluginData = pluginData;
	}
	
	@Override
	public void setSelected(int id, int x, int y) {
	}

	@Override
	public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean mouseOver) {
		getMinecraft().getTextRenderer().render(TextColor.BOLD + pluginData.getName(), x + 2, y + 1, mouseOver ? 16777120 : 0xFFFFFF, true);
		getMinecraft().getTextRenderer().render("by " + pluginData.getAuthor(), x + 2, y + 12, mouseOver ? 16777120 : 0xFFFFFF, true);
	}

	@Override
	public boolean mousePressed(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		list.setSelected(slotIndex);
		return false;
	}

	@Override
	public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
		
	}

	public PluginData getPluginData() {
		return pluginData;
	}
	
}
