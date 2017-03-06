package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;

/**
 * Basic checkbox renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class CheckboxRenderer implements ComponentRenderer<Checkbox> {

    private final BasicRenderer renderer;

    public CheckboxRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(Checkbox checkbox) {

    }

    @Override
    public void render(Checkbox checkbox) {
        ColorPalette palette = renderer.getPalette();
        GLManager.glColor(RenderUtils.getColorWithAffects(palette.getDefaultComponent(), checkbox.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(checkbox.getCheckbox());
        if (checkbox.isEnabled()) {
            GLManager.glColor(RenderUtils.getColorWithAffects(checkbox.isEnabled() ? palette.getHighlightComponent() : palette.getDefaultComponent(), checkbox.isHovered(), Mouse.isButtonDown(0)));
            RenderUtils.drawRect(new int[] { checkbox.getX() + 2, checkbox.getY() + 2, 8, 8 });
        }
        renderer.getFontRenderer().drawString(checkbox.getFont(), checkbox.getText(), checkbox.getX() + 2 + net.halalaboos.huzuni.indev.gui.components.Checkbox.CHECKBOX_SIZE, checkbox.getY(), 0xFFFFFFFF);
    }

    @Override
    public void post(Checkbox checkbox) {

    }
}
