package net.halalaboos.huzuni.gui.screen.account.indev;

import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.indev.gui.components.Button;

public class AccountButton extends Button {

	private String account;

	AccountButton(String account) {
		super("account", account.split(":")[0]);
		this.account = account;
	}

	@Override
	protected void onPressed() {
		super.onPressed();
		try {
			String username = account.split(":")[0];
			String password = account.split(":")[1];
			MinecraftUtils.loginToMinecraft(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
