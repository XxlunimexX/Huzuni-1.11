package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Dropdown;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

import java.awt.*;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class DropdownRenderer implements ComponentRenderer<Dropdown> {

    private final Color buttonOn = new Color(255, 255, 255, 255);

    private final Color buttonOff = new Color(138, 138, 138, 255);

    private final FontRenderer fontRenderer;

    public DropdownRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void pre(Dropdown dropdown) {

    }

    @Override
    public void render(Dropdown dropdown) {
        GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.GREY, dropdown.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(dropdown.getArea());
        if (dropdown.isExpanded()) {
            for (int i = 0; i < dropdown.getItems().length; i++) {
                int[] area = dropdown.getItemArea(i);
                // boolean hovered = dropdown.getHoveredItem(mouse x, mouse y) == i;
                int color = RenderUtils.getColorWithAffects((dropdown.getSelected() == i ? buttonOn : buttonOff), dropdown.isHovered(), Mouse.isButtonDown(0)).getRGB();

                GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.GREY, dropdown.isHovered(), Mouse.isButtonDown(0)));
                RenderUtils.drawRect(area);

                drawString(dropdown.getFont(), dropdown.getItems()[i].getName(), area[0], area[1] + area[3] / 2, color);
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
        fontRenderer.drawString(fontData, text, x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
