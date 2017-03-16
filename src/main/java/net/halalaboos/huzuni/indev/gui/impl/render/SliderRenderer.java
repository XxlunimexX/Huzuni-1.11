package net.halalaboos.huzuni.indev.gui.impl.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.indev.gui.impl.BasicComponentRenderer;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;
import org.lwjgl.input.Mouse;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.*;


/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class SliderRenderer extends BasicComponentRenderer<Slider> {

    public SliderRenderer(Toolbox toolbox, FontRenderer fontRenderer, ImageRenderer imageRenderer) {
        super(toolbox, fontRenderer, imageRenderer);
    }

    @Override
    public void render(Slider slider) {
        GLUtils.glColor(toolbox.get(COLOR_DEFAULT));
        GLUtils.drawRect(slider.getArea());
        GLUtils.glColor(GLUtils.getColorWithAffects(toolbox.get(COLOR_HIGHLIGHT), slider.isSliding() || slider.isHovered(), Mouse.isButtonDown(0)));
        GLUtils.drawRect(slider.getSliderBar());
    }
}
