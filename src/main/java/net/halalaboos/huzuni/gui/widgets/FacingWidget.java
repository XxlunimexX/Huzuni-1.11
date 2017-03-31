package net.halalaboos.huzuni.gui.widgets;

import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.mcwrapper.api.util.enums.Face;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class FacingWidget extends BackgroundWidget {
		
	public FacingWidget(WidgetManager menuManager) {
		super("Facing", "Render which direction the player is facing (based on x/z)", menuManager);
	}

	@Override
	public void renderMenu(int x, int y, int width, int height) {
		super.renderMenu(x, y, width, height);
		Face face = getPlayer().getFace();
		String text = face.getName() + " (" + face.getInfo() + ")";
        theme.drawStringWithShadow(text, x, y, 0xFFFFFF);
		this.setWidth(theme.getStringWidth(text) + 2);
		this.setHeight(theme.getStringHeight(text));
	}
}
