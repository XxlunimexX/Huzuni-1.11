package net.halalaboos.huzuni.api.util.gl;

import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static org.lwjgl.opengl.GL11.*;

/**
 * Simplifies some opengl functions. <br/>
 * Some of the functions found within this class were used within other projects and may not be seen used elsewhere within the client. This class has still been trimmed down.
 * */
public final class GLManager {
	
	public static List<Integer> textureIds = new ArrayList<>(),
			vbos = new ArrayList<>();
	
	private static int lastMouseX, lastMouseY;
	
	private static final Random random = new Random();
	
	private GLManager() {
		
	}
	
	public static void glScissor(int[] rect) {
		glScissor(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
	}
	
	public static void glScissor(float x, float y, float x1, float y1) {
		int factor = getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) (getMinecraft().getScreenResolution().height - (y1 * factor)), (int) ((x1 - x) * factor), (int) ((y1 - y) * factor));
	}
	
	public static int genTexture() {
		int textureId = GL11.glGenTextures();
		textureIds.add(textureId);
		return textureId;
	}
	
	/**
	 * @param filter determines how the texture will interpolate when scaling up / down. <br>
	 * GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
	 * @param wrap determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
	 * GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
	 * */
	public static int applyTexture(int texId, File file, int filter, int wrap) throws IOException {
		applyTexture(texId, ImageIO.read(file), filter, wrap);
        return texId;
	}
	
	/**
	 * @param filter determines how the texture will interpolate when scaling up / down. <br>
	 * GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
	 * @param wrap determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
	 * GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
	 * */
	public static int applyTexture(int texId, BufferedImage image, int filter, int wrap) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
        return texId;
	}
	
	/**
	 * @param filter determines how the texture will interpolate when scaling up / down. <br>
	 * GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
	 * @param wrap determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
	 * GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
	 * */
	public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glBindTexture(GL_TEXTURE_2D, 0);
        return texId;
	}
	
	public static int genVbo() {
		int id = GL15.glGenBuffers();
		vbos.add(id);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
		return id;
	}

	/**
     * This is never used
     * */
	public static void cleanup() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		for (int texId : textureIds) {
			GL11.glDeleteTextures(texId);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	public static int getMouseX() {
    	return (Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth);
    }
	
	public static int getMouseDX() {
		return getMouseX() - lastMouseX;
	}
	
    public static int getMouseY() {
    	return (getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayHeight - 1);
    }
    
	public static int getMouseDY() {
		return getMouseY() - lastMouseY;
	}
    
    public static int getScreenWidth() {
    	return getMinecraft().getScreenResolution().scaledWidth;
    }

    public static int getScreenHeight() {
    	return getMinecraft().getScreenResolution().scaledHeight;
    }

    /**
     * @return The scale factor used with the player's screen resolution and gui scale information.
     * */
    public static int getScaleFactor() {
		return getMinecraft().getScreenResolution().factor;
	}
    
    public static void glColor(float red, float green, float blue, float alpha) {
		getGLStateManager().color(red, green, blue, alpha);
	}
    
    public static void glColor(Color color) {
		getGLStateManager().color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
	}
	
	public static void glColor(Color color, float alpha) {
		getGLStateManager().color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, alpha);
	}
	
	public static void glColor(int color) {
		getGLStateManager().color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, (color >> 24 & 0xff) / 255F);
	}
	
	public static void glColor(int color, float alpha) {
		getGLStateManager().color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, alpha);
	}
	
	/**
     * This isn't mine, but it's the most beautiful random color generator I've seen. Reminds me of easter.
     * */
    public static Color getRandomColor(int saturationRandom, float luminance) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(saturationRandom) + (float) saturationRandom) / (float) saturationRandom + (float) saturationRandom;
        return Color.getHSBColor(hue, saturation, luminance);
    }
    
    public static Color getHSBColor(float hue, float saturation, float luminance) {
        return Color.getHSBColor(hue, saturation, luminance);
    }
    
    public static Color getRandomColor() {
        return getRandomColor(1000, 0.6F);
    }
    
    public static void update() {
    	lastMouseX = getMouseX();
    	lastMouseY = getMouseY();
    }
}
