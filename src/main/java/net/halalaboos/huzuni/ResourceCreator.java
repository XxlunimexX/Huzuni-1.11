package net.halalaboos.huzuni;

import net.halalaboos.huzuni.indev.gui.TypeCreator;
import net.halalaboos.huzuni.indev.gui.impl.FontCreator;
import net.halalaboos.huzuni.indev.gui.impl.ImageCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates resources for use within the mod. Will destroy those resources once the mod is finished. <br/>
 * Examples: FontData, Images, Models?, etc. <br/>
 * Created by Brandon Williams on 3/15/2017.
 */
public class ResourceCreator {

    private final Map<String, TypeCreator> creators = new HashMap<>();

    public ResourceCreator() {
        put("image", new ImageCreator());
        put("font", new FontCreator());
    }

    /**
     * Adds a type creator to this work station.
     * */
    public void put(String type, TypeCreator creator) {
        creators.put(type, creator);
    }

    /**
     * @return An object which is designated to be a resource.
     * */
    public <O> O create(String type, String resource, Object... params) {
        @SuppressWarnings("unchecked") TypeCreator<O> creator = creators.get(type);
        if (creator != null) {
            return creator.create(resource, params);
        }
        return null;
    }
}
