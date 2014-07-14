/**
 * 
 */
package gui.tables.managers;

import gui.tables.buttons.CellButton;

/**
 * @author Wojciech Pierzchalski
 *
 */
public interface CellButtonManager {
	public static final int TOP_LEFT_HORIZONTAL=0;
	public static final int TOP_LEFT_VERTICAL=1;
	public static final int TOP_RIGHT_VERTICAL=2;
	public static final int BOTTOM_LEFT_HORIZONTAL=3;
	
	/**
	 * Dodaje przycisk do tabeli.
	 * @param column indeks kolumny tabeli
	 * @param row indeks rz�du tabeli
	 * @param button przycisk do dodania
	 */
	public void addButton(int column,int row,CellButton button);
	/**
	 * Usuwa przycisk z tabeli.
	 * @param column indeks kolumny tabeli
	 * @param row indeks rz�du tabeli
	 * @param button przycisk do usuni�cia
	 */
	public void removeButton(int column,int row,CellButton button);
	/**
	 * Ustawia jak maj� byc orientowane przyciski w kom�rce tabeli.
	 * @param orientation orientacja przycisk�w
	 * @see #TOP_LEFT_HORIZONTAL
	 * @see #TOP_LEFT_VERTICAL
	 * @see #TOP_RIGHT_VERTICAL
	 * @see #BOTTOM_LEFT_HORIZONTAL
	 */
	public void setOrientation(int orientation);
	/**
	 * 
	 * @return aktualna orientacja przycisk�w w tabeli
	 * @see #setOrientation(int)
	 */
	public int getOrientation();
	/**
	 * Ustawia aktuln� kom�rk� w tabeli
	 * @param column indeks kolumny
	 * @param row indeks rz�du
	 */
	public void setCell(int column,int row);
	/**
	 * Ukrywa przyciski
	 */
	public void hideButtons();
	/**
	 * Pokazuje przyciski
	 */
	public void showButtons();
}
