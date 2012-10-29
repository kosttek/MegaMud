package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Account;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class AccountTest {

	private static ConnectionSource connectionSource;
	private static String databaseUrl = "jdbc:sqlite:db/test.db";
	private static Dao<Account,String> accountDao;
	
	private static String predefinedLogin = "predefinedLogin";
	private static String predefinedPassword = "predefinedPassword";
	
	@Before
	public void setUp() throws Exception {
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();
		DbManager.init();
		TableUtils.clearTable(connectionSource, Account.class);

		accountDao = Account.createDao();
		
		String predefinedLogin = AccountTest.predefinedLogin;
		String password = AccountTest.predefinedPassword;
		Account predefinedAccount = new Account();
		predefinedAccount.setLogin(predefinedLogin);
		predefinedAccount.setPassword(password);

		accountDao.create(predefinedAccount);		
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test
	public void should_create_new_account() {
		try {
			String login = "newLogin";
			String password = "_secret";
			Account.registerNewAccount(login, password);
	
			Account account2 = Account.getByLogin(login);
			Assert.assertEquals(password, account2.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create a new account.");
		}
	}

	@Test (expected = SQLException.class)
	public void should_not_create_nonunique_login() throws SQLException{
		Account account = new Account();
		account.setLogin(predefinedLogin);
		account.setPassword(predefinedPassword);

		accountDao.create(account);
	}
	
	@Test
	public void should_get_account_by_login_and_password(){
		Account account = Account.getByLoginAndPassword(predefinedLogin, predefinedPassword);
		assertFalse(account == null);
		assertEquals(predefinedLogin, account.getLogin());
	}
	
	@Test
	public void should_return_null_for_invalid_password(){
		assertEquals(null, Account.getByLoginAndPassword(predefinedLogin, "invalid_password"));
	}
	
	@Test
	public void should_find_existing_account(){
		assertTrue(Account.isRegistered(predefinedLogin));
	}
	
	@Test
	public void should_not_find_not_existing_account(){
		assertFalse(Account.isRegistered("fakeAccount"));
	}
}
