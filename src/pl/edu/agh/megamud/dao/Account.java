package pl.edu.agh.megamud.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import pl.edu.agh.megamud.dao.base.AccountBase;

public class Account extends AccountBase {
	public static boolean isRegistered(String login){
		return getByLogin(login) != null;
	}
	
	public static Account getByLogin(String login){
		Dao<Account, String> dao = createDao();
		PreparedQuery<Account> preparedQuery;
		try {
			preparedQuery = dao.queryBuilder()
					.where().eq("login", login)
					.prepare();
			List<Account> accounts = dao.query(preparedQuery);
			if (accounts.size() == 1){
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Account getByLoginAndPassword(String login, String password){
		try {
			Dao<Account, String> accountDao = createDao();
			PreparedQuery<Account> preparedQuery;
			preparedQuery = accountDao.queryBuilder()
					.where().eq("login", login)
					.and().eq("password", password)
					.prepare();
			List<Account> accounts = accountDao.query(preparedQuery);
			if (accounts.size() == 1){
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void registerNewAccount(String login, String password) throws SQLException{
		Account account = new Account();
		account.setLogin(login);
		account.setPassword(password);

		createDao().create(account);	
	}
}