package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.DatabaseObjectDescription;

import model.Account;

public class AccountDao {
	private Connection conn;

	public AccountDao(Connection conn) {
		this.conn = conn;
	}

	public Account login(String username, String password) {
		String sql = "select * from account where username=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getString("password").equals(password)) {
				return new Account(rs.getLong("id"), rs.getString("name"), rs.getString("username"),
						rs.getString("password"), rs.getBoolean("is_teacher"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Account findOneById(Long accountId) {
		String sql = "select id, name, username from account where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Account(rs.getLong("id"), rs.getString("name"), rs.getString("username"), "", false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Account> findRegisteredStudent(Long moduleId) {
		List<Account> accounts = new ArrayList<>();
		String sql = "select * from account_module where module_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, moduleId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accounts.add(findOneById(rs.getLong("account_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
}
