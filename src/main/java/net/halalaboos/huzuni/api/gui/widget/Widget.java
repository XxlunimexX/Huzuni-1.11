package net.halalaboos.huzuni.api.gui.widget;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.gui.Theme;
import net.halalaboos.huzuni.api.gui.WidgetManager;
import net.halalaboos.huzuni.api.node.Node;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import org.lwjgl.input.Mouse;

import java.io.IOException;

/**
 * Widgets are movable menus that have their own settings and display their own custom information.
 * */
public abstract class Widget extends Node {
	
	protected static final Huzuni huzuni = Huzuni.INSTANCE;
	
	protected final WidgetManager widgetManager;
		
	protected Theme theme;
	
	protected int x, y, width, height;
	
	protected int offsetX, offsetY;
	
	protected boolean enabled = false, dragging = false;
	
	protected Glue oldGlue = ScreenGlue.NONE, glue = ScreenGlue.NONE;
		
	public Widget(String name, String description, WidgetManager widgetManager) {
		super(name, description);
		this.setWidth(100);
		this.setHeight(12);
		this.widgetManager = widgetManager;
	}

	/**
	 * @return True if the given position is within this widget.
	 * */
	public boolean isPointInside(int x, int y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
	}

	/**
	 * Updates the widgets position, dragging information, and glue information.
	 * */
	public void update() {
		if (Mouse.isButtonDown(0)) {
			if (dragging) {
				this.x = GLUtils.getMouseX() - offsetX;
				this.y = GLUtils.getMouseY() - offsetY;
				ScreenGlue.keepWithinScreen(this);
				widgetManager.formatWidgets(this);
				Glue newGlue = ScreenGlue.getScreenGlue(this);
				WidgetGlue widgetGlue = widgetManager.calculateWidgetGlue(this);
				if (widgetGlue != null) {
					newGlue = widgetGlue;
				}
				if (!newGlue.equals(oldGlue)) {
					updateGlue(oldGlue, newGlue);
					this.oldGlue = glue;
				}
				this.glue = newGlue;
			}
		} else {
			this.dragging = false;
		}
	}

	/**
	 * Applies the glue to this widget only if not dragging the widget.
	 * */
	public void useGlue() {
		if (!dragging) {
			glue.formatX(this);
			glue.formatY(this);
			ScreenGlue.keepWithinScreen(this);
		}
	}

	@Deprecated
	protected void updateGlue(Glue oldGlue, Glue newGlue) {
		
	}

	/**
	 * Invoked when the mouse button is clicked.
	 * */
	public boolean mouseClicked(int x, int y, int buttonId) {
		boolean returnValue = dragging = isPointInside(x, y);
		if (returnValue) {
			offsetX = x - this.x;
			offsetY = y - this.y;
		}
		return returnValue;
	}

	/**
	 * Invoked when rendering the menu. This is where the custom information is displayed.
	 * */
	public abstract void renderMenu(int x, int y, int width, int height);

	/**
	 * Invoked when the keyboard is typed.
	 * */
	public void keyTyped(int keyCode) {}

	/**
	 * Invoked when loading the widget.
	 * */
	public void load() {}

	/**
	 * Invoked when initializing the widget.
	 * */
	public void init() {}

	public int getX() {
		return x;
	}

	public Widget setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Widget setY(int y) {
		this.y = y;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isDragging() {
		return dragging;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Widget setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public Glue getGlue() {
		return glue;
	}

	public Widget setGlue(Glue glue) {
		this.glue = glue;
		return this;
	}
	
	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	@Override
	public boolean hasNode(JsonObject json) {
		JsonElement name = json.get("name");
		return name != null && name.getAsString().equals(getName());
	}
	
	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			setEnabled(json.get("enabled").getAsBoolean());
			setX(json.get("x").getAsInt());
			setY(json.get("y").getAsInt());
			String glue = json.get("glue").getAsString();
			if (!WidgetGlue.loadGlue(this, widgetManager, glue))
				setGlue(ScreenGlue.load(json.get("glue").getAsString()));
		}
	}

	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		json.addProperty("name", getName());
		json.addProperty("enabled", isEnabled());
		json.addProperty("x", x);
		json.addProperty("y", y);
		if (glue instanceof ScreenGlue)
			json.addProperty("glue", ((ScreenGlue) glue).name());
		else if (glue instanceof WidgetGlue) {
			json.addProperty("glue", WidgetGlue.getName(((WidgetGlue) glue)));
		}
	}	
	
}
