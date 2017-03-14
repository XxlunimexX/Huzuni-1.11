package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.components.ComboBox;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.impl.ColorPack;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class ComboBoxRenderer implements ComponentRenderer<ComboBox> {

    private final BasicRenderer renderer;

    private final Toolbox toolbox;

    public ComboBoxRenderer(BasicRenderer renderer, Toolbox toolbox) {
        this.renderer = renderer;
        this.toolbox = toolbox;
    }

    @Override
    public void pre(ComboBox comboBox) {

    }

    @Override
    public void render(ComboBox comboBox) {
        ColorPack pack = renderer.getPack();
        boolean highlight = comboBox.isExpanded() || comboBox.isHovered();

        GLManager.glColor(RenderUtils.getColorWithAffects(pack.getDefaultComponent(), highlight, comboBox.isHovered() && Mouse.isButtonDown(0)));
        RenderUtils.drawRect(comboBox.getArea());
        renderer.getFontRenderer().drawString(comboBox.getFont(), comboBox.getSelectedItem().toString(), comboBox.getX() + 3, comboBox.getY() + 1, pack.getEnabledText().getRGB());
        if (comboBox.isExpanded()) {
            for (int i = 0; i < comboBox.getItems().length; i++) {
                int[] area = comboBox.getItemArea(i);
                boolean hovered = comboBox.getHoveredItem(toolbox.getMouseX(), toolbox.getMouseY()) == i;
                int color = RenderUtils.getColorWithAffects((comboBox.getSelected() == i ? pack.getEnabledText() : pack.getDisabledText()), comboBox.isHovered() && hovered, Mouse.isButtonDown(0)).getRGB();

                GLManager.glColor(RenderUtils.getColorWithAffects(pack.getDefaultComponent(), comboBox.isHovered() && hovered, Mouse.isButtonDown(0)));
                RenderUtils.drawRect(area);

                drawString(comboBox.getFont(), comboBox.getItems()[i].toString(), area[0], area[1] + area[3] / 2, color);
            }
        }
    }

    @Override
    public void post(ComboBox comboBox) {

    }

    /**
     * Originally draws a centered string with the default font renderer.
     * */
    public void drawString(FontData fontData, String text, int x, int y, int color) {
        renderer.getFontRenderer().drawString(fontData, text, x + 4, y - fontData.getFontHeight() / 2, color);
    }
}
