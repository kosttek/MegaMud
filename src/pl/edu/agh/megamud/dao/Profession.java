package pl.edu.agh.megamud.dao;

import pl.edu.agh.megamud.dao.base.ProfessionBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "profession")
public class Profession extends ProfessionBase{
	public final static Profession DEFAULT=new Profession("borg");
	
	static{
		try{
			Profession.createDao().create(DEFAULT);
			Profession.createDao().refresh(DEFAULT);
		}catch(Exception e){}
	}
	public Profession(){
		
	}
	public Profession(String s){
		this.setName(s);
		this.setLevel(0);
		this.setParent(null);
		this.setDescription("");
	}
}