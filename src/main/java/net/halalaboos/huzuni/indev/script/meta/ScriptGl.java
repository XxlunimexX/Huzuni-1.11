package net.halalaboos.huzuni.indev.script.meta;

import net.halalaboos.huzuni.indev.gui.impl.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontManager;
import net.halalaboos.huzuni.indev.gui.render.FontRenderer;
import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_QUADS;

/**
 * Wrapper rendering class used by scripts. <br/>
 * Created by Brandon Williams on 2/18/2017.
 */
public final class ScriptGl {

    private final Tessellator tessellator = Tessellator.getInstance();

    private final FontRenderer fontRenderer = new BasicFontRenderer();

    private final FontManager fontManager;

    public ScriptGl(FontManager fontManager) {
        this.fontManager = fontManager;
    }

    /**
     * @return The width in pixels of the text rendered.
     * */
    public int drawString(String text, int x, int y, int color) {
        return fontRenderer.drawString(text, x, y, color);
    }

    /**
     * Renders a string with a black shadow behind. <br/>
     * @return The width in pixels of the text rendered.
     * */
    public int drawStringWithShadow(String text, int x, int y, int color) {
        return Math.max(fontRenderer.drawString(text, x, y, color), fontRenderer.drawString(text, x + 1, y + 1, 0xFF000000));
    }

    /**
     * Sets the font used by this font renderer to a plain font with the size and name specified.
     * */
    public void setFont(String fontName, int fontSize) {
        this.fontRenderer.setFontData(fontManager.getFont(fontName, fontSize, Font.PLAIN, true));
    }

    /**
     * Set the font used by this font renderer to a font which matches the criteria specified.
     * */
    public void setFont(String fontName, int fontSize, int style) {
        this.fontRenderer.setFontData(fontManager.getFont(fontName, fontSize, style, true));
    }

    /**
     * Sets the color to the rgb value specified. Alpha is set to 255.
     * */
    public void color(int rgb) {
        color(rgb, rgb, rgb, 255);
    }

    /**
     * Sets the r, g, b color values to the rgb value specified. Alpha is set to alpha.
     * */
    public void color(int rgb, int alpha) {
        color(rgb, rgb, rgb, alpha);
    }

    /**
     * Sets the color to the r, g, b values specified. alpha is set to 255.
     * */
    public void color(int r, int g, int b) {
        color(r, g, b, 255);
    }

    /**
     * Sets the color to the r, g, b, a values specified.
     * */
    public void color(int r, int g, int b, int a) {
        GLManager.glColor(r / 255F, g / 255F, b / 255F, a / 255F);
    }

    /**
     * Draws a rectangle within the x, y, x1, and y1 positions.
     * */
    public void rect(int x, int y, int x1, int y1) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        vertexBuffer.pos(x, y1, 0F).endVertex();
        vertexBuffer.pos(x1, y1, 0F).endVertex();
        vertexBuffer.pos(x1, y, 0F).endVertex();
        vertexBuffer.pos(x, y, 0F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }
}
