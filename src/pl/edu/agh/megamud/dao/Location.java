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

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.dao.base.LocationBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "location")
public class Location extends LocationBase {

	public Location connectTwoWay(Location destination, String exitName,
			String comeBackName) throws SQLException {
		Portal outgoing = new Portal(exitName);
		Portal incoming = new Portal(comeBackName);

		outgoing.setEntry(this);
		outgoing.setDestination(destination);

		incoming.setEntry(destination);
		incoming.setDestination(this);

		Dao<Portal, Integer> portalDao = Portal.createDao();
		portalDao.create(outgoing);
		portalDao.create(incoming);

		return this;
	}

	public Location connectOneWay(Location destination, String exitName) throws SQLException {
		Portal outgoing = new Portal(exitName);

		outgoing.setEntry(this);
		outgoing.setDestination(destination);

		Dao<Portal, Integer> portalDao = Portal.createDao();
		portalDao.create(outgoing);

		return this;
	}
	
	public Portal getExitByName(String name) throws SQLException {
		Dao<Portal, Integer> dao = Portal.createDao();
		PreparedQuery<Portal> preparedQuery = dao.queryBuilder().where()
				.eq("name", name).and().eq("entry_id", this.getId()).prepare();
		List<Portal> portals = dao.query(preparedQuery);
		if (portals.size() == 0) {
			return null;
		} else {
			return portals.get(0);
		}
	}

	public Location getDestinationByExitName(String name) throws SQLException {
		Portal portal = this.getExitByName(name);
		if (portal == null) {
			return null;
		}
		return portal.getDestination();
	}

	public Location create() throws SQLException {
		Location.createDao().create(this);
		return this;
	}

	public Location refresh() throws SQLException {
		Location.createDao().refresh(this);
		return this;
	}
}
