/**
 * 
 */
package gui.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;



/**
 * @author Wojciech Pierzchalski
 *
 */
public class DateDialog extends JDialog implements PropertyChangeListener{
	/**
	 * Border b≥edu dla pola roku
	 * @see #yearField
	 */
	private static final MatteBorder ERROR_BORDER=new MatteBorder(1, 1, 1, 1, Color.RED);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Przycisk ustawienia nowej daty
	 */
	private String btnString1 = "Ustaw";
	/**
	 * Przycisk anulowania ustawieÒ
	 */
	private String btnString2 = "Anuluj";
	/**
	 * Obiekt kalendarza
	 */
    private Calendar calendar = Calendar.getInstance();
    /**
     * WewnÍtrzny panel do zgrupowania ComboBox i TextFielda
     * @see #yearField
     * @see #getMonthComboBox()
     */
    private JPanel innerPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
    /**
     * Wybrany miesiπc. Przyjume -1 jeúli niedokonano wyboru.
     */
    private int month=-1;
    /**
     * ComboBox wyboru miesiπca
     */
    private JComboBox monthCB;
    /**
     * Wiadomoúc do uzytkownika.
     */
    private String msg = "Ustaw nowπ datÍ grafiku";
    /**
     * Domyúlny Border dla pola roku
     * @see #yearField
     */
    private final Border normalBorder;
    /**
     * Kontener
     */
    private JOptionPane optionPane;
    /**
     * Wybrany rok. Przyjmuje wartoúÊ -1 jeúli niedokonano wyboru.
     */
    private int year=-1;
    /**
     * Pole roku
     */
    private JTextField yearField=new JTextField(4);
    
    public DateDialog(JFrame frame){
    	this(frame,null);
    }
    
    public DateDialog(JFrame frame,Date data){
    	super(frame,true);
    	if(data!=null) this.calendar.setTime(data);
    	this.getMonthComboBox().setSelectedIndex(this.calendar.get(Calendar.MONTH));
    	this.month=this.getMonthComboBox().getSelectedIndex()+1;
    	this.normalBorder=this.yearField.getBorder();
    	this.yearField.setText(String.valueOf(this.calendar.get(Calendar.YEAR)));
    	this.year=Integer.parseInt(this.yearField.getText());
    	this.setTitle("Zmiana daty grafiku");
    	this.yearField.setFont(this.getMonthComboBox().getFont());
  
    	
    	this.innerPanel.add(this.getMonthComboBox());
    	this.innerPanel.add(this.yearField);
      
        Object[] array = {this.msg, this.innerPanel};
        Object[] options = {btnString1, btnString2};

        optionPane = new JOptionPane(array,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);

        setContentPane(optionPane);
        this.validate();
        this.pack();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
        		@Override
                public void windowClosing(WindowEvent we) {
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        addComponentListener(new ComponentAdapter() {
        		@Override
        		public void componentShown(ComponentEvent ce) {
        			yearField.requestFocusInWindow();
            }
        });
        optionPane.addPropertyChangeListener(this);
    }
    /**
     * 
     * @return reference do obiektu ComboBox wyboru miesiπca
     */
    private JComboBox getMonthComboBox(){
		if(this.monthCB!=null) return this.monthCB;
		final String[] months={"STYCZE—",
								"LUTY",
								"MARZEC",
								"KWIECIE—",
								"MAJ",
								"CZERWIEC",
								"LIPIEC",
								"SIERPIE—",
								"WRZESIE—",
								"PAèDZIERNIK",
								"LISTOPAD",
								"GRUDZIE—"};
		this.monthCB=new JComboBox(months);
		this.monthCB.setBackground(Color.WHITE);

		this.monthCB.setPreferredSize(new Dimension(100, 19));
		return this.monthCB;
	}
    /**
     * Czyúci pola i ukrywa okno dialogowe
     */
	public void clearAndHide() {
        yearField.setText(null);
        yearField.setBorder(this.normalBorder);
        setVisible(false);
    }

    /**
	 * @return wybrany miesiπc
	 */
	public int getMonth() {
		return this.month;
	}
    /**
	 * @return wybrany rok
	 */
	public int getYear() {
		return this.year;
	}
	
    @Override
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		

        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) 
            {
                    this.month = this.getMonthComboBox().getSelectedIndex()+1;
                    this.yearField.setBorder(this.normalBorder);
                    boolean ok=true;
                    try
                    {
                    	this.year= Integer.parseInt(this.yearField.getText());
                    }
                    catch(NumberFormatException ex)
                    {
                    	this.month=-1;
                    	this.year=-1;
                    	this.yearField.setBorder(ERROR_BORDER);
                    	ok=false;
                    }
                    if(ok) this.clearAndHide();
                    
            } else { 
            	this.month=-1;
            	this.year=-1;
            	clearAndHide();
            }
            
        }
		
	}
    /**
     * Wype≥nia pola okna dialogowego na podstawie daty
     * @param data data do wype≥nienia okna dialogowego
     */
	public void setDate(Date data){
    	this.calendar.setTime(data);
    }

	@Override
    public void setVisible(boolean b) {
    	if(b)
    	{
    		this.getMonthComboBox().setSelectedIndex(this.calendar.get(Calendar.MONTH));
        	this.month=this.getMonthComboBox().getSelectedIndex()+1;
        	this.yearField.setText(String.valueOf(this.calendar.get(Calendar.YEAR)));
        	this.year=Integer.parseInt(this.yearField.getText());
    	}
    	super.setVisible(b);
    }

}
