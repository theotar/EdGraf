/**
 * 
 */
package gui.tables;

import grafik.Grafik;
import grafik.Grafik.TypDnia;
import grafik.GrafikFactory;
import gui.EdGraf;
import gui.events.GrafikTableModelEvent;
import gui.tables.buttons.CellButton;
import gui.tables.editors.ArrayEditor;
import gui.tables.editors.DyzurArrayEditor;
import gui.tables.managers.CellButtonManager;
import gui.tables.managers.ColumnCellButtonManager;
import gui.tables.models.GrafikTableModel;
import gui.tables.renderers.ArrayRenderer;
import gui.tables.renderers.DyzurArrayRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

/**
 * Tabela reprezentuj�ca grafik dyzur�w.
 * @author Wojciech Pierzchalski
 *
 */
public class GrafikTable extends JTable {
	/**
	 * Indeks kolumny dyzurnych
	 */
	private static final int KOLUMNA_DYZURNY=1;
	/**
	 * Indeks kolumny ilo�ci godzin
	 */
	private static final int KOLUMNA_ILG=3;
	/**
	 * Indeks kolumny "LP"
	 */
	private static final int KOLUMNA_LP=0;
	/**
	 * Indeks kolumny "dyzur w godz."
	 */
	private static final int KOLUMNA_PRZEDZIALY=2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Menadzer przycisk�w
	 * @see CellButtonManager
	 */
	private CellButtonManager cellButtonManager;
	/**
	 * Przycisk usuwania dyzurnego
	 */
	private CellButton deleteButton;
	/**
	 * Przycisk przesuni�cia dyzurnego na d�
	 */
	private CellButton downButton;
	/**
	 * Rednerer tablicy dy�ur�w
	 */
	private DyzurArrayRenderer dyzurRenderer;
	/**
	 * Kolor b��dnego dy�uru
	 */
	private Color errorColor=Color.YELLOW;
	/**
	 * Edytor ilo�ci godzin dyzuru
	 */
	private ArrayEditor ilgEditor;
	/**
	 * Renderer ilo�ci godzin
	 */
	private ArrayRenderer ilgRenderer;
	/**
	 * Wysoko�� rz�du dyzuru
	 */
	private int innnerRowHeight=20;
	/**
	 * Kolory dni tygodnia
	 */
	private Map<Grafik.TypDnia, Color> kolory;
	/**
	 * Edytor przedzia��w czasu dyzuru
	 */
	private ArrayEditor przedzialEditor;
	/**
	 * Renderer przedzia��w czasu dyzuru
	 */
	private ArrayRenderer przedzialRenderer;
	/**
	 * Przycisk przesuni�cia dy�urnego w g�r�.
	 */
	private CellButton upButton;

	{
		kolory=new HashMap<Grafik.TypDnia, Color>();
		kolory.put(TypDnia.POWSZEDNI, Color.WHITE);
		kolory.put(TypDnia.SOBOTA, new Color(0, 255, 255));
		kolory.put(TypDnia.NIEDZIELA, Color.RED);
		kolory.put(TypDnia.DZIEN_ENERGETYKA, new Color(0, 0x80, 0));
		kolory.put(TypDnia.SWIETO, new Color(0x8B, 0, 0));
	}
	
	public GrafikTable() {
		this(new GrafikTableModel(GrafikFactory.getGrafikNextMonth()));
				
	}
	public GrafikTable(Grafik grafik){
		this(new GrafikTableModel(grafik));
	}
	
	public GrafikTable(TableModel dm) {
		super(dm);
		this.errorColor=Color.YELLOW;
		this.setRowSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setFocusable(false);
		
		this.przedzialRenderer=new ArrayRenderer(String.class);
		this.przedzialRenderer.setRowHeight(this.innnerRowHeight);
		this.przedzialEditor=new ArrayEditor(String.class);
		this.przedzialEditor.setRowHeight(innnerRowHeight);
		this.przedzialEditor.addTableModelListener(this);
		this.ilgRenderer=new ArrayRenderer(Double.class);
		this.ilgRenderer.setRowHeight(this.innnerRowHeight);
		this.ilgEditor=new ArrayEditor(Double.class);
		this.ilgEditor.setRowHeight(innnerRowHeight);
		this.ilgEditor.addTableModelListener(this);
		this.dyzurRenderer=new DyzurArrayRenderer(this);
		
		this.setDefaultRenderer(String[].class, this.przedzialRenderer);
		this.setDefaultEditor(String[].class, this.przedzialEditor);
		
		this.setDefaultRenderer(Double[].class, this.ilgRenderer);
		this.setDefaultEditor(Double[].class, this.ilgEditor);
		
		this.setDefaultRenderer(Grafik.Dyzur[].class, this.dyzurRenderer);
		

		DefaultTableCellRenderer.UIResource dr=(DefaultTableCellRenderer.UIResource) this.getDefaultRenderer(Double.class);
		dr.setHorizontalAlignment(JLabel.CENTER);
		
		dr=(DefaultTableCellRenderer.UIResource) this.getDefaultRenderer(String.class);
	
		this.recalculateRowHeight();
		
		this.setGridColor(Color.black);
		this.addMouseMotionListener(new MouseAdapter(){
			@Override
			public void mouseMoved(MouseEvent e) {
				
				int col=GrafikTable.this.columnAtPoint(e.getPoint());
				int row=GrafikTable.this.rowAtPoint(e.getPoint());
				int eCol=GrafikTable.this.editingColumn;
				if(eCol!=col)
					GrafikTable.this.cellButtonManager.setCell(col, row);
				
			}
		});
		this.setErrorColor(this.errorColor);
		this.initButtons();
	}
	/**
	 * Metoda inicjuj�ca przyciski tabeli.
	 * @see #cellButtonManager
	 * @see CellButton
	 */
	private void initButtons(){
		this.setLayout(null);
		this.cellButtonManager=new ColumnCellButtonManager(this);
		this.deleteButton=new CellButton("X");
		this.deleteButton.setBackground(Color.RED);
		this.deleteButton.setForeground(Color.WHITE);
		this.deleteButton.setToolTipText("Usu� dy�urnego");
		this.deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {	
					@Override
					public void run() {
						int row=GrafikTable.this.deleteButton.getRow();
						int n = JOptionPane.showOptionDialog(GrafikTable.this,
							    "Usun�� dy�urnego: "+
							    GrafikTable.this.getModel().getValueAt(row, GrafikTable.KOLUMNA_DYZURNY)
							    +"?",
							    "Potwierdzenie usuni�cia",
							    JOptionPane.YES_NO_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null,     //do not use a custom Icon
							    new String[]{"TAK","NIE"},  //the titles of buttons
							    "NIE"); //default button title
						if(n==JOptionPane.YES_OPTION)
						{
							GrafikTable.this.removeDyzurny(row);
						}
					}
				});
				
			}
		});
		this.cellButtonManager.addButton(KOLUMNA_DYZURNY, 0,this.deleteButton);
		
		this.upButton=new CellButton(new ImageIcon(GrafikTable.class.getResource("/gui/tables/res/arrowUp.png")));
		this.upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						int row=GrafikTable.this.upButton.getRow();
						GrafikTable.this.swapDyzurny(row, row-1);	
					}
				});	
			}
		});
		this.cellButtonManager.addButton(KOLUMNA_DYZURNY, 0,this.upButton);
		
		this.downButton=new CellButton(new ImageIcon(GrafikTable.class.getResource("/gui/tables/res/arrowDown.png")));
		this.downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int row=GrafikTable.this.downButton.getRow();
						GrafikTable.this.swapDyzurny(row, row+1);
					}
				});	
			}
		});
		this.cellButtonManager.addButton(KOLUMNA_DYZURNY, 0,this.downButton);
		
	}
	/**
	 * Metoda przeliczaj�ca wielko�� rz�du w tabeli na podstawie ilo�ci okres�w dyzur�w w grafiku.
	 * @see Grafik#ileOkresowDyzurow()
	 */
	private void recalculateRowHeight(){
		this.setRowHeight(innnerRowHeight*this.getModel().ileOkresowDyzurow());
	}
	/**
	 * Dodanie dy�urnego do grafiku
	 * @param dyzurny nowy dyzurny
	 */
	public void addDyzurny(String dyzurny){
		this.getModel().addDyzurny(dyzurny);
	}
	/**
	 * Dodanie okresu dyzuru do grafiku
	 */
	public void addOkresDyzuru(){
		this.getModel().addOkresDyzuru();
		this.recalculateRowHeight();
	}
	@Override
	public void createDefaultColumnsFromModel() {
		super.createDefaultColumnsFromModel();
		if(this.innnerRowHeight==0) this.innnerRowHeight=20;
		this.getColumnModel().getColumn(KOLUMNA_LP).setMinWidth(20);
		this.getColumnModel().getColumn(KOLUMNA_LP).setMaxWidth(20);
		this.getColumnModel().getColumn(KOLUMNA_DYZURNY).setMinWidth(150);
		this.getColumnModel().getColumn(KOLUMNA_PRZEDZIALY).setMinWidth(80);
		this.getColumnModel().getColumn(KOLUMNA_PRZEDZIALY).setMaxWidth(80);
		this.getColumnModel().getColumn(KOLUMNA_ILG).setMinWidth(30);
		this.getColumnModel().getColumn(KOLUMNA_ILG).setMaxWidth(30);
		
		this.getColumnModel().getColumn(this.getColumnCount()-1).setMinWidth(50);
		this.getColumnModel().getColumn(this.getColumnCount()-1).setMaxWidth(50);
		
		for(int i=GrafikTable.KOLUMNA_ILG+1;i<this.getColumnCount()-1;i++)
		{
			this.getColumnModel().getColumn(i).setCellEditor(new DyzurArrayEditor(this));

				this.getColumnModel().getColumn(i).setMinWidth(this.getInnnerRowHeight());
				this.getColumnModel().getColumn(i).setMaxWidth(this.getInnnerRowHeight());
			
		}
		
	}
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		this.cellButtonManager.hideButtons();
		return super.getCellEditor(row, column);
	}
	
	/**
	 * @return kolor b��dnego dy�uru
	 */
	public Color getErrorColor() {
		return this.errorColor;
	}
	/**
	 * @return wysoko�� rz�du dy�uru
	 */
	public int getInnnerRowHeight() {
		return this.innnerRowHeight;
	}
	/**
	 * @return kolory dni tygodnia
	 */
	public Map<Grafik.TypDnia, Color> getKolory() {
		return this.kolory;
	}
	@Override
	public GrafikTableModel getModel(){
		return (GrafikTableModel) super.getModel();
	}
	/**
	 * Usuwa dy�urnego
	 * @param idx indeks dyzurnego do usuni�cia
	 */
	public void removeDyzurny(int idx){
		this.getModel().removeDyzurny(idx);
	}
	/**
	 * Usuwa ostatni okres dy�uru
	 */
	public void removeOkresDyzuru(){
		this.getModel().removeOkresDyzuru();
		this.recalculateRowHeight();
	}
	/**
	 * Ustawia now� date grafiku
	 * @param month nowy miesi�c
	 * @param year nowy rok
	 */
	public void setDataGrafiku(int month, int year){
		this.getModel().ustawDate(month, year);
	}
	/**
	 * @param errorColor nowy kolor b��dnego dy�uru
	 */
	public void setErrorColor(Color errorColor) {
		this.errorColor = errorColor;
		this.dyzurRenderer.updateErrorColor();
		for(int i=GrafikTable.KOLUMNA_ILG+1;i<this.getColumnCount()-1;i++)
		{
			((DyzurArrayEditor)this.getColumnModel().getColumn(i).getCellEditor()).updateErrorColor();
	
		}
	}
	
	/**
	 * @param innnerRowHeight nowa wysoko�� rz�du dyzuru
	 */
	public void setInnnerRowHeight(int innnerRowHeight) {
		this.innnerRowHeight = innnerRowHeight;
	}
	/**
	 * @param kolory nowe kolory dni tygodnia
	 */
	public void setKolory(Map<Grafik.TypDnia, Color> kolory) {
		this.kolory = kolory;
	}
	@Override
	public void setModel(TableModel dataModel) {
		if(!(dataModel instanceof GrafikTableModel))
			throw new IllegalArgumentException("dataModel musi by� klas� dziedzicz�c� po GrafikTableModel");
		super.setModel(dataModel);
	}
	/**
	 * Ustawia,czy grafik ma sprawdza� b��dy.
	 * @param sprawdzaj czy sprawdza� b��dy
	 * @see Grafik#setSprawdzajBledy(boolean)
	 */
	public void setSprawdzajBledy(boolean sprawdzaj){
		this.getModel().setSprawdzajBledy(sprawdzaj);
	}
	/**
	 * Zamienia dyzurnych miejscami
	 * @see GrafikTableModel#swapDyzurny(int, int)
	 */
	public void swapDyzurny(int idxFrom,int idxTo){
		this.getModel().swapDyzurny(idxFrom, idxTo);
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		if(e instanceof GrafikTableModelEvent)
		{
			GrafikTableModelEvent ge=(GrafikTableModelEvent) e;
			this.getModel().setValueAt(ge, ge.getOuterRow(), ge.getOuterColumn());
		}
		else super.tableChanged(e);
	}
	/**
	 * Metoda od�wie�aj�ca kolory tabeli na podstawie ustawie� EdGraf.
	 * @param settings ustawienia EdGraf
	 * @see EdGraf#getSettings()
	 */
	public void updateColor(Properties settings){
		this.setErrorColor(new Color(Integer.parseInt(settings.getProperty("color.error", String.valueOf(Color.YELLOW.getRGB())))));
		this.kolory.put(TypDnia.NIEDZIELA, new Color(Integer.valueOf(settings.getProperty("color.TypDnia.NIEDZIELA",
				String.valueOf(Color.RED.getRGB())))));
		this.kolory.put(TypDnia.SOBOTA, new Color(Integer.valueOf(settings.getProperty("color.TypDnia.SOBOTA",
				String.valueOf(new Color(0, 255, 255).getRGB())))));
		this.kolory.put(TypDnia.DZIEN_ENERGETYKA, new Color(Integer.valueOf(settings.getProperty("color.TypDnia.DZIEN_ENERGETYKA",
				String.valueOf(new Color(0, 0x80, 0).getRGB())))));
		this.kolory.put(TypDnia.SWIETO, new Color(Integer.valueOf(settings.getProperty("color.TypDnia.SWIETO",
				String.valueOf(new Color(0x8B, 0, 0).getRGB())))));
		this.kolory.put(TypDnia.POWSZEDNI, new Color(Integer.valueOf(settings.getProperty("color.TypDnia.POWSZEDNI",
				String.valueOf(Color.WHITE.getRGB())))));	
	}
}
