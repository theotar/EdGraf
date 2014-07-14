/**
 * 
 */
package gui.tables.renderers;

import grafik.Grafik.Dyzur;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

/**
 * @author Wojciech Pierzchalski
 *
 */
public class DyzurRenderer extends JCheckBox implements TableCellRenderer, UIResource
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Domyslna ikona CheckBox
	 */
	private Icon defIcon;
	/**
	 * Domyslny kolor b³edu dyzuru
	 */
    private Color errorColor=Color.YELLOW;
    /**
     * Ikona mozliwego b³edu
     */
    private Icon mozliwyBladIcon;

	public DyzurRenderer() {
	    super();
	    this.setHorizontalAlignment(JLabel.CENTER);
        this.setBorderPainted(true);
        this.setBorder( new EmptyBorder(1, 1, 1, 1));
        
        this.defIcon=this.getIcon();
        this.mozliwyBladIcon=new Icon() {
			@Override
			public int getIconHeight() {
				return 0;
			}
			@Override
			public int getIconWidth() {
				return 0;
			}
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
			}
		};   
	}
	public DyzurRenderer(Color errColor){
		this();
		this.errorColor=errColor;
	}

	/**
	 * @return domysla ikona Checkbox
	 */
	public Icon getDefIcon() {
		return this.defIcon;
	}
	/**
	 * @return kolor b³edu dyzuru
	 */
	public Color getErrorColor() {
		return this.errorColor;
	}
	/**
	 * @return ikona mozliwego b³edu dyzuru
	 */
	public Icon getMozliwyBladIcon() {
		return this.mozliwyBladIcon;
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
					       boolean isSelected, boolean hasFocus, int row, int column) {
    
        Dyzur dyzur=(Dyzur) value;
    	this.setSelected(dyzur.isJest());
    	this.setBackground(table.getBackground());    
        
        if(dyzur.isJest())
        {
        	this.setIcon(this.defIcon);
        	if(dyzur.bledyIsEmpty())
        	{
        		this.setToolTipText("Brak b³êdów");
        	}
        	else
        	{
        		this.setToolTipText(dyzur.bledyToHTMLString());
        		this.setBackground(this.errorColor);
        	}
        }
        else 
        {
        	this.setToolTipText("");
        	if(dyzur.mozliweBledyisEmpty()) this.setIcon(this.defIcon);
        	else this.setIcon(this.mozliwyBladIcon);
        }

        return this;
    }
	/**
	 * @param defIcon ustawia domyœln¹ ikone CheckBoxa
	 */
	public void setDefIcon(Icon defIcon) {
		this.defIcon = defIcon;
	}
	/**
	 * @param errorColor ustawia kolor b³êdu dyzuru
	 */
	public void setErrorColor(Color errorColor) {
		this.errorColor = errorColor;
	}
	/**
	 * @param mozliwyBladIcon ustawia ikone mozliwego b³edu
	 */
	public void setMozliwyBladIcon(Icon mozliwyBladIcon) {
		this.mozliwyBladIcon = mozliwyBladIcon;
	}
}
