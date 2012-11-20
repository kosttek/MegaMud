package pl.edu.agh.megamud.world;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.Portal;
import pl.edu.agh.megamud.dao.base.LocationBase;
import pl.edu.agh.megamud.dao.base.PortalBase;

public class CaveInitializer {
	
	static private String moduleName;
	static public Location A1,
							B1, B2, B3,
							C3, C5, C6, C7,
							D1, D2, D3, D4, D5, D7,
							E1, E5, E6, E7,
							F1;
	
	private static String getId(){ return moduleName; }
	
	private static Location prepareLocation(String name,String desc) throws SQLException{
		Location loc1 = new Location();
		
		loc1.setName(name)
			.setDescription(desc)
			.setModule(getId());
		
		LocationBase.createDao().create(loc1);
		
		return loc1;
	}
	
	private static void preparePortal(String from,String to,String name) throws SQLException{
		Location loc1 = LocationBase.createDao()
				.queryBuilder()
					.where().eq("name", from)
					.and().eq("module", getId())
				.query().
				get(0);
		Location loc2 = LocationBase.createDao()
				.queryBuilder()
					.where().eq("name", to)
					.and().eq("module", getId())
				.query()
				.get(0);
		
		Portal p = new Portal();
		p.setDestination(loc2);
		p.setEntry(loc1);
		p.setName(name);
		PortalBase.createDao().create(p);
	}	
	
	public static void init(String moduleName) throws SQLException{
		CaveInitializer.moduleName = moduleName;
		
		
		
		A1 = prepareLocation("start","Znajdujesz sie w wielkiej grocie. Wyglada na to, ze sie tu ocknales.");

		B1 = prepareLocation("b1","Ten tunel skalny ma bardzo sliska powierzchnie. Boisz sie, ze upadniesz.");
		preparePortal("start","b1","poludnie");
		preparePortal("b1","start","polnoc");
		
		B2 = prepareLocation("b2","Idac korytarzem czujesz, ze wznosi sie ku gorze.");
		
		preparePortal("b1","b2","wschod");
		preparePortal("b2","b1","zachod");
		
		B3 = prepareLocation("b3","Jestes w malej jaskini. Na srodku pali sie ognisko.");
		preparePortal("b2","b3","wschod");
		preparePortal("b3","b2","zachod");		
	}
}
