package CryptographyTool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import CryptographyTool.CryptographyTool.DisplayLabel;
import CryptographyTool.CryptographyTool.FrequencyCellRenderer;

public class SubstitutionCipherPanel extends JPanel {
	public static char[] decryptedArr;
	public static String decryptedString;
	public static String selectedLetter, selectedReplacement;
	public static ArrayList<String> remainingLetters;
	public static String remainingLettersString;
	public static char[] savedArr;
	public static String[] twoLetterWordsArr = { "an", "am", "as", "at", "be", "by", "do", "go", "he", "if", "in", "it",
			"me", "no", "of", "on", "or", "so", "to", "us", "we" };
	public static String[] threeLetterWordsArr = { "and", "any", "are", "boy", "but", "can", "did", "for", "get", "had",
			"has", "her", "him", "his", "how", "not", "our", "say", "she", "the", "too", "was", "who", "you" };

	public SubstitutionCipherPanel() {
		reset();

		JLabel padding = new JLabel(" ");
		Border border = BorderFactory.createLineBorder(Color.BLUE, 5);

		// left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder( 3, -30, 3, 3 ));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JLabel chartTitle = new JLabel("Letter Frequency Chart");
		chartTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));
		URL imageurl = getClass().getResource("/images/letterFrequencyChart.png");
		//Image chart = Toolkit.getDefaultToolkit().getImage(imageurl);
		//JLabel chartLabel = new JLabel(new ImageIcon(chart));

		JLabel wordsTitle = new JLabel("Common Words:");
		wordsTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));

		JPanel commonWordsPanel = new JPanel();
		String twoLetterString = "";
		for (int i = 0; i < twoLetterWordsArr.length; i++) {
			twoLetterString += twoLetterWordsArr[i] + "<br>";
		}
		for (int i = 0; i < threeLetterWordsArr.length - twoLetterWordsArr.length; i++) {
			twoLetterString += "<br>";
		}
		twoLetterString += "</html>";
		JLabel twoLetterWords = new JLabel("<html>Two letters: <br>" + twoLetterString);

		String threeLetterString = "";
		for (int i = 0; i < threeLetterWordsArr.length; i++) {
			threeLetterString += threeLetterWordsArr[i] + "<br>";
		}
		twoLetterString += "</html>";
		JLabel threeLetterWords = new JLabel("<html>Three letters: <br>" + threeLetterString);
		

		commonWordsPanel.add(twoLetterWords);
		twoLetterWords.setBorder(new EmptyBorder( 3, 3, 3, 30 ));
		commonWordsPanel.add(threeLetterWords);

		leftPanel.add(chartTitle);
		//leftPanel.add(chartLabel);
		leftPanel.add(wordsTitle);
		leftPanel.add(commonWordsPanel);

		// right panel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JLabel remainingLettersTitle = new JLabel("Remaining Letters:");
		remainingLettersTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));

		JLabel remainingLettersText = new JLabel(remainingLettersString);
		remainingLettersText.setFont(new Font("TimesNewRoman", Font.PLAIN, 15));

		JLabel tableTitle = new JLabel("Frequency Count:");
		tableTitle.setFont(new Font("TimesNewRoman", Font.BOLD, 15));

		JPanel helperPanel = new JPanel();
		String[] columnNames = { "Letter", "Frequency" };
		Object[][] data = new Object[26][2];
		for (int i = 0; i < 26; i++) {
			data[i] = new Object[] { (char) (i + 'A'), CryptographyTool.frequencyCount[i] };
		}
		JTable frequencyTable = new JTable(data, columnNames);
		frequencyTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		frequencyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		frequencyTable.setModel(tableModel);

		TableColumn column = null;
		for (int i = 0; i < 2; i++) {
			column = frequencyTable.getColumnModel().getColumn(i);
			// set width of cells
			column.setMaxWidth(30);
			// set color of cells with frequency 0 to red, else set to green
			frequencyTable.setDefaultRenderer(frequencyTable.getColumnClass(i), new FrequencyCellRenderer());
		}

		helperPanel.add(frequencyTable);

		infoPanel.add(remainingLettersTitle);
		infoPanel.add(remainingLettersText);
		infoPanel.add(padding);
		infoPanel.add(tableTitle);
		infoPanel.add(helperPanel);

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
		String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		JComboBox<String> letterBox = new JComboBox<String>(letter);
		letterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedLetter = String.valueOf(letterBox.getSelectedItem());
			}
		});

		JLabel instruction = new JLabel(" replace with ");

		String[] replacement = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
				"r", "s", "t", "u", "v", "w", "x", "y", "z", "_" };
		JComboBox<String> replacementBox = new JComboBox<String>(replacement);
		replacementBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedReplacement = String.valueOf(replacementBox.getSelectedItem());
			}
		});

		JButton convertBtn = new JButton("Convert");
		convertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!remainingLetters.contains(selectedReplacement)) {
					for (int i = 0; i < decryptedArr.length; i++) {
						if (decryptedArr[i] == selectedReplacement.charAt(0)) {
							decryptedArr[i] = '_';
						}
					}
				}

				for (int i = 0; i < CryptographyTool.encryptedArr.length; i++) {
					if (CryptographyTool.encryptedArr[i] == selectedLetter.charAt(0)) {
						if (decryptedArr[i] != '_' && !remainingLetters.contains(Character.toString(decryptedArr[i]))) {
							remainingLetters.add(Character.toString(decryptedArr[i]));
						}
						remainingLetters.remove(selectedReplacement);
						Collections.sort(remainingLetters, String.CASE_INSENSITIVE_ORDER);
						decryptedArr[i] = selectedReplacement.charAt(0);
					}
				}

				// update
				remainingLettersString = "";
				for (int i = 0; i < remainingLetters.size(); i++) {
					remainingLettersString += remainingLetters.get(i) + " ";
				}
				remainingLettersText.setText(remainingLettersString);

				decryptedString = "";
				for (int i = 0; i < decryptedArr.length; i++) {
					decryptedString += decryptedArr[i];
				}
				String[][] dividedText = CryptographyTool.dividedText(decryptedString);
				decryptedText1.setText(dividedText[0][1]);
				decryptedText2.setText(dividedText[1][1]);
				decryptedText3.setText(dividedText[2][1]);
				decryptedText4.setText(dividedText[3][1]);
			}
		});
		panel1.add(letterBox);
		panel1.add(instruction);
		panel1.add(replacementBox);
		panel1.add(convertBtn);

		JPanel panel2 = new JPanel();
		JButton saveBtn = new JButton("Save current state");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < decryptedArr.length; i++) {
					savedArr[i] = decryptedArr[i];
				}
			}
		});
		JButton loadBtn = new JButton("Load last save");
		loadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < savedArr.length; i++) {
					decryptedArr[i] = savedArr[i];
				}
				decryptedString = "";
				for (int i = 0; i < decryptedArr.length; i++) {
					decryptedString += decryptedArr[i];
				}
				String[][] dividedText = CryptographyTool.dividedText(decryptedString);
				decryptedText1.setText(dividedText[0][1]);
				decryptedText2.setText(dividedText[1][1]);
				decryptedText3.setText(dividedText[2][1]);
				decryptedText4.setText(dividedText[3][1]);

				remainingLetters = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
						"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
				for (int i = 0; i < decryptedArr.length; i++) {
					if (remainingLetters.contains(Character.toString(decryptedArr[i]))) {
						remainingLetters.remove(Character.toString(decryptedArr[i]));
					}
				}

				remainingLettersString = "";
				for (int i = 0; i < remainingLetters.size(); i++) {
					remainingLettersString += remainingLetters.get(i) + " ";
				}
				remainingLettersText.setText(remainingLettersString);
			}
		});
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

				remainingLettersText.setText(remainingLettersString);

				for (int i = 0; i < 26; i++) {
					frequencyTable.setValueAt((char) (i + 'A'), i, 0);
					frequencyTable.setValueAt(CryptographyTool.frequencyCount[i], i, 1);
				}
			}
		});
		panel2.add(saveBtn);
		panel2.add(loadBtn);
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

		this.add(leftPanel, BorderLayout.WEST);
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
			if (Character.isLetter(current)) {
				CryptographyTool.frequencyCount[(current - 'A')]++; // update frequency count
				decryptedArr[i] = '_';
			} else {
				decryptedArr[i] = current; // fill in symbols and numbers in decrypted array
			}
			CryptographyTool.encryptedArr[i] = current;
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
		selectedLetter = "A";
		selectedReplacement = "a";
		remainingLetters = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
		remainingLettersString = "";
		for (int i = 0; i < remainingLetters.size(); i++) {
			remainingLettersString += remainingLetters.get(i) + " ";
		}
		CryptographyTool.frequencyCount = new int[26];

		convertInput();

		savedArr = new char[CryptographyTool.input.length()];
		// convert input to array
		for (int i = 0; i < CryptographyTool.input.length(); i++) {
			char current = CryptographyTool.input.charAt(i);
			if (Character.isLetter(current)) {
				savedArr[i] = '_';
			} else {
				savedArr[i] = '_';
			}
		}
	}
}
