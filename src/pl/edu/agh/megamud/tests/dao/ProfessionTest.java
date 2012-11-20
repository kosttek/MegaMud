package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ProfessionTest {

	private static ConnectionSource connectionSource;
	private static String databaseUrl = "jdbc:sqlite:db/test.db";

	private static Dao<PlayerCreature, String> playerCreatureDao;
	private static Dao<Player, String> playerDao;
	private static Dao<Profession, Integer> professionDao;

	private static Player predefinedPlayer = null;
	private static PlayerCreature predefinedPlayerCreature = null;

	@BeforeClass
	public static void init() throws SQLException {
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();
		TableUtils.dropTable(connectionSource, Player.class, true);
		TableUtils.dropTable(connectionSource, PlayerCreature.class, true);
		TableUtils.dropTable(connectionSource, Profession.class, true);

		DbManager.init();

		playerCreatureDao = PlayerCreature.createDao();
		playerDao = Player.createDao();
		professionDao = Profession.createDao();

		connectionSource.close();
	}

	@Before
	public void setUp() throws Exception {
		connectionSource = DbManager.getConnectionSource();
		TableUtils.clearTable(connectionSource, PlayerCreature.class);
		TableUtils.clearTable(connectionSource, Player.class);

		predefinedPlayer = new Player();
		predefinedPlayer.setLogin("predefinedPlayer");
		predefinedPlayer.setPassword("_secret");
		playerDao.create(predefinedPlayer);

		predefinedPlayerCreature = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(predefinedPlayerCreature);
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test
	public void should_traverse_profession_chain() throws SQLException {
		Profession ancestor = new Profession();
		ancestor.setName("ancestor");
		professionDao.create(ancestor);

		Profession child = new Profession();
		child.setName("child");
		child.setParent(ancestor);
		professionDao.create(child);

		predefinedPlayerCreature.setProfession(child);
		playerCreatureDao.update(predefinedPlayerCreature);

		Profession parent = predefinedPlayerCreature.getProfession()
				.getParent();
		assertEquals(ancestor.getName(), parent.getName());
	}
}