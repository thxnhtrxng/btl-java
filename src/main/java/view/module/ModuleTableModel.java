package view.module;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Module;

@RequiredArgsConstructor
public class ModuleTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	@NonNull
	private final List<Module> modules;
	private final String[] columnName = new String[] { "ID", "Tên", "Số tín chỉ", "Số lượng", "Đã đăng ký", "Mô tả" };
	@SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] { Long.class, String.class, Integer.class, Integer.class,
			Integer.class, String.class };

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public int getRowCount() {
		return modules.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Module module = this.modules.get(arg0);
		switch (arg1) {
		case 0:
			return module.getId();
		case 1:
			return module.getName();
		case 2:
			return module.getCredit();
		case 3:
			return module.getQuantity();
		case 4:
			return module.getRegistered();
		case 5:
			return module.getDescription();

		default:
			break;
		}
		return null;
	}

	public Module getRow(int row) {
		return modules.get(row);
	}

	public List<Module> getList() {
		return this.modules;
	}
}
