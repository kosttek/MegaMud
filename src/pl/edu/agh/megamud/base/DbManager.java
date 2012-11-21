/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package pl.edu.agh.megamud.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.ItemAttribute;
import pl.edu.agh.megamud.dao.LocationItem;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Portal;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DbManager {
	private static String dbPath = "jdbc:sqlite:db/dev.db";
	
	private DbManager(){}

	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		DbManager.dbPath = dbPath;
	}

	private static ConnectionSource connectionSource;

	public static ConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			try {
				connectionSource = new JdbcConnectionSource(dbPath);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connectionSource;
	}

	public static void init() {
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(),
					Player.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					PlayerCreature.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					Profession.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					pl.edu.agh.megamud.dao.Item.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					CreatureItem.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					Attribute.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					ItemAttribute.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					CreatureAttribute.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					pl.edu.agh.megamud.dao.Module.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					pl.edu.agh.megamud.dao.Location.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					Portal.class);
			TableUtils.createTableIfNotExists(getConnectionSource(),
					LocationItem.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
