/**
 * 
 */
package gui.dialogs;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
/**
 * Okno dialogowe ustawieñ wydruku
 * @author Wojciech Pierzchalski
 *
 */
public class PrintDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Przycisk drukowania
	 */
	private final JButton btnDrukuj = new JButton("Drukuj");
	/**
	 * ComboBox wyboru drukarki
	 */
	private JComboBox cbDrukarki;
	/**
	 * ComboBox wybory, czy wydruk ma byæ w kolorze
	 */
	private final JComboBox cbKolor;
	/**
	 * Atrybut okreœla czy wywo³anie metody <code>setDefaultDyzurnyPerPage()</code> ma ustawiæ wartoœæ
	 * liczby dy¿urnych na stronê na wartoœæ domyœln¹
	 * @see #setDefaultDyzurnyPerPage()
	 */
	private boolean defaultDyzurnyPerPage=false;
	/**
	 * Jeœli u¿ytkownik zatwierdzi drukowanie wartoœc pola jest <code>true</code>
	 */
	private boolean print=false;
	/**
	 * Pole wyboru liczby dy¿urnych na stronê
	 */
	private JSpinner spDyzurnyPerPage;
	/**
	 * Pole wyboru liczby kopii
	 */
	private JSpinner spKopie;
	public PrintDialog(){
		this(PrinterJob.lookupPrintServices());
	}
	
	public PrintDialog(PrintService[] services) {
		setResizable(false);
		setModal(true);
		this.setTitle("Drukuj");
		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		
		
		JButton btnAnuluj = new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrintDialog.this.print=false;
				PrintDialog.this.setVisible(false);	
			}
		});
		this.btnDrukuj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					PrintDialog.this.print=true;
					PrintDialog.this.setVisible(false);		
			}
		});
		
		
		JLabel lblDrukarka = new JLabel("Drukarka");
		GridBagConstraints gbc_lblDrukarka = new GridBagConstraints();
		gbc_lblDrukarka.insets = new Insets(5, 5, 5, 5);
		gbc_lblDrukarka.gridx = 0;
		gbc_lblDrukarka.gridy = 0;
		gbc_lblDrukarka.anchor = GridBagConstraints.WEST;
		this.getContentPane().add(lblDrukarka, gbc_lblDrukarka);
		
		cbDrukarki = new JComboBox(services);
		cbDrukarki.setFocusable(false);
		cbDrukarki.setBackground(Color.WHITE);
		cbDrukarki.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				JLabel lbl=(JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
				lbl.setText(((PrintService)value).getName());
				return lbl;
			}
		});
		
				GridBagConstraints gbc_cbDrukarki = new GridBagConstraints();
				gbc_cbDrukarki.insets = new Insets(5, 0, 5, 0);
				gbc_cbDrukarki.gridwidth = 3;
				gbc_cbDrukarki.gridy = 0;
				gbc_cbDrukarki.gridx = 1;
				this.getContentPane().add(cbDrukarki, gbc_cbDrukarki);
				
		
		JLabel lblLiczbaKopii = new JLabel("Liczba kopii");
		GridBagConstraints gbc_lblLiczbaKopii = new GridBagConstraints();
		gbc_lblLiczbaKopii.insets = new Insets(0, 5, 5, 5);
		gbc_lblLiczbaKopii.anchor = GridBagConstraints.WEST;
		gbc_lblLiczbaKopii.gridy = 2;
		gbc_lblLiczbaKopii.gridx = 0;
		this.getContentPane().add(lblLiczbaKopii, gbc_lblLiczbaKopii);
		
		
		JLabel lblDrukwKolorze = new JLabel("Druk w kolorze");
		GridBagConstraints gbc_lblDrukwKolorze = new GridBagConstraints();
		gbc_lblDrukwKolorze.insets = new Insets(0, 5, 5, 5);
		gbc_lblDrukwKolorze.anchor = GridBagConstraints.WEST;
		gbc_lblDrukwKolorze.gridy = 1;
		gbc_lblDrukwKolorze.gridx = 0;
		this.getContentPane().add(lblDrukwKolorze, gbc_lblDrukwKolorze);
		
		cbKolor = new JComboBox();
		cbKolor.setBackground(Color.WHITE);
		cbKolor.setFocusable(false);
		cbKolor.setModel(new DefaultComboBoxModel(new String[] {"TAK", "NIE"}));
		
		GridBagConstraints gbc_cbKolor = new GridBagConstraints();
		gbc_cbKolor.anchor = GridBagConstraints.WEST;
		gbc_cbKolor.insets = new Insets(0, 0, 5, 5);
		gbc_cbKolor.gridy = 1;
		gbc_cbKolor.gridx = 2;
		this.getContentPane().add(cbKolor, gbc_cbKolor);
		
		spKopie = new JSpinner();
		spKopie.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spKopie.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_spKopie = new GridBagConstraints();
		gbc_spKopie.anchor = GridBagConstraints.WEST;
		gbc_spKopie.insets = new Insets(0, 0, 5, 5);
		gbc_spKopie.gridx = 2;
		gbc_spKopie.gridy = 2;
		getContentPane().add(spKopie, gbc_spKopie);
		
		JLabel lblLiczbaDyurnychNa = new JLabel("Liczba dy\u017Curnych na stron\u0119");
		GridBagConstraints gbc_lblLiczbaDyurnychNa = new GridBagConstraints();
		gbc_lblLiczbaDyurnychNa.anchor = GridBagConstraints.WEST;
		gbc_lblLiczbaDyurnychNa.gridwidth = 2;
		gbc_lblLiczbaDyurnychNa.insets = new Insets(0, 5, 5, 5);
		gbc_lblLiczbaDyurnychNa.gridx = 0;
		gbc_lblLiczbaDyurnychNa.gridy = 3;
		getContentPane().add(lblLiczbaDyurnychNa, gbc_lblLiczbaDyurnychNa);
		
		spDyzurnyPerPage = new JSpinner();
		spDyzurnyPerPage.setModel(new SpinnerNumberModel(1, 1, 7, 1));
		spDyzurnyPerPage.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_spDyzurnyPerPage = new GridBagConstraints();
		gbc_spDyzurnyPerPage.anchor = GridBagConstraints.WEST;
		gbc_spDyzurnyPerPage.insets = new Insets(0, 0, 5, 5);
		gbc_spDyzurnyPerPage.gridx = 2;
		gbc_spDyzurnyPerPage.gridy = 3;
		getContentPane().add(spDyzurnyPerPage, gbc_spDyzurnyPerPage);
		
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.TRAILING));
		panel.add(btnDrukuj);
		panel.add(btnAnuluj);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.gridwidth = 3;
		gbc_panel.gridy = 4;
		gbc_panel.gridx = 1;
		this.getContentPane().add(panel, gbc_panel);
		this.pack();
	}
	/**
	 * 
	 * @return liczba kopii wydruku
	 */
	public int getCopies(){
		return (Integer) this.spKopie.getValue();
	}
	/**
	 * 
	 * @return liczba dy¿urnych na stronê
	 */
	public int getDyzurnyPerPage(){
		return (Integer) this.spDyzurnyPerPage.getValue();
	}
	/**
	 * 
	 * @return PrintService - wybrana drukarka
	 */
	public PrintService getPrintService(){
		return (PrintService) this.cbDrukarki.getSelectedItem();
	}
	/**
	 * 
	 * @return true je¿eli u¿ytkownik zatwierdzi³ drukowanie
	 * @see PrintDialog#print
	 */
	public boolean isPrintApproved(){
		return this.print;
	}
	/**
	 * 
	 * @return true je¿li wydruk ma byæ w kolorze
	 */
	public boolean isPrintInColor(){
		return this.cbKolor.getSelectedIndex()==0;
	}
	/**
	 * Ustawia domyœln¹ wartoœæ liczby dy¿urnych na stronê, pod warunkiem ,¿e <code>defaultDyzurnyPerPage==false</code>
	 */
	public void setDefaultDyzurnyPerPage(){
		if(!this.defaultDyzurnyPerPage)
		{
			this.setDyzurnyPerPage((Integer)((SpinnerNumberModel)this.spDyzurnyPerPage.getModel()).getMaximum());
		}
	}
	/**
	 * Ustawia liczbê dy¿urnych na stronê
	 * @param dpp nowa liczba dy¿urnych na stronê
	 */
	public void setDyzurnyPerPage(int dpp){
		this.spDyzurnyPerPage.setValue(dpp);
	}
	/**
	 * Ustawia maksymaln¹ liczbê dyzurnych na stronê.
	 * @param mdpp  nowa maksymalna liczba dy¿urnych na stronê
	 */
	public void setMaxDyzurnyPerPage(int mdpp){
		SpinnerNumberModel model=(SpinnerNumberModel) this.spDyzurnyPerPage.getModel();
		model.setMaximum(mdpp);
	}
	@Override
	public void setVisible(boolean b) {
		if(b) this.print=false;
		super.setVisible(b);
	}
}
