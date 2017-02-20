package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.mcwrapper.api.MCWrapper;

/**
 * Widget which displays the games FPS.
 * */
public class FPSWidget extends BackgroundWidget {

	public FPSWidget(WidgetManager menuManager) {
		super("FPS", "Render the FPS which the game is running at", menuManager);
		
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		String fps = "FPS: " + MCWrapper.getMinecraft().getFPS();
		theme.drawStringWithShadow(fps, x, y, 0xFFFFFF);
		this.setWidth(theme.getStringWidth(fps) + 2);
		this.setHeight(theme.getStringHeight(fps));
	}

}
