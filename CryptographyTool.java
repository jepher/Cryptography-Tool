package CryptographyTool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CryptographyTool {
	public static String input;
	public static int[] frequencyCount;
	public static char[] encryptedArr;
	public static String encryptedString;
	public static JFrame guiFrame = new JFrame("Cryptography Tool");

	public static void main(String[] args) {
		new CryptographyTool();
	}

	public static String[][] dividedText(String decryptedString) {
		// 34 characters / row
		// 4 rows
		String[][] dividedText = new String[4][4];
		String eString = encryptedString;
		String dString = decryptedString;

		// divide text only if there are spaces
		if (eString.contains(" ")) {
			for (int i = 0; i < dividedText.length; i++) {
				if (eString.length() > 34) {
					int index = 33;
					while (eString.charAt(index) != ' ' && eString.charAt(index + 1) != ' ')
						index--;
					dividedText[i] = new String[] { eString.substring(0, index + 1), dString.substring(0, index + 1) };
					eString = eString.substring(index + 1);
					dString = dString.substring(index + 1);
				} else {
					dividedText[i] = new String[] { eString, dString };
					eString = "";
					dString = "";
				}
			}
		} else {
			dividedText[0] = new String[] { encryptedString, decryptedString };
		}

		return dividedText;
	}

	public CryptographyTool() {
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Cryptography Tool");
		guiFrame.setSize(1250, 850);
		guiFrame.setResizable(false);
		guiFrame.setLocationRelativeTo(null); // center on screen

		getInput();

		// make tabs
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Shift", null, new ShiftCipherPanel());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Substitution", null, new SubstitutionCipherPanel());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

		guiFrame.add(tabbedPane);
		guiFrame.setVisible(true);
	}

	public static void getInput() {
		CryptographyTool.input = JOptionPane.showInputDialog("Enter cipher:");
		if (CryptographyTool.input == null || CryptographyTool.input.equals(""))
			System.exit(0);

		CryptographyTool.input = CryptographyTool.input.toUpperCase();
	}

	public static class DisplayLabel extends JLabel {
		public DisplayLabel(String text, int fontStyle) { // fontStyle: 0 = plain, 1 = bold
			if (fontStyle == 0)
				setFont(new Font("Courier", Font.PLAIN, 20));
			else
				setFont(new Font("Courier", Font.BOLD, 20));
			setHorizontalAlignment(JLabel.CENTER);
			setAlignmentX(Component.CENTER_ALIGNMENT);
			// setMaximumSize(new Dimension(700, 20));
			setText(text);
		}
	}

	public static class FrequencyCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			if (column == 1 && (int)table.getValueAt(row, column) == 0 || column == 0 && (int)table.getValueAt(row, column + 1) == 0) {
				setBackground(Color.red);
			} else {
				setBackground(Color.green);
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
}
