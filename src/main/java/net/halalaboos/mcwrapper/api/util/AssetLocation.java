package net.halalaboos.mcwrapper.api.util;

public class AssetLocation {

	private final String DOMAIN;
	private final String PATH;

	/**
	 * @param domain {@link #getDomain()}
	 * @param path {@link #getPath()}
	 */
	public AssetLocation(String domain, String path) {
		this.DOMAIN = domain;
		this.PATH = path;
	}

	public AssetLocation(String path) {
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
		else if (!(obj instanceof AssetLocation)) return false;
		else {
			AssetLocation assetLocation = (AssetLocation)obj;
			return this.getPath().equals(assetLocation.getPath()) && this.getDomain().equals(assetLocation.getDomain());
		}
	}

	@Override
	public String toString() {
		return DOMAIN + ":" + PATH;
	}
}
