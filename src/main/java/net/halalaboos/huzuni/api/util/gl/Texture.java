package net.halalaboos.huzuni.api.util.gl;

import net.halalaboos.mcwrapper.api.util.ResourcePath;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Simple texture class, originally loaded a bufferedimage from file and stored a texture id like you would expect, but I decided to take advantage of Minecraft's resource shiz.
 * */
public class Texture {
	
	private final ResourcePath texture;
	
	public Texture(String textureURL) {
		texture = new ResourcePath("huzuni/textures/" + textureURL);
		getMinecraft().bindTexture(texture);
	}
	
	public void render(float x, float y, float width, float height) {
		render(x, y, width, height, 0F, 0F, 1F, 1F);
	}
	
	public void render(float x, float y, float width, float height, float u, float v, float t, float s) {
		bindTexture();
    	GLUtils.drawTextureRect(x, y, width, height, u, v, t, s);
	}
	
	public void bindTexture() {
		getMinecraft().bindTexture(texture);
		getGLStateManager().enableTexture2D();
	}
	
	@Override
	public String toString() {
		return texture.getPath();
	}
}
