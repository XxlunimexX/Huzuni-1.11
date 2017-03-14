package net.halalaboos.huzuni.indev.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Work station which creates objects for the gui system. <br/>
 * Essentially a Factory, as it is used to access resources and create objects reflecting the given parameters. <br/>
 * Each creator is given a string to represent the type of objects it creates. <br/>
 * Created by Brandon Williams on 3/6/2017.
 */
public class Workstation {

    private final Map<String, TypeCreator> creators = new HashMap<>();

    /**
     * Adds a type creator to this work station.
     * */
    public void put(String type, TypeCreator creator) {
        creators.put(type, creator);
    }

    /**
     * @return An object which is designated to be a resource.
     * */
    public <O> Optional<O> create(String type, String resource, Object... params) {
        TypeCreator creator = creators.get(type);
        if (creator != null) {
            return creator.create(resource, params);
        }
        return Optional.empty();
    }

}
