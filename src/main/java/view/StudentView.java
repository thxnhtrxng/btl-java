package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dao.ModuleDao;
import model.Account;
import view.module.ModuleTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class StudentView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ModuleTableModel mtm;
	private ModuleDao moduleDao;

	private void reloadTable() {
		reloadTable(moduleDao.findAll());
	}

	private void reloadTable(List<model.Module> modules) {
		mtm = new ModuleTableModel(modules);
		table.setModel(mtm);
		table.repaint();
	}

	/**
	 * Create the panel.
	 */
	public StudentView(Main main) {
		setBackground(new Color(128, 128, 128));
		moduleDao = new ModuleDao(main.getConnection());
		mtm = new ModuleTableModel(moduleDao.findAll());

		JLabel lblName = new JLabel("Hi, " + main.getAccount().getName());
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setFont(new Font("Dialog", Font.BOLD, 20));

		JScrollPane scrollPane = new JScrollPane();

		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.Module m = mtm.getRow(table.getSelectedRow());
					if (m.getQuantity() == m.getRegistered()) {
						JOptionPane.showMessageDialog(new JFrame(), "Học phần đã đầy");
						return;
					}

					if (moduleDao.register(main.getAccount().getId(), m.getId())) {
						m.setRegistered(m.getRegistered() + 1);
						moduleDao.update(m);
						JOptionPane.showMessageDialog(new JFrame(), "Đăng ký thành công");
						reloadTable();
						return;
					}
					JOptionPane.showMessageDialog(new JFrame(), "Bạn đã đăng ký học phần này");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một học phần");
				}

			}
		});

		JButton btnNewButton = new JButton("Registered");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reloadTable(moduleDao.findRegisteredModule(main.getAccount().getId()));
			}
		});

		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.Module m = mtm.getRow(table.getSelectedRow());
					Account acc = main.getAccount();
					if (!moduleDao.checkRegistered(acc.getId(), m.getId())) {
						JOptionPane.showMessageDialog(new JFrame(), "Bạn chưa đăng ký học phần này");
						return;
					}
					if (moduleDao.cancalRegistration(main.getAccount().getId(), m.getId())) {
						m.setRegistered(m.getRegistered() - 1);
						moduleDao.update(m);
						JOptionPane.showMessageDialog(new JFrame(), "Hủy học phần thành công");
						reloadTable();
						return;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một học phần");
				}
			}
		});

		JButton btnNewButton_2 = new JButton("All");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reloadTable();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(91)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(113)
									.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(147)
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addGap(54)
									.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addGap(96))))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 815, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(91, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(56, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(92, Short.MAX_VALUE))
		);

		table = new JTable(mtm);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
