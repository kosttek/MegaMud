package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_creature")
public abstract class PlayerCreatureBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = true)
	private String name;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Player player;
	
	@DatabaseField(canBeNull = false, defaultValue = "100")
	private Integer hp;
	
	@DatabaseField(canBeNull = false, defaultValue = "1")
	private Integer level;
	
	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer exp;
	
	@DatabaseField(canBeNull = false, defaultValue = "100")
	private Integer exp_needed;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Profession profession=Profession.DEFAULT;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureItem> creatureItems;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureAttribute> creatureAttributes;	
	
	public ForeignCollection<CreatureItem> getCreatureItems() {
		return creatureItems;
	}

	public void setCreatureItems(ForeignCollection<CreatureItem> creatureItems) {
		this.creatureItems = creatureItems;
	}

	public PlayerCreatureBase(){
		
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getExp_needed() {
		return exp_needed;
	}

	public void setExp_needed(Integer exp_needed) {
		this.exp_needed = exp_needed;
	}
	
	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public ForeignCollection<CreatureAttribute> getCreatureAttributes() {
		return creatureAttributes;
	}

	public void setCreatureAttributes(
			ForeignCollection<CreatureAttribute> creatureAttributes) {
		this.creatureAttributes = creatureAttributes;
	}

	public static Dao<PlayerCreature,String> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), PlayerCreature.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
