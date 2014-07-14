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
	 * rz¹d przycisku w tabeli
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
	 * @return indeks rzêdu w tabeli
	 */
	public int getRow() {
		return this.row;
	}
	/**
	 * Ustawia indeksy kolumny i rzêdu w tabeli
	 * @param column indeks kolumny
	 * @param row indeks rzêdu
	 */
	public void setCell(int column,int row){
		this.row=row;
		this.column=column;
	}
	

}
