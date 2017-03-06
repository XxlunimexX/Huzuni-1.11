package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.api.util.gl.RenderUtils;
import net.halalaboos.huzuni.indev.gui.components.Slider;
import net.halalaboos.huzuni.indev.gui.render.ComponentRenderer;
import org.lwjgl.input.Mouse;


/**
 * Basic button renderer. <br/>
 * Created by Brandon Williams on 2/19/2017.
 */
public class SliderRenderer implements ComponentRenderer<Slider> {

    private final BasicRenderer renderer;

    public SliderRenderer(BasicRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void pre(Slider slider) {

    }

    @Override
    public void render(Slider slider) {
        GLManager.glColor(renderer.getPalette().getDefaultComponent());
        RenderUtils.drawRect(slider.getArea());
        GLManager.glColor(RenderUtils.getColorWithAffects(renderer.getPalette().getHighlightComponent(), slider.isSliding() || slider.isHovered(), Mouse.isButtonDown(0)));
        RenderUtils.drawRect(slider.getSliderBar());
    }

    @Override
    public void post(Slider slider) {

    }
}
