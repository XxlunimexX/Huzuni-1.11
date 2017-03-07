package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.indev.gui.Image;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Basic implementation of the image interface. <br/>
 * Accepts an array of pixels along with a width and a height. <br/>
 * Or create an image after loading your own image into memory. <br/>
 * Created by Brandon Williams on 3/7/2017.
 */
public class BasicImage implements Image {

    private final int id, width, height;

    public BasicImage(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public BasicImage(int[] pixels, int width, int height) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
