package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.huzuni.api.node.impl.*;
import net.halalaboos.huzuni.api.util.RateLimiter;
import net.halalaboos.huzuni.indev.gui.Component;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.ContainerManager;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.impl.BasicToolbox;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;
import net.halalaboos.huzuni.indev.gui.layouts.ListLayout;
import net.halalaboos.mcwrapper.api.client.gui.screen.Screen;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.FONT_TOOLTIP;
import static net.halalaboos.huzuni.indev.gui.impl.Pointers.IMAGE_ARROW;
import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;

/**
 * Testing the new GUI API. <br/>
 * Created by Brandon Williams on 2/15/2017.
 */
public class GuiTestScreen extends Screen {

    /**
     * Keeps the rate at which the menu is updated to a constant 60 updates/second.
     * */
    private final RateLimiter updateLimiter = new RateLimiter(TimeUnit.SECONDS, 60);

    private final BasicToolbox toolbox = new BasicToolbox();

    private final FontData globalFont, titleFont, descriptionFont, textfieldFont;

    private final ContainerManager manager;

    private Container settings;

    public GuiTestScreen() {
        super();
        // Apply the fonts to the tool box.
        globalFont = huzuni.resourceCreator.create("font", "font_global", "RobotoCondensed-Regular.ttf", Font.PLAIN, 18);
        titleFont = huzuni.resourceCreator.create("font", "font_title", "RobotoCondensed-Bold.ttf", Font.BOLD, 48);
        descriptionFont = huzuni.resourceCreator.create("font", "font_description", "RobotoCondensed-Italic.ttf", Font.ITALIC, 16);
        textfieldFont = huzuni.resourceCreator.create("font", "font_textfield","RobotoCondensed-Italic.ttf", Font.ITALIC, 20);
        toolbox.put(FONT_TOOLTIP, globalFont);
        toolbox.put(IMAGE_ARROW, huzuni.resourceCreator.create("image", IMAGE_ARROW.getName(), "arrow.png"));

        // Apply a random color pack to the tool box.
        ColorPack colorPack = ColorPack.values()[(int) (Math.random() * ColorPack.values().length)];
        colorPack.apply(toolbox);

        BasicRenderer renderer = new BasicRenderer(toolbox);
        manager = new ContainerManager(renderer, toolbox);
        //blurShader = new ResourceLocation("shaders/post/blur.json");
    }

    @Override
    public void initGui() {
        super.initGui();
        // initGui is invoked whenever the screen is resized, so it is necessary to clear the containers.
        manager.clear();

        // Dimensions of the entire menu.
        int x = (int) (width * (160F / 1920F));
        int y = (int) (height * (60F / 1080F));
        int width = this.width - x * 2;
        int height = this.height - y * 2;

        // Width of the scrollable area used to store the mods.
        int scrollWidth = (int) (0.25F * width);

        settings = new Container("settings");
        settings.setPosition(x + scrollWidth, y);
        settings.setSize(width - scrollWidth, height);

        ScrollableContainer modsList = new ScrollableContainer("mods");
        modsList.setLayering(false);
        modsList.setLayout(new ListLayout(6, 1));
        modsList.setPosition(x, y);
        modsList.setSize(scrollWidth, height);

        // Populate the scrollable container with the mods of huzuni.
        for (Mod mod : huzuni.modManager.getMods()) {
            Button button = new Button("mod", mod.getName());
            button.setHighlight(mod.isEnabled());
            button.onPressed((thisbutton, action) -> {
                switch (MouseButton.getMouseButton(action.buttonId)) {
					case LEFT:
						loadMod(mod);
						return true;
					case RIGHT:
						mod.toggle();
						thisbutton.setHighlight(mod.isEnabled());
						return true;
				}
                return true;
            });
            button.setFont(globalFont);
            button.setHeight(20);
            modsList.add(button);
        }

        // Layout and add the containers.
        settings.layout();
        modsList.layout();
        manager.add(settings);
        manager.add(modsList);
        //mc.entityRenderer.loadShader(blurShader);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        while (updateLimiter.reached()) manager.update();
        manager.render();
        getGLStateManager().disableBlend();
    }

    @Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        manager.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        manager.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        manager.keyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (Mouse.getEventButton() == -1) {
            int wheel = Mouse.getEventDWheel();
            if (wheel > 0)
                wheel = 1;
            else if (wheel < 0)
                wheel = -1;
            if (wheel != 0)
                manager.mouseWheel(wheel);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Invoked by mod buttons to load their settings.
     * */
    public void loadMod(Mod mod) {
        settings.clear();

        // Create the title label.
        Label title = new Label("title", mod.getName(), titleFont, new Color(218, 218, 218));
        title.setPosition(10, 10);
        settings.add(title);

        Label description = new Label("description", mod.getDescription(), descriptionFont, new Color(118, 118, 118));
        description.setPosition(10, 40);
        settings.add(description);

        int settingsHeight = loadNodes(mod.settings, 10, settings.getHeight() - 10, 4, settings, true);

        ScrollableContainer childContainer = new ScrollableContainer("invisible-background");
        childContainer.setPosition(10, 60);
        childContainer.setSize(settings.getWidth() - 20, settings.getHeight() - 66 - settingsHeight);

        loadNodes(mod, 0, 0, 1, childContainer, false);
        childContainer.layout();
        settings.add(childContainer);
        settings.layout();
    }

    /**
     * @return The total height of the components from the provided node.
     * */
    private int loadNodes(Node node, int x, int y, int padding, Container container, boolean reversed) {
        int startY = y;
        // Create the components for each child within the node.
        for (Node child : node.getChildren()) {
            Component component = null;

            // Create a check box for the toggleable.
            if (child instanceof Toggleable) {
                component = new ToggleableCheckbox((Toggleable) child, globalFont);

                // Create value container for each value.
            } else if (child instanceof Value) {
                component = new ValueContainer((Value) child, globalFont, descriptionFont);

                // If we have JUST a node.
            } else if (child.getClass().isAssignableFrom(Node.class)) {
                Container internal = new Container("invisible");
                internal.setUseLayoutSize(true);
                internal.setLayering(false);
                internal.setAutoLayout(true);
                internal.setLayout(new GridLayout(2, GridLayout.INFINITE_LENGTH, 0, 0, 1));
                loadNodes(child, x, y, padding, internal, reversed);
                internal.layout();
                component = internal;

                // Create the string node container for string nodes.
            } else if (child instanceof StringNode) {
                component = new StringNodeContainer((StringNode) child, globalFont, textfieldFont);

                // Create the color node container for color nodes.
            } else if (child instanceof ColorNode) {
                component = new ColorNodeContainer((ColorNode) child, globalFont, textfieldFont);

                // Create the item list container for item lists.
            } else if (child instanceof ItemList) {
                component = new ItemListContainer((ItemList) child, globalFont);

                // Create the mode dropdown for modes.
            }  else if (child instanceof Mode) {
                component = new ModeContainer((Mode) child, globalFont);
            }

            // If the node had a component made for it, we position it and either decrement or increment the y position.
            if (component != null) {
                component.setPosition(x, y - (reversed ? component.getHeight() : 0));
                y += reversed ? -component.getHeight() - padding : component.getHeight() + padding;
                container.add(component);
            }
        }
        // Return the difference from the start y position to the final y position. This calculates the height.
        return reversed ? (startY - y) : (y - startY);
    }

}
