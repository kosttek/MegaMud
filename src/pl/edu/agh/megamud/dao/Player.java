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
package pl.edu.agh.megamud.dao;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import pl.edu.agh.megamud.dao.base.PlayerBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player")
public class Player extends PlayerBase {
	public static boolean isRegistered(String login) {
		return getByLogin(login) != null;
	}

	public static Player getByLogin(String login) {
		Dao<Player, String> dao = createDao();
		PreparedQuery<Player> preparedQuery;
		try {
			preparedQuery = dao.queryBuilder().where().eq("login", login)
					.prepare();
			List<Player> accounts = dao.query(preparedQuery);
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

	public static Player getByLoginAndPassword(String login, String password) {
		try {
			Dao<Player, String> accountDao = createDao();
			PreparedQuery<Player> preparedQuery;
			preparedQuery = accountDao.queryBuilder().where()
					.eq("login", login).and()
					.eq("password_md5", hashPassword(password)).prepare();
			List<Player> accounts = accountDao.query(preparedQuery);
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

	public void setPassword(String passwordPlainText) {
		setPasswordMd5(hashPassword(passwordPlainText));
	}

	public static String hashPassword(String passwordPlainText) {
		try {
			byte[] bytesOfMessage = passwordPlainText.getBytes("UTF-8");

			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			return Arrays.toString(thedigest);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static Player registerNewAccount(String login, String password)
			throws SQLException {
		Player account = new Player();
		account.setLogin(login);
		account.setPassword(password);

		createDao().create(account);
		return account;
	}
}
