package net.halalaboos.huzuni.indev.gui;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static org.lwjgl.opengl.GL11.*;

/**
 * Stores image information, can be rendered using an image renderer. <br/>
 * Accepts an array of pixels along with a width and a height. <br/>
 * Create an image object after loading your own data into memory. <br/>
 * Created by Brandon Williams on 3/7/2017.
 */
public class ImageData {

    private final int id, width, height;

    public ImageData(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public ImageData(int[] pixels, int width, int height) {
        this.id = GLUtils.genTexture();
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
        GLUtils.applyTexture(id, width, height, buffer, GL_NEAREST, GL_REPEAT);
    }

    /**
     * Binds to this image, allowing for rendering.
     * */
    public void bind() {
        getGLStateManager().bindTexture(id);
    }

    /**
     * Unbinds this image.
     * */
    public void unbind() {
        getGLStateManager().bindTexture(0);
    }

    /**
     * @return The id lwjgl created for representing this image.
     * */
    public int getId() {
        return id;
    }

    /**
     * @return This image's width.
     * */
    public int getWidth() {
        return width;
    }

    /**
     * @return This image's height.
     * */
    public int getHeight() {
        return height;
    }

    /**
     * Destroys this image data.
     * */
    public void destroy() {
        // TODO: destroy the texture.
    }
}
