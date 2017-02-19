package net.halalaboos.huzuni.indev.gui.render;

import net.halalaboos.huzuni.indev.gui.Component;

/**
 * Renderer used to render specified components. <br/>
 * Created by Brandon Williams on 1/29/2017.
 */
public interface ComponentRenderer <T extends Component> {

    /**
     * Invoked before rendering the component.
     * */
    void pre(T component);

    /**
     * Invoked to render the component.
     * */
    void render(T component);

    /**
     * Invoked after rendering the component and it's children.
     * */
    void post(T component);

}
