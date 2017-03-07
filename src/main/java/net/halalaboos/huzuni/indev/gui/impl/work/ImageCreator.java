package net.halalaboos.huzuni.indev.gui.impl.work;

import net.halalaboos.huzuni.indev.gui.Image;
import net.halalaboos.huzuni.indev.gui.impl.BasicImage;
import net.halalaboos.huzuni.indev.gui.impl.BasicWorkstation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Brandon Williams on 3/7/2017.
 */
public class ImageCreator extends BasicWorkstation<Image> {

    @Override
    protected Image createObject(String name, Object... params) {
        try {
            BufferedImage bufferedImage = ImageIO.read(ImageCreator.class.getClass().getResourceAsStream(name));
            int[] pixels = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
            return new BasicImage(bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth()), bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
