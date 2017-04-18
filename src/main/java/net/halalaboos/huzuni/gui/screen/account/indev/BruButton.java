package net.halalaboos.huzuni.gui.screen.account.indev;

import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.ClickAction;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;

import java.util.function.BiFunction;

/**
 * {@link Button} that uses a {@link BiFunction} to handle mouse click actions, similar to the {@link net.halalaboos.mcwrapper.api.client.gui.screen.Button} class.
 * Since we won't be using the {@link #onPressed()} method, I'm manually just adding the same action listeners from the Button class.  I just don't feel the
 * need to make a separate component type for this, since it's still a {@link Button} and there's no need to make another renderer for this.
 *
 * The purpose for this is because the current {@link #onPressed()} method doesn't seem to have anything for the mouse button, and I'd rather get
 * your (Hal) input on this concept before I go about changing the actual class.
 *
 * This class might not even be necessary and I could be looking over things, which would be pretty normal
 */
public class BruButton extends Button {

	private BiFunction<BruButton, MouseButton, Boolean> clickFunction;

	private boolean pressed = false;

	public BruButton(String tag, String text) {
		super(tag, text);

		this.addListener(Actions.MOUSEPRESS, (ClickAction.ClickActionListener) action -> {
			if (isHovered() && isPointInside(action.x, action.y)) {
				pressed = true;
				return true;
			}
			return false;
		});
		this.addListener(Actions.MOUSERELEASE, (ClickAction.ClickActionListener) action -> {
			if (pressed && isPointInside(action.x, action.y)) {
				pressed = false;
				clickFunction.apply(this, MouseButton.getMouseButton(action.buttonId));
				return true;
			}
			pressed = false;
			return false;
		});
		setHeight(20);
	}

	public void onClick(BiFunction<BruButton, MouseButton, Boolean> onClick) {
		this.clickFunction = onClick;
	}

	@Override
	protected final void onPressed() {}
}
