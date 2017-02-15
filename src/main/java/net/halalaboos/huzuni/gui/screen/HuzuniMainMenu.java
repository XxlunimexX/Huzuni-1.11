package net.halalaboos.huzuni.gui.screen;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.util.render.Texture;
import net.halalaboos.huzuni.gui.screen.account.HuzuniAccounts;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.halalaboos.huzuni.render.PanoramaRenderer;
import net.minecraft.realms.RealmsBridge;
import net.minecraftforge.common.ForgeVersion;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class HuzuniMainMenu extends HuzuniScreen {
	
    private final Texture TITLE = new Texture("title.png");
    
    private final PanoramaRenderer panoramaRenderer = new PanoramaRenderer(width, height);
    private GuiButton realmsButton;
    
	public HuzuniMainMenu() {
		super();
	}

	@Override
	public void initGui() {
        panoramaRenderer.init();
        panoramaRenderer.updateSize(width, height);
		int y = this.height / 4 + 48;
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, y, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, y + 24, 98, 20, I18n.format("menu.multiplayer")));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 100, y + 24 * 2, 98, 20, I18n.format("fml.menu.mods")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 2, y + 24, 98, 20, "Accounts"));
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, y + 72, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, y + 72, 98, 20, I18n.format("menu.quit")));
        this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, y + 72));
		this.realmsButton = this.addButton(new GuiButton(14, this.width / 2 + 2, this.height / 4 + 48 + 24 * 2, 98, 20, I18n.format("menu.online", new Object[0]).replace("Minecraft", "").trim()));
		if (huzuni.settings.hasUpdate()) {
        	this.buttonList.add(new HuzuniLink(6, this.width / 2 - mc.fontRenderer.getStringWidth("Download") / 2, y + 102, mc.fontRenderer.getStringWidth("Download"), 15,"Download"));
        }
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (button.id == 1) {
			this.mc.displayGuiScreen(new GuiWorldSelection(this));
		}

		if (button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		
		if (button.id == 3) {
			this.mc.displayGuiScreen(new HuzuniAccounts(this));
		}

		if (button.id == 4) {
			this.mc.shutdown();
		}
		if (button.id == 6) {
	        try {
				Desktop.getDesktop().browse(new URL("http://halalaboos.net").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		if (button.id == 7) {
			this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
		}

		if (button.id == 14 && this.realmsButton.visible) {
			this.switchToRealms();
		}
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
        panoramaRenderer.panoramaTick();
        if (huzuni.settings.hasUpdate()) {
        	for (GuiButton button : buttonList) {
        		if (button.id == 6) {
        			return;
        		}
        	}
        	this.initGui();
        }
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.disableAlpha();
		panoramaRenderer.renderSkybox(mouseX, mouseY, partialTicks);
		GlStateManager.enableAlpha();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		float titleX = width / 2 - 150, titleY = 20;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		TITLE.render(titleX, titleY + 10, 300, 100);
		String forgeVersion = "Forge " + ForgeVersion.getVersion();
		String huzuniVersion = Huzuni.VERSION + Huzuni.MCVERSION;
		this.drawString(fontRenderer, huzuniVersion, width - fontRenderer.getStringWidth(huzuniVersion) - 2, height - 12, 0x3FFFFFFF);
        this.drawString(fontRenderer, forgeVersion, width - fontRenderer.getStringWidth(forgeVersion) - 2, height - 24, 0x3FFFFFFF);
        if (huzuni.settings.hasUpdate())
            this.drawCenteredString(fontRenderer, "New version available!", width / 2, height / 4 + 142, 0xFFFFFF);
	}

	private void switchToRealms() {
		RealmsBridge realmsbridge = new RealmsBridge();
		realmsbridge.switchToRealms(this);
	}
}
