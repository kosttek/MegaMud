package pl.edu.agh.megamud.dao;

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.dao.base.ModuleBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "module")
public class Module extends ModuleBase{
	//pl.edu.agh.megamud.module.DefaultModule
	static{
		Module m=new Module();
		m.setJava_class("pl.edu.agh.megamud.module.DefaultModule");
		try {
			Module.createDao().deleteBuilder().delete();
			m.create();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Module> getModules() throws SQLException{
		return ModuleBase.createDao().queryForAll();
	}
	
	public Module create() throws SQLException{
		ModuleBase.createDao().create(this);
		return this;
	}	

	public Module refresh() throws SQLException{
		ModuleBase.createDao().refresh(this);
		return this;
	}
}