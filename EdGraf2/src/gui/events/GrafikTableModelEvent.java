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
	 * indeks kolumny tabeli zewnêtrznej
	 */
	private int outerColumn=-1;
	/**
	 * indeks rzêdu tabeli zewnêtrznej
	 */
	private int outerRow=-1;
	/**
	 * Nowa wartoœæ komórki
	 */
	private Object value=null;
	
		
	/**
	 * 
	 * @param source model generuj¹cy event
	 * @param firstRow pierwszy zmieniony rz¹d modelu
	 * @param lastRow ostatni zmieniony rz¹d modelu
	 * @param column kolumna modelu
	 * @param outerRow zewnêtrzny rz¹d modelu
	 * @param outerColumn zewnêtrzna kolumna modelu
	 * @param value nowa wartoœæ komórki
	 */
	public GrafikTableModelEvent(TableModel source, int firstRow, int lastRow,
			int column,int outerRow, int outerColumn, Object value) {
		super(source, firstRow, lastRow, column,TableModelEvent.UPDATE);
		this.outerColumn=outerColumn;
		this.outerRow=outerRow;
		this.value=value;
	}


	/**
	 * @return kolumna tabeli zewnêtrznej
	 */
	public int getOuterColumn() {
		return this.outerColumn;
	}


	/**
	 * @return rz¹d tabeli zewnêtrznej
	 */
	public int getOuterRow() {
		return this.outerRow;
	}


	/**
	 * @return nowa wartoœæ
	 */
	public Object getValue() {
		return this.value;
	}


	/**
	 * @param outerColumn kolumna tabeli zewnêtrznej do ustawienia
	 */
	public void setOuterColumn(int outerColumn) {
		this.outerColumn = outerColumn;
	}


	/**
	 * @param outerRow rz¹d tabeli zewnêtrznej do ustawienia
	 */
	public void setOuterRow(int outerRow) {
		this.outerRow = outerRow;
	}


	/**
	 * @param value wartoœc do ustawienia
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

	

}
