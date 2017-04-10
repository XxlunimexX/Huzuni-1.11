package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Map;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Basic implementation of the tool box. <br/>
 * Created by Brandon Williams on 3/13/2017.
 */
public class BasicToolbox implements Toolbox {

    private final Map<String, Object> objects = new HashMap<>();

    @Override
    public int getMouseX() {
        return (Mouse.getX() * getWidth() / getMinecraft().getScreenResolution().width);
    }

    @Override
    public int getMouseY() {
        return (getHeight() - Mouse.getY() * getHeight() / getMinecraft().getScreenResolution().height - 1);
    }

    @Override
    public int getWidth() {
        return getMinecraft().getScreenResolution().scaledWidth;
    }

    @Override
    public int getHeight() {
        return getMinecraft().getScreenResolution().scaledHeight;
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

    @Override
    public <O> O get(String location) {
        //noinspection unchecked
        return (O) objects.get(location);
    }

    @Override
    public <O> O get(Nameable location) {
        //noinspection unchecked
        return (O) objects.get(location.getName());
    }

    @Override
    public void put(String location, Object object) {
        objects.put(location, object);
    }

    @Override
    public void put(Nameable location, Object object) {
        objects.put(location.getName(), object);
    }
}
