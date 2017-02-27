package net.halalaboos.mcwrapper.api.client.gui;

public interface TextRenderer {

	void render(String text, int x, int y, int color, boolean shadow);

	int getHeight();

	int getWidth(String text);

}
