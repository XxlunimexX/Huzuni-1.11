package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import org.lwjgl.input.Mouse;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;

/**
 * Basic checkbox renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class CheckboxRenderer extends BasicComponentRenderer<Checkbox> {

    public CheckboxRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }

    @Override
    public void render(Checkbox checkbox) {
        GLUtils.glColor(GLUtils.getColorWithAffects(toolbox.get(COLOR_DEFAULT), checkbox.isHovered(), Mouse.isButtonDown(0)));
        GLUtils.drawRect(checkbox.getCheckbox());
        if (checkbox.isEnabled()) {
            GLUtils.glColor(GLUtils.getColorWithAffects(checkbox.isEnabled() ? toolbox.get(COLOR_HIGHLIGHT) : toolbox.get(COLOR_DEFAULT), checkbox.isHovered(), Mouse.isButtonDown(0)));
            GLUtils.drawRect(new int[] { checkbox.getX() + 2, checkbox.getY() + 2, 8, 8 });
        }
        fontRenderer.drawString(checkbox.getFont(), checkbox.getText(), checkbox.getX() + 2 + net.halalaboos.huzuni.indev.gui.components.Checkbox.CHECKBOX_SIZE, checkbox.getY(), 0xFFFFFFFF);
    }
}
