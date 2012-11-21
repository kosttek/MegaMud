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

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.ItemAttribute;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public abstract class ItemBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = true)
	private String description;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer max_level;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureItem> creatureItems;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<ItemAttribute> itemAttributes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMax_level() {
		return max_level;
	}

	public void setMax_level(Integer max_level) {
		this.max_level = max_level;
	}

	public ForeignCollection<CreatureItem> getCreatureItems() {
		return creatureItems;
	}

	public void setCreatureItems(ForeignCollection<CreatureItem> creatureItems) {
		this.creatureItems = creatureItems;
	}

	public ForeignCollection<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(
			ForeignCollection<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public ItemBase() {

	}

	public static Dao<Item, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					Item.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
