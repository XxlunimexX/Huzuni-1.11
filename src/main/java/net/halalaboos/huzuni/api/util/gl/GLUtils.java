package net.halalaboos.huzuni.api.util.gl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
import java.util.Random;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static org.lwjgl.opengl.GL11.*;

/**
 * Utility class to assist in rendering.
 * */
public final class GLUtils {
	
	private static final Tessellator tessellator = Tessellator.getInstance();

	private static final Random random = new Random();

	private GLUtils() {
		
	}

	public static void drawTextureRect(float x, float y, float width, float height, float u, float v, float t, float s) {
		VertexBuffer renderer = tessellator.getBuffer();
		renderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		renderer.pos(x, y, 0F).tex(u, v).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x + width, y + height, 0F).tex(t, s).endVertex();
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		tessellator.draw();
	}

	/**
	 * Renders a simple lined border around the given x, y, x1, and y1 coordinates. 
	 * */
	public static void drawBorder(float size, float x, float y, float x1, float y1) {
		glLineWidth(size);
		getGLStateManager().disableTexture2D();
		getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	VertexBuffer vertexBuffer = tessellator.getBuffer();
    	vertexBuffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION);
    	vertexBuffer.pos(x, y, 0F).endVertex();
    	vertexBuffer.pos(x, y1, 0F).endVertex();
    	vertexBuffer.pos(x1, y1, 0F).endVertex();
    	vertexBuffer.pos(x1, y, 0F).endVertex();
    	tessellator.draw();
		getGLStateManager().enableTexture2D();
	}
	
	/**
	 * Renders a line from the given x, y positions to the second x1, y1 positions.
	 * */
	public static void drawLine(float size, float x, float y, float x1, float y1) {
		glLineWidth(size);
		getGLStateManager().disableTexture2D();
		getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	VertexBuffer vertexBuffer = tessellator.getBuffer();
    	vertexBuffer.begin(GL_LINES, DefaultVertexFormats.POSITION);
    	vertexBuffer.pos(x, y, 0F).endVertex();
    	vertexBuffer.pos(x1, y1, 0F).endVertex();
    	tessellator.draw();
    	getGLStateManager().enableTexture2D();
	}

	/**
	 * Renders a simple rectangle around the given x, y, width, and height values within the rect array.
	 * */
	public static void drawRect(int[] rect) {
		drawRect(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
	}

	/**
	 * Renders a simple rectangle around the given x, y, width, and height values within the rect array.
	 * */
	public static void drawRect(float[] rect) {
		drawRect(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
	}

	/**
	 * Renders a simple rectangle around the given x, y, x1, and y1 coordinates.
	 * */
	public static void drawRect(float x, float y, float x1, float y1) {
		getGLStateManager().disableTexture2D();
		getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	VertexBuffer vertexBuffer = tessellator.getBuffer();
    	vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
    	vertexBuffer.pos(x, y1, 0F).endVertex();
    	vertexBuffer.pos(x1, y1, 0F).endVertex();
    	vertexBuffer.pos(x1, y, 0F).endVertex();
    	vertexBuffer.pos(x, y, 0F).endVertex();
    	tessellator.draw();
    	getGLStateManager().enableTexture2D();
	}
	
	/**
	 * Renders a simple rectangle and line border around the given x, y, x1, and y1 coordinates.
	 * */
	public static void drawBorderRect(float x, float y, float x1, float y1, float borderSize) {
		drawBorder(borderSize, x, y, x1, y1);
		drawRect(x, y, x1, y1);
	}
	
	/**
	 * Translates and scales to allow for billboarding.
	 * <br>
	 * @see <a href= http://www.opengl-tutorial.org/intermediate-tutorials/billboards-particles/billboards/>Billboards</a>
	 * 
	 * */
	public static void prepareBillboarding(float x, float y, float z, boolean modifyPitch) {
		float scale = 0.016666668F * 1.6F;
		glTranslatef(x, y, z);
		glNormal3f(0.0F, 1.0F, 0.0F);
		glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		if (modifyPitch) {
			if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
				glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			} else {
				glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
			}
		}
		glScalef(-scale, -scale, scale);
	}
	
	/**
	 * @return A {@link Color} object with effects applied to it based on the boolean values given.
	 * */
	public static Color getColorWithAffects(Color color, boolean mouseOver, boolean mouseDown) {
    	return mouseOver ? (mouseDown ? color.darker() : color.brighter()) : color;
    }

	/**
	 * @return A hexadecimal color with effects applied to it based on the boolean values given.
	 * */
	public static int getColorWithAffects(int color, boolean mouseOver, boolean mouseDown) {
    	return mouseOver ? (mouseDown ? darker(color, 0.2F) : brighter(color, 0.2F)) : color;
    }

    /**
     * @return A hexadecimal color that is darkened by the scale amount provided.
     */
	public static int darker(int color, float scale) {
		int red = (color >> 16 & 255), green =  (color >> 8 & 255), blue = (color & 255), alpha = (color >> 24 & 0xff);
		red = (int) (red - red * scale);
		red = Math.min(red, 255);
		green = (int) (green - green * scale);
		green = Math.min(green, 255);
		blue = (int) (blue - blue * scale);
		blue = Math.min(blue, 255);
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	/**
     * @return A hexadecimal color that is brightened by the scale amount provided.
     * */
	public static int brighter(int color, float scale) {
		int red = (color >> 16 & 255), green =  (color >> 8 & 255), blue = (color & 255), alpha = (color >> 24 & 0xff);
		red = (int) (red + red * scale);
		red = Math.min(red, 255);
		green = (int) (green + green * scale);
		green = Math.min(green, 255);
		blue = (int) (blue + blue * scale);
		blue = Math.min(blue, 255);
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	/**
     * @return A hexadecimal representation of the rgba color values specified (between 0 - 1)
     * */
	public static int toColor(float r, float g, float b, float a) {
		return ((int) (a * 255F) << 24) | ((int) (r * 255F) << 16) | ((int) (g * 255F) << 8) | (int) (b * 255F);
	}

	/**
	 * @return the mouse x position.
	 * */
	public static int getMouseX() {
		return (Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth);
	}

	/**
	 * @return The mouse y position.
	 * */
	public static int getMouseY() {
		return (getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayHeight - 1);
	}

	/**
	 * @return The width of the screen.
	 * */
	public static int getScreenWidth() {
		return getMinecraft().getScreenResolution().scaledWidth;
	}

	/**
	 * @return The height of the screen.
	 * */
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

	public static void glScissor(int[] rect) {
		glScissor(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
	}

	public static void glScissor(float x, float y, float x1, float y1) {
		int factor = getScaleFactor();
		GL11.glScissor((int) (x * factor), (int) (getMinecraft().getScreenResolution().height - (y1 * factor)), (int) ((x1 - x) * factor), (int) ((y1 - y) * factor));
	}

	/**
	 * @return Newly generated lwjgl vbo id.
	 * */
	public static int genVbo() {
		int id = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
		return id;
	}

	/**
	 * @return Newly generated lwjgl texture id.
	 * */
	public static int genTexture() {
		int textureId = GL11.glGenTextures();
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

}