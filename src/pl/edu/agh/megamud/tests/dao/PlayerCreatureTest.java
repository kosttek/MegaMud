package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

public class PlayerCreatureTest {

	private static ConnectionSource connectionSource;
	private static String databaseUrl = "jdbc:sqlite:db/test.db";
	private static Dao<PlayerCreature,String> playerCreatureDao;
	private static Dao<Player,String> playerDao;
	private static Player predefinedPlayer = null;
	
	@BeforeClass
	public static void init() throws SQLException{
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();
		TableUtils.dropTable(connectionSource, Player.class, true);
		TableUtils.dropTable(connectionSource, PlayerCreature.class, true);
		
		DbManager.init();

		playerCreatureDao = PlayerCreature.createDao();
		playerDao = Player.createDao();	
		
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
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test
	public void should_autogenerate_ids() throws SQLException{
		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);

		PlayerCreature pc2 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc2);

		PlayerCreature pc3 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc3);		

		Integer expected = pc1.getId()+1;
		Integer actual = pc2.getId();
		assertEquals(expected, actual);

		expected = pc2.getId()+1;
		actual = pc3.getId();
		assertEquals(expected, actual);		
	}
	
	@Test(expected = SQLException.class)
	public void should_not_create_player_creature_without_player() throws SQLException{
		playerCreatureDao.create(new PlayerCreature());		
	}
	
	@Test
	public void should_create_player_creature_with_player() throws SQLException{
		PlayerCreature pc = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc);
		
		assertEquals(predefinedPlayer.getLogin(), pc.getPlayer().getLogin());
	}
	
	@Test
	public void should_allow_multiple_player_creatures_for_one_player() throws SQLException{
		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);

		PlayerCreature pc2 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc2);

		PlayerCreature pc3 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc3);		
		
		assertEquals(3, predefinedPlayer.getPlayerCreatures().size());
	}
	
	@Test
	public void should_allow_only_one_creature_for_player_creature_and_overwrite_previous() throws SQLException{
		Player timmy = new Player();
		timmy.setLogin("timmy");
		timmy.setPassword("_secret");
		playerDao.create(timmy);	
		
		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);
		
		assertEquals(predefinedPlayer.getLogin(), pc1.getPlayer().getLogin());
		
		pc1.setPlayer(timmy);
		playerCreatureDao.update(pc1);
		
		assertFalse(predefinedPlayer.getLogin().equals(pc1.getPlayer().getLogin()));
		assertEquals(timmy.getLogin(), pc1.getPlayer().getLogin());
	}
}
