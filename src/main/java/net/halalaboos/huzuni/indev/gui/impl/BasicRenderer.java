package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.indev.gui.*;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.components.*;
import net.halalaboos.huzuni.api.gui.font.BasicFontRenderer;
import net.halalaboos.huzuni.api.gui.font.FontRenderer;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Checkbox;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * Basic implementation of the renderer for the GUI. <br/>
 * Also implements the Toolbox interface, making it work for both parameters of a ContainerManager. <br/>
 * Created by Brandon Williams on 2/13/2017.
 */
public class BasicRenderer extends RenderManager implements Toolbox {

    private final FontRenderer fontRenderer = new BasicFontRenderer();

    private ColorPack palette = ColorPack.values()[(int) (Math.random() * ColorPack.values().length)];

    public BasicRenderer() {
        super(new BasicPopupRenderer());
        ((BasicPopupRenderer) getPopupRenderer()).setRenderer(this);
        this.setRenderer(Button.class, new ButtonRenderer(this));
        this.setRenderer(Checkbox.class, new CheckboxRenderer(this));
        this.setRenderer(Container.class, new ContainerRenderer(this));
        this.setRenderer(ScrollableContainer.class, false, new ScrollableContainerRenderer(this));
        this.setRenderer(Slider.class, new SliderRenderer(this));
        this.setRenderer(Label.class, new LabelRenderer(fontRenderer));
        this.setRenderer(TextField.class, new TextFieldRenderer(this));
        this.setRenderer(Dropdown.class, new DropdownRenderer(this));
    }

    @Override
    public int getMouseX() {
        return GLManager.getMouseX();
    }

    @Override
    public int getMouseY() {
        return GLManager.getMouseY();
    }

    @Override
    public int getWidth() {
        return GLManager.getScreenWidth();
    }

    @Override
    public int getHeight() {
        return GLManager.getScreenHeight();
    }

    @Override
    public String getClipboard() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) transferable.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    public boolean isPointInside(int x, int y, int[] rect) {
        return x > rect[0] && y > rect[1] && x < rect[0] + rect[2] && y < rect[1] + rect[3];
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public ColorPack getPalette() {
        return palette;
    }

    public void setPalette(ColorPack palette) {
        this.palette = palette;
    }
}
