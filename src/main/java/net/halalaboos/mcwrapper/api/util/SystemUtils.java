package net.halalaboos.mcwrapper.api.util;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class SystemUtils {

	public static void copyToClipboard(String text) {
		StringSelection stringselection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
	}
}
