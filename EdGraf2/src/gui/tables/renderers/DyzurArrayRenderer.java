package gui.tables.renderers;

import grafik.Grafik.Dyzur;
import gui.tables.GrafikTable;
import gui.tables.models.DyzurArrayTableModel;
import gui.tables.models.GrafikTableModel;
import java.awt.Component;
import javax.swing.JTable;
/**
 * Renderer tablicy dyzurów  w tabeli JTable
 * @author Wojciech Pierzchalski
 *
 */
public class DyzurArrayRenderer extends ArrayRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Renderer dyzuru
	 */
	private DyzurRenderer dyzurRenderer;
	/**
	 * Tabela, w której zainstlowany jest renderer
	 */
	private GrafikTable parentTable;
	
	public DyzurArrayRenderer(GrafikTable grafikTable) {
		super(Dyzur.class);
		this.parentTable=grafikTable;
		this.setRowHeight(this.parentTable.getInnnerRowHeight());
		this.setModel(new DyzurArrayTableModel());
		this.dyzurRenderer=new DyzurRenderer(grafikTable.getErrorColor());
		this.setDefaultRenderer(Dyzur.class, this.dyzurRenderer);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {	
		this.setBackground(column);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	/**
	 * Ustawia t³o tabeli, na podstawie numery kolumny w tabeli rodzicu 
	 * @param column indeks kolumnt tabeli rodzica
	 */
	public void setBackground(int column) {
		super.setBackground(this.parentTable.getKolory().get(this.parentTable.getModel().getValueAt(GrafikTableModel.DZIEN_TYGODNIA, column)));
	}
	/**
	 * Odœwie¿a kolor b³edy w renderzerze dy¿uru
	 * @see GrafikTable#getErrorColor()
	 * @see DyzurRenderer#setErrorColor(java.awt.Color)
	 */
	public void updateErrorColor(){
		this.dyzurRenderer.setErrorColor(this.parentTable.getErrorColor());
	}
}
