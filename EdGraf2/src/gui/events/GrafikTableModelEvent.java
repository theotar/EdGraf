/**
 * 
 */
package gui.events;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

/**
 * Event zmiany modelu tabeli generowany przez ArrayTableModel/
 * @author Wojciech Pierzchalski
 *
 */
public class GrafikTableModelEvent extends TableModelEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * indeks kolumny tabeli zewn�trznej
	 */
	private int outerColumn=-1;
	/**
	 * indeks rz�du tabeli zewn�trznej
	 */
	private int outerRow=-1;
	/**
	 * Nowa warto�� kom�rki
	 */
	private Object value=null;
	
		
	/**
	 * 
	 * @param source model generuj�cy event
	 * @param firstRow pierwszy zmieniony rz�d modelu
	 * @param lastRow ostatni zmieniony rz�d modelu
	 * @param column kolumna modelu
	 * @param outerRow zewn�trzny rz�d modelu
	 * @param outerColumn zewn�trzna kolumna modelu
	 * @param value nowa warto�� kom�rki
	 */
	public GrafikTableModelEvent(TableModel source, int firstRow, int lastRow,
			int column,int outerRow, int outerColumn, Object value) {
		super(source, firstRow, lastRow, column,TableModelEvent.UPDATE);
		this.outerColumn=outerColumn;
		this.outerRow=outerRow;
		this.value=value;
	}


	/**
	 * @return kolumna tabeli zewn�trznej
	 */
	public int getOuterColumn() {
		return this.outerColumn;
	}


	/**
	 * @return rz�d tabeli zewn�trznej
	 */
	public int getOuterRow() {
		return this.outerRow;
	}


	/**
	 * @return nowa warto��
	 */
	public Object getValue() {
		return this.value;
	}


	/**
	 * @param outerColumn kolumna tabeli zewn�trznej do ustawienia
	 */
	public void setOuterColumn(int outerColumn) {
		this.outerColumn = outerColumn;
	}


	/**
	 * @param outerRow rz�d tabeli zewn�trznej do ustawienia
	 */
	public void setOuterRow(int outerRow) {
		this.outerRow = outerRow;
	}


	/**
	 * @param value warto�c do ustawienia
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

	

}
