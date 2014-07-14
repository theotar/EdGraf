/**
 * 
 */
package gui.tables.editors;

import grafik.Grafik.Dyzur;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;


/**
 * Edytor obiektu klasy Grafik.Dyzur w JTable 
 * @author Wojciech Pierzchalski
 *
 */
public class DyzurEditor extends DefaultCellEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * CheckBox, który s³u¿y jako komponent edytora.
	 */
	private JCheckBox chkBox;
	public DyzurEditor() {
		super(new JCheckBox());
		this.chkBox=(JCheckBox) this.getComponent();
		this.chkBox.setHorizontalAlignment(JCheckBox.CENTER);
		this.chkBox.removeActionListener(this.delegate);
		delegate = new EditorDelegate() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Object getCellEditorValue() {
			
		    	return Boolean.valueOf(chkBox.isSelected());
		    }

		    public void setValue(Object value) { 
            	boolean selected = false; 
				if (value instanceof Boolean) {
				    selected = ((Boolean)value).booleanValue();
				}
				else if (value instanceof String) {
				    selected = value.equals("true");
				}
				else if(value instanceof Dyzur){
					selected=((Dyzur)value).isJest();
				}
				DyzurEditor.this.chkBox.setSelected(selected);
            }
        };  
        this.chkBox.addActionListener(delegate);
	}
}
