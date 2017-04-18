package net.halalaboos.huzuni.gui.screen.account.indev;

import net.halalaboos.huzuni.api.account.Account;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * {@link net.halalaboos.huzuni.indev.gui.components.Button} used for displaying and logging into an {@link Account}.
 */
public class AccountButton extends BruButton {

	private Account account;
	private AccountScreen screen;

	AccountButton(Account account, AccountScreen screen) {
		super("account", account.isHidden() ? "(hidden)" : account.getUsername());
		this.account = account;
		this.screen = screen;

		onClick((bruButton, mouseButton) -> {
			if (mouseButton == MouseButton.LEFT) {
				try {
					account.login();
					this.screen.status.setText("Logged in: " + getMinecraft().session().name());
				} catch (Exception e) {
					e.printStackTrace();
					this.screen.status.setText("Failed to login!");
				}
			} else if (mouseButton == MouseButton.RIGHT) {
				toggleHidden();
			}
			updateText();
			return true;
		});
	}

	private void updateText() {
		this.setText(!account.isHidden() ? account.getUsername() : "(hidden)");
	}

	private void toggleHidden() {
		account.setHidden(!account.isHidden());
	}
}
