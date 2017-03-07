package net.halalaboos.huzuni.indev.gui;

import java.util.Optional;

/**
 * Work station which creates objects for the gui system. <br/>
 * Essentially a Factory, as it is used to access resources and create objects reflecting the given parameters. <br/>
 * Created by Brandon Williams on 3/6/2017.
 */
public interface Workstation {

    /**
     * @return An object which is designated to be a resource.
     * */
    <O> Optional<O> create(String resource, Object... params);

}
