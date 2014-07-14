/**
 * 
 */
package gui.dialogs;

import gui.EdGraf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * Okno dialogowe o programie...
 * @author Wojciech Pierzchalski
 *
 */
public class AboutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AboutDialog(){
		setTitle("O programie");
		this.setModal(true);
		this.setResizable(false);
		
		JLabel obraz = new JLabel("");
		obraz.setIcon(new ImageIcon(AboutDialog.class.getResource("/gui/res/about.png")));
		getContentPane().add(obraz, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(255, 255, 240));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{104, 79, 0, 0};
		gbl_panel.rowHeights = new int[]{43, 17, 67, 0, 0, 0, 79, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblEdgrafV = new JLabel("EdGraf");
		lblEdgrafV.setPreferredSize(new Dimension(133, 20));
		lblEdgrafV.setMinimumSize(new Dimension(133, 14));
		lblEdgrafV.setMaximumSize(new Dimension(133, 14));
	
		lblEdgrafV.setHorizontalAlignment(SwingConstants.LEFT);
		lblEdgrafV.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		GridBagConstraints gbc_lblEdgrafV = new GridBagConstraints();
		gbc_lblEdgrafV.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEdgrafV.gridwidth = 3;
		gbc_lblEdgrafV.anchor = GridBagConstraints.SOUTH;
		gbc_lblEdgrafV.insets = new Insets(10, 9, 5, 0);
		gbc_lblEdgrafV.gridx = 0;
		gbc_lblEdgrafV.gridy = 0;
		panel.add(lblEdgrafV, gbc_lblEdgrafV);
		
		JLabel lblEdytorGrafikwDyurw = new JLabel("Edytor grafik\u00F3w dy\u017Cur\u00F3w domowych");
		lblEdytorGrafikwDyurw.setHorizontalAlignment(SwingConstants.LEFT);
		lblEdytorGrafikwDyurw.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblEdytorGrafikwDyurw = new GridBagConstraints();
		gbc_lblEdytorGrafikwDyurw.gridwidth = 3;
		gbc_lblEdytorGrafikwDyurw.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEdytorGrafikwDyurw.insets = new Insets(0, 10, 5, 10);
		gbc_lblEdytorGrafikwDyurw.gridx = 0;
		gbc_lblEdytorGrafikwDyurw.gridy = 1;
		panel.add(lblEdytorGrafikwDyurw, gbc_lblEdytorGrafikwDyurw);
		
		JLabel lblNewLabel = new JLabel("Wersja");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 20, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblws = new JLabel(EdGraf.VERSION);
		GridBagConstraints gbc_lblws = new GridBagConstraints();
		gbc_lblws.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblws.insets = new Insets(0, 0, 5, 5);
		gbc_lblws.gridx = 1;
		gbc_lblws.gridy = 2;
		panel.add(lblws, gbc_lblws);
		
		JLabel lblAutor = new JLabel("Autor");
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.anchor = GridBagConstraints.WEST;
		gbc_lblAutor.insets = new Insets(0, 20, 5, 5);
		gbc_lblAutor.gridx = 0;
		gbc_lblAutor.gridy = 3;
		panel.add(lblAutor, gbc_lblAutor);
		
		JLabel lblWojciechPierzchalski = new JLabel("Wojciech Pierzchalski");
		GridBagConstraints gbc_lblWojciechPierzchalski = new GridBagConstraints();
		gbc_lblWojciechPierzchalski.insets = new Insets(0, 0, 5, 5);
		gbc_lblWojciechPierzchalski.gridx = 1;
		gbc_lblWojciechPierzchalski.gridy = 3;
		panel.add(lblWojciechPierzchalski, gbc_lblWojciechPierzchalski);
		
		JLabel lblKontakt = new JLabel("Kontakt");
		GridBagConstraints gbc_lblKontakt = new GridBagConstraints();
		gbc_lblKontakt.anchor = GridBagConstraints.WEST;
		gbc_lblKontakt.insets = new Insets(0, 20, 5, 5);
		gbc_lblKontakt.gridx = 0;
		gbc_lblKontakt.gridy = 4;
		panel.add(lblKontakt, gbc_lblKontakt);
		
		JLabel lblwojciechpierzchalskigmailcom = new JLabel("<HTML><A HREF=\"mailto:wojciech.pierzchalski@gmail.com\">wojciech.pierzchalski@gmail.com</HTML");
		
		GridBagConstraints gbc_lblwojciechpierzchalskigmailcom = new GridBagConstraints();
		gbc_lblwojciechpierzchalskigmailcom.anchor = GridBagConstraints.WEST;
		gbc_lblwojciechpierzchalskigmailcom.gridwidth = 2;
		gbc_lblwojciechpierzchalskigmailcom.insets = new Insets(0, 0, 5, 0);
		gbc_lblwojciechpierzchalskigmailcom.gridx = 1;
		gbc_lblwojciechpierzchalskigmailcom.gridy = 4;
		panel.add(lblwojciechpierzchalskigmailcom, gbc_lblwojciechpierzchalskigmailcom);
		
		
		JLabel lblLicencja = new JLabel("Licencja");
		GridBagConstraints gbc_lblLicencja = new GridBagConstraints();
		gbc_lblLicencja.anchor = GridBagConstraints.WEST;
		gbc_lblLicencja.insets = new Insets(0, 20, 5, 5);
		gbc_lblLicencja.gridx = 0;
		gbc_lblLicencja.gridy = 5;
		panel.add(lblLicencja, gbc_lblLicencja);
		
		JLabel lblGpl = new JLabel("GNU GPL v3");
		GridBagConstraints gbc_lblGpl = new GridBagConstraints();
		gbc_lblGpl.anchor = GridBagConstraints.WEST;
		gbc_lblGpl.insets = new Insets(0, 0, 5, 5);
		gbc_lblGpl.gridx = 1;
		gbc_lblGpl.gridy = 5;
		panel.add(lblGpl, gbc_lblGpl);
		
		JLabel lblNewLabel_1 = new JLabel("<HTML>\r\nWykonywalny plik Windows zosta\u0142 stworzony\r\n za pomoc\u0105 programu Launch4J<Br>\r\n<A href=\"\">http://launch4j.sourceforge.net/</a>\r\n</html>");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 10, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 6;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		this.setVisible(false);
		this.pack();
	}

}
