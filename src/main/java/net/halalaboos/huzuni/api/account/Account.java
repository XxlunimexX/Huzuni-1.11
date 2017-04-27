package net.halalaboos.huzuni.api.account;

import com.mojang.authlib.exceptions.AuthenticationException;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.mcwrapper.api.MCWrapper;

/**
 * Represents a Minecraft account stored in our {@code accounts.json} file.
 *
 * Most, if not all, clients just store the accounts in an {@code accounts.txt} file with a list of user:pass logins.
 * We did the same for a while, but it was messy.  There also was an issue where things such as migrated accounts
 * would display in things such as our account manager with the email rather than the actual username (which is
 * possible to fix with the user:pass approach, some clients have done user:pass:actualusername), and it just felt a
 * bit hacky.
 *
 * This new approach gives us a bit more of a readable representation of individual accounts and gives us a bit more
 * control over how they're displayed in our accounts manager.
 */
public class Account {

	/**
	 * The 'username' that is used to log into this account.  Since some accounts may be migrated, this won't always
	 * be the same as {@link #username}
	 */
	private String loginUser;

	/**
	 * The username of the account that is displayed in-game.
	 *
	 * The reason we are storing this as well as the {@link #loginUser} is so that we can display this instead
	 * of an email for migrated accounts on our alt manager.
	 */
	private String username;

	/**
	 * Uh-oh!  Plain text, I know.  Same as our current version (and any other client out there), though doesn't
	 * make it any more secure.  Encryption would be nice to add, though.
	 *
	 * The reason that we don't have encryption for now is that we haven't put a lot of thought into how we would
	 * handle encryption/decryption.  Maybe just have it be specific to the computer's HWID or something? Who knows...
	 */
	private String password;

	/**
	 * Whether or not the account is 'hidden', meaning the username isn't shown in the accounts screen.
	 */
	private boolean hidden = false;

	/**
	 * The actual username for the account, won't always be the same as the {@link #getLoginUser()}.
	 *
	 * @see #username
	 * @return The account's name
	 */
	public String getUsername() {
		if (username == null) {
			return loginUser;
		}
		return username;
	}

	/**
	 * The login username, which is used to log into the account.  For migrated accounts, this will be an email.
	 *
	 * Otherwise, it will be the same as {@link #getUsername()}
	 *
	 * @see #loginUser
	 * @return The login username
	 */
	public String getLoginUser() {
		return loginUser;
	}

	/**
	 * The password used to log into the account.  For now this is plaintext until we put some time into some
	 * encryption ideas.
	 *
	 * @see #password
	 * @return The account's password
	 */
	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
		if (!loginUser.contains("@")) {
			setUsername(loginUser);
		}
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isHidden() {
		return hidden;
	}

	public final void login() {
		try {
			if (isCracked()) {
				MinecraftUtils.loginOffline(getUsername());
			} else {
				MinecraftUtils.loginToMinecraft(loginUser, password);
			}
			this.setUsername(MCWrapper.getMinecraft().session().name());
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Whether or not the account is 'cracked', meaning it doesn't have a password and can only be used on
	 * offline/cracked servers.
	 */
	public boolean isCracked() {
		return password == null || password.length() == 0;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Account && ((Account) o).getUsername().equalsIgnoreCase(getUsername());
	}
}
