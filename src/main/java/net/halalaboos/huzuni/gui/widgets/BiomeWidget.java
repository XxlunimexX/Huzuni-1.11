package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class BiomeWidget extends BackgroundWidget {

	public BiomeWidget(WidgetManager menuManager) {
		super("Biome", "Renders the biome you are currently inside", menuManager);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
        String biome = "Biome: " + getPlayer().getCurrentBiome();
		theme.drawStringWithShadow(biome, x, y, 0xFFFFFF);
		this.setWidth(theme.getStringWidth(biome) + 2);
		this.setHeight(theme.getStringHeight(biome));
	}
}
