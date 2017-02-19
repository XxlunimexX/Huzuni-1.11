package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.huzuni.api.util.render.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;


/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class SliderRenderer implements ComponentRenderer<Slider> {

    @Override
    public void pre(Slider slider) {

    }

    @Override
    public void render(Slider slider) {
        GLManager.glColor(BasicRenderer.GREY);
        RenderUtils.drawRect(slider.getArea());
        GLManager.glColor(RenderUtils.getColorWithAffects(BasicRenderer.ENABLED, slider.isSliding() || slider.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(slider.getSliderBar());
    }

    @Override
    public void post(Slider slider) {

    }
}
