package net.halalaboos.huzuni.indev.gui;

import java.util.List;

/**
 * Lays out components in a specific organization. <br/>
 * Created by Brandon Williams on 1/15/2017.
 */
public interface Layout <C extends Container> {

    /**
     * Invoked by a container to assemble each component into the layout this layout specifies.
     * */
    void layout(C container, List<Component> components);


    /**
     * Updates this components layout within the container.
     * */
    void updateComponentPositions(Component component);

    /**
     * @return True if every component within this container is properly laid out.
     * */
    boolean laidOut(C container);

    /**
     * @return True if this layout should update the components with the container's position.
     * */
    boolean updateWithContainer();

    /**
     *
     * */
    void setUpdateWithContainer(boolean updateWithContainer);

    /**
     * @return An int array representing the padding between a container and it's components.
     * */
    int[] getPadding();

    /**
     * Set the padding between a container and it's components.
     * */
    void setPadding(int x, int y, int width, int height);
}
