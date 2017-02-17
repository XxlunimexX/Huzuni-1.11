package net.halalaboos.huzuni.indev.gui;

import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.layouts.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple component which contains and handles other components. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public class Container extends Component {

    protected final List<Component> components = new ArrayList<>();

    private Layout layout = new EmptyLayout();

    // Enable layering of components when activated.
    private boolean layering = true;

    public Container(String tag) {
        super(tag);
    }

    @Override
    public void update() {
        // Used to ensure only one component is updated with a true hover state. When this container has a hover state of false, no component will receive a true hover state.
        boolean hover = isHovered();

        // Iterate backwards through each component and update their hovered state along with updating them.
        for (int i = components.size() - 1; i >= 0; i--) {
            Component component = components.get(i);
            // Set hover true if no other component has been set hovered = true and this component has the mouse over it.
            if (hover && component.isPointInside(inputUtility.getMouseX(), inputUtility.getMouseY())) {
                component.setHovered(true);
                hover = false;
            } else
                component.setHovered(false);
            component.update();
        }
    }

    @Override
    public boolean isActivated(Actions type, Object data) {
        // Update all component's mouse clicks.
        for (int i = components.size() - 1; i >= 0; i--) {
            Component component = components.get(i);
            if (component.isActivated(type, data)) {
                // Layer this component.
                if (layering) {
                    components.remove(component);
                    components.add(component);
                }
                return true;
            }
        }
        return super.isActivated(type, data);
    }

    @Override
    public String getTooltip() {
        // Sends the tooltip of the component which is hovered.
        for (int i = components.size() - 1; i >= 0; i--) {
            Component component = components.get(i);
            if (component.isPointInside(inputUtility.getMouseX(), inputUtility.getMouseY())) {
                return component.getTooltip();
            }
        }
        return null;
    }

    /**
     * Lays out each component within the container according to the layout specified.
     * */
    public void layout() {
        this.layout.layout(this, components);
    }

    /**
     * Lays out any child components which are containers.
     * */
    public void layoutChildren() {
        for (Component component : components) {
            if (component instanceof Container) {
                ((Container) component).layout();
            }
        }
    }

    public Layout getLayout() {
        return layout;
    }

    /**
     * Sets this containers layout.
     * */
    public void setLayout(Layout layout) {
        this.layout = layout;
        if (this.layout == null)
            this.layout = new EmptyLayout();
    }

    public List<Component> getComponents() {
        return components;
    }

    /**
     * Adds this component to this container.
     * */
    public boolean add(Component component) {
        // Update this component with the new gui utility.
        component.setInputUtility(inputUtility);
        component.setParent(this);
        boolean returnVal = components.add(component);
        if (!layout.laidOut(this))
            layout();
        return returnVal;
    }

    /**
     * Removes this component from this container.
     * */
    public boolean remove(Component component) {
        return components.remove(component);
    }

    public void clear() {
        components.clear();
    }

    public void setLayering(boolean layering) {
        this.layering = layering;
    }

    @Override
    protected void setInputUtility(InputUtility inputUtility) {
        super.setInputUtility(inputUtility);
        // Update every child with the new gui utility.
        for (Component child : components) {
            child.setInputUtility(inputUtility);
        }
    }
}
