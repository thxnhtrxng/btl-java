package view;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dao.ModuleDao;
import model.Module;
import view.account.AccountView;
import view.module.ModuleCreator;
import view.module.ModuleEditor;
import view.module.ModuleTableModel;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class TeacherView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ModuleTableModel mtm;
	private ModuleDao moduleDao;
	private JTextField tfSearch;
	
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
	public TeacherView(Main main) {
		setBackground(new Color(255, 204, 153));
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.moduleDao = new ModuleDao(main.getConnection());
		mtm = new ModuleTableModel(moduleDao.findAll());
		
		JLabel lblName = new JLabel("Hi, " + main.getAccount().getName());
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ModuleCreator.display(moduleDao)) {
					JOptionPane.showMessageDialog(new JFrame(), "Thêm học phần thành công");
					reloadTable();
					return;
				}
				JOptionPane.showMessageDialog(new JFrame(), "Thêm học phần thất bại");
			}
		});
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Module m = mtm.getRow(table.getSelectedRow());
					if (ModuleEditor.display(moduleDao, m)) {
						JOptionPane.showMessageDialog(new JFrame(), "Sửa thành công");
						reloadTable();
						return;
					}
					JOptionPane.showMessageDialog(new JFrame(), "Sửa thất bại");
				} catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một học phần");
				}
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Module m = mtm.getRow(table.getSelectedRow());
					if (moduleDao.delete(m.getId())) {
						JOptionPane.showMessageDialog(new JFrame(), "Xóa thành công");
						reloadTable();
						return;
					}
					JOptionPane.showMessageDialog(new JFrame(), "Xóa thất bại");
				} catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một học phần");
				}
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reloadTable(moduleDao.findByName(tfSearch.getText()));
			}
		});
		
		tfSearch = new JTextField();
		tfSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfSearch.setColumns(10);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reloadTable();
			}
		});
		
		JButton btnNewButton_1 = new JButton("Registered Student");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Module m = mtm.getRow(table.getSelectedRow());
					main.changePanel(new AccountView(main, m));
				} catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), "Vui lòng chọn một học phần");
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(83)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblName)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAdd)
							.addGap(48)
							.addComponent(btnEdit)
							.addGap(52)
							.addComponent(btnDelete)
							.addGap(50)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
							.addComponent(tfSearch, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSearch)))
					.addContainerGap(26, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(58, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 779, GroupLayout.PREFERRED_SIZE)
					.addGap(51))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblName)
							.addGap(32))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton_1)
							.addGap(18)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(btnEdit)
						.addComponent(btnDelete)
						.addComponent(btnSearch)
						.addComponent(tfSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		
		table = new JTable(mtm);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
