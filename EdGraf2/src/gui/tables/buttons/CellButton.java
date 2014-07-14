/**
 * 
 */
package gui.tables.buttons;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Wojciech Pierzchalski
 *
 */
public class CellButton extends JButton {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * kolumna przycisku w tabeli
	 */
	private int column=0;
	/**
	 * rz�d przycisku w tabeli
	 */
	private int row=0;
	
	public CellButton(Icon icon) {
		super(icon);
	}
	public CellButton(String text) {
		super(text);
	}
	/**
	 * @return indeks kolumny w tabeli
	 */
	public int getColumn() {
		return this.column;
	}
	/**
	 * @return indeks rz�du w tabeli
	 */
	public int getRow() {
		return this.row;
	}
	/**
	 * Ustawia indeksy kolumny i rz�du w tabeli
	 * @param column indeks kolumny
	 * @param row indeks rz�du
	 */
	public void setCell(int column,int row){
		this.row=row;
		this.column=column;
	}
	

}
