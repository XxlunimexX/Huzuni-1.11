package net.halalaboos.mcwrapper.api.client.gui;

public interface TextRenderer {

	void render(String text, int x, int y, int color, boolean shadow);
	void render(String text, float x, float y, int color);

	int getHeight();

	int getWidth(String text);

}
