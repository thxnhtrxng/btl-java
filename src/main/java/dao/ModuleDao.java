package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Module;

public class ModuleDao {
	private Connection conn;

	public ModuleDao(Connection conn) {
		this.conn = conn;
	}

	public boolean create(Module module) {
		String sql = "insert into module(name, credit, quantity, registered, description) values(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, module.getName());
			ps.setInt(2, module.getCredit());
			ps.setInt(3, module.getQuantity());
			ps.setInt(4, module.getRegistered());
			ps.setString(5, module.getDescription());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Module> findAll() {
		List<Module> modules = new ArrayList<>();
		String sql = "select * from module";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				modules.add(new Module(rs.getLong("id"), rs.getString("name"), rs.getInt("credit"),
						rs.getInt("quantity"), rs.getInt("registered"), rs.getString("description")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules;
	}

	public List<Module> findByName(String name) {
		List<Module> modules = new ArrayList<>();
		String sql = "select * from module where name like ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				modules.add(new Module(rs.getLong("id"), rs.getString("name"), rs.getInt("credit"),
						rs.getInt("quantity"), rs.getInt("registered"), rs.getString("description")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules;
	}
	
	public Module findOne(Long moduleId) {
		String sql = "select * from module where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, moduleId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Module(rs.getLong("id"), rs.getString("name"), rs.getInt("credit"),
						rs.getInt("quantity"), rs.getInt("registered"), rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean checkRegistered(Long accountId, Long moduleId) {
		String sql = "select * from account_module where account_id=? and module_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountId);
			ps.setLong(2, moduleId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(Long accountId, Long moduleId) {
		String sql = "insert into account_module(account_id, module_id) values(?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountId);
			ps.setLong(2, moduleId);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cancalRegistration(Long accountId, Long moduleId) {
		String sql = "delete from account_module where account_id=? and module_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountId);
			ps.setLong(2, moduleId);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<Module> findRegisteredModule(Long accountId) {
		List<Module> modules = new ArrayList<>();
		String sql = "select * from account_module where account_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, accountId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				modules.add(findOne(rs.getLong("module_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules;
	}

	public boolean update(Module module) {
		String sql = "update module set name =?, credit=?, quantity=?, registered=?, description=? where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, module.getName());
			ps.setInt(2, module.getCredit());
			ps.setInt(3, module.getQuantity());
			ps.setInt(4, module.getRegistered());
			ps.setString(5, module.getDescription());
			ps.setLong(6, module.getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Long id) {
		String sql = "delete from module where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
