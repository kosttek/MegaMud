package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class PlayerTest {

	private static ConnectionSource connectionSource;
	private static String databaseUrl = "jdbc:sqlite:db/test.db";
	private static Dao<Player,String> accountDao;
	
	private static String predefinedLogin = "predefinedLogin";
	private static String predefinedPassword = "predefinedPassword";
	
	@Before
	public void setUp() throws Exception {
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();
		DbManager.init();
		TableUtils.clearTable(connectionSource, Player.class);

		accountDao = Player.createDao();
		
		String predefinedLogin = PlayerTest.predefinedLogin;
		String password = PlayerTest.predefinedPassword;
		Player predefinedAccount = new Player();
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
			Player.registerNewAccount(login, password);
	
			Player account2 = Player.getByLogin(login);
			Assert.assertNotNull(account2);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Could not create a new account.");
		}
	}

	@Test (expected = SQLException.class)
	public void should_not_create_nonunique_login() throws SQLException{
		Player account = new Player();
		account.setLogin(predefinedLogin);
		account.setPassword(predefinedPassword);

		accountDao.create(account);
	}
	
	@Test
	public void should_get_account_by_login_and_password(){
		Player account = Player.getByLoginAndPassword(predefinedLogin, predefinedPassword);
		assertNotNull(account);
		assertEquals(predefinedLogin, account.getLogin());
	}
	
	@Test
	public void should_return_null_for_invalid_password(){
		assertEquals(null, Player.getByLoginAndPassword(predefinedLogin, "invalid_password"));
	}
	
	@Test
	public void should_find_existing_account(){
		assertTrue(Player.isRegistered(predefinedLogin));
	}
	
	@Test
	public void should_not_find_not_existing_account(){
		assertFalse(Player.isRegistered("fakeAccount"));
	}
	
	@Test
	public void should_set_hashed_password(){
		Player account = new Player();
		account.setPassword(predefinedPassword);		
		String hash = Player.hashPassword(predefinedPassword);
		
		assertEquals(hash, account.getPasswordMd5());
	}
}
