package pl.edu.agh.megamud.dao;

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.dao.base.ModuleNPCBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "module")
public class ModuleNPC extends ModuleNPCBase {
	public static List<ModuleNPC> getNPCs(Module mod) throws SQLException {
		return ModuleNPCBase.createDao().queryBuilder().where()
				.eq("module", mod.getJava_class()).query();
	}

	public ModuleNPC create() throws SQLException {
		ModuleNPCBase.createDao().create(this);
		return this;
	}

	public ModuleNPC refresh() throws SQLException {
		ModuleNPCBase.createDao().refresh(this);
		return this;
	}
}