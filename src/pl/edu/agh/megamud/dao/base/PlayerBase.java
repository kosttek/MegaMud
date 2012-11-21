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
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public abstract class PlayerBase {
	@DatabaseField(id = true)
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@DatabaseField(canBeNull = false)
	private String password_md5;

	public String getPasswordMd5() {
		return this.password_md5;
	}

	protected void setPasswordMd5(String passwordMd5) {
		this.password_md5 = passwordMd5;
	}

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer priviledges;

	public Integer getPriviledges() {
		return priviledges;
	}

	public void setPriviledges(Integer priviledges) {
		this.priviledges = priviledges;
	}

	@ForeignCollectionField(eager = true)
	private ForeignCollection<PlayerCreature> playerCreatures;

	public ForeignCollection<PlayerCreature> getPlayerCreatures() {
		try {
			createDao().refresh((Player) this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playerCreatures;
	}

	public void setPlayerCreatures(
			ForeignCollection<PlayerCreature> playerCreatures) {
		this.playerCreatures = playerCreatures;
	}

	public PlayerBase() {
	}

	public static Dao<Player, String> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					Player.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
