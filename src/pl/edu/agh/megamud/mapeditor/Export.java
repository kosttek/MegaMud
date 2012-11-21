package pl.edu.agh.megamud.mapeditor;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.base.LocationBase;
import pl.edu.agh.megamud.world.CaveInitializer;

public class Export {

/*	private static Location prepareLocation(String name, String desc)
			throws SQLException {
		Location loc1 = new Location();

	//	loc1.setName(name).setDescription(desc).setModule(getId());

		LocationBase.createDao().create(loc1);

		return loc1;
	}*/
	
	public static void saveToDatabase() throws SQLException
	{
		
		Collection<GraphElements.MyVertex> col = EditorMouseMenu.g.getVertices();
		
		Iterator<GraphElements.MyVertex> iter = col.iterator();
				
		for (; iter.hasNext();)
		{
			GraphElements.MyVertex location = (GraphElements.MyVertex) iter.next();
			
			pl.edu.agh.megamud.dao.Location locClass = new pl.edu.agh.megamud.dao.Location();
			locClass.setName(location.getName());
			locClass.setDescription(location.getDescription());
			locClass.setModule("default");
			pl.edu.agh.megamud.dao.Location.createDao().create(locClass);
			
		}

		iter = col.iterator();
		
		for (; iter.hasNext();)
		{
			
			GraphElements.MyVertex location = (GraphElements.MyVertex) iter.next();
			
			Collection<GraphElements.MyEdge> colsucc = EditorMouseMenu.g.getOutEdges(location);
			
			for (Iterator<GraphElements.MyEdge> itersucc = colsucc.iterator(); itersucc.hasNext();)
			{
				GraphElements.MyEdge locationsucc = (GraphElements.MyEdge) itersucc.next();
				String portalName = locationsucc.getName();
				GraphElements.MyVertex destination = EditorMouseMenu.g.getDest(locationsucc);
				String destinationName = destination.getName();
				String sourceName = location.getName();
				pl.edu.agh.megamud.dao.Location sourceLoc = pl.edu.agh.megamud.dao.Location.getLocationByName(sourceName);
				pl.edu.agh.megamud.dao.Location destLoc = pl.edu.agh.megamud.dao.Location.getLocationByName(destinationName);
				sourceLoc.connectOneWay(destLoc, portalName);
			}
			
		}
	}
	
	
	/*
	A1 = prepareLocation(
			"start",
			"You find yourself in a huge cave. Air here is suffocating and you can barely see anything. "
					+ "It looks like you woke up here.");
	*/
}
