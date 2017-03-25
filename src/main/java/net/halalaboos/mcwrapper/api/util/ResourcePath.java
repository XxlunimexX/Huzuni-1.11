package net.halalaboos.mcwrapper.api.util;

import net.halalaboos.mcwrapper.api.MCWrapper;

import java.io.IOException;
import java.io.InputStream;

public class ResourcePath {

	private final String DOMAIN;
	private final String PATH;

	/**
	 * @param domain {@link #getDomain()}
	 * @param path {@link #getPath()}
	 */
	public ResourcePath(String domain, String path) {
		this.DOMAIN = domain;
		this.PATH = path;
	}

	public ResourcePath(String path) {
		this("minecraft", path);
	}

	/**
	 * The domain, or source of this asset.  Assets that are from mods would generally have the name of
	 * the mod as the domain.
	 */
	public String getDomain() {
		return DOMAIN;
	}

	/**
	 * The path of this asset, where the file itself is.
	 */
	public String getPath() {
		return PATH;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		else if (!(obj instanceof ResourcePath)) return false;
		else {
			ResourcePath assetLocation = (ResourcePath)obj;
			return this.getPath().equals(assetLocation.getPath()) && this.getDomain().equals(assetLocation.getDomain());
		}
	}

	@Override
	public String toString() {
		return DOMAIN + ":" + PATH;
	}

	public InputStream getInputStream() {
		try {
			return MCWrapper.getMinecraft().getInputStream(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
