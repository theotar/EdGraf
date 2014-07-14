/**
 * 
 */
package gui.tables.renderers;

import gui.tables.models.ArrayTableModel;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer tablicy obiektów w Tabeli JTable
 * @author Wojciech Pierzchalski
 *
 */
public class ArrayRenderer extends JTable implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Klasa obiektów renderowanych
	 */
	private Class<?> innerClass;
	
	public ArrayRenderer(Class<?> innerClass) {
		super(new ArrayTableModel(innerClass));
		this.innerClass=innerClass;
		DefaultTableCellRenderer dr=(DefaultTableCellRenderer.UIResource) this.getDefaultRenderer(this.innerClass);
		if(dr!=null) dr.setHorizontalAlignment(JLabel.CENTER);
	}
	/**
	 * @return klasa obiektów renderowanych
	 */
	public Class<?> getInnerClass() {
		return this.innerClass;
	}
	@Override
	public ArrayTableModel getModel() {
		return (ArrayTableModel) super.getModel();
	}
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		this.getModel().setInnerArray((Object[]) value);
		return this;
	}
	/**
	 * @param innerClass klasa do renderowania
	 */
	public void setInnerClass(Class<?> innerClass) {
		this.innerClass = innerClass;
	}
}
