package net.halalaboos.huzuni.api.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {

	private List<Account> loadedAccounts = new ArrayList<>();

	private File accountsFile;

	/**
	 * This is only here so that old accounts are ported over.
	 */
	private File oldAccountsFile;

	/**
	 * Returns a new {@link Account} instance from the given 'raw' input.  This is mainly used to convert an existing
	 * 'accounts.txt' file that contains accounts in a user:pass format to an {@link Account}.
	 *
	 * @param raw The raw login, in a username:password format.
	 */
	public Account getAccount(String raw) {
		Account account = new Account();
		String username = raw.split(":")[0];
		//In the case where someone's password contains a ':'.
		String password = StringUtils.substringAfter(raw, ":");
		account.setLoginUser(username);
		account.setPassword(password);
		return account;
	}

	/**
	 * Sets the {@link #accountsFile}.  The reason this isn't done in the constructor is because the
	 * {@link Huzuni#getSaveFolder()} isn't yet declared at that point.
	 *
	 * @param accountsFile The accounts file (In the case of huzuni, it's just accounts.json)
	 */
	public void setFile(File accountsFile) {
		this.accountsFile = accountsFile;
	}

	/**
	 * Adds the given {@link Account} to our {@link #loadedAccounts}.
	 *
	 * @param account The account to add
	 */
	public void addAccount(Account account) {
		if (!loadedAccounts.contains(account)) {
			this.loadedAccounts.add(account);
		}
	}

	/**
	 * This method does two things:
	 *
	 * For starters, it will load the <i>old</i> accounts file ({@link #oldAccountsFile}) and convert them over to our
	 * new format using {@link #convertOldAccounts()}.
	 *
	 * Afterwards, it will load accounts from our {@link #accountsFile}, then add them to our {@link #loadedAccounts}.
	 */
	public void load() {
		loadedAccounts.clear();
		oldAccountsFile = new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts.txt");
		if (accountsFile.exists()) {
			Gson gson = new Gson();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
				Type type = new TypeToken<List<Account>>(){}.getType();
				List<Account> accounts = gson.fromJson(reader, type);
				loadedAccounts.addAll(accounts);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				accountsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		convertOldAccounts();
	}

	/**
	 * Saves the {@link #loadedAccounts} to the {@link #accountsFile}.
	 */
	public void save() {
		if (!accountsFile.exists()) {
			try {
				accountsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fileWriter = new FileWriter(accountsFile);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(loadedAccounts, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts the contents of our old {@code Accounts.txt} file to {@link Account} objects to be added to our
	 * {@link #accountsFile}.  After this, we rename the old file to {@code Accounts-UNUSED.txt}, since we will no
	 * longer be using these (though some people may not want their accounts deleted for whatever reason).
	 */
	private void convertOldAccounts() {
		if (oldAccountsFile.exists()) {
			try {
				Huzuni.LOGGER.log(Level.INFO, "Converting old Accounts.txt file...");
				List<String> oldAccounts = FileUtils.readFile(oldAccountsFile);
				for (String s : oldAccounts) {
					addAccount(getAccount(s));
				}
				oldAccountsFile.renameTo(new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts-UNUSED.txt"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Account> getLoadedAccounts() {
		return loadedAccounts;
	}
}
