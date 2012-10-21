package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.Account;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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
		connectionSource = new JdbcConnectionSource(databaseUrl);
		accountDao = DaoManager.createDao(connectionSource, Account.class);

		TableUtils.createTableIfNotExists(connectionSource, Account.class);
		TableUtils.clearTable(connectionSource, Account.class);
		
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
			String newLogin = "newLogin";
			String password = "_secret";
			Account account = new Account();
			account.setLogin(newLogin);
			account.setPassword(password);

			accountDao.create(account);
	
			Account account2 = accountDao.queryForId(newLogin);
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
		try {
			PreparedQuery<Account> preparedQuery = accountDao.queryBuilder()
					.where().eq("login", predefinedLogin)
					.and().eq("password", predefinedPassword)
					.prepare();
			List<Account> accounts = accountDao.query(preparedQuery);
			
			assertEquals(1, accounts.size());
		} catch (SQLException e){
			e.printStackTrace();
			fail("SQLException");
		}
	}
	
	@Test
	public void should_return_empty_list_for_invalid_password(){
		try {
			PreparedQuery<Account> preparedQuery = accountDao.queryBuilder()
					.where().eq("login", predefinedLogin)
					.and().eq("password", "invalid_password")
					.prepare();
			List<Account> accounts = accountDao.query(preparedQuery);
			
			assertEquals(0, accounts.size());
		} catch (SQLException e){
			e.printStackTrace();
			fail("SQLException");
		}
	}
}
