/**
 * 
 */
package gui.tables.models;

import gui.events.GrafikTableModelEvent;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabeli wewn�trznej w JTable u�ywany przez ArrayRenderer i ArrayEditor
 * @author Wojciech Pierzchalski
 */
public class  ArrayTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Kolumna tabeli zewn�trznej
	 */
	private int outerColumn=-1;
	/**
	 * Rz�d tabeli zewn�trznej
	 */
	private int outerRow=-1;
	/**
	 * Tablica obiekt�w tabeli
	 */
	protected Object[] innerArray;
	/**
	 * Klasa obiekt�w przechowywanych w modelu
	 */
	protected Class<?> innerClass;
			
	public ArrayTableModel(Class<?> innerClass) {
		super();
		this.innerClass = innerClass;
	}

	@Override
	public void fireTableCellUpdated(int row, int column) {
		fireTableChanged(new GrafikTableModelEvent(this, row, row, column,
				this.outerRow,this.outerColumn,this.getValueAt(row, column)));
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.innerClass;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return null;
	}

	/**
	 * @return tablic� obiekt�w modelu
	 * @see ArrayTableModel#innerArray
	 */
	public Object[] getInnerArray() {
		return this.innerArray;
	}

	/**
	 * @return indeks kolumny w tabeli zewn�trznej
	 */
	public int getOuterColumn() {
		return this.outerColumn;
	}

	/**
	 * @return indeks rz�du w tabeli zewn�trznej
	 */
	public int getOuterRow() {
		return this.outerRow;
	}
	@Override
	public int getRowCount() {
		return this.innerArray.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.innerArray[rowIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	/**
	 * @param innerArray tablica obiekt�w do ustawienia
	 * @see #innerArray
	 */
	public void setInnerArray(Object[] innerArray) {
		this.innerArray = innerArray;
	}

	/**
	 * @param outerColumn indeks kolumny w tabeli zewn�trznej do ustawienia
	 */
	public void setOuterColumn(int outerColumn) {
		this.outerColumn = outerColumn;
	}

	/**
	 * @param outerRow indeks rz�du w tabeli zewn�trznej do ustawienia
	 */
	public void setOuterRow(int outerRow) {
		this.outerRow = outerRow;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			this.innerArray[rowIndex]=aValue;
			this.fireTableCellUpdated(rowIndex, columnIndex);	
	}
}
