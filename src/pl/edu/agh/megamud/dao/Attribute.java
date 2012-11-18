package pl.edu.agh.megamud.dao;

import pl.edu.agh.megamud.dao.base.AttributeBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "attribute")
public class Attribute extends AttributeBase{
	
	public static final String STRENGTH = "strength";
}