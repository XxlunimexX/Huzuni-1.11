package net.halalaboos.huzuni.gui.screen.account.indev;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.util.FileUtils;
import net.halalaboos.huzuni.api.util.RateLimiter;
import net.halalaboos.huzuni.indev.ColorPack;
import net.halalaboos.huzuni.indev.gui.ContainerManager;
import net.halalaboos.huzuni.indev.gui.FontData;
import net.halalaboos.huzuni.indev.gui.components.Button;
import net.halalaboos.huzuni.indev.gui.containers.ScrollableContainer;
import net.halalaboos.huzuni.indev.gui.impl.BasicRenderer;
import net.halalaboos.huzuni.indev.gui.impl.BasicToolbox;
import net.halalaboos.huzuni.indev.gui.layouts.ListLayout;
import net.halalaboos.mcwrapper.api.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;

public class AccountScreen extends Screen {

	private final BasicToolbox toolbox = new BasicToolbox();
	private final RateLimiter updateLimiter = new RateLimiter(TimeUnit.SECONDS, 60);

	private final FontData globalFont;
	private ContainerManager manager;

	private final File accountsFile = new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts.txt");

	private List<String> totalAccounts;

	public AccountScreen() {
		this.globalFont = huzuni.resourceCreator.create("font", "font_global", "RobotoCondensed-Regular.ttf", Font.PLAIN, 18);
		ColorPack colorPack = ColorPack.BLUE_GREY;
		colorPack.apply(toolbox);
		BasicRenderer renderer = new BasicRenderer(toolbox);
		manager = new ContainerManager(renderer, toolbox);
		totalAccounts = this.readAccounts();
	}

	@Override
	public void initGui() {
		super.initGui();
		manager.clear();

		int padding = ((int) (this.width * 0.1F));
		int y = (int) (height * (60F / 1080F));
		int width = this.width - (padding * 2);
		int height = this.height / 2;

		ScrollableContainer accountList = new ScrollableContainer("accounts");
		accountList.setLayering(false);
		accountList.setLayout(new ListLayout(6, 1));
		accountList.setPosition(padding, y);
		accountList.setSize(width, height);

		for (String account : totalAccounts) {
			Button button = new AccountButton(account);
			button.setFont(globalFont);
			button.setHeight(20);
			accountList.add(button);
		}

		accountList.layout();
		manager.add(accountList);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawBackground();
		while (updateLimiter.reached())
			manager.update();
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

	private List<String> readAccounts() {
		if (accountsFile.exists()) {
			return FileUtils.readFile(accountsFile);
		}
		return new ArrayList<>();
	}
}
