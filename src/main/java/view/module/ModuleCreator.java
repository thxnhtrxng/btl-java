package view.module;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ModuleDao;
import model.Module;

public class ModuleCreator {
	private static JTextField tfName, tfCredit, tfQuantity, tfRegistered, tfDescription;
	private static JLabel lbName, lbCredit, lbQuantity, lbRegistered, lbDescription;

	public static boolean display(ModuleDao moduleDao) {
		JPanel panel = new JPanel(new GridLayout(0, 1));

		lbName = new JLabel("Tên học phần");
		panel.add(lbName);
		tfName = new JTextField();
		panel.add(tfName);

		lbCredit = new JLabel("Số tín chỉ");
		tfCredit = new JTextField();
		panel.add(lbCredit);
		panel.add(tfCredit);

		lbQuantity = new JLabel("Số lượng");
		tfQuantity = new JTextField();
		panel.add(lbQuantity);
		panel.add(tfQuantity);

		lbRegistered = new JLabel("Đã đăng ký");
		tfRegistered = new JTextField();
		panel.add(lbRegistered);
		panel.add(tfRegistered);

		lbDescription = new JLabel("Mô tả");
		tfDescription = new JTextField();
		panel.add(lbDescription);
		panel.add(tfDescription);

		int result = JOptionPane.showConfirmDialog(null, panel, "Thêm học phần", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			try {
				String name = tfName.getText(), description = tfDescription.getText();
				Integer credit = Integer.parseInt(tfCredit.getText()),
						quantity = Integer.parseInt(tfQuantity.getText()),
						registered = Integer.parseInt(tfRegistered.getText());

				return moduleDao.create(new Module(name, credit, quantity, registered, description));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
