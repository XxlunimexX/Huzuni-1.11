package net.halalaboos.huzuni.gui.screen.account;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.account.Account;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.api.util.RateLimiter;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.indev.ColorPack;
import net.halalaboos.huzuni.indev.gui.Container;
import net.halalaboos.huzuni.indev.gui.ContainerManager;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.actions.Actions;
import net.halalaboos.huzuni.indev.gui.actions.KeystrokeAction;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.components.Label;
import net.halalaboos.huzuni.indev.gui.components.TextField;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.impl.BasicToolbox;
import net.halalaboos.huzuni.indev.gui.layouts.ListLayout;
import net.halalaboos.huzuni.indev.gui.layouts.PaddedLayout;
import net.halalaboos.mcwrapper.api.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

public class AccountScreen extends Screen {

	private final RateLimiter updateLimiter = new RateLimiter(TimeUnit.SECONDS, 60);

	private final FontData globalFont, titleFont;

	protected ContainerManager manager;

	/**
	 * The {@link Label} used to display the current logged in user in the format of "Logged in: [username]"
	 */
	Label status;

	public AccountScreen() {
		this.globalFont = huzuni.resourceCreator.create("font", "font_global", "RobotoCondensed-Regular.ttf", Font.PLAIN, 18);
		this.titleFont = huzuni.resourceCreator.create("font", "font_title", "RobotoCondensed-Bold.ttf", Font.BOLD, 48);
		ColorPack colorPack = ColorPack.BLUE_GREY;
		BasicToolbox toolbox = new BasicToolbox();
		colorPack.apply(toolbox);
		BasicRenderer renderer = new BasicRenderer(toolbox);
		manager = new ContainerManager(renderer, toolbox);
		setPanorama(true);
	}

	@Override
	public void initGui() {
		super.initGui();
		manager.clear();

		int padding = ((int) (this.width * 0.1F));
		int y = (int)(this.height * 0.1F);
		int width = this.width - (padding * 2);
		int height = this.height / 2;

		Container statusContainer = setupStatusContainer(padding, y, width, 40);
		ScrollableContainer accountList = setupAccountContainer(padding, y + statusContainer.getHeight(), width, height);
		Container controlsContainer = setupControlsContainer(padding, accountList.getY() + accountList.getHeight(), width, 32);

		statusContainer.layout();
		accountList.layout();
		controlsContainer.layout();
		manager.add(statusContainer);
		manager.add(accountList);
		manager.add(controlsContainer);
	}

	private Container setupControlsContainer(int x, int y, int width, int height) {
		ScrollableContainer controlsContainer = new ScrollableContainer("controls");
		controlsContainer.setLayout(new ListLayout(6, 1));
		controlsContainer.setPosition(x, y);
		controlsContainer.setSize(width, height);
		Button addAccount = new Button("add", "Add/Login");
		addAccount.onPressed((button, action) -> createLoginContainer());
		addAccount.setFont(globalFont);
		addAccount.setHeight(20);
		controlsContainer.add(addAccount);
		return controlsContainer;
	}

	private Container setupLoginContainer(int x, int y, int width, int height) {
		ScrollableContainer loginContainer = new ScrollableContainer("login");
		loginContainer.setLayout(new ListLayout(6, 1));
		loginContainer.setPosition(x, y);
		loginContainer.setLayering(false);
		loginContainer.setSize(width, height);

		TextField username = new TextField("username", "");
		username.setFont(globalFont);
		username.setHeight(20);
		TextField password = new TextField("password", "");
		password.setFont(globalFont);
		password.setHeight(20);

		Button login = new Button("login", "Login (Offline)");
		login.onPressed((button, action) -> {
			try {
				//If the password field is empty, assume this is a cracked account
				if (password.getText().length() == 0 && username.getText().length() > 1) {
					Account cracked = new Account();
					cracked.setLoginUser(username.getText());
					huzuni.accountManager.addAccount(cracked);
					cracked.login();
					status.setText("Logged in: " + getMinecraft().session().name());
					initGui();
					return true;
				}
				//If the account fails to log in, an exception is thrown and the account won't be added.
				MinecraftUtils.loginToMinecraft(username.getText(), password.getText());
				Account added = new Account();
				added.setLoginUser(username.getText());
				added.setPassword(password.getText());
				huzuni.accountManager.addAccount(added);
				status.setText("Logged in: " + getMinecraft().session().name());
				initGui();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		});
		login.setFont(globalFont);
		login.setHeight(20);

		//If the password text field has no text, then have the login button display that it'll be added as an offline account
		password.addListener(Actions.KEYSTROKE, (KeystrokeAction.KeystrokeActionListener) action -> {
			if (password.getText().length() > 0) {
				login.setText("Login");
			} else {
				login.setText("Login (Offline)");
			}
			return false;
		});

		Button close = new Button("cancel", "Cancel");
		close.onPressed((button, action) -> {
			manager.remove(loginContainer);
			return true;
		});
		close.setHeight(20);
		close.setFont(globalFont);
		loginContainer.add(username);
		loginContainer.add(password);
		loginContainer.add(login);
		loginContainer.add(close);
		return loginContainer;
	}

	boolean displayDeleteWarning(AccountButton accountButton) {
		//If the alert popup is already displayed, don't do anything
		if (manager.contains("alert")) return false;

		ScrollableContainer warningContainer = new ScrollableContainer("alert");
		warningContainer.setLayout(new ListLayout(6, 1));
		warningContainer.setPosition((width / 2) - 100, (height / 2) - 45);
		warningContainer.setLayering(false);
		warningContainer.setSize(200, 65);

		Label confirm = new Label("title", "Delete account \"" + accountButton.account.getUsername() + "\"?");
		confirm.setFont(globalFont);
		confirm.setHeight(20);

		Button delete = new Button("delete", "Delete");
		delete.onPressed((button, action) -> {
			Account account = accountButton.account;
			huzuni.accountManager.removeAccount(account);
			initGui();
			return true;
		});
		delete.setFont(globalFont);
		delete.setHeight(20);

		Button close = new Button("cancel", "Cancel");
		close.onPressed((button, action) -> {
			manager.remove(warningContainer);
			return true;
		});
		close.setHeight(20);
		close.setFont(globalFont);
		warningContainer.add(confirm);
		warningContainer.add(delete);
		warningContainer.add(close);
		warningContainer.layout();
		manager.add(warningContainer);
		return false;
	}

	/**
	 * Sets up the accounts container, which contains a {@link ScrollableContainer} with {@link AccountButton buttons}
	 * for each account in the {@link #accountsFile}.
	 */
	private ScrollableContainer setupAccountContainer(int x, int y, int width, int height) {
		ScrollableContainer accountList = new ScrollableContainer("accounts");
		accountList.setLayering(false);
		accountList.setLayout(new ListLayout(6, 1));
		accountList.setPosition(x, y);
		accountList.setSize(width, height);

		for (Account account : Huzuni.INSTANCE.accountManager.getAccounts()) {
			Button button = new AccountButton(account, this);
			button.setFont(globalFont);
			button.setHeight(20);
			accountList.add(button);
		}
		return accountList;
	}

	/**
	 * TODO: This shouldn't be how we approach our screens. One container should be necessary, will provide a new layout to make this easier.
	 * Sets up the status container, which is used to render the {@link #status status text} above the account list.
	 */
	private Container setupStatusContainer(int x, int y, int width, int height) {
		Container statusContainer = new Container("info");
		statusContainer.setLayout(new PaddedLayout( 6));
		statusContainer.setPosition(x, y);
		statusContainer.setSize(width, height);
		//The default status text will just say you're logged in as whatever your session name is.
		this.status = new Label("accountstatus", "Logged in: " + getMinecraft().session().name(), titleFont, new Color(218, 218, 218));
		statusContainer.add(status);
		return statusContainer;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GLUtils.glColor(0, 0, 0, 0.25F);
		GLUtils.drawRect(0, 0, width, height);
		while (updateLimiter.reached())
			manager.update();
		manager.render();
		getGLStateManager().disableBlend();
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		manager.mousePressed(mouseX, mouseY, mouseButton);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		manager.mouseReleased(mouseX, mouseY, mouseButton);
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		manager.keyTyped(typedChar, keyCode);
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

	private boolean createLoginContainer() {
		if (manager.contains("login")) return false;
		Container login = setupLoginContainer((width / 2) - 100, (height / 2) - 45, 200, 95);
		login.layout();
		manager.add(login);
		return true;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
