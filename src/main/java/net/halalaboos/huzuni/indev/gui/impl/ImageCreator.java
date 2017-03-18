package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.ImageData;
import net.halalaboos.huzuni.indev.gui.TypeCreator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Used to instantiate images. <br/>
 * Created by Brandon Williams on 3/7/2017.
 */
public class ImageCreator extends TypeCreator<ImageData> {

    @Override
    protected ImageData createObject(String name, Object... params) {
        try {
            // Reads a buffered image.
            BufferedImage bufferedImage = ImageIO.read(ImageCreator.class.getResourceAsStream((String) params[0]));
            // Create an array to hold the pixels of the loaded image.
            int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
            // Create an image data and put the buffered images pixels into the pixel array.
            return new ImageData(bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth()), bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
