package net.halalaboos.huzuni.indev.gui.render;

import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages the rendering of components. This is separated from the container manager to allow for a 'theme-able' component system. <br/>
 * Created by Brandon Williams on 1/29/2017.
 */
public class RenderManager {

    /**
     * Each component's class is mapped to a renderer. This is used to ensure each component type has it's own renderer.
     * */
    private final Map<ComponentRendererMatcher, ComponentRenderer> renderers = new HashMap<>();

    private PopupRenderer popupRenderer = new EmptyPopupRenderer();

    /**
     * Assigns this renderer to the class specified. Can render subclasses of this class.
     * */
    public void setRenderer(Class<? extends Component> clazz, ComponentRenderer renderer) {
        renderers.put(new ClassMatcher<>(clazz), renderer);
    }

    /**
     * Assigns this renderer to the class specified.
     * @param loose 'Loose-Comparison' - When false exactly matches the class, which may prove problematic with internal classes.
     * */
    public void setRenderer(Class<? extends Component> clazz, boolean loose, ComponentRenderer renderer) {
        renderers.put(new ClassMatcher<>(clazz, loose), renderer);
    }

    /**
     * Assigns this renderer to a class with a tag.
     * */
    public void setRenderer(Class<? extends Component> clazz, boolean loose, String tag, ComponentRenderer renderer) {
        renderers.put(new TagMatcher<>(tag, clazz, loose), renderer);
    }

    /**
     * Assigns this renderer to a class with a tag.
     * */
    public void setRenderer(Class<? extends Component> clazz, String tag, ComponentRenderer renderer) {
        renderers.put(new TagMatcher<>(tag, clazz), renderer);
    }

    /**
     * Renders a component and the components within that component.
     * */
    public void render(Component component) {
        // Filter the
        // ^ I'm keeping it this way.
        Optional<Map.Entry<ComponentRendererMatcher, ComponentRenderer>> renderer = renderers.entrySet().stream().max(Comparator.comparingInt(o -> o.getKey().matches(component)));
        if (renderer.isPresent()) {
            renderer.get().getValue().pre(component);
            renderer.get().getValue().render(component);

            // Render the children of this component if it is a container.
            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    render(child);
                }
            }
            renderer.get().getValue().post(component);
        }
    }

    public void setPopupRenderer(PopupRenderer popupRenderer) {
        if (popupRenderer != null)
            this.popupRenderer = popupRenderer;
    }

    public PopupRenderer getPopupRenderer() {
        return popupRenderer;
    }

    /**
     * Matches a component to a renderer.
     * */
    public interface ComponentRendererMatcher {

        /**
         * @return The 'weight' of qualifiers this component passes from this matcher.
         * */
        int matches(Component component);
    }

    /**
     * Matches a renderer to a class. Can 'loosely' match to a class, meaning the component could also be a subclass of the class specified.
     * */
    public class ClassMatcher <T extends Component> implements ComponentRendererMatcher {

        private final Class<T> clazz;

        private boolean loose;

        private ClassMatcher(Class<T> clazz) {
            this(clazz, true);
        }

        private ClassMatcher(Class<T> clazz, boolean loose) {
            this.clazz = clazz;
            this.loose = loose;
        }

        @Override
        public int matches(Component component) {
            return loose ? (clazz.isAssignableFrom(component.getClass()) ? 1 : 0) : component.getClass() == clazz ? 3 : 0;
        }
    }

    /**
     * Matches a renderer to class with a tag.
     * */
    public class TagMatcher <T extends Component> extends ClassMatcher <T> {

        private final String tag;

        private TagMatcher(String tag, Class<T> clazz) {
            super(clazz);
            this.tag = tag;
        }

        private TagMatcher(String tag, Class<T> clazz, boolean loose) {
            super(clazz, loose);
            this.tag = tag;
        }

        @Override
        public int matches(Component component) {
            return (super.matches(component) > 0) ? (component.getTag().equals(tag) ? 2 : 0) : 0;
        }
    }
}
