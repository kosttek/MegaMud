package pl.edu.agh.megamud.dao;

import com.j256.ormlite.table.DatabaseTable;

import pl.edu.agh.megamud.dao.base.PlayerCreatureBase;

@DatabaseTable(tableName = "player_creature")
public class PlayerCreature extends PlayerCreatureBase {

	public PlayerCreature(){}
	
	public PlayerCreature(Player player){
		setPlayer(player);
	}
}