package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Dropdown;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class DropdownRenderer implements ComponentRenderer<Dropdown> {

    private final BasicRenderer renderer;

    public DropdownRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(Dropdown dropdown) {

    }

    @Override
    public void render(Dropdown dropdown) {
        ColorPalette palette = renderer.getPalette();
        GLManager.glColor(RenderUtils.getColorWithAffects(palette.getDefaultComponent(), dropdown.isExpanded() || dropdown.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(dropdown.getArea());
        renderer.getFontRenderer().drawString(dropdown.getFont(), dropdown.getSelectedItem().toString(), dropdown.getX() + 3, dropdown.getY() + 1, palette.getEnabledText().getRGB());
        if (dropdown.isExpanded()) {
            for (int i = 0; i < dropdown.getItems().length; i++) {
                int[] area = dropdown.getItemArea(i);
                boolean hovered = dropdown.getHoveredItem(renderer.getMouseX(), renderer.getMouseY()) == i;
                int color = RenderUtils.getColorWithAffects((dropdown.getSelected() == i ? palette.getEnabledText() : palette.getDisabledText()), dropdown.isHovered() && hovered, Mouse.isButtonDown(0)).getRGB();

                GLManager.glColor(RenderUtils.getColorWithAffects(palette.getDefaultComponent(), dropdown.isHovered() && hovered, Mouse.isButtonDown(0)));
                RenderUtils.drawRect(area);

                drawString(dropdown.getFont(), dropdown.getItems()[i].toString(), area[0], area[1] + area[3] / 2, color);
            }
        }
    }

    @Override
    public void post(Dropdown dropdown) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        renderer.getFontRenderer().drawString(fontData, text, x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
