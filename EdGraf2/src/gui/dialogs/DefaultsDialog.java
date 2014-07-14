package gui.dialogs;


import grafik.Grafik;
import grafik.Grafik.CzasDyzuru;
import gui.EdGraf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * Okno dialogowe ustawieñ domyœlnych
 * @author Wojciech Pierzchalski
 *
 */
public class DefaultsDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista domyœlnych dy¿urnych
	 */
	private JList lstDyzurni;
	/**
	 * Lista domyœlnych okresów dy¿urów
	 */
	private JList lstOkresy;
	/**
	 * Komponen - rodzic okna dialogowego
	 */
	private EdGraf parent;
	/**
	 * Pole wyboru iloœci godzin w okresie dy¿uru
	 */
	private JSpinner spIlg;
	/**
	 * Pole wyboru miesiêcznegop offsetu grafiku
	 */
	private JSpinner spOffset;
	/**
	 * Pole nowego dy¿urnego
	 */
	private JTextField tfDyzurny;
	/**
	 * Pole firmy
	 */
	private JTextField tfFirma;
	/**
	 * Pole jednostki organizacyjnej
	 */
	private JTextField tfGrupa;
	/**
	 * Pole klauzuli
	 */
	private JTextField tfKlauzula;
	/**
	 * Pole nowego przedzia³u czasu
	 */
	private JTextField tfPrzedzial;
	/**
	 * Pole tytu³u grafiku
	 */
	private JTextField tfTytul;
	
	public DefaultsDialog() {
		this.setVisible(false);
		this.setModal(true);
		this.setResizable(false);
		
		setTitle("Warto\u015Bci domy\u015Blne");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{143, 0, 0, 151, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 35, 0, 0, 0, 28, 0, 60, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTytu = new JLabel("Tytu\u0142 grafiku");
		GridBagConstraints gbc_lblTytu = new GridBagConstraints();
		gbc_lblTytu.gridwidth = 4;
		gbc_lblTytu.anchor = GridBagConstraints.WEST;
		gbc_lblTytu.insets = new Insets(5, 5, 5, 5);
		gbc_lblTytu.gridx = 0;
		gbc_lblTytu.gridy = 1;
		getContentPane().add(lblTytu, gbc_lblTytu);
		
		tfTytul = new JTextField();
		GridBagConstraints gbc_tfTytul = new GridBagConstraints();
		gbc_tfTytul.gridwidth = 7;
		gbc_tfTytul.insets = new Insets(0, 5, 5, 5);
		gbc_tfTytul.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTytul.gridx = 0;
		gbc_tfTytul.gridy = 2;
		getContentPane().add(tfTytul, gbc_tfTytul);
		tfTytul.setColumns(10);
		
		JLabel lblNazwaFirmy = new JLabel("Nazwa firmy");
		GridBagConstraints gbc_lblNazwaFirmy = new GridBagConstraints();
		gbc_lblNazwaFirmy.gridwidth = 4;
		gbc_lblNazwaFirmy.anchor = GridBagConstraints.WEST;
		gbc_lblNazwaFirmy.insets = new Insets(0, 5, 5, 5);
		gbc_lblNazwaFirmy.gridx = 0;
		gbc_lblNazwaFirmy.gridy = 3;
		getContentPane().add(lblNazwaFirmy, gbc_lblNazwaFirmy);
		
		tfFirma = new JTextField();
		GridBagConstraints gbc_tfFirma = new GridBagConstraints();
		gbc_tfFirma.gridwidth = 7;
		gbc_tfFirma.insets = new Insets(0, 5, 5, 5);
		gbc_tfFirma.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfFirma.gridx = 0;
		gbc_tfFirma.gridy = 4;
		getContentPane().add(tfFirma, gbc_tfFirma);
		tfFirma.setColumns(10);
		
		JLabel lblJednostkaOrg = new JLabel("Jednostka organizacyjna");
		GridBagConstraints gbc_lblJednostkaOrg = new GridBagConstraints();
		gbc_lblJednostkaOrg.gridwidth = 4;
		gbc_lblJednostkaOrg.anchor = GridBagConstraints.WEST;
		gbc_lblJednostkaOrg.insets = new Insets(0, 5, 5, 5);
		gbc_lblJednostkaOrg.gridx = 0;
		gbc_lblJednostkaOrg.gridy = 5;
		getContentPane().add(lblJednostkaOrg, gbc_lblJednostkaOrg);
		
		tfGrupa = new JTextField();
		GridBagConstraints gbc_tfGrupa = new GridBagConstraints();
		gbc_tfGrupa.gridwidth = 7;
		gbc_tfGrupa.insets = new Insets(0, 5, 5, 5);
		gbc_tfGrupa.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGrupa.gridx = 0;
		gbc_tfGrupa.gridy = 6;
		getContentPane().add(tfGrupa, gbc_tfGrupa);
		tfGrupa.setColumns(10);
		
		JLabel lblKlauzulaBezpieczestwa = new JLabel("Klauzula bezpiecze\u0144stwa");
		GridBagConstraints gbc_lblKlauzulaBezpieczestwa = new GridBagConstraints();
		gbc_lblKlauzulaBezpieczestwa.gridwidth = 2;
		gbc_lblKlauzulaBezpieczestwa.anchor = GridBagConstraints.WEST;
		gbc_lblKlauzulaBezpieczestwa.insets = new Insets(0, 5, 5, 5);
		gbc_lblKlauzulaBezpieczestwa.gridx = 0;
		gbc_lblKlauzulaBezpieczestwa.gridy = 7;
		getContentPane().add(lblKlauzulaBezpieczestwa, gbc_lblKlauzulaBezpieczestwa);
		
		tfKlauzula = new JTextField();
		GridBagConstraints gbc_tfKlauzula = new GridBagConstraints();
		gbc_tfKlauzula.gridwidth = 7;
		gbc_tfKlauzula.insets = new Insets(0, 5, 5, 5);
		gbc_tfKlauzula.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfKlauzula.gridx = 0;
		gbc_tfKlauzula.gridy = 8;
		getContentPane().add(tfKlauzula, gbc_tfKlauzula);
		tfKlauzula.setColumns(10);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 9;
		getContentPane().add(label, gbc_label);
		
		JLabel lblOffsetMiesicznyDla = new JLabel("Offset miesi\u0119czny grafiku");
		GridBagConstraints gbc_lblOffsetMiesicznyDla = new GridBagConstraints();
		gbc_lblOffsetMiesicznyDla.anchor = GridBagConstraints.WEST;
		gbc_lblOffsetMiesicznyDla.insets = new Insets(0, 5, 5, 5);
		gbc_lblOffsetMiesicznyDla.gridx = 0;
		gbc_lblOffsetMiesicznyDla.gridy = 10;
		getContentPane().add(lblOffsetMiesicznyDla, gbc_lblOffsetMiesicznyDla);
		
		spOffset = new JSpinner();
		spOffset.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spOffset.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_spOffset = new GridBagConstraints();
		gbc_spOffset.anchor = GridBagConstraints.WEST;
		gbc_spOffset.gridwidth = 2;
		gbc_spOffset.insets = new Insets(0, 0, 5, 5);
		gbc_spOffset.gridx = 1;
		gbc_spOffset.gridy = 10;
		getContentPane().add(spOffset, gbc_spOffset);
		
		JLabel lblDomylniDyzurni = new JLabel("Domy\u015Blni dyzurni");
		GridBagConstraints gbc_lblDomylniDyzurni = new GridBagConstraints();
		gbc_lblDomylniDyzurni.anchor = GridBagConstraints.SOUTH;
		gbc_lblDomylniDyzurni.insets = new Insets(0, 5, 5, 5);
		gbc_lblDomylniDyzurni.gridx = 0;
		gbc_lblDomylniDyzurni.gridy = 11;
		getContentPane().add(lblDomylniDyzurni, gbc_lblDomylniDyzurni);
		
		JLabel lblDomylneOkresyDyurw = new JLabel("Domy\u015Blne okresy dy\u017Cur\u00F3w");
		GridBagConstraints gbc_lblDomylneOkresyDyurw = new GridBagConstraints();
		gbc_lblDomylneOkresyDyurw.anchor = GridBagConstraints.SOUTH;
		gbc_lblDomylneOkresyDyurw.insets = new Insets(0, 0, 5, 5);
		gbc_lblDomylneOkresyDyurw.gridx = 3;
		gbc_lblDomylneOkresyDyurw.gridy = 11;
		getContentPane().add(lblDomylneOkresyDyurw, gbc_lblDomylneOkresyDyurw);
		
		JScrollPane spLstDyzurni = new JScrollPane();
		spLstDyzurni.setPreferredSize(new Dimension(2, 100));
		GridBagConstraints gbc_spLstDyzurni = new GridBagConstraints();
		gbc_spLstDyzurni.fill = GridBagConstraints.BOTH;
		gbc_spLstDyzurni.gridheight = 5;
		gbc_spLstDyzurni.insets = new Insets(0, 5, 5, 5);
		gbc_spLstDyzurni.gridx = 0;
		gbc_spLstDyzurni.gridy = 12;
		getContentPane().add(spLstDyzurni, gbc_spLstDyzurni);
		
		lstDyzurni = new JList();
		spLstDyzurni.setViewportView(lstDyzurni);
		lstDyzurni.setModel(new DefaultListModel());
		
		JButton btnDyzurnyUp = new JButton("");
		btnDyzurnyUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstDyzurni.getSelectedIndex();
				if(idx>0)
				{
					Object o=DefaultsDialog.this.getLstDyzurniModel().get(idx-1);
					DefaultsDialog.this.getLstDyzurniModel().set(idx-1, DefaultsDialog.this.getLstDyzurniModel().get(idx));
					DefaultsDialog.this.getLstDyzurniModel().set(idx,o);
					DefaultsDialog.this.lstDyzurni.setSelectedIndex(idx-1);
				}
			}
		});
		btnDyzurnyUp.setPreferredSize(new Dimension(20, 20));
		btnDyzurnyUp.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		GridBagConstraints gbc_btnDyzurnyUp = new GridBagConstraints();
		gbc_btnDyzurnyUp.anchor = GridBagConstraints.WEST;
		gbc_btnDyzurnyUp.gridwidth = 2;
		gbc_btnDyzurnyUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnDyzurnyUp.gridx = 1;
		gbc_btnDyzurnyUp.gridy = 12;
		getContentPane().add(btnDyzurnyUp, gbc_btnDyzurnyUp);
		
		JButton btnOkresUp = new JButton("");
		btnOkresUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstOkresy.getSelectedIndex();
				if(idx>0)
				{
					Object o=DefaultsDialog.this.getLstOkresyModel().get(idx-1);
					DefaultsDialog.this.getLstOkresyModel().set(idx-1, DefaultsDialog.this.getLstOkresyModel().get(idx));
					DefaultsDialog.this.getLstOkresyModel().set(idx,o);
					DefaultsDialog.this.lstOkresy.setSelectedIndex(idx-1);
				}
			}
		});
		btnOkresUp.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnOkresUp.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnOkresUp = new GridBagConstraints();
		gbc_btnOkresUp.anchor = GridBagConstraints.WEST;
		gbc_btnOkresUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnOkresUp.gridx = 4;
		gbc_btnOkresUp.gridy = 12;
		getContentPane().add(btnOkresUp, gbc_btnOkresUp);
		
		JButton btnDyzurnyDown = new JButton("");
		btnDyzurnyDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstDyzurni.getSelectedIndex();
				if(idx!=-1&&idx<DefaultsDialog.this.getLstDyzurniModel().size()-1)
				{
					Object o=DefaultsDialog.this.getLstDyzurniModel().get(idx+1);
					DefaultsDialog.this.getLstDyzurniModel().set(idx+1, DefaultsDialog.this.getLstDyzurniModel().get(idx));
					DefaultsDialog.this.getLstDyzurniModel().set(idx,o);
					DefaultsDialog.this.lstDyzurni.setSelectedIndex(idx+1);
				}
			}
		});
		btnDyzurnyDown.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnDyzurnyDown.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnDyzurnyDown = new GridBagConstraints();
		gbc_btnDyzurnyDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDyzurnyDown.anchor = GridBagConstraints.WEST;
		gbc_btnDyzurnyDown.gridwidth = 2;
		gbc_btnDyzurnyDown.gridx = 1;
		gbc_btnDyzurnyDown.gridy = 13;
		getContentPane().add(btnDyzurnyDown, gbc_btnDyzurnyDown);
		
		JButton btnOkresDown = new JButton("");
		btnOkresDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstOkresy.getSelectedIndex();
				if(idx!=-1&&idx<DefaultsDialog.this.getLstOkresyModel().size()-1)
				{
					Object o=DefaultsDialog.this.getLstOkresyModel().get(idx+1);
					DefaultsDialog.this.getLstOkresyModel().set(idx+1, DefaultsDialog.this.getLstOkresyModel().get(idx));
					DefaultsDialog.this.getLstOkresyModel().set(idx,o);
					DefaultsDialog.this.lstOkresy.setSelectedIndex(idx+1);
				}
			}
		});
		btnOkresDown.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnOkresDown.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnOkresDown = new GridBagConstraints();
		gbc_btnOkresDown.anchor = GridBagConstraints.WEST;
		gbc_btnOkresDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnOkresDown.gridx = 4;
		gbc_btnOkresDown.gridy = 13;
		getContentPane().add(btnOkresDown, gbc_btnOkresDown);
		
		JButton btnDyzurnyDelete = new JButton("X");
		btnDyzurnyDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstDyzurni.getSelectedIndex();
				if(idx!=-1)
				{
					if(DefaultsDialog.this.getLstDyzurniModel().getSize()>1)
						DefaultsDialog.this.getLstDyzurniModel().remove(idx);
				}
			}
		});
		btnDyzurnyDelete.setFocusable(false);
		btnDyzurnyDelete.setBackground(Color.RED);
		btnDyzurnyDelete.setForeground(Color.WHITE);
		btnDyzurnyDelete.setMargin(new Insets(2, 2, 2, 2));
		btnDyzurnyDelete.setMinimumSize(new Dimension(20, 20));
		btnDyzurnyDelete.setMaximumSize(new Dimension(20, 20));
		btnDyzurnyDelete.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnDyzurnyDelete = new GridBagConstraints();
		gbc_btnDyzurnyDelete.gridwidth = 2;
		gbc_btnDyzurnyDelete.anchor = GridBagConstraints.WEST;
		gbc_btnDyzurnyDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDyzurnyDelete.gridx = 1;
		gbc_btnDyzurnyDelete.gridy = 14;
		getContentPane().add(btnDyzurnyDelete, gbc_btnDyzurnyDelete);
		
		JButton btnOkresDeelete = new JButton("X");
		btnOkresDeelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idx=DefaultsDialog.this.lstOkresy.getSelectedIndex();
				if(idx!=-1)
				{
					if(DefaultsDialog.this.getLstOkresyModel().getSize()>1)
						DefaultsDialog.this.getLstOkresyModel().remove(idx);
				}
			}
		});
		btnOkresDeelete.setMargin(new Insets(2, 2, 2, 2));
		btnOkresDeelete.setMinimumSize(new Dimension(20, 20));
		btnOkresDeelete.setMaximumSize(new Dimension(20, 20));
		btnOkresDeelete.setBackground(Color.RED);
		btnOkresDeelete.setForeground(Color.WHITE);
		btnOkresDeelete.setFocusable(false);
		btnOkresDeelete.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnOkresDeelete = new GridBagConstraints();
		gbc_btnOkresDeelete.anchor = GridBagConstraints.WEST;
		gbc_btnOkresDeelete.gridwidth = 2;
		gbc_btnOkresDeelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnOkresDeelete.gridx = 4;
		gbc_btnOkresDeelete.gridy = 14;
		getContentPane().add(btnOkresDeelete, gbc_btnOkresDeelete);
		
		JLabel lblNazwiskoIImi = new JLabel("Nazwisko i imi\u0119");
		GridBagConstraints gbc_lblNazwiskoIImi = new GridBagConstraints();
		gbc_lblNazwiskoIImi.anchor = GridBagConstraints.SOUTH;
		gbc_lblNazwiskoIImi.insets = new Insets(0, 0, 0, 5);
		gbc_lblNazwiskoIImi.gridx = 2;
		gbc_lblNazwiskoIImi.gridy = 15;
		getContentPane().add(lblNazwiskoIImi, gbc_lblNazwiskoIImi);
		
		JLabel lblPrzedziaCzasu = new JLabel("Przedzia\u0142 czasu");
		GridBagConstraints gbc_lblPrzedziaCzasu = new GridBagConstraints();
		gbc_lblPrzedziaCzasu.anchor = GridBagConstraints.SOUTH;
		gbc_lblPrzedziaCzasu.insets = new Insets(0, 0, 0, 5);
		gbc_lblPrzedziaCzasu.gridx = 5;
		gbc_lblPrzedziaCzasu.gridy = 15;
		getContentPane().add(lblPrzedziaCzasu, gbc_lblPrzedziaCzasu);
		
		JLabel lblIlg = new JLabel("il.g.");
		GridBagConstraints gbc_lblIlg = new GridBagConstraints();
		gbc_lblIlg.anchor = GridBagConstraints.SOUTH;
		gbc_lblIlg.insets = new Insets(0, 0, 0, 5);
		gbc_lblIlg.gridx = 6;
		gbc_lblIlg.gridy = 15;
		getContentPane().add(lblIlg, gbc_lblIlg);
		
		JButton btnDyzurnyAdd = new JButton("");
		btnDyzurnyAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=DefaultsDialog.this.tfDyzurny.getText();
				if(str!=null&&!str.isEmpty())
					DefaultsDialog.this.getLstDyzurniModel().addElement(str);
			}
		});
		btnDyzurnyAdd.setPreferredSize(new Dimension(20, 20));
		//btnDyzurnyAdd.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollLeftArrowActive.gif")));
		GridBagConstraints gbc_btnDyzurnyAdd = new GridBagConstraints();
		gbc_btnDyzurnyAdd.anchor = GridBagConstraints.EAST;
		gbc_btnDyzurnyAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnDyzurnyAdd.gridx = 1;
		gbc_btnDyzurnyAdd.gridy = 16;
		getContentPane().add(btnDyzurnyAdd, gbc_btnDyzurnyAdd);
		
		tfDyzurny = new JTextField();
		tfDyzurny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=DefaultsDialog.this.tfDyzurny.getText();
				if(str!=null&&!str.isEmpty())
					DefaultsDialog.this.getLstDyzurniModel().addElement(str);
			}
		});
		GridBagConstraints gbc_tfDyzurny = new GridBagConstraints();
		gbc_tfDyzurny.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDyzurny.insets = new Insets(0, 0, 5, 10);
		gbc_tfDyzurny.gridx = 2;
		gbc_tfDyzurny.gridy = 16;
		getContentPane().add(tfDyzurny, gbc_tfDyzurny);
		tfDyzurny.setColumns(10);
		
		JScrollPane spLstOkresy = new JScrollPane();
		spLstOkresy.setMaximumSize(new Dimension(32767, 100));
		spLstOkresy.setPreferredSize(new Dimension(2, 100));
		GridBagConstraints gbc_spLstOkresy = new GridBagConstraints();
		gbc_spLstOkresy.gridheight = 5;
		gbc_spLstOkresy.fill = GridBagConstraints.BOTH;
		gbc_spLstOkresy.insets = new Insets(0, 0, 5, 5);
		gbc_spLstOkresy.gridx = 3;
		gbc_spLstOkresy.gridy = 12;
		getContentPane().add(spLstOkresy, gbc_spLstOkresy);
		
		lstOkresy = new JList();
		lstOkresy.setPreferredSize(new Dimension(0, 100));
		spLstOkresy.setViewportView(lstOkresy);
		lstOkresy.setModel(new DefaultListModel());
		
		JButton btnOkresAdd = new JButton("");
		btnOkresAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=DefaultsDialog.this.tfPrzedzial.getText();
				if(str!=null&&!str.isEmpty())
				{
					
					DefaultsDialog.this.getLstOkresyModel().addElement
						(new CzasDyzuru(str, Double.valueOf(DefaultsDialog.this.spIlg.getValue().toString())));
				}
			}
		});
		//btnOkresAdd.setIcon(new ImageIcon(DefaultsDialog.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollLeftArrowActive.gif")));
		btnOkresAdd.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_btnOkresAdd = new GridBagConstraints();
		gbc_btnOkresAdd.anchor = GridBagConstraints.EAST;
		gbc_btnOkresAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnOkresAdd.gridx = 4;
		gbc_btnOkresAdd.gridy = 16;
		getContentPane().add(btnOkresAdd, gbc_btnOkresAdd);
		
		tfPrzedzial = new JTextField();
		tfPrzedzial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=DefaultsDialog.this.tfPrzedzial.getText();
				if(str!=null&&!str.isEmpty())
				{
					DefaultsDialog.this.getLstOkresyModel().addElement
					(new CzasDyzuru(str, Double.valueOf(DefaultsDialog.this.spIlg.getValue().toString())));
				}
			}
		});
		GridBagConstraints gbc_tfPrzedzial = new GridBagConstraints();
		gbc_tfPrzedzial.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPrzedzial.insets = new Insets(0, 0, 5, 5);
		gbc_tfPrzedzial.gridx = 5;
		gbc_tfPrzedzial.gridy = 16;
		getContentPane().add(tfPrzedzial, gbc_tfPrzedzial);
		tfPrzedzial.setColumns(10);
		
		spIlg = new JSpinner();
		spIlg.setModel(new SpinnerNumberModel(0.0, 0.0, 24.0, 0.5));
		GridBagConstraints gbc_spIlg = new GridBagConstraints();
		gbc_spIlg.insets = new Insets(0, 0, 5, 5);
		gbc_spIlg.gridx = 6;
		gbc_spIlg.gridy = 16;
		getContentPane().add(spIlg, gbc_spIlg);
		
		JButton btnOdczytaj = new JButton("Odczytaj z grafiku");
		btnOdczytaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafik g=DefaultsDialog.this.parent.getGrafik();
				DefaultsDialog dd=DefaultsDialog.this;
				dd.getLstDyzurniModel().removeAllElements();
				dd.getLstOkresyModel().removeAllElements();
				dd.tfDyzurny.setText("");
				dd.tfPrzedzial.setText("");
				
				dd.tfFirma.setText(g.getFirma());
				dd.tfGrupa.setText(g.getGrupa());
				dd.tfKlauzula.setText(g.getKlauzula());
				dd.tfTytul.setText(g.getTytul());
				
				for(int i=0;i<g.iluDyzurnych();i++)
					dd.getLstDyzurniModel().addElement(g.getDyzurny(i));
				for(int i=0;i<g.ileOkresowDyzurow();i++)
					dd.getLstOkresyModel().addElement(g.getOkresDyzuru(i));
			}
		});
		GridBagConstraints gbc_btnOdczytaj = new GridBagConstraints();
		gbc_btnOdczytaj.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnOdczytaj.insets = new Insets(0, 5, 10, 5);
		gbc_btnOdczytaj.gridx = 0;
		gbc_btnOdczytaj.gridy = 17;
		getContentPane().add(btnOdczytaj, gbc_btnOdczytaj);
		
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btnZastosuj=new JButton("Zastosuj");
		btnZastosuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultsDialog.this.saveSettings();
			}
		});
		JButton btnOK=new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultsDialog.this.saveSettings();
				DefaultsDialog.this.setVisible(false);
			}
		});
		JButton btnAnuluj=new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultsDialog.this.setVisible(false);
			}
		});
		
		panel.add(btnZastosuj);
		panel.add(btnOK);
		panel.add(btnAnuluj);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTH;
		gbc_panel.insets = new Insets(0, 5, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 17;
		gbc_panel.gridwidth=6;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		
		getContentPane().add(panel, gbc_panel);
		
		this.pack();
	}
	public DefaultsDialog(EdGraf edGraf){
		this();
		this.parent=edGraf;
	}
	/**
	 * Metoda wypelnia listê dy¿urnych na podstawie ustawieñ programu EdGraf
	 * @see #lstDyzurni
	 * @see EdGraf#getSettings()
	 * @see #parent
	 */
	private void fillDyzurni(){
		((DefaultListModel)(this.lstDyzurni.getModel())).removeAllElements();
		String dyzurny;
		int i=0;
		do
		{
			dyzurny=this.parent.getSettings().getProperty("dyzurny."+i++);
			if(dyzurny!=null) ((DefaultListModel)(this.lstDyzurni.getModel())).addElement(dyzurny);
		}
		while(dyzurny!=null);
	}
	/**
	 * Metoda wypelnia listê okresów na podstawie ustawieñ programu EdGraf
	 * @see #lstDyzurni
	 * @see EdGraf#getSettings()
	 * @see #parent
	 */
	private void fillOkresy(){
		((DefaultListModel)(this.lstOkresy.getModel())).removeAllElements();
		String okres;
		int i=0;
		do
		{
			okres=this.parent.getSettings().getProperty("czasDyzuru."+i++);
			if(okres!=null) 
			{
				String[] tab=okres.split(";");
				
				((DefaultListModel)(this.lstOkresy.getModel())).addElement(new CzasDyzuru(tab[0],Double.parseDouble(tab[1])));
			}
		}
		while(okres!=null);
	}
	/**
	 * 
	 * @return model listy dy¿urnych
	 * @see #lstDyzurni
	 */
	public DefaultListModel getLstDyzurniModel(){
		return (DefaultListModel) this.lstDyzurni.getModel();
	}
	/**
	 * 
	 * @return model listy okresów
	 * @see #lstOkresy
	 */
	public DefaultListModel getLstOkresyModel(){
		return (DefaultListModel) this.lstOkresy.getModel();
	}
	/**
	 * Metoda zapisuje wartoœci okna dialogowego do ustawieñ EdGraf.
	 * @see #parent
	 * @see EdGraf#getSettings()
	 * @see EdGraf#saveSettings()
	 */
	public void saveSettings(){
		DefaultsDialog dd=this;
		Properties props=dd.parent.getSettings();
		props.setProperty("firma", dd.tfFirma.getText());
		props.setProperty("grupa", dd.tfGrupa.getText());
		props.setProperty("klauzula", dd.tfKlauzula.getText());
		props.setProperty("tytul", dd.tfTytul.getText());
		props.setProperty("monthOffset", dd.spOffset.getValue().toString());
		
		int i=0;
		while(props.remove("dyzurny."+i++)!=null);
		i=0;
		while(props.remove("czasDyzuru."+i++)!=null);
		for(i=0;i<dd.getLstDyzurniModel().getSize();i++)
			props.setProperty("dyzurny."+i, dd.getLstDyzurniModel().get(i).toString());
		for(i=0;i<dd.getLstOkresyModel().getSize();i++)
		{
			CzasDyzuru cd=(CzasDyzuru) dd.getLstOkresyModel().get(i);
			props.setProperty("czasDyzuru."+i, cd.getPrzedzialCzasu()+";"+cd.getIloscGodzin());
		}
		dd.parent.saveSettings();
	}
	@Override
	public void setVisible(boolean b) {
		if(b)
		{
			Properties settings=this.parent.getSettings();
			this.tfFirma.setText(settings.getProperty("firma"));
			this.tfDyzurny.setText("");
			this.tfGrupa.setText(settings.getProperty("grupa"));
			this.tfKlauzula.setText(settings.getProperty("klauzula"));
			this.tfPrzedzial.setText("");
			this.tfTytul.setText(settings.getProperty("tytul"));
			this.spIlg.setValue(0);
			this.spOffset.setValue(Integer.parseInt(settings.getProperty("monthOffset","0")));
			this.fillDyzurni();
			this.fillOkresy();
		}
		super.setVisible(b);
	}
}
