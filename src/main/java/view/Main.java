package view;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DBConnection;
import lombok.Getter;
import lombok.Setter;
import model.Account;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection conn;
	@Getter
	@Setter
	private Account account;
	
	public void changePanel(JPanel panel) {
		getContentPane().removeAll();
		getContentPane().add(panel);
		repaint();
		revalidate();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main main = new Main();
					main.changePanel(new Login(main));
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Connection getConnection() {
		return this.conn;
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		conn = DBConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 882, 588);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
