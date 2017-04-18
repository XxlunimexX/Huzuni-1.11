package net.halalaboos.huzuni.gui.screen.account.indev;

import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * {@link net.halalaboos.huzuni.indev.gui.components.Button} used for displaying and logging into a Minecraft account.
 *
 * TODO: Avoid using a String for {@link #account} and instead make an Account object that would be managed elsewhere
 */
public class AccountButton extends BruButton {

	private String account;
	private AccountScreen screen;

	private String hiddenText = "(hidden)";

	AccountButton(String account, AccountScreen screen) {
		super("account", account.split(":")[0]);
		this.account = account;
		this.screen = screen;

		onClick((bruButton, mouseButton) -> {
			if (mouseButton == MouseButton.LEFT) {
				try {
					String username = this.account.split(":")[0];
					String password = this.account.split(":")[1];
					MinecraftUtils.loginToMinecraft(username, password);
					this.screen.status.setText("Logged in: " + getMinecraft().session().name());
				} catch (Exception e) {
					e.printStackTrace();
					this.screen.status.setText("Failed to login!");
				}
			} else {
				boolean hidden = this.getText().equals(hiddenText);
				this.setText(hidden ? this.account.split(":")[0] : hiddenText);
			}
			return true;
		});
	}
}
