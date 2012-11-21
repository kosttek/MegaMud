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
package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.ItemAttribute;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.stmt.PreparedQuery;

public abstract class AttributeBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, unique = true)
	private String name;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<ItemAttribute> itemAttributes;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureAttribute> creatureAttributes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public ForeignCollection<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(
			ForeignCollection<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public ForeignCollection<CreatureAttribute> getCreatureAttributes() {
		return creatureAttributes;
	}

	public void setCreatureAttributes(
			ForeignCollection<CreatureAttribute> creatureAttributes) {
		this.creatureAttributes = creatureAttributes;
	}

	public AttributeBase() {

	}

	public static Attribute findByName(String name) {
		Dao<Attribute, Integer> dao = createDao();
		PreparedQuery<Attribute> preparedQuery;
		try {
			preparedQuery = dao.queryBuilder().where().eq("name", name)
					.prepare();
			List<Attribute> accounts = dao.query(preparedQuery);
			if (accounts.size() == 1) {
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void insertIfNotExists(String name) throws SQLException {
		Attribute attribute = findByName(name);
		if (attribute == null) {
			attribute = new Attribute(name);
			Attribute.createDao().create(attribute);
		}
	}

	public static Dao<Attribute, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					Attribute.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
