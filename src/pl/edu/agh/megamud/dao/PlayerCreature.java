package pl.edu.agh.megamud.dao;

import pl.edu.agh.megamud.dao.base.PlayerCreatureBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_creature")
public class PlayerCreature extends PlayerCreatureBase {

	public PlayerCreature() {
	}

	public PlayerCreature(Player player) {
		setPlayer(player);
	}
}