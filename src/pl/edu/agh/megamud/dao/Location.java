package pl.edu.agh.megamud.dao;

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.dao.base.LocationBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "location")
public class Location extends LocationBase{
	
	public Location connectTo(Location destination, String exitName) throws SQLException{
		Portal outgoing = new Portal(exitName);
		Portal incoming = new Portal(exitName);
		
		outgoing.setEntry(this);
		outgoing.setDestination(destination);
		
		incoming.setEntry(destination);
		incoming.setDestination(this);
		
		Dao<Portal, Integer> portalDao  = Portal.createDao();
		portalDao.create(outgoing);
		portalDao.create(incoming);
		
		return this;
	}
	
	public Portal getExitByName(String name) throws SQLException{
		Dao<Portal, Integer> dao = Portal.createDao();
		PreparedQuery<Portal> preparedQuery = dao.queryBuilder()
				.where().eq("name", name)
				.and().eq("entry_id", this.getId())
				.prepare();
		List<Portal> portals = dao.query(preparedQuery);
		if (portals.size() == 0){
			return null;
		} else {
			return portals.get(0);
		}
	}
	
	public Location getDestinationByExitName(String name) throws SQLException{
		Portal portal = this.getExitByName(name);
		if (portal == null){
			return null;
		}
		return portal.getDestination();
	}
	
	public Location create() throws SQLException{
		Location.createDao().create(this);
		return this;
	}	

	public Location refresh() throws SQLException{
		Location.createDao().refresh(this);
		return this;
	}
}