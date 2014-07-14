/**
 * 
 */
package gui.tables.models;

import grafik.Grafik;
import grafik.Grafik.CzasDyzuru;
import grafik.Grafik.Dyzur;
import gui.events.GrafikTableModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Model tabeli odwzorowuj¹cy grafik.
 * @author Wojciech Pierzchalski
 *
 */
public class GrafikTableModel extends AbstractTableModel {
	
	/**
	 * Liczba kolumn po kolumnach dy¿uru
	 */
	private static final int ILE_POL_PO=1;
	/**
	 * Liczba kolumn przed kolumnami dy¿uru
	 */
	private static final int ILE_POL_PRZED=4;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Numer rzêdu, który okresla typ dnia tygodnia
	 * @see grafik.Grafik.TypDnia
	 * @see #getValueAt(int, int)
	 */
	public static final int DZIEN_TYGODNIA=-1;
	/**
	 * Obiekt grafiku, który jest odwzorowywany
	 */
	protected Grafik grafik;
	
	public GrafikTableModel(Grafik g) {
		this.grafik=g;
	}
	/**
	 * Dodanie dy¿urnego
	 * @param dyzurny nowy dyzurny
	 * @see grafik.Grafik#addDyzurny(java.lang.String)
	 */
	public void addDyzurny(String dyzurny) {
		this.grafik.addDyzurny(dyzurny);
		this.fireTableDataChanged();
	}
	/**
	 * Dodanie okresu dy¿uru
	 */
	public void addOkresDyzuru(){
		this.grafik.addCzasDyzuru(new CzasDyzuru("00.00-00.00", 0));
		this.fireTableDataChanged();
		//this.fireTableStructureChanged();
	}
	/**
	 * Forwards the given notification event to all TableModelListeners that registered themselves 
	 * as listeners for this table model.
	 * Odpalaæ w przypadku,gdy zmieniono kolumnê
	 * @param columnIndex indeks zmienione kolumny
	 */
	public void fireColumnUpdated(int columnIndex){
		fireTableChanged(new TableModelEvent(this, 0, this.getRowCount()-1,
                columnIndex, TableModelEvent.UPDATE));
	}
	/**
	 * Forwards the given notification event to all TableModelListeners that registered themselves 
	 * as listeners for this table model.
	 * Odpalaæ, gdy wstawiono dy¿ur
	 * @param columnIndex indeks kolumny, w której wstawiono dyzur
	 */
	public void fireDyzurSet(int columnIndex){
		int offset=ILE_POL_PRZED-1;
		int dzienMiesiaca=columnIndex-offset;
		Grafik.ZakresTygodnia zt=this.grafik.getZakresTygodnia(dzienMiesiaca);
		for(int i=zt.getPoczatekTygodnia();i<=zt.getKoniecTygodnia();i++)
			this.fireColumnUpdated(i+offset);
		this.fireColumnUpdated(this.getColumnCount()-1);
	}
	/**
	 * Forwards the given notification event to all TableModelListeners that registered themselves 
	 * as listeners for this table model.
	 * Odpalaæ, gdy zmieniono liczbê godzin w okresie dy¿uru
	 */
	public void fireGodzinyUpdated() {
		fireTableChanged(new TableModelEvent(this, 0, this.getRowCount()-1,
                3, TableModelEvent.UPDATE));	
	}
	/**
	 * Forwards the given notification event to all TableModelListeners that registered themselves 
	 * as listeners for this table model.
	 * Odpalaæ, gdy zmieniono liczbê okresów dyzurów
	 */
	public void firePrzedzialyUpdated(){
		fireTableChanged(new TableModelEvent(this, 0, this.getRowCount()-1,
                2, TableModelEvent.UPDATE));
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		if(columnIndex==this.getColumnCount()-1) return Double.class;
		
		switch(columnIndex)
		{
		case 0: return String.class;
		case 1: return String.class;	
		case 2: return String[].class;
		case 3: return Double[].class;
		default: return Grafik.Dyzur[].class;	
		}
		
	}
	@Override
	public int getColumnCount() {
		return ILE_POL_PRZED+this.grafik.ileDniMiesiaca()+ILE_POL_PO;
	}
	@Override
	public String getColumnName(int column) {
		if(column==this.getColumnCount()-1) return "Uwagi";
		switch(column)
		{
		case 0: return "L.p.";
		case 1: return "Nazwisko i Imiê";
		case 2: return "Dy¿ur w godz.";
		case 3: return "il.g.";
		default: return  String.valueOf(column-ILE_POL_PRZED+1);	
		}
	}
	@Override
	public int getRowCount() {
		return this.grafik.iluDyzurnych();
	}
	/**
	 * 
	 * {@inheritDoc}
	 * Gdy <code>rowIndex==DZIEN_TYGODNIA</code> zwraca typ dnia tygodnia.
	 * @see #DZIEN_TYGODNIA
	 * @see grafik.Grafik.TypDnia
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==DZIEN_TYGODNIA) return this.grafik.jakiDzien(columnIndex-ILE_POL_PRZED+1);
		if(columnIndex==this.getColumnCount()-1) return this.grafik.ileGodzin(rowIndex);
		switch(columnIndex)
		{
		case 0: return String.valueOf(rowIndex+1)+".";
		case 1: return this.grafik.getDyzurny(rowIndex);
		case 2: return this.grafik.getPrzedzialyCzasu();
		case 3: return this.grafik.getIlosciGodzinDyzuru();
		default: return this.grafik.getDyzury(rowIndex, columnIndex-ILE_POL_PRZED+1);	
		}
	}
	/**
	 * @return iloœæ dni w miesi¹cu grafiku
	 * @see grafik.Grafik#ileDniMiesiaca()
	 */
	public int ileDniMiesiaca() {
		return this.grafik.ileDniMiesiaca();
	}
	/**
	 * @return iloœæ okresów dy¿urów
	 * @see grafik.Grafik#ileOkresowDyzurow()
	 */
	public int ileOkresowDyzurow() {
		return this.grafik.ileOkresowDyzurow();
	}
	/**
	 * @return liczba dy¿urnych w grafiku
	 * @see grafik.Grafik#iluDyzurnych()
	 */
	public int iluDyzurnych() {
		return this.grafik.iluDyzurnych();
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex!=0&&columnIndex!=this.getColumnCount()-1;
	}
	/**
	 * Usuwa dyzurnego z grafiku
	 * @param idx indeks dy¿urnego
	 * @see grafik.Grafik#removeDyzurny(int)
	 */
	public void removeDyzurny(int idx) {
		if(this.grafik.removeDyzurny(idx)) this.fireTableDataChanged();
	}
	/**
	 * Usuwa ostatni okres dy¿uru z grafiku
	 * @see Grafik#removeCzasDyzuru(int)
	 */
	public void removeOkresDyzuru(){
		this.grafik.removeCzasDyzuru(this.grafik.ileOkresowDyzurow()-1);
		this.fireTableDataChanged();
	}
	/**
	 * @param grafik nowy grafik do ustawienia
	 */
	public void setGrafik(Grafik grafik) {
		this.grafik = grafik;
		this.fireTableStructureChanged();
	}
	/**
	 * Ustawia,czy Grafik ma sprawdzaæ b³edy
	 * @param sprawdzajBledy czy sprawdzac b³edy
	 * @see grafik.Grafik#setSprawdzajBledy(boolean)
	 */
	public void setSprawdzajBledy(boolean sprawdzajBledy) {
		this.grafik.setSprawdzajBledy(sprawdzajBledy);
		this.fireTableDataChanged();
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(aValue instanceof GrafikTableModelEvent)
		{
			GrafikTableModelEvent ge=(GrafikTableModelEvent)aValue;
			if(columnIndex==2)
			{
				
				this.grafik.setPrzedzialCzasuDyzuru(ge.getFirstRow(), (String) ge.getValue() );
				this.firePrzedzialyUpdated();
			}
			else if(columnIndex==3)
			{
				this.grafik.setIloscGodzinDyzuru
				(ge.getFirstRow(), (Double)ge.getValue());
				this.fireTableDataChanged();
			}
			else
			{	
				this.grafik.setDyzur(rowIndex, ge.getFirstRow(), columnIndex-ILE_POL_PRZED+1, ((Dyzur)ge.getValue()).isJest());
				this.fireDyzurSet(columnIndex);
				//this.fireTableDataChanged();
			}
		}
		else if(aValue instanceof TableModelEvent)
		{
			if(columnIndex==3)
			{
				TableModelEvent e=(TableModelEvent)aValue;
				this.grafik.setIloscGodzinDyzuru
				(e.getFirstRow(), (Double)((TableModel)e.getSource()).getValueAt(e.getFirstRow(), e.getColumn()));
				this.fireTableDataChanged();
			}	
		}
		else if(columnIndex==1)
			this.grafik.setDyzurny(rowIndex, aValue.toString());
		else super.setValueAt(aValue, rowIndex, columnIndex);
	}
	/**
	 * Zamienia miejscami dy¿urnych
	 * @param idxFrom indeks pierwszego dy¿urnego
	 * @param idxTo indeks drugiego dy¿urnego
	 */
	public void swapDyzurny(int idxFrom,int idxTo){
		if(idxFrom<0||idxTo<0||idxFrom>=this.iluDyzurnych()||idxTo>=this.iluDyzurnych()) return;
		String dyzurny=this.grafik.getDyzurny(idxTo);
		this.grafik.setDyzurny(idxTo, this.grafik.getDyzurny(idxFrom));
		this.grafik.setDyzurny(idxFrom, dyzurny);
		this.fireTableCellUpdated(idxFrom, 1);
		this.fireTableCellUpdated(idxTo, 1);
	}
	/**
	 * Ustawia now¹ date grafiku
	 * @param miesiac nowy miesi¹c
	 * @param rok nowy rok grafiku
	 * @see grafik.Grafik#ustawDate(int, int)
	 */
	public void ustawDate(int miesiac, int rok) {
		if(this.grafik.ustawDate(miesiac, rok)) this.fireTableStructureChanged();
	}
}
