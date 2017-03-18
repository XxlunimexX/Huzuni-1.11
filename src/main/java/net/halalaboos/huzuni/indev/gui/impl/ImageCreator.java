package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.indev.gui.ImageData;
import net.halalaboos.huzuni.indev.gui.TypeCreator;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Used to instantiate images. <br/>
 * Creation of each image is as follows: (String) params[0] = file name. <br/>
 * Created by Brandon Williams on 3/7/2017.
 */
public class ImageCreator extends TypeCreator<ImageData> {

    @Override
    protected ImageData createObject(String name, Object... params) {
        if (params != null && params.length >= 1) {
            try {
                // Reads a buffered image.
                BufferedImage bufferedImage = ImageIO.read(ImageCreator.class.getResourceAsStream(Huzuni.ASSETS_LOCATION + "textures/" + params[0]));
                // BufferedImage bufferedImage = ImageIO.read(ImageCreator.class.getResourceAsStream(FilenameUtils.separatorsToSystem(FilenameUtils.normalize(Huzuni.ASSETS_LOCATION + "textures/" + params[0]))));
                // Create an array to hold the pixels of the loaded image.
                int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
                // Create an image data and put the buffered images pixels into the pixel array.
                return new ImageData(bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth()), bufferedImage.getWidth(), bufferedImage.getHeight());
            } catch (Exception e) {
                Huzuni.LOGGER.log(Level.FATAL, String.format("Unable to load image: '%s' Error: %s", params[0], e.getMessage()));
            }
        }
        return null;
    }
}
