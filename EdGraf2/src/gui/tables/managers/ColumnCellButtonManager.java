/**
 * 
 */
package gui.tables.managers;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import gui.tables.buttons.CellButton;

/**
 * Manager przycisk�w dla tabeli, kt�ry organizuje przycski w kolumny.
 * @author Wojciech Pierzchalski
 *
 */
public class ColumnCellButtonManager implements CellButtonManager {
	
	/**
	 * Domy�lna wysoko�� przycisku
	 * @see CellButton
	 */
	private int buttonHeight=20;
	/**
	 * Mapa listy przycisk�w na indeks kolumny
	 */
	private Map<Integer,List<CellButton>> buttons=new HashMap<Integer,List<CellButton>>();
	/**
	 * Domy�lna d�ugo�� przycisku
	 * @see CellButton
	 */
	private int buttonWidth=20;
	/**
	 * Aktualnie wy�wietlana kolumna
	 */
	private int currentColumn=0;
	/**
	 * Aktualnie wy�wietlany rz�d
	 */
	private int currentRow=0;
	/**
	 * Orientacja przycisk�w w kom�rce
	 * @see CellButtonManager#setOrientation(int)
	 */
	private int orientation=CellButtonManager.TOP_RIGHT_VERTICAL;
	/**
	 * Tabela, w kt�rej wy�wietlane s� przyciski
	 */
	private JTable table;
	
	public ColumnCellButtonManager() {
		
	}
	public ColumnCellButtonManager(JTable table){
		this();
		this.table=table;
	}
	/**
	 * Zwraca list� przycisk�w dla danej kolumny, je�eli nie ma takiej listy tworzy now� pust�.
	 * @param columnIndex indeks kolumny
	 * @return lista przycisk�w
	 */
	private List<CellButton> getColumnButtons(int columnIndex){
		if(this.buttons.get(columnIndex)==null)
			this.buttons.put(columnIndex, new ArrayList<CellButton>());
		return this.buttons.get(columnIndex);
	}
	/**
	 * Ukrywa przyciski z danej kolumny
	 * @param column indeks kolumny
	 */
	private void hideButtons(int column){
		List<CellButton> list=this.buttons.get(column);
		if(list!=null)
		{
			for(CellButton button:list) button.setVisible(false);
		}
	}
	/**
	 * Pokazuje przycisk w danej kom�rce
	 * @param column indeks przycisku
	 * @param row indeks rz�du
	 * @param button przycisk do pokazania
	 */
	private void showButton(int column,int row,CellButton button){
		List<CellButton> lista=this.buttons.get(column);
		if(lista!=null)
		{
			int idx=lista.indexOf(button);
			if(idx!=-1)
			{
				Rectangle rect=this.table.getCellRect(row, column, false);
				switch(this.orientation)
				{
					case CellButtonManager.TOP_LEFT_HORIZONTAL:
						rect.x+=this.buttonWidth*idx;
						break;
					case CellButtonManager.TOP_LEFT_VERTICAL:
						rect.y+=this.buttonHeight*idx;
						break;
					case CellButtonManager.BOTTOM_LEFT_HORIZONTAL:
						rect.y=rect.y+rect.height-this.buttonHeight;
						rect.x+=this.buttonWidth*idx;
						break;
					case CellButtonManager.TOP_RIGHT_VERTICAL:
						rect.x=rect.x+rect.width-this.buttonWidth;
						rect.y+=this.buttonHeight*idx;
						break;
				}
				rect.height=this.buttonHeight;
				rect.width=this.buttonWidth;
				button.setCell(column, row);
				button.setBounds(rect);
				button.setVisible(true);
			}
		}
	}
	@Override
	public void addButton(int column, int row, CellButton button) {
		button.setFocusable(false);
		button.setMaximumSize(new Dimension(this.buttonWidth,this.buttonHeight));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBounds(0, 0, this.buttonWidth, this.buttonHeight);
		button.setVisible(false);
		this.getColumnButtons(column).add(button);
		this.table.add(button);
		
	}
	
	@Override
	public int getOrientation() {
		return this.orientation;
	}

	public void hideButtons(){
		this.hideButtons(this.currentColumn);
			
	}
	
	@Override
	public void removeButton(int column, int row, CellButton button) {
		List<CellButton> list=this.buttons.get(column);
		if(list!=null)
		{
			list.remove(button);
			if(list.size()==0) this.buttons.remove(list);
		}
		this.table.remove(button);
		
	}
	@Override
	public void setCell(int column, int row) {
		if(column!=this.currentColumn)
		{
			this.hideButtons(this.currentColumn);
			this.showButtons(column, row);
		}
		else if(row!=this.currentRow)
		{
			this.showButtons(column, row);
		}
		this.currentColumn=column;
		this.currentRow=row;
		
	}
	@Override
	public void setOrientation(int orientation) {
		this.orientation=orientation;
		
	}
	@Override
	public void showButtons(){
		this.showButtons(this.currentColumn, this.currentRow);
	}
	/**
	 * Pokazuje przyciski w danej kom�rce
	 * @param column indeks kolumny
	 * @param row indeks rz�du
	 */
	public void showButtons(int column, int row) {
		List<CellButton> list=this.buttons.get(column);
		if(list!=null)
		{
			for(CellButton button:list)
				this.showButton(column, row, button);
		}
	}
}
