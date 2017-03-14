package net.halalaboos.huzuni.indev.gui.impl;

import net.halalaboos.huzuni.api.util.gl.GLManager;
import net.halalaboos.huzuni.indev.gui.Toolbox;
import net.halalaboos.huzuni.indev.gui.Workstation;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * Basic implementation of the tool box. <br/>
 * Created by Brandon Williams on 3/13/2017.
 */
public class BasicToolbox implements Toolbox {

    private final Workstation workstation = new Workstation();

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

    @Override
    public Workstation getWorkstation() {
        return workstation;
    }
}
