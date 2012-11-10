package pl.edu.agh.megamud.dao;

import pl.edu.agh.megamud.dao.base.ProfessionBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "profession")
public class Profession extends ProfessionBase{
	public final static Profession DEFAULT=new Profession("borg");
	public Profession(){
		
	}
	public Profession(String s){
		this.setName(s);
	}
}