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

	private final List<Account> accounts = new ArrayList<>();

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
		// In the case where someone's password contains a ':'.
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
	 * Adds the given {@link Account} to our {@link #accounts}.
	 *
	 * @param account The account to add
	 */
	public void addAccount(Account account) {
		if (!accounts.contains(account)) {
			this.accounts.add(account);
		}
	}

	public void removeAccount(Account account) {
		if (accounts.contains(account)) {
			accounts.remove(account);
		}
	}

	/**
	 * This method does two things:
	 *
	 * For starters, it will load the <i>old</i> accounts file ({@link #oldAccountsFile}) and convert them over to our
	 * new format using {@link #convertOldAccounts()}.
	 *
	 * Afterwards, it will load accounts from our {@link #accountsFile}, then add them to our {@link #accounts}.
	 */
	public void load() {
		accounts.clear();
		oldAccountsFile = new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts.txt");
		if (accountsFile.exists()) {
			Gson gson = new Gson();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
				Type type = new TypeToken<List<Account>>(){}.getType();
				List<Account> accounts = gson.fromJson(reader, type);
				this.accounts.addAll(accounts);
				reader.close();
			} catch (IOException e) {
				Huzuni.LOGGER.log(Level.ERROR, "Unable to load accounts!");
			}
		} else {
			try {
				accountsFile.createNewFile();
			} catch (IOException e) {
				Huzuni.LOGGER.log(Level.ERROR, "Unable to create accounts file!");
			}
		}

		//
		// The "convertOldAccounts" function is unnecessary, since it is only invoked once.
		// Another thing I modified was the 'loadedAccounts' field. It is implied that they are already loaded.
		// A comment explaining this code would be nice.
		//
		if (oldAccountsFile.exists()) {
			try {
				Huzuni.LOGGER.log(Level.INFO, "Converting old Accounts.txt file...");
				List<String> oldAccounts = FileUtils.readFile(oldAccountsFile);
				for (String s : oldAccounts) {
					addAccount(getAccount(s));
				}
				oldAccountsFile.renameTo(new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts-UNUSED.txt"));
			} catch (IOException e) {
				Huzuni.LOGGER.log(Level.ERROR, "Unable to convert old accounts file!");
			}
		}
	}

	/**
	 * Saves the {@link #accounts} to the {@link #accountsFile}.
	 */
	public void save() {
		try {
			FileWriter fileWriter = new FileWriter(accountsFile);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(accounts, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			Huzuni.LOGGER.log(Level.ERROR, "Unable to save accounts!");
		}
	}

	public List<Account> getAccounts() {
		return accounts;
	}
}
