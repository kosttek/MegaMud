package pl.edu.agh.megamud.dao;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

import pl.edu.agh.megamud.dao.base.PlayerBase;

@DatabaseTable(tableName = "player")
public class Player extends PlayerBase {
	public static boolean isRegistered(String login){
		return getByLogin(login) != null;
	}
	
	public static Player getByLogin(String login){
		Dao<Player, String> dao = createDao();
		PreparedQuery<Player> preparedQuery;
		try {
			preparedQuery = dao.queryBuilder()
					.where().eq("login", login)
					.prepare();
			List<Player> accounts = dao.query(preparedQuery);
			if (accounts.size() == 1){
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Player getByLoginAndPassword(String login, String password){
		try {
			Dao<Player, String> accountDao = createDao();
			PreparedQuery<Player> preparedQuery;
			preparedQuery = accountDao.queryBuilder()
					.where().eq("login", login)
					.and().eq("password_md5", hashPassword(password))
					.prepare();
			List<Player> accounts = accountDao.query(preparedQuery);
			if (accounts.size() == 1){
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPassword(String passwordPlainText){
		setPasswordMd5(hashPassword(passwordPlainText));
	}
	
	public static String hashPassword(String passwordPlainText){
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
	
	public static Player registerNewAccount(String login, String password) throws SQLException{
		Player account = new Player();
		account.setLogin(login);
		account.setPassword(password);

		createDao().create(account);
		return account;
	}
}