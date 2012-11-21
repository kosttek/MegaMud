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
	
	public static void saveToDatabase()
	{
		
		Collection<GraphElements.MyVertex> col = EditorMouseMenu.g.getVertices();
		
		for (Iterator<GraphElements.MyVertex> iter = col.iterator(); iter.hasNext();)
		{
			GraphElements.MyVertex location = (GraphElements.MyVertex) iter.next();
			
			pl.edu.agh.megamud.dao.Location locClass = new pl.edu.agh.megamud.dao.Location();
			
			Collection<GraphElements.MyVertex> colsucc = EditorMouseMenu.g.getSuccessors(location);
			
			for (Iterator<GraphElements.MyVertex> itersucc = col.iterator(); itersucc.hasNext();)
			{
				GraphElements.MyVertex locationsucc = (GraphElements.MyVertex) itersucc.next();
				//locClass.connectTo(locationsucc.getName(), exitName)
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
