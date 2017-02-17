package net.halalaboos.huzuni.indev.gui;

/**
 * Renderer used to render specified components. <br/>
 * Created by Brandon Williams on 1/29/2017.
 */
public interface ComponentRenderer <T extends Component> {

    void render(T component);

}
