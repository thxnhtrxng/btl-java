package view.account;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Account;

@RequiredArgsConstructor
public class AccountTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	@NonNull
	private final List<Account> accounts;
	private final String[] columnNames = new String[] { "ID", "Name", "Username" };
	@SuppressWarnings("rawtypes")
	private final Class[] columnClass = new Class[] { Long.class, String.class, String.class };

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return accounts.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Account acc = accounts.get(arg0);
		switch (arg1) {
		case 0:
			return acc.getId();
		case 1:
			return acc.getName();
		case 2:
			return acc.getUsername();
		default:
			break;
		}
		return null;
	}

	public Account getRow(int row) {
		return accounts.get(row);
	}
}
