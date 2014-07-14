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
	 * @param row indeks rzêdu tabeli
	 * @param button przycisk do dodania
	 */
	public void addButton(int column,int row,CellButton button);
	/**
	 * Usuwa przycisk z tabeli.
	 * @param column indeks kolumny tabeli
	 * @param row indeks rzêdu tabeli
	 * @param button przycisk do usuniêcia
	 */
	public void removeButton(int column,int row,CellButton button);
	/**
	 * Ustawia jak maj¹ byc orientowane przyciski w komórce tabeli.
	 * @param orientation orientacja przycisków
	 * @see #TOP_LEFT_HORIZONTAL
	 * @see #TOP_LEFT_VERTICAL
	 * @see #TOP_RIGHT_VERTICAL
	 * @see #BOTTOM_LEFT_HORIZONTAL
	 */
	public void setOrientation(int orientation);
	/**
	 * 
	 * @return aktualna orientacja przycisków w tabeli
	 * @see #setOrientation(int)
	 */
	public int getOrientation();
	/**
	 * Ustawia aktuln¹ komórkê w tabeli
	 * @param column indeks kolumny
	 * @param row indeks rzêdu
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
