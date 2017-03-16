package net.halalaboos.huzuni.indev.gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic factory, used to create objects and store their values based on their name. <br/>
 * Created by Brandon Williams on 3/6/2017.
 */
public abstract class TypeCreator<O> {

    private final Map<String, O> objects = new HashMap<>();

    public O create(String name, Object... params) {
        O result = objects.computeIfAbsent(name, (o) -> {
            O object = createObject(name, params);
            if (object != null)
                objects.put(name, object);
            return object;
        });
        return result;
    }

    /**
     * @return The object this work station will produce with the given parameters. <br/>
     * Should either return null or a default object if the parameters are not sufficient.
     * */
    protected abstract O createObject(String name, Object... params);

}
