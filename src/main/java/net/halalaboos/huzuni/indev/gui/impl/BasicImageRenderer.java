package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.gui.ImageData;
import net.halalaboos.huzuni.indev.gui.render.ImageRenderer;

/**
 * Basic image renderer. <br/>
 * Created by Brandon Williams on 3/15/2017.
 */
public class BasicImageRenderer implements ImageRenderer {

    @Override
    public void draw(ImageData image, int x, int y, int width, int height) {
        image.bind();
        GLUtils.drawTextureRect(x, y, width, height, 0F, 0F, 1F, 1F);
        image.unbind();
    }

    @Override
    public void draw(ImageData image, int x, int y, float scale) {
        image.bind();
        GLUtils.drawTextureRect(x, y, image.getWidth() * scale, image.getHeight() * scale, 0F, 0F, 1F, 1F);
        image.unbind();
    }
}
