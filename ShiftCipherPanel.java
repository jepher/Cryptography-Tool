package CryptographyTool;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import CryptographyTool.CryptographyTool.DisplayLabel;
import CryptographyTool.CryptographyTool.FrequencyCellRenderer;

public class ShiftCipherPanel extends JPanel {
	public static char[] decryptedArr;
	public static String decryptedString;
	public static int shift;
	public static String[] alphabet;
	public static String[] shiftedAlphabet;

	public ShiftCipherPanel() {
		reset();

		JLabel padding = new JLabel(" ");

		// right panel
		JPanel infoPanel = new JPanel();

		JPanel shiftPanel = new JPanel();
		shiftPanel.setLayout(new BoxLayout(shiftPanel, BoxLayout.Y_AXIS));
		JLabel shiftTitle = new JLabel("Shift Table");
		shiftTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));

		Object[] shiftColumnNames = { "Original", "Shifted" };
		Object[][] shiftArr = new Object[26][2];
		for (int i = 0; i < 26; i++) {
			shiftArr[i] = new Object[] { (char) (i + 'A'), (char) (i + 'a') };
		}
		JTable shiftTable = new JTable(shiftArr, shiftColumnNames);
		// shiftTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		shiftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel shiftModel = new DefaultTableModel(shiftArr, shiftColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		shiftTable.setModel(shiftModel);
		TableColumn column = null;
		for (int i = 0; i < 2; i++) {
			column = shiftTable.getColumnModel().getColumn(i);
			column.setMaxWidth(30);
		}
		shiftPanel.add(shiftTitle);
		shiftPanel.add(shiftTable);

		JPanel frequencyPanel = new JPanel();
		frequencyPanel.setLayout(new BoxLayout(frequencyPanel, BoxLayout.Y_AXIS));
		JLabel tableTitle = new JLabel("Frequency Count");
		tableTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));

		String[] columnNames = { "Letter", "Frequency" };
		Object[][] data = new Object[26][2];
		for (int i = 0; i < 26; i++) {
			data[i] = new Object[] { (char) (i + 'A'), CryptographyTool.frequencyCount[i] };
		}
		JTable frequencyTable = new JTable(data, columnNames);
		// frequencyTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		frequencyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		frequencyTable.setModel(tableModel);
		column = null;
		for (int i = 0; i < 2; i++) {
			column = frequencyTable.getColumnModel().getColumn(i);
			// set width of cells
			column.setMaxWidth(30);
			// set color of cells with frequency 0 to red, else set to green
			frequencyTable.setDefaultRenderer(frequencyTable.getColumnClass(i), new FrequencyCellRenderer());
		}
		frequencyPanel.add(tableTitle);
		frequencyPanel.add(frequencyTable);

		infoPanel.add(padding);
		infoPanel.add(shiftPanel);
		infoPanel.add(frequencyPanel);
		// infoPanel.add(helperPanel);

		// center panel
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));

		String[][] dividedText = CryptographyTool.dividedText(decryptedString);

		DisplayLabel encryptedText1 = new DisplayLabel(dividedText[0][0], 1);
		DisplayLabel decryptedText1 = new DisplayLabel(dividedText[0][1], 0);
		DisplayLabel encryptedText2 = new DisplayLabel(dividedText[1][0], 1);
		DisplayLabel decryptedText2 = new DisplayLabel(dividedText[1][1], 0);
		DisplayLabel encryptedText3 = new DisplayLabel(dividedText[2][0], 1);
		DisplayLabel decryptedText3 = new DisplayLabel(dividedText[2][1], 0);
		DisplayLabel encryptedText4 = new DisplayLabel(dividedText[3][0], 1);
		DisplayLabel decryptedText4 = new DisplayLabel(dividedText[3][1], 0);

		// operation panel
		JPanel operationPanel = new JPanel();
		operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));

		JPanel panel1 = new JPanel();
		JLabel instruction = new JLabel("Select shift:");

		String[] options = new String[26];
		for (int i = 0; i < 26; i++) {
			options[i] = Integer.toString(i);
		}
		JComboBox<String> shiftBox = new JComboBox<String>(options);
		shiftBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shift = Integer.parseInt((String) shiftBox.getSelectedItem());
			}
		});

		JButton convertBtn = new JButton("Convert");
		convertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < 26; i++) {
					shiftedAlphabet[i] = Character.toString((char) (shift(shift, 'a' + i)));
					shiftModel.setValueAt(shiftedAlphabet[i], i, 1);
				}

				// update
				decryptedString = "";
				for (int i = 0; i < CryptographyTool.encryptedString.length(); i++) {
					if (Character.isLetter(CryptographyTool.encryptedString.charAt(i)))
						decryptedString += (char) (shift(shift,
								Character.toLowerCase(CryptographyTool.encryptedString.charAt(i))));
					else
						decryptedString += CryptographyTool.encryptedString.charAt(i);
				}
				decryptedString = decryptedString.toLowerCase();
				String[][] dividedText = CryptographyTool.dividedText(decryptedString);
				decryptedText1.setText(dividedText[0][1]);
				decryptedText2.setText(dividedText[1][1]);
				decryptedText3.setText(dividedText[2][1]);
				decryptedText4.setText(dividedText[3][1]);
			}
		});
		panel1.add(instruction);
		panel1.add(shiftBox);
		panel1.add(convertBtn);

		JPanel panel2 = new JPanel();
		JButton resetBtn = new JButton("Start new cipher");
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				CryptographyTool.getInput();
				reset();
				String[][] dividedText = CryptographyTool.dividedText(decryptedString);
				encryptedText1.setText(dividedText[0][0]);
				decryptedText1.setText(dividedText[0][1]);
				encryptedText2.setText(dividedText[1][0]);
				decryptedText2.setText(dividedText[1][1]);
				encryptedText3.setText(dividedText[2][0]);
				decryptedText3.setText(dividedText[2][1]);
				encryptedText4.setText(dividedText[3][0]);
				decryptedText4.setText(dividedText[3][1]);

				for (int i = 0; i < 26; i++) {
					frequencyTable.setValueAt((char) (i + 'A'), i, 0);
					frequencyTable.setValueAt(CryptographyTool.frequencyCount[i], i, 1);
				}
			}
		});
		panel2.add(resetBtn);

		operationPanel.add(panel1);
		operationPanel.add(panel2);

		middlePanel.add(encryptedText1);
		middlePanel.add(decryptedText1);
		middlePanel.add(encryptedText2);
		middlePanel.add(decryptedText2);
		middlePanel.add(encryptedText3);
		middlePanel.add(decryptedText3);
		middlePanel.add(encryptedText4);
		middlePanel.add(decryptedText4);
		middlePanel.add(operationPanel);

		this.add(middlePanel, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.EAST);
	}

	public static void convertInput() {
		CryptographyTool.encryptedArr = new char[CryptographyTool.input.length()];
		decryptedArr = new char[CryptographyTool.input.length()];

		// convert input to array
		CryptographyTool.frequencyCount = new int[26];
		for (int i = 0; i < CryptographyTool.input.length(); i++) {
			char current = CryptographyTool.input.charAt(i);
			if (Character.isLetter(current))
				CryptographyTool.frequencyCount[(current - 'A')]++; // update frequency count

			CryptographyTool.encryptedArr[i] = current;
			decryptedArr[i] = Character.toLowerCase(current);
		}

		// convert array to string
		CryptographyTool.encryptedString = "";
		decryptedString = "";
		for (int i = 0; i < CryptographyTool.encryptedArr.length; i++) {
			CryptographyTool.encryptedString += CryptographyTool.encryptedArr[i];
			decryptedString += decryptedArr[i];
		}
	}

	public void reset() {
		// reset values
		shift = 0;

		alphabet = new String[26];
		for (int i = 0; i < 26; i++)
			alphabet[i] = Character.toString((char) ('a' + i));

		shiftedAlphabet = new String[26];
		for (int i = 0; i < 26; i++)
			shiftedAlphabet[i] = Character.toString((char) ('a' + i));

		CryptographyTool.frequencyCount = new int[26];

		convertInput();
	}

	public int shift(int shift, int start) {
		if (start - shift < 'a') {
			return (int) ('z' + 1 - (shift - start % 'a'));
		} else
			return (int) (start - shift);
	}
}
