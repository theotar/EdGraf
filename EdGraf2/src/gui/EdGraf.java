package gui;

import grafik.Grafik;
import grafik.GrafikFactory;
import grafik.printers.GrafikPrinter;
import grafik.printers.PrintableGrafik;
import gui.dialogs.AboutDialog;
import gui.dialogs.ColorDialog;
import gui.dialogs.DateDialog;
import gui.dialogs.DefaultsDialog;
import gui.tables.GrafikTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

/**
 * G³ówne okno programu EdGraf
 * @author Wojciech Pierzchalski
 * @version 2.0.1.1
 */
public class EdGraf extends JFrame implements TableModelListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Obiekt pliku z ustawieniami programu<Br>
	 * Domyœlnie {$userdir}/.EdGraf/EdGraf.conf
	 */
	private static final File SETTINGS_FILE=new File(System.getProperty("user.home")+"/.EdGraf/EdGraf.conf");
	/**
	 * Wersja programu
	 */
	public static final String VERSION="2.0.1.1"; 
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		updateUI();
		File settingsFolder=new File(System.getProperties().getProperty("user.home")+"/.EdGraf");
		if(!settingsFolder.exists()) settingsFolder.mkdir();
		final String path;
		if(args.length>0) path=args[0];
		else path=null;
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	if(path==null) new EdGraf();
            	else new EdGraf(GrafikFactory.getGrafikFromFile(path));

            }
        });
    	

	}
	/**
	 * Metoda usawiaj¹ca UI komponentów na potrzeby programu
	 */
	public static void updateUI(){
		UIManager.put("FileChooser.openDialogTitleText", "Otwórz");
        UIManager.put("FileChooser.lookInLabelText", "Szukaj w");
        UIManager.put("FileChooser.openButtonText", "Otwórz");
        UIManager.put("FileChooser.cancelButtonText", "Anuluj");
        UIManager.put("FileChooser.fileNameLabelText", "Nazwa pliku");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Typ pliku");
        UIManager.put("FileChooser.openButtonToolTipText", "Otwórz zaznaczony plik");
        UIManager.put("FileChooser.cancelButtonToolTipText","Anuluj");
        UIManager.put("FileChooser.fileNameHeaderText","Nazwa pliku");
        UIManager.put("FileChooser.upFolderToolTipText", "Wy¿ej");
        UIManager.put("FileChooser.homeFolderToolTipText","Pulpit");
        UIManager.put("FileChooser.newFolderToolTipText","Nowy folder");
        UIManager.put("FileChooser.listViewButtonToolTipText","Lista");
        UIManager.put("FileChooser.newFolderButtonText","Utwórz nowy folder");
        UIManager.put("FileChooser.renameFileButtonText", "Zmieñ nazwê pliku");
        UIManager.put("FileChooser.deleteFileButtonText", "Usuñ plik");
        UIManager.put("FileChooser.filterLabelText", "Typ pliku");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegó³y");
        UIManager.put("FileChooser.fileSizeHeaderText","Rozmiar");
        UIManager.put("FileChooser.fileDateHeaderText", "Data modyfikacji");
        UIManager.put("FileChooser.saveInLabelText", "Zapisz w");
        UIManager.put("FileChooser.saveDialogTitleText","Zapisz jako");
        
        UIManager.put("ColorChooser.cancelText","Anuluj");
		UIManager.put("ColorChooser.previewText","Podgl¹d");
		UIManager.put("ColorChooser.rgbBlueText","Niebieski");
		UIManager.put("ColorChooser.rgbGreenText","Zielony");
		UIManager.put("ColorChooser.rgbRedText","Czerwony");
		UIManager.put("ColorChooser.swatchesNameText","Próbki");
		UIManager.put("ColorChooser.swatchesRecentText","Ostatnie");
		UIManager.put("ColorChooser.sampleText","Przyk³adowy tekst");
		
		UIManager.put("Panel.background",new Color(255, 255, 240));
		UIManager.put("OptionPane.background",new Color(255, 255, 240));
		
		//UIManager.put("ContentPane.background",new Color(255, 255, 240));
	}
	/**
	 * Okno dialogowe "O programie"
	 */
	private AboutDialog aboutDialog=null;
	/**
	 * Przycisk sprawdzania b³edów
	 */
	private JToggleButton btnSprawdajBledy;
	/**
	 * okno dialogowe wyboru kolorów
	 */
	private ColorDialog colorDialog=new ColorDialog(this);
	/**
	 * Aktualnie edytowany plik
	 */
	private File currentFile;
	/**
	 * Okno dialogowe ustawieñ domyœlnych
	 */
	private DefaultsDialog defaultsDialog=new DefaultsDialog(this);
	/**
	 * Obiekt grafiku
	 */
	private Grafik grafik;
	/**
	 * Panel z polami Tytu³u,Klauzuli itp.
	 */
	private JPanel grafikPanel;
	/**
	 * Obiekt drukuj¹cy grafik
	 */
	private PrintableGrafik grafikPrinter;
	/**
	 * Label pokazuj¹cy miesi¹c i rok grafiku
	 */
	private JLabel lblMiesiacRok;
	/**
	 * Panel zawierj¹cy Toolbar i GrafikPanel
	 * @see JToolBar
	 * @see #grafikPanel
	 * @see #toolbar
	 */
	private JPanel northPanel;
	/**
	 * Okno dialogowe otwarcia pliku
	 */
	private JFileChooser openFileDialog;
	/**
	 * Okno dialogowe zamkniêcia pliku
	 */
	private JFileChooser saveFileDialog;
	/**
	 * Ustawienia programu
	 * @see Properties
	 */
	private Properties settings;
	/**
	 * Tabela grafiku
	 */
	private GrafikTable tabela;
	/**
	 * Aktualnie zapamietana wysokoœæ tabeli grafiku
	 * @see #tabela
	 * @see JTable#getHeight()
	 */
	private int tabelaHeight=0;
	/**
	 * Kontener scrolluj¹cy dla tabeli grafiku
	 * @see #tabela
	 */
	private JScrollPane tabelaScrollPane;
	/**
	 * Pole firmy
	 */
	private JTextField tfFirma;
	/**
	 * Pole jednostki organizacyjnej
	 */
	private JTextField tfJednostkaOrg;
	/**
	 * Pole klauzuli
	 */
	private JTextField tfKlauzula;
	/**
	 * Pole tytu³u grafiku
	 */
	private JTextField tfTytul;
	/**
	 * Pasek narzedzi programu
	 */
	private JToolBar toolbar;
	
	public EdGraf() {
		this(GrafikFactory.getGrafikFromSettingsFile(EdGraf.SETTINGS_FILE));
	}

	public EdGraf(Grafik g){
		if(this.settings==null) this.loadSettings();
		if(g==null) g=GrafikFactory.getGrafikFromProperties(this.settings);
		this.grafik=g;
		grafikPrinter = new GrafikPrinter(this);
		this.setTitle("Edytor grafików");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(EdGraf.class.getResource("/gui/res/EdGraf.png")));
	
		Dimension size=new Dimension(1000, 300);
		this.setMinimumSize(size);


		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				EdGraf.this.zakoncz();
			}
		});
		this.initComponents();
		this.validate();
		this.pack();
		this.resizeTabelaScrollPane(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.toFront();
		
	}
	/**
	 * Metoda wype³nia pola w grafikPanelu na podstawie grafiku
	 * @see #grafikPanel
	 * @see #grafik
	 */
	private void fillGrafikPanel(){
		this.tfFirma.setText(this.grafik.getFirma());
		this.tfJednostkaOrg.setText(this.grafik.getGrupa());
		this.tfKlauzula.setText(this.grafik.getKlauzula());
		this.tfTytul.setText(this.grafik.getTytul());
		this.lblMiesiacRok.setText(this.grafik.getMiesiacRok());
		this.btnSprawdajBledy.setSelected(this.grafik.isSprawdzajBledy());
	}
	/**
	 * Metoda tworz¹ca domyœlne ustawienia programu.
	 * @return domyslne ustawienia programu
	 */
	private Properties getDefaultProperties(){
		Properties prop=new Properties();
		prop.setProperty("color.error", String.valueOf(Color.YELLOW.getRGB()));
		prop.setProperty("color.TypDnia.POWSZEDNI", String.valueOf( Color.WHITE.getRGB()));
		prop.setProperty("color.TypDnia.SOBOTA", String.valueOf( new Color(0, 255, 255).getRGB()));
		prop.setProperty("color.TypDnia.NIEDZIELA", String.valueOf( Color.RED.getRGB()));
		prop.setProperty("color.TypDnia.DZIEN_ENERGETYKA", String.valueOf( new Color(0, 0x80, 0).getRGB()));
		prop.setProperty("color.TypDnia.SWIETO", String.valueOf( new Color(0x8B, 0, 0).getRGB()));
		prop.setProperty("czasDyzuru.0", "07.00-15.00;8");
		prop.setProperty("czasDyzuru.1", "17.00-20.00;5");
		prop.setProperty("czasDyzuru.2", "20.00-02.00;6");
		prop.setProperty("czasDyzuru.3", "02.00-07.00;5");
		prop.setProperty("dyzurny.0", "Jan Kowalski");
		prop.setProperty("monthOffset", "1");
		return prop;
	}
	/**
	 * Metoda inicjalizuj¹ca komponenty formularza
	 */
	private void initComponents(){
		this.getContentPane().setLayout(new BorderLayout());
		this.initMenu();
		this.initNorthPanel();
		this.fillGrafikPanel();
		this.tabela=new GrafikTable(this.grafik);
		this.tabela.updateColor(this.settings);
		this.tabela.getModel().addTableModelListener(this);
		this.tabelaScrollPane=new JScrollPane(this.tabela);
		tabelaScrollPane.setPreferredSize(new Dimension(0, 0));
		this.tabelaScrollPane.getViewport().setBackground(Color.WHITE);
		this.getContentPane().add(this.tabelaScrollPane,BorderLayout.CENTER);
		
		this.saveFileDialog=new JFileChooser();
		this.saveFileDialog.setAcceptAllFileFilterUsed(false);
		this.openFileDialog=new JFileChooser();
		this.openFileDialog.setAcceptAllFileFilterUsed(false);
		
		this.saveFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Pliki grafików", "gdf"));
		this.openFileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Pliki grafików", "grf","gdf"));
	
	}
	/**
	 * Metoda inicjuj¹ca grafikPanel
	 * @see #grafikPanel
	 */
	private void initGrafikPanel(){
		this.grafikPanel=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		
		JLabel lbl=new JLabel("Firma");
		lbl.setHorizontalTextPosition(JLabel.LEFT);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;

		
		this.grafikPanel.add(lbl,c);
		
		lbl=new JLabel("Jednostka organizacyjna");
		lbl.setHorizontalTextPosition(JLabel.LEFT);
		c=new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=1;
		c.gridy=0;
		this.grafikPanel.add(lbl,c);
		
		lbl=new JLabel("Klauzula bezpieczeñstwa");
		lbl.setHorizontalTextPosition(JLabel.LEFT);
		c=new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=2;
		c.gridy=0;
		this.grafikPanel.add(lbl,c);
		
		lbl=new JLabel("Tutu³ grafiku");
		lbl.setHorizontalTextPosition(JLabel.LEFT);
		c=new GridBagConstraints();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=2;
		this.grafikPanel.add(lbl,c);
		
		lbl=new JLabel("Grafik na");
		lbl.setHorizontalTextPosition(JLabel.CENTER);
		c=new GridBagConstraints();
		//c.fill=c.HORIZONTAL;
		c.gridx=2;
		c.gridy=2;
		this.grafikPanel.add(lbl,c);
		
		JTextField tf=new JTextField(25);
		tf.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}		
			@Override
			public void insertUpdate(DocumentEvent e) {
					try {
						EdGraf.this.grafik.setFirma(e.getDocument().getText(0, e.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					EdGraf.this.grafik.setFirma(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(2, 0, 5, 5);
		this.tfFirma=tf;
		this.grafikPanel.add(tf,c);
		
		tf=new JTextField(40);
		tf.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}		
			@Override
			public void insertUpdate(DocumentEvent e) {
					try {
						EdGraf.this.grafik.setGrupa(e.getDocument().getText(0, e.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					EdGraf.this.grafik.setGrupa(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		c=new GridBagConstraints();
		c.gridx=1;
		c.gridy=1;
		c.insets=new Insets(2, 0, 5, 5);
		this.tfJednostkaOrg=tf;
		this.grafikPanel.add(tf,c);
		
		tf=new JTextField(20);
		tf.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}		
			@Override
			public void insertUpdate(DocumentEvent e) {
					try {
						EdGraf.this.grafik.setKlauzula(e.getDocument().getText(0, e.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					EdGraf.this.grafik.setKlauzula(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		c=new GridBagConstraints();
		c.gridx=2;
		c.gridy=1;
		c.insets=new Insets(2, 0, 5, 5);
		this.tfKlauzula=tf;
		this.grafikPanel.add(tf,c);
		
		tf=new JTextField();
		tf.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}		
			@Override
			public void insertUpdate(DocumentEvent e) {
					try {
						EdGraf.this.grafik.setTytul(e.getDocument().getText(0, e.getDocument().getLength()));
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					EdGraf.this.grafik.setTytul(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		c.insets=new Insets(2, 0, 5, 5);
		this.tfTytul=tf;
		this.grafikPanel.add(tf,c);
		
		lbl=new JLabel();
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setForeground(Color.BLUE);
		c=new GridBagConstraints();
		c.gridx=2;
		c.gridy=3;
		c.gridwidth=1;
		//c.fill=c.HORIZONTAL;
		this.lblMiesiacRok=lbl;
		this.grafikPanel.add(lbl,c);
		
		//this.getContentPane().add(this.grafikPanel,BorderLayout.SOUTH);
	}
	/**
	 * Metoda inicjuj¹ca menu programu
	 * @see JFrame#setJMenuBar(JMenuBar)
	 * @see JMenuBar
	 */
	private void initMenu(){
		JMenuBar menubar=new JMenuBar();
		JMenu menuPlik=new JMenu("Plik");
		JMenuItem miNowy=new JMenuItem("Nowy...");
		miNowy.setIcon(new ImageIcon(EdGraf.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		miNowy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.newGrafik();
			}
		});
		menuPlik.add(miNowy);
		JMenuItem miOtworz=new JMenuItem("Otwórz...");
		miOtworz.setIcon(new ImageIcon(EdGraf.class.getResource("/gui/res/openFile.gif")));
		miOtworz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		miOtworz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.open();	
			}
		});
		menuPlik.add(miOtworz);
		menuPlik.addSeparator();
		JMenuItem miZapisz=new JMenuItem("Zapisz", new ImageIcon(EdGraf.class.getResource("/gui/res/saveFile.gif")));
		miZapisz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		miZapisz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.save();	
			}
		});
		menuPlik.add(miZapisz);
		JMenuItem miZapiszJako=new JMenuItem("Zapisz jako...");
		miZapiszJako.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.saveAs();	
			}
		});
		menuPlik.add(miZapiszJako);
		menuPlik.addSeparator();
		JMenuItem miDrukujItem=new JMenuItem("Drukuj...", new ImageIcon(EdGraf.class.getResource("/gui/res/print.png")));
		miDrukujItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		miDrukujItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.print();	
			}
		});
		menuPlik.add(miDrukujItem);
		menuPlik.addSeparator();
		JMenuItem miZakoncz=new JMenuItem("Zakoñcz");
		miZakoncz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.zakoncz();	
			}
		});
		menuPlik.add(miZakoncz);
		
		menubar.add(menuPlik);
		
		JMenu menuUstawienia=new JMenu("Ustawienia");
		JMenuItem miWD=new JMenuItem("Wartoœci domyœlne");
		miWD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.defaultsDialog.setLocationRelativeTo(EdGraf.this);
				EdGraf.this.defaultsDialog.setVisible(true);
			}
		});
		menuUstawienia.add(miWD);
		
		JMenuItem miKolory=new JMenuItem("Kolory");
		miKolory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.colorDialog.setLocationRelativeTo(EdGraf.this);
				EdGraf.this.colorDialog.setVisible(true);
			}
		});
		menuUstawienia.add(miKolory);
		menubar.add(menuUstawienia);
		
		JMenu menuPomoc=new JMenu("Pomoc");
		JMenuItem miO=new JMenuItem("O programie");
		miO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.getAboutDialog().setLocationRelativeTo(EdGraf.this);
				EdGraf.this.getAboutDialog().setVisible(true);
			}
		});
		menuPomoc.add(miO);
		
		menubar.add(menuPomoc);
		menubar.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		this.setJMenuBar(menubar);
		
	}
	/**
	 * Metoda inicjuj¹ca northPanel
	 * @see #northPanel
	 */
	private void initNorthPanel(){
		this.initToolbar();
		this.initGrafikPanel();
		this.northPanel=new JPanel(new BorderLayout());
		Dimension size=new Dimension(1000, 130);
		this.northPanel.setPreferredSize(size);
		this.northPanel.setMinimumSize(size);
		this.northPanel.add(this.toolbar,BorderLayout.NORTH);
		this.northPanel.add(this.grafikPanel,BorderLayout.CENTER);
		this.getContentPane().add(this.northPanel,BorderLayout.NORTH);
	}
	/**
	 * Metoda inicjuj¹ca pasek narzêdzi
	 * @see #toolbar
	 */
	private void initToolbar(){
		this.toolbar=new JToolBar();
		this.toolbar.setFloatable(false);
		this.toolbar.setPreferredSize(new Dimension(1000,40));
		this.toolbar.setMargin(new Insets(5, 0, 5, 0));
		
		JButton btnNowy=new JButton(new ImageIcon(EdGraf.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));

		btnNowy.setFocusable(false);
		btnNowy.setToolTipText("Utwórz nowy grafik");
		btnNowy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.newGrafik();
			}
		});
		this.toolbar.add(btnNowy);
		
		JButton btnOtworz=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/openFile.gif")));
		btnOtworz.setFocusable(false);
		btnOtworz.setToolTipText("Otwórz grafik");
		btnOtworz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.open();	
			}
		});
		this.toolbar.add(btnOtworz);
		
		JButton btnZapisz=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/saveFile.gif")));
		btnZapisz.setFocusable(false);
		btnZapisz.setToolTipText("Zapisz grafik");
		btnZapisz.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.save();	
			}
		});
		this.toolbar.add(btnZapisz);
		
		this.toolbar.addSeparator();
		JButton btnDrukuj=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/print.png")));
		btnDrukuj.setFocusable(false);
		btnDrukuj.setToolTipText("Drukuj grafik");
		btnDrukuj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.print();	
			}
		});
		this.toolbar.add(btnDrukuj);
		
		this.toolbar.addSeparator(new Dimension(50, 40));
		final JToggleButton tbtnSprawdzaj=new JToggleButton(new ImageIcon(EdGraf.class.getResource("/gui/res/check.png")));
		tbtnSprawdzaj.setFocusable(false);
		tbtnSprawdzaj.setToolTipText("Sprawdzaj b³êdy");
		tbtnSprawdzaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.setSprawdzajBledy(tbtnSprawdzaj.isSelected());
			}
		});
		this.btnSprawdajBledy=tbtnSprawdzaj;
		this.toolbar.add(tbtnSprawdzaj);
		
		this.toolbar.addSeparator(new Dimension(50, 40));
		JButton btnZmianaDaty=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/calendar.png")));
		btnZmianaDaty.setFocusable(false);
		btnZmianaDaty.setToolTipText("Zmiana daty grafiku");
		btnZmianaDaty.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DateDialog dd=new DateDialog(EdGraf.this,EdGraf.this.grafik.getDate());
				dd.setLocationRelativeTo(EdGraf.this);
				dd.setVisible(true);
				if(dd.getMonth()>0)
					EdGraf.this.tabela.setDataGrafiku(dd.getMonth(), dd.getYear());
				
				
			}
		});
		this.toolbar.add(btnZmianaDaty);
		
		this.toolbar.addSeparator(new Dimension(50, 40));
		JButton btnAddDyzurny=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/addDyzurny.gif")));
		btnAddDyzurny.setFocusable(false);
		btnAddDyzurny.setToolTipText("Dodaj dy¿urnego");
		btnAddDyzurny.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.addDyzurny(null);		
			}
		});
		this.toolbar.add(btnAddDyzurny);
		
		JButton btnAddOkresDyzuru=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/addOkres.png")));
		btnAddOkresDyzuru.setFocusable(false);
		btnAddOkresDyzuru.setToolTipText("Dodaj okres dy¿uru");
		btnAddOkresDyzuru.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EdGraf.this.addOkresDyzuru();	
			}
		});
		this.toolbar.add(btnAddOkresDyzuru);
		
		JButton btnRemoveOkresDyzuru=new JButton(new ImageIcon(EdGraf.class.getResource("/gui/res/delOkres.png")));
		btnRemoveOkresDyzuru.setFocusable(false);
		btnRemoveOkresDyzuru.setToolTipText("Usuñ ostatni okres dy¿uru");
		btnRemoveOkresDyzuru.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(EdGraf.this,
					    "Usun¹æ ostatni okres dy¿uru?",
					    "Ostatni okres dy¿uru",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,     //do not use a custom Icon
					    new String[]{"TAK","NIE"},  //the titles of buttons
					    "NIE"); //default button title
				if(n==JOptionPane.YES_OPTION)
					EdGraf.this.removeOkresDyzuru();	
			}
		});
		this.toolbar.add(btnRemoveOkresDyzuru);
		
		
	}
	/**
	 * Metoda wczytuj¹ca ustawienia programu z pliku <code>SETTING_FILE</code>
	 * <Br> Jeœli plik jest pusty lub niepe³ny ustawiane s¹ wartoœci domyœlne.
	 * @see #SETTINGS_FILE
	 * @see #settings
	 * @see #getDefaultProperties()
	 */
	private void loadSettings(){
		this.settings=this.getDefaultProperties();
		if(SETTINGS_FILE.exists())
		{
			try {
				FileInputStream fin=new FileInputStream(SETTINGS_FILE);
				this.settings.load(fin);
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * Dobranie wysokoœci <code>tabelaScrollPane</code> i wysokoœci okna do wysokoœci tabeli dy¿urów.<br>
	 * <li>Jeœli okno jest z maksymalizowane metoda nie robi nic</li>
	 * @param pack czy zmieniæ wysokoœæ okna
	 */
	private void resizeTabelaScrollPane(boolean pack){
		int maxHeight=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		maxHeight*=0.90;
		maxHeight-=this.northPanel.getSize().getHeight();
		if(tabelaHeight!=this.tabela.getHeight())
		{
			Dimension tabSize=this.tabela.getSize();
			this.tabelaHeight=tabSize.height;
			Dimension spSize=this.tabelaScrollPane.getSize();
			Dimension hSize=this.tabela.getTableHeader().getSize();
			Dimension newSize=new Dimension(spSize.width, tabSize.height+hSize.height+5);
			if(newSize.height>maxHeight) newSize.height=maxHeight;
			this.tabelaScrollPane.setPreferredSize(newSize);
			if(pack&&this.getExtendedState()==NORMAL)this.pack();
		}
	}
	/**
	 * Dodanie nowego dyzurnego
	 * @param s Nazwisko i Imiê dyzurnego
	 */
	public void addDyzurny(String s){
		final String str;
		if(s==null) str="Nowy dy¿urny";
		else str=s;
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				EdGraf.this.tabela.addDyzurny(str);	
			}
		});
	}
	/**
	 * Dodanie okresu dy¿uru
	 */
	public void addOkresDyzuru(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				EdGraf.this.tabela.addOkresDyzuru();
			}
		});
	}
	/**
	 * @return referencja do okna dialogowego o programie
	 */
	public AboutDialog getAboutDialog() {
		if(this.aboutDialog==null) this.aboutDialog=new AboutDialog();
		return this.aboutDialog;
	}
	/**
	 * @return edytowany grafik
	 */
	public Grafik getGrafik() {
		return this.grafik;
	}
	/**
	 * @return ustawienia programu
	 */
	public Properties getSettings() {
		return this.settings;
	}
	/**
	 * @return Plik usawieñ programu
	 */
	public File getSettingsFile() {
		return SETTINGS_FILE;
	}
	/**
	 * @return tabel grafiku
	 */
	public GrafikTable getTabela() {
		return this.tabela;
	}
	/**
	 * Utworzenie nowego grafiku
	 */
	public void newGrafik(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int n = JOptionPane.showOptionDialog(EdGraf.this,
					    "Zapisaæ zmiany przed utworzeniem nowego grafiku?",
					    "Nowy grafik",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,     //do not use a custom Icon
					    new String[]{"TAK","NIE","ANULUJ"},  //the titles of buttons
					    "TAK"); //default button title
				boolean nowy=false;
				switch(n)
				{
				case JOptionPane.NO_OPTION:
					nowy=true;break;
				case JOptionPane.YES_OPTION:
					if(EdGraf.this.save()) nowy=true;break;
				}
				if(nowy)
				{
					Grafik g=GrafikFactory.getGrafikFromProperties(EdGraf.this.settings);
					EdGraf.this.grafik=g;
					EdGraf.this.tabela.getModel().setGrafik(EdGraf.this.grafik);
					EdGraf.this.fillGrafikPanel();
					EdGraf.this.currentFile=null;
				}
			}
		});
	}
	/**
	 * Otwarcie grafiku z pliku . Metoda otwiera okno dialogowe wyboru pliku do otwarcia, 
	 * uprzednio pytaj¹c siê u¿ytkownika, czy zapisaæ jego poprzedni grafik.
	 * @see #openFileDialog
	 */
	public void open(){
		int odp=this.openFileDialog.showOpenDialog(this);
		if(odp==JFileChooser.APPROVE_OPTION)
		{
			this.currentFile=this.openFileDialog.getSelectedFile();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						Grafik g=null;
						if(new FileNameExtensionFilter("", "grf").accept(EdGraf.this.currentFile))
						{
							g=GrafikFactory.getGrafikFromEdgraf1(EdGraf.this.currentFile);
							EdGraf.this.currentFile=null;
						}
						else
						{
							FileInputStream fin=new FileInputStream(EdGraf.this.currentFile);
							ObjectInputStream in=new ObjectInputStream(fin);
							Object o=in.readObject();
							if(!(o instanceof Grafik)) throw new IOException();
							else
							{
								g=(Grafik) o;	
							}
						}
						if(g!=null)
						{
							EdGraf.this.grafik=g;
							EdGraf.this.tabela.getModel().setGrafik(EdGraf.this.grafik);
							EdGraf.this.fillGrafikPanel();
						}
						
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(EdGraf.this, "Podany plik nie istnieje", "B³¹d", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(EdGraf.this, "Nast¹pi³ b³¹d odczytu z pliku", "B³¹d", JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(EdGraf.this, "Nast¹pi³ b³¹d odczytu z pliku", "B³¹d", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
			});
		}
	}
	/**
	 * Metoda drukuj¹ca grafik
	 * @see EdGraf#grafikPrinter
	 * @see PrintableGrafik
	 */
	public void print(){
		try {
			this.grafikPrinter.print(this.grafik);
		} catch (PrinterException e) {
			JOptionPane.showMessageDialog(this, "Wyst¹pi³ b³ad wydruku", "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Metoda usuwa ostatni okres dy¿uru
	 */
	public void removeOkresDyzuru(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				EdGraf.this.tabela.removeOkresDyzuru();
			}
		});
	}
	/**
	 * Metoda zapisu grafiku do pliku.<Br>
	 * Jeœli <code>currentFile==null</code> uruchamia metodê <code>saveAs()</code>
	 * @return true jeœli zapis siê powiód³.
	 * @see #currentFile
	 * @see #saveAs()
	 */
	public boolean save(){
		if(this.currentFile==null) return this.saveAs();
		else
		{
			FileOutputStream fos;
			try {
				
				fos=new FileOutputStream(EdGraf.this.currentFile);
				ObjectOutputStream out=new ObjectOutputStream(fos);
				out.writeObject(EdGraf.this.grafik);
				out.flush();
				out.close();
				return true;
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(EdGraf.this, "Nast¹pi³ b³¹d zapisu do pliku", "B³¹d", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}
	/**
	 * Metoda wyboru lokalizacji do zapisu grafiku do pliku. Po kaceptacji wuboru nastêpuje zapis.
	 * @return true jesli zapis siê powiód³
	 * @see Grafik
	 * @see #saveFileDialog
	 * @see #save()
	 */
	public boolean saveAs(){
		int odp=this.saveFileDialog.showSaveDialog(this);
		if(odp==JFileChooser.APPROVE_OPTION) 
		{
			this.currentFile=this.saveFileDialog.getSelectedFile();
			if(!this.saveFileDialog.getFileFilter().accept(this.currentFile))
				this.currentFile=new File(this.currentFile.getPath()+".gdf");
			return this.save();
		}
		else return false;
		
	}
	/**
	 * Metoda zapisu ustawieñ programu do pliku.
	 * @see #settings
	 * @see #SETTINGS_FILE
	 * @see Properties
	 */
	public void saveSettings(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {	
					FileOutputStream fout=new FileOutputStream(EdGraf.SETTINGS_FILE);
					EdGraf.this.settings.store(fout, null);
					fout.close();	
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	/**
	 * Metoda ustawiaj¹ca now¹ datê grafiku.
	 * @param month nowy miesi¹c
	 * @param year nowy rok
	 */
	public void setDataGrafiku(final int month,final int year){
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {			
				@Override
				public void run() {
					EdGraf.this.tabela.setDataGrafiku(month, year);		
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param settings nowe ustawienia programu do ustawienia
	 */
	public void setSettings(Properties settings) {
		this.settings = settings;
	}
	/**
	 * Czy maj¹ byæ sprawdzane b³êdy grafiku
	 * @param sprawdzaj true jeœli maj¹ byc sprawdzane b³êdy
	 */
	public void setSprawdzajBledy(final boolean sprawdzaj){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				EdGraf.this.tabela.setSprawdzajBledy(sprawdzaj);
			}
		});
	}
	/**
	 * {@inheritDoc}
	 * Jeœli zmieni³ siê grafik metoda odœwie¿a pole {@link #lblMiesiacRok} i wywyo³uje metodê {@link #resizeTabelaScrollPane(boolean)}
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		
			SwingUtilities.invokeLater(new Runnable() {			
				@Override
				public void run() {
					EdGraf.this.lblMiesiacRok.setText(EdGraf.this.grafik.getMiesiacRok());
					EdGraf.this.resizeTabelaScrollPane(true);
				}
			});
	}
	/**
	 * Metoda koñcz¹ca program.
	 */
	public void zakoncz(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				int n = JOptionPane.showOptionDialog(EdGraf.this,
					    "Zapisaæ zmiany przed wyjœciem?",
					    "Wyjœcie z programu",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,     //do not use a custom Icon
					    new String[]{"TAK","NIE","ANULUJ"},  //the titles of buttons
					    "TAK"); //default button title
				boolean end=false;
				switch(n)
				{
				case JOptionPane.NO_OPTION:
					end=true;break;
				case JOptionPane.YES_OPTION:
					if(EdGraf.this.save()) end=true;break;
				}
				if(end) System.exit(0);	
			}
		});
	}
}
