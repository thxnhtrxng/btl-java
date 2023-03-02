package view.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.AccountDao;
import dao.ModuleDao;
import model.Account;
import view.Main;
import view.TeacherView;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Font;

public class AccountView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private AccountTableModel atm;
	private AccountDao accountDao;
	private ModuleDao moduleDao;
	private model.Module module;

	private void reloadTable() {
		reloadTable(accountDao.findRegisteredStudent(module.getId()));
	}

	private void reloadTable(List<Account> accounts) {
		atm = new AccountTableModel(accounts);
		table.setModel(atm);
		table.repaint();
	}

	/**
	 * Create the panel.
	 */
	public AccountView(Main main, model.Module module) {
		this.module = module;
		accountDao = new AccountDao(main.getConnection());
		moduleDao = new ModuleDao(main.getConnection());
		atm = new AccountTableModel(accountDao.findRegisteredStudent(module.getId()));

		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Account acc = atm.getRow(table.getSelectedRow());
					if (moduleDao.cancalRegistration(acc.getId(), module.getId())) {
						module.setRegistered(module.getRegistered()-1);
						moduleDao.update(module);
						JOptionPane.showMessageDialog(new JFrame(), "Xóa thành công");
						reloadTable();
						return;
					}
					JOptionPane.showMessageDialog(new JFrame(), "Xóa thất bại");
				} catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một tài khoản");
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.changePanel(new TeacherView(main));
			}
		});
		
		JTextArea txtrRegisteredStudents = new JTextArea();
		txtrRegisteredStudents.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 25));
		txtrRegisteredStudents.setTabSize(16);
		txtrRegisteredStudents.setText("registered students\r\n");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(399)
							.addComponent(txtrRegisteredStudents, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(176)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 657, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(175, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(202)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(206))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(txtrRegisteredStudents, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(134, Short.MAX_VALUE))
		);

		table = new JTable(atm);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
