package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.AccountDao;
import model.Account;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.SystemColor;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private AccountDao accountDao;
	private JButton btnLogin;

	/**
	 * Create the panel.
	 */
	public Login(Main main) {
		setBackground(SystemColor.activeCaption);
		accountDao = new AccountDao(main.getConnection());
		
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Đăng Nhập");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		
		JLabel lblNewLabel_1 = new JLabel("Username :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblNewLabel_2 = new JLabel("Password :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		pfPassword = new JPasswordField();
		pfPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		
		btnLogin = new JButton("Login");
		btnLogin.setBackground(SystemColor.info);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tfUsername.getText();
				if (username.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter username");
					return;
				}
				String password = String.valueOf(pfPassword.getPassword());
				if (password.equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter password");
					return;
				}

				Account account = accountDao.login(username, password);
				if (account != null) {
					JOptionPane.showMessageDialog(new JFrame(), "Login success!");
					main.setAccount(account);
					if (account.getIsTeacher()) {
						main.changePanel(new TeacherView(main));
					} else {
						main.changePanel(new StudentView(main));
					}
					return;
				}
				JOptionPane.showMessageDialog(new JFrame(), "Login failed!");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(262)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2))
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tfUsername, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
						.addComponent(pfPassword, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
					.addGap(225))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(340)
					.addComponent(btnLogin)
					.addContainerGap(339, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(310, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addGap(216))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(71)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addComponent(btnLogin)
					.addContainerGap(194, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}
}
