package net.halalaboos.huzuni;

import net.halalaboos.huzuni.api.util.FileUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Downloads the latest version from https://huzuni.github.io/version.
 * */
public final class HuzuniUpdater extends Thread {

	private final Huzuni huzuni;
	
	public HuzuniUpdater(Huzuni huzuni) {
		this.huzuni = huzuni;
	}
	
	@Override
	public void run() {
		String version = "";
		try {
			version = FileUtils.readURL(new URL("http://halalaboos.net/version"));
			//in case it returns some html gibberish
			if (version.length() >= 20) {
				version = Huzuni.BUILD_NUMBER + "";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		huzuni.settings.setNewestVersion(version);
	}
	
}
