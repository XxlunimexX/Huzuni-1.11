package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.api.util.render.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic checkbox renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class CheckboxRenderer implements ComponentRenderer<Checkbox> {

    private final FontRenderer fontRenderer;

    public CheckboxRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
    }

    @Override
    public void pre(Checkbox checkbox) {

    }

    @Override
    public void render(Checkbox checkbox) {
        GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.GREY, checkbox.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(checkbox.getCheckbox());
        if (checkbox.isEnabled()) {
            GLManager.glColor(RenderUtils.getColorWithAffects(checkbox.isEnabled() ? BasicRenderer.ENABLED : BasicRenderer.GREY, checkbox.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(new int[] { checkbox.getX() + 2, checkbox.getY() + 2, 8, 8 });
        }
        fontRenderer.drawString(checkbox.getFont(), checkbox.getText(), checkbox.getX() + 2 + net.halalaboos.huzuni.indev.gui.components.Checkbox.CHECKBOX_SIZE, checkbox.getY(), 0xFFFFFFFF);
    }

    @Override
    public void post(Checkbox checkbox) {

    }
}
