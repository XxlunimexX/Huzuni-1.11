package net.halalaboos.mcwrapper.api.util;

import net.halalaboos.mcwrapper.api.MCWrapper;

import java.io.IOException;
import java.io.InputStream;

public class ResourcePath {

	private final String domain;
	private final String path;

	/**
	 * @param domain {@link #getDomain()}
	 * @param path {@link #getPath()}
	 */
	public ResourcePath(String domain, String path) {
		this.domain = domain;
		this.path = path;
	}

	public ResourcePath(String path) {
		this("minecraft", path);
	}

	/**
	 * The domain, or source of this asset.  Assets that are from mods would generally have the name of
	 * the mod as the domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * The path of this asset, where the file itself is.
	 */
	public String getPath() {
		return path;
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
		return domain + ":" + path;
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
