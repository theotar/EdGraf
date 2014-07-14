/**
 * 
 */
package gui.tables.models;

import grafik.Grafik.Dyzur;

/**
 * Model tabeli wewnêtrznej zawieraj¹cy obiekty klasy Grafik.Dyzur
 * @author Wojciech Pierzchalski
 *
 */
public class DyzurArrayTableModel extends ArrayTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DyzurArrayTableModel() {
		super(Dyzur.class);
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.innerArray[rowIndex]=new Dyzur((Dyzur)this.innerArray[rowIndex], (Boolean)aValue);	
		this.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
}
