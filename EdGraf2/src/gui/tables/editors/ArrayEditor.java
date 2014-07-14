/**
 * 
 */
package gui.tables.editors;

import gui.tables.models.ArrayTableModel;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

/**
 * Edytor tablic w JTable
 * @author Wojciech Pierzchalski
 *
 */
public class ArrayEditor extends AbstractCellEditor implements TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Klasa obiektów, jakie edytuje edytor.
	 */
	private Class<?> innerClass;
	/**
	 * Tabela, która jest de facto edytorem.
	 */
	protected JTable table;
	
	public ArrayEditor(Class<?> innerClass) {
		this.table=new JTable(new ArrayTableModel(innerClass));
		this.innerClass=innerClass;
		DefaultTableCellRenderer dr=(DefaultTableCellRenderer.UIResource) this.table.getDefaultRenderer(this.innerClass);
		if(dr!=null) dr.setHorizontalAlignment(JLabel.CENTER);
	}
	/**
	 * Delegowana metoda z <code>table</code> do dodania <code>TableModelListener</code>.
	 * @param l TableModelListener
	 * @see #table
	 * @see TableModelListener
	 */
	public void addTableModelListener(TableModelListener l){
		this.table.getModel().addTableModelListener(l);
	}
	
	@Override
	public Object getCellEditorValue() {
		return ((ArrayTableModel)this.table.getModel()).getInnerArray();
	}
	/**
	 * @return klasa, która edytuje edytor
	 */
	public Class<?> getInnerClass() {
		return this.innerClass;
	}
	/**
	 * @return tabela edytora.
	 */
	public JTable getTable() {
		return this.table;
	}
	@Override
	public Component getTableCellEditorComponent(JTable table,
			Object value, boolean isSelected, int row, int column) {
		ArrayTableModel atm=(ArrayTableModel) this.table.getModel();
		atm.setInnerArray((Object[]) value);
		atm.setOuterColumn(column);
		atm.setOuterRow(row);
		return this.table;
	}
	/**
	 * @param innerClass nowa klasa, któr¹ ma edytowaæ edytor
	 */
	public void setInnerClass(Class<?> innerClass) {
		this.innerClass = innerClass;
	}
	/**
	 * Ustawia now¹ wysokoœæ rzêdu w tabeli edytora
	 * @param rowHeight nowa wysokoœæ rzêdu
	 */
	public void setRowHeight(int rowHeight) {
		this.table.setRowHeight(rowHeight);
	}
	/**
	 * @param table nowa tabela edytora
	 */
	public void setTable(JTable table) {
		this.table = table;
	}
}
