/**
 * 
 */
package gui.dialogs;

import grafik.Grafik.TypDnia;
import gui.EdGraf;
import gui.tables.GrafikTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author Wojciech Pierzchalski
 *
 */
public class ColorDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Metoda ustawia kolor t³a przycisku na zadany przez <code>color</color> 
	 * oraz kolor tekstu przycisku na odwrotny do zadanego
	 * @param btn przycisk do pokolorwania
	 * @param color zadany kolor
	 * @see JButton#setBackground(Color)
	 * @see JButton#setForeground(Color)
	 */
	private static void color(JButton btn,Color color){
		btn.setBackground(color);
		btn.setForeground(new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue()));
	}
	/**
	 * Przycisk Dnia Energetyka
	 */
	private JButton btnDE;
	/**
	 * Przycisk wczytania ustawieñ domyœlnych
	 */
	private JButton btnDeafault;
	/**
	 * Przycisk b³ednie wstawionego dy¿uru
	 */
	private JButton btnError;
	/**
	 * Przycisk niedzieli
	 */
	private JButton btnNiedziela;
	/**
	 * Przycisk dnia powszedniego
	 */
	private JButton btnPowszedni;
	/**
	 * Przycisk soboty
	 */
	private JButton btnSobota;
	/**
	 * Przycisk Œwieta
	 */
	private JButton btnSwieto;
	
	/**
	 *  Rodzic okna
	 */
	private EdGraf parent;
	public ColorDialog(EdGraf parent) {
		this.parent=parent;
		setResizable(false);
		setModal(true);
		this.setTitle("Ustawienia kolorów");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{120, 120, 120, 0};
		gridBagLayout.rowHeights = new int[]{23, 23, 23, 37, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		btnSobota = new JButton("Sobota");
		btnSobota.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSobota.setFocusable(false);
		btnSobota.setForeground(Color.BLACK);
		btnSobota.setBackground(Color.RED);
		btnSobota.addActionListener(this);
		
		btnPowszedni = new JButton("Zwyk³y dzieñ");
		btnPowszedni.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPowszedni.setFocusable(false);
		btnPowszedni.setForeground(Color.BLACK);
		btnPowszedni.setBackground(Color.RED);
		btnPowszedni.addActionListener(this);
		GridBagConstraints gbc_btnPowszedni = new GridBagConstraints();
		gbc_btnPowszedni.fill = GridBagConstraints.BOTH;
		gbc_btnPowszedni.insets = new Insets(5, 5, 5, 5);
		gbc_btnPowszedni.gridx = 0;
		gbc_btnPowszedni.gridy = 0;
		getContentPane().add(btnPowszedni, gbc_btnPowszedni);
		GridBagConstraints gbc_btnSobota = new GridBagConstraints();
		gbc_btnSobota.fill = GridBagConstraints.BOTH;
		gbc_btnSobota.insets = new Insets(5, 0, 5, 5);
		gbc_btnSobota.gridx = 1;
		gbc_btnSobota.gridy = 0;
		getContentPane().add(btnSobota, gbc_btnSobota);
		
		btnDE = new JButton("Dzieñ Energetyka");
		btnDE.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDE.setFocusable(false);
		btnDE.setForeground(Color.BLACK);
		btnDE.setBackground(Color.RED);
		btnDE.addActionListener(this);
		
		btnSwieto = new JButton("Œwiêto");
		btnSwieto.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSwieto.setFocusable(false);
		btnSwieto.setForeground(Color.BLACK);
		btnSwieto.setBackground(Color.RED);
		btnSwieto.addActionListener(this);
		
		
		btnNiedziela = new JButton("Niedziela");
		
		btnNiedziela.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNiedziela.setFocusable(false);
		btnNiedziela.setForeground(Color.BLACK);
		btnNiedziela.setBackground(Color.RED);
		btnNiedziela.addActionListener(this);
		GridBagConstraints gbc_btnNiedziela = new GridBagConstraints();
		gbc_btnNiedziela.fill = GridBagConstraints.BOTH;
		gbc_btnNiedziela.insets = new Insets(5, 0, 5, 5);
		gbc_btnNiedziela.gridx = 2;
		gbc_btnNiedziela.gridy = 0;
		getContentPane().add(btnNiedziela, gbc_btnNiedziela);
		
		btnError = new JButton("B³¹d");
		btnError.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnError.setFocusable(false);
		btnError.setForeground(Color.BLACK);
		btnError.setBackground(Color.RED);
		btnError.addActionListener(this);
		GridBagConstraints gbc_btnError = new GridBagConstraints();
		gbc_btnError.insets = new Insets(0, 5, 5, 5);
		gbc_btnError.fill = GridBagConstraints.BOTH;
		gbc_btnError.gridx = 0;
		gbc_btnError.gridy = 1;
		getContentPane().add(btnError, gbc_btnError);
		GridBagConstraints gbc_btnSwieto = new GridBagConstraints();
		gbc_btnSwieto.fill = GridBagConstraints.BOTH;
		gbc_btnSwieto.insets = new Insets(0, 0, 5, 5);
		gbc_btnSwieto.gridx = 1;
		gbc_btnSwieto.gridy = 1;
		getContentPane().add(btnSwieto, gbc_btnSwieto);
		GridBagConstraints gbc_btnDE = new GridBagConstraints();
		gbc_btnDE.fill = GridBagConstraints.BOTH;
		gbc_btnDE.insets = new Insets(0, 0, 5, 5);
		gbc_btnDE.gridx = 2;
		gbc_btnDE.gridy = 1;
		getContentPane().add(btnDE, gbc_btnDE);
		
		btnDeafault = new JButton("Przywr\u00F3c domy\u015Blne");
		btnDeafault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				ColorDialog cd=ColorDialog.this;
				ColorDialog.color(cd.btnError, Color.YELLOW);
				ColorDialog.color(cd.btnDE, new Color(0, 0x80, 0));
				ColorDialog.color(cd.btnNiedziela, Color.RED);
				ColorDialog.color(cd.btnPowszedni, Color.WHITE);
				ColorDialog.color(cd.btnSobota, new Color(0, 255, 255));
				ColorDialog.color(cd.btnSwieto, new Color(0x8B, 0, 0));
			}
		});
		GridBagConstraints gbc_btnDeafault = new GridBagConstraints();
		gbc_btnDeafault.anchor = GridBagConstraints.SOUTH;
		gbc_btnDeafault.insets = new Insets(0, 5, 10, 5);
		gbc_btnDeafault.gridx = 0;
		gbc_btnDeafault.gridy = 3;
		getContentPane().add(btnDeafault, gbc_btnDeafault);
		gbc_btnDeafault.anchor = GridBagConstraints.SOUTH;
		gbc_btnDeafault.gridx = 2;
		gbc_btnDeafault.gridy = 3;
		
		JPanel panel=new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton btnOk=new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorDialog cd=ColorDialog.this;
				Properties props=cd.parent.getSettings();
				GrafikTable gt=cd.parent.getTabela();
				props.put("color.error", String.valueOf(cd.btnError.getBackground().getRGB()));
				gt.setErrorColor(cd.btnError.getBackground());
				props.put("color.TypDnia.NIEDZIELA", String.valueOf(cd.btnNiedziela.getBackground().getRGB()));
				gt.getKolory().put(TypDnia.NIEDZIELA, cd.btnNiedziela.getBackground());
				props.put("color.TypDnia.SOBOTA", String.valueOf(cd.btnSobota.getBackground().getRGB()));
				gt.getKolory().put(TypDnia.SOBOTA, cd.btnSobota.getBackground());
				props.put("color.TypDnia.SWIETO", String.valueOf(cd.btnSwieto.getBackground().getRGB()));
				gt.getKolory().put(TypDnia.SWIETO, cd.btnSwieto.getBackground());
				props.put("color.TypDnia.POWSZEDNI", String.valueOf(cd.btnPowszedni.getBackground().getRGB()));
				gt.getKolory().put(TypDnia.POWSZEDNI, cd.btnPowszedni.getBackground());
				props.put("color.TypDnia.DZIEN_ENERGETYKA", String.valueOf(cd.btnDE.getBackground().getRGB()));
				gt.getKolory().put(TypDnia.DZIEN_ENERGETYKA, cd.btnDE.getBackground());
				cd.parent.saveSettings();
				cd.parent.repaint();
				cd.setVisible(false);
			}
		});
		JButton btnAnuluj=new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorDialog.this.setVisible(false);
			}
		});
		panel.add(btnOk);
		panel.add(btnAnuluj);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 3;
		getContentPane().add(panel, gbc_panel);
		
		
		this.pack();
	}
	
	/**
	 * Metoda obs³uguj¹ca zdarzenie wciœniêcia przycisku.<BR>
	 * Wyœwietla okno dialogowe wyboru koloru,a po akceptacji koloruje przycisk.
	 * @see #color(JButton, Color)
	 * @see JColorChooser#showDialog(Component, String, Color)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn=(JButton) e.getSource();
		Color nowy=JColorChooser.showDialog(this, "Wybierz kolor - "+btn.getText(), btn.getBackground());
		if(nowy!=null) color(btn,nowy);
	}

	@Override
	public void setVisible(boolean b) {
		if(b)
		{
			color(this.btnDE,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.TypDnia.DZIEN_ENERGETYKA"))));
			color(this.btnNiedziela,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.TypDnia.NIEDZIELA"))));
			color(this.btnPowszedni,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.TypDnia.POWSZEDNI"))));
			color(this.btnSobota,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.TypDnia.SOBOTA"))));
			color(this.btnSwieto,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.TypDnia.SWIETO"))));
			color(this.btnError,new Color(Integer.parseInt((String) this.parent.getSettings().get("color.error"))));
		}
		super.setVisible(b);
	}

}
