package net.halalaboos.huzuni.indev.gui;

import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;
import net.halalaboos.huzuni.indev.gui.actions.Direction;
import net.halalaboos.huzuni.indev.gui.actions.KeystrokeAction;
import net.halalaboos.huzuni.indev.gui.render.RenderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages containers. <br/>
 * Created by Brandon Williams on 1/23/2017.
 */
public class ContainerManager {

    private final List<Container> containers = new ArrayList<>();

    private RenderManager renderManager;

    private Toolbox toolbox;

    private String tooltip = null;

    public ContainerManager(RenderManager renderManager, Toolbox toolbox) {
        this.renderManager = renderManager;
        this.toolbox = toolbox;
    }

    /**
     * Invoked to render the containers.
     * */
    public void render() {
        for (Container container : containers) {
            renderManager.render(container);
        }
        renderManager.getPopupRenderer().drawTooltip(tooltip, toolbox.getMouseX(), toolbox.getMouseY());
    }

    /**
     * Updates all containers.
     * */
    public void update() {
        // Used to ensure only one container is updated with a true hover state.
        boolean hover = true;

        // Reset the tool tip, since we will be calculating the hover state of each component.
        this.tooltip = null;

        for (int i = containers.size() - 1; i >= 0; i--) {
            Container container = containers.get(i);
            // Set hover true if no other container has been set hovered = true and this container has the mouse over it.
            if (hover && container.isPointInside(toolbox.getMouseX(), toolbox.getMouseY())) {
                this.tooltip = container.getTooltip();
                container.setHovered(true);
                hover = false;
            } else
                container.setHovered(false);
            container.update();
        }
    }

    /**
     * Dispatches mouse clicks to containers.
     * */
    public void mousePressed(int mouseX, int mouseY, int buttonId) {
        for (int i = containers.size() - 1; i >= 0; i--) {
            Container container = containers.get(i);
            if (container.isActivated(Actions.MOUSEPRESS, new ClickAction(mouseX, mouseY, buttonId)))
                break;
        }
    }

    /**
     * Dispatches mouse clicks to containers.
     * */
    public void mouseReleased(int mouseX, int mouseY, int buttonId) {
        for (int i = containers.size() - 1; i >= 0; i--) {
            Container container = containers.get(i);
            if (container.isActivated(Actions.MOUSERELEASE, new ClickAction(mouseX, mouseY, buttonId)))
                break;
        }
    }

    /**
     * Dispatches key presses to containers.
     * */
    public void keyTyped(char typedChar, int keyCode) {
        for (Container container : containers) {
            if (container.isActivated(Actions.KEYSTROKE, new KeystrokeAction(keyCode, typedChar)))
                break;
        }
    }

    /**
     * Notifies all containers that the mouse wheel moved in any direction.
     * */
    public void mouseWheel(int direction) {
        for (Container container : containers) {
            if (container.isActivated(Actions.MOUSEWHEEL, direction < 0 ? Direction.UP : Direction.DOWN))
                break;
        }
    }

    /**
     * Adds the container to this manager.
     * */
    public boolean add(Container container) {
        container.setToolbox(toolbox);
        return this.containers.add(container);
    }

    public boolean remove(Container container) {
        return this.containers.remove(container);
    }

    public void clear() {
        this.containers.clear();
    }

    public boolean contains(String tag) {
        for (Container container : this.containers) {
            if (container.getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
