package net.halalaboos.huzuni.indev;

import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.settings.Node;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.api.util.RateLimiter;
import net.halalaboos.huzuni.gui.screen.HuzuniScreen;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.ContainerManager;
import net.halalaboos.huzuni.indev.gui.components.*;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.layouts.*;
import net.halalaboos.huzuni.api.gui.font.FontData;
import net.halalaboos.huzuni.indev.gui.layouts.GridLayout;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Testing the new GUI API. <br/>
 * Created by Brandon Williams on 2/15/2017.
 */
public class GuiTestScreen  extends HuzuniScreen {

    private final ContainerManager manager;

    private final RateLimiter updateLimiter = new RateLimiter(TimeUnit.SECONDS, 60);

    private Container settings;

    // Font data used within different parts of the menu.
    private final FontData title, mods, description, defaultFont, textField;

    private ResourceLocation blurShader;

    public GuiTestScreen() {
        super();
        BasicRenderer renderer = new BasicRenderer();
        manager = new ContainerManager(renderer, renderer);
        title = huzuni.fontManager.getFont("Roboto Condensed", 48, Font.BOLD, true);
        description = huzuni.fontManager.getFont("Roboto Condensed", 16, Font.ITALIC, true);
        mods = huzuni.fontManager.getFont("Roboto Condensed", 18, Font.PLAIN, true);
        defaultFont = huzuni.fontManager.getFont("Roboto Condensed", 20, Font.PLAIN, true);
        textField = huzuni.fontManager.getFont("Roboto Condensed", 20, Font.ITALIC, true);
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
        modsList.setLayout(new ListLayout(6, 1));
        modsList.setPosition(x, y);
        modsList.setSize(scrollWidth, height);

        // Populate the scrollable container with the mods of huzuni.
        for (Mod mod : huzuni.modManager.getMods()) {
            Button button = new ModButton(mod, this);
            button.setFont(mods);
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
        while (updateLimiter.reached())
            manager.update();
        manager.render();
        //GLManager.update();
        GlStateManager.disableBlend();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        manager.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        manager.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	//if (keyCode == KEY_ESCAPE) {
    	//	mc.entityRenderer.switchUseShader();
		//}
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
    public void loadModSettings(Mod mod) {
        settings.clear();

        // Create the title label.
        Label title = new Label("title", mod.getName(), this.title, new Color(218, 218, 218));
        title.setPosition(10, 10);
        settings.add(title);

        Label description = new Label("description", mod.getDescription(), this.description, new Color(118, 118, 118));
        description.setPosition(10, 40);
        settings.add(description);

        TextField nameField = new TextField("lined", mod.settings.getDisplayName(), mod.getName());
        nameField.setSize(100, textField.getFontHeight() + 2);
        nameField.setPosition(10, settings.getHeight() - nameField.getHeight() - 2);
        nameField.setFont(textField);
        settings.add(nameField);

        ScrollableContainer childContainer = new ScrollableContainer("invisible-background");
        childContainer.setLayout(new ListLayout(1, 1));
        childContainer.setPosition(10, 60);
        childContainer.setSize(settings.getWidth() - 20, settings.getHeight() - 60 - nameField.getHeight() - 2);

        loadNodes(mod, childContainer);
        childContainer.layout();
        settings.add(childContainer);
        settings.layout();
    }

    private void loadNodes(Node node, Container container) {
        // Create the components for each child within the node.
        for (Node child : node.getChildren()) {

            // Create a check box for the toggleable.
            if (child instanceof Toggleable) {
                ToggleableCheckbox checkbox = new ToggleableCheckbox((Toggleable) child);
                checkbox.setFont(defaultFont);
                container.add(checkbox);

                // Create value container for each value.
            } else if (child instanceof Value) {
                ValueContainer valueContainer = new ValueContainer((Value) child);
                valueContainer.getTitle().setFont(defaultFont);
                valueContainer.getDescription().setFont(this.description);
                valueContainer.getDescription().setColor(new Color(118, 118, 118));
                container.add(valueContainer);

                // If we have JUST a node.
            } else if (child.getClass().isAssignableFrom(Node.class)) {
                Container container1 = new Container("goob");
                container1.setUseLayoutSize(true);
                container1.setLayering(false);
                container1.setAutoLayout(true);
                container1.setLayout(new GridLayout(2, GridLayout.INFINITE_LENGTH, 0, 0, 1));
                loadNodes(child, container1);
                container1.layout();
                container.add(container1);
            }
        }
    }

}
