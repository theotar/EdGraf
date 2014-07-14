package gui.tables.editors;

import java.awt.Component;
import javax.swing.JTable;
import grafik.Grafik.Dyzur;
import gui.tables.GrafikTable;
import gui.tables.models.DyzurArrayTableModel;
import gui.tables.models.GrafikTableModel;
import gui.tables.renderers.DyzurRenderer;

/**
 * Edytor Dyzur[] w GrafikTable
 * @author Wojciech Pierzchalski
 */
public class DyzurArrayEditor extends ArrayEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Renderer dla obiektu Dyzuru
	 * @see grafik.Grafik.Dyzur
	 */
	private DyzurRenderer dyzurRenderer;
	/**
	 * Tabela rodzic edytora
	 */
	private GrafikTable parentTable;
	
	public DyzurArrayEditor(GrafikTable grafikTable){
		super(DyzurArrayEditor.class);
		this.parentTable=grafikTable;
		this.table.setRowHeight(this.parentTable.getInnnerRowHeight());
		this.table.setModel(new DyzurArrayTableModel());
		this.dyzurRenderer=new DyzurRenderer(this.parentTable.getErrorColor());
		this.table.setDefaultRenderer(Dyzur.class, this.dyzurRenderer);
		this.table.setDefaultEditor(Dyzur.class, new DyzurEditor());
		this.table.getModel().addTableModelListener(this.parentTable);
	}
	@Override
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		
		this.setBackground(column);
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
	/**
	 * Ustawia kolor edytora na podstawie kolumny tabeli rodzica
	 * @param column indeks kolumny tabeli rodzica
	 * @see GrafikTable#getKolory()
	 * @see grafik.Grafik.TypDnia
	 */
	public void setBackground(int column) {
		this.table.setBackground(this.parentTable.getKolory().get(this.parentTable.getModel().getValueAt(GrafikTableModel.DZIEN_TYGODNIA, column)));
	}
	/**
	 * Odœwie¿a Kolor b³edu w rendererze dy¿uru.
	 * @see DyzurRenderer#setErrorColor(java.awt.Color)
	 * @see grafik.Grafik.TypBledu
	 */
	public void updateErrorColor(){
		this.dyzurRenderer.setErrorColor(this.parentTable.getErrorColor());
	}
}
