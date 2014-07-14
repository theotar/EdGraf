package grafik.printers;

import grafik.Grafik;
import gui.dialogs.PrintDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;

/**
 * Klasa zapewniaj¹ca obs³ugê wydruku grafiku.
 * @author Wojciech Pierzchalski
 * @version 1.0
 */
public class GrafikPrinter implements Printable, PrintableGrafik{
	/**
	 * Elementy poziome tabeli dyzurów
	 * @author Wojciech Pierzchalski
	 *
	 */
	private enum TableHorizontalElementHeight{
		/**
		 * Nag³ówek
		 */
		HEADER(4*3),
		/**
		 * Linia pozioma obrysu nag³ówka
		 */
		HEADER_LINE(1),
		/**
		 * Linia pozioma oddzielaj¹ca rzêdy dy¿urnych
		 */
		HLINE(2),
		/**
		 * Komórka dy¿uru
		 */
		ICELL((int)(5*2.8)),
		/**
		 * Wewnêtrzna linia pozioma pomiêdzy okresami dy¿urów
		 */
		IHLINE(1);
		/**
		 * Wysokoœæ elementu
		 */
		public final int height;
		TableHorizontalElementHeight(int h){
			this.height=h;
		}
	}
	/**
	 * Elementy pionowe tabeli dy¿urów
	 * @author Wojciech Pierzchalski
	 *
	 */
	private enum TableVerticalElementWidth{
		/**
		 * Komórka "Dy¿ur w godz."
		 */
		DG_CELL(21*3),
		/**
		 * Komórka dy¿uru
		 */
		ICELL((int)(5*2.8)),
		/**
		 * Komórka "il.g."
		 */
		ILG_CELL(6*3),
		/**
		 * Komórka "Lp"
		 */
		LP_CELL(5*3),
		/**
		 * Komórka dy¿urnego
		 */
		NI_CELL(37*3),
		/**
		 * Komórka uwag
		 */
		UWAGI_CELL(20*3),
		/**
		 * Linia pionowa oddzielaj¹ca komórki
		 */
		VLINE(1);
		/**
		 * D³ugoœæ elementu
		 */
		public final int width;
		
		private TableVerticalElementWidth(int w) {
			this.width=w;
		}
	} 
	/**
	 * Maksymalna liczba dy¿urnych, których grafk dy¿urów zmieœci siê na jednej stronie.
	 * 
	 */
	private static final int MAX_DYZURNY_PER_PAGE=7;
	/**
	 * Liczba kopii do wydruku
	 */
	private int copies=1;
	/**
	 * Czcionka daty grafiku
	 */
	private Font dataFont=new Font("Times New Roman CE",Font.BOLD,12);
	/**
	 * Kolor wype³nenia komórek dy¿uru w Dniu Energetyka
	 */
	private Color deColor=new Color(0, 0x80, 0);
	/**
	 * £añcuch formatuj¹cy dla liczb zmiennoprzecinkowych w wydruku. Domyœlnie "%.0f";
	 * @see String#format(String, Object...)
	 */
	private String doubleFormatString="%.0f";
	/**
	 * Liczba dy¿urnych, których grafik zostanie wydrukowane na jednej stronie.
	 */
	private int dyzurnyPerPage=2;
	/**
	 * Czcionka firmy
	 */
	private Font firmaFont=new Font("Arial CE",Font.BOLD,12);
	/**
	 * Czcionka dla pól Sporz¹dzi³...Sprawdzi³...Zatwierdzi³....
	 */
	private Font footerFont=new Font("Times New Roman CE",Font.PLAIN,10);
	/**
	 * Kontekst graficzny
	 */
	private Graphics2D g;
	/**
	 * Grafik do wydruku
	 */
	private Grafik grafik;
	/**
	 * Czcionka jednostki organizacyjnej
	 */
	private Font grupaFont=new Font("Arial CE",Font.PLAIN,10);
	/**
	 * Czcionka klauzuli
	 */
	private Font klauzulaFont=new Font("Times New Roman CE",Font.BOLD,12);
	/**
	 * Czcionka dla komórki "Lp."
	 */
	private Font lpFont=new Font("Times New Roman CE",Font.PLAIN,10);
	/**
	 * Kolor wype³nenia komórek dy¿uru w niedziele i œwieta.
	 */
	private Color nColor=Color.RED;
	/**
	 * Czcionka dy¿urnego
	 */
	private Font niFont=new Font("Times New Roman CE",Font.BOLD,12);
	/**
	 * Czcionka okresów dy¿urów
	 */
	private Font oicFont=new Font("Times New Roman CE",Font.BOLD,10);
	/**
	 * Wynikowa liczba stron wydruku
	 */
	private int pages=1;
	/**
	 * Komponent-rodzic obiektu.
	 */
	private Component parent=null;
	/**
	 * Okno dialogowe ustawieñ wydruku
	 */
	private PrintDialog printDialog;
	/**
	 * Obiekt zadania wydruku
	 * @see PrintDialog
	 */
	private PrinterJob printerJob=PrinterJob.getPrinterJob();
	/**
	 * Wydruk w kolorze
	 */
	private boolean printInColor=true;
	/**
	 * Kolor wype³nenia komórek dy¿uru w soboty
	 */
	private Color sColor=new Color(0, 255, 255);
	/**
	 * Czcionka nag³ówka tabeli
	 */
	private Font tableHeaderFont=new Font("Times New Roman CE",Font.PLAIN,9);
	/**
	 * Elementy poziome tabeli
	 * @see TableHorizontalElementHeight
	 */
	private TableHorizontalElementHeight[] tableHeights;
	/**
	 * Elementy pionowe tabeli
	 * @see TableVerticalElementWidth
	 */
	private TableVerticalElementWidth[] tableWidths;
	/**
	 * Wspo³rzêdna y tabeli
	 */
	private int tableY=100;
	/**
	 * Czcionka tytu³u grafiku.
	 */
	private Font tytulFont=new Font("Arial CE",Font.BOLD,12);
	/**
	 * Czcionka komórki"uwagi"
	 */
	private Font uFont=new Font("Times New Roman CE",Font.PLAIN,10);
	/**
	 * Bazowa wspó³rzêdna x wydruku	
	 */
	private int x=20;
	/**
	 * Czcionka dy¿uru (X)
	 */
	private Font xFont=new Font("Times New Roman CE",Font.BOLD,10);
	/**
	 * Bazowa wspó³rzêdna y wydruku
	 */
	private int y=20;
	/**
	 * Opcje wydruku
	 * @see HashPrintRequestAttributeSet
	 */
	HashPrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
	
	public GrafikPrinter() {
		this.aset.add(OrientationRequested.LANDSCAPE);
		this.aset.add(MediaSize.ISO.A4.getMediaSizeName());
	}
	public GrafikPrinter(Component parent){
		this();
		this.parent=parent;
	}
	/**
	 * Metoda wylicza bazow¹ wspó³rzêdna x wydruku, na podstawie formatu strony
	 * @param pf format strony
	 */
	private void calculateX(PageFormat pf){
		this.x=(int) ((pf.getImageableWidth()-this.getTableWidth())/2);
	}
	/**
	 * Metoda wylicza bazow¹ wspó³rzêdna y wydruku, na podstawie formatu strony
	 * @param pf format strony
	 */
	private void calculateY(PageFormat pf){
		this.y=(int) (pf.getImageableHeight()*0.066);
		this.tableY=this.y+this.getHeaderHeight();
		
	}
	/**
	 * Metoda koloruj¹ca tabelê.
	 */
	private void colorTable(){
		Color color=g.getColor();
		int y=this.tableY;
		int tableHeight=this.getTableHeight()-TableHorizontalElementHeight.HLINE.height;
		int x;
		for(int dzien=1;dzien<=this.grafik.ileDniMiesiaca();dzien++)
		{
			switch(this.grafik.jakiDzien(dzien))
			{
				case SOBOTA:
					x=this.getCellX(TableVerticalElementWidth.ICELL, dzien);
					g.setColor(this.sColor);
					g.fillRect(x-1, y, TableVerticalElementWidth.ICELL.width+1, tableHeight);
					break;
				case SWIETO:
				case NIEDZIELA:
					x=this.getCellX(TableVerticalElementWidth.ICELL, dzien);
					g.setColor(this.nColor);
					g.fillRect(x-1, y, TableVerticalElementWidth.ICELL.width+1, tableHeight);
					break;
				case DZIEN_ENERGETYKA:
					x=this.getCellX(TableVerticalElementWidth.ICELL, dzien);
					g.setColor(this.deColor);
					g.fillRect(x-1, y, TableVerticalElementWidth.ICELL.width+1, tableHeight);
					break;
			}
		}
		g.setColor(color);
	}
	/**
	 * Metoda wype³niaj¹ca listê elementów poziomych tabeli, na podstawie numeru strony
	 * @param page numer strony
	 * @see GrafikPrinter#tableHeights
	 */
	private void createTableHeights(int page){
		ArrayList<TableHorizontalElementHeight> lista=new ArrayList<TableHorizontalElementHeight>();
		lista.add(TableHorizontalElementHeight.HEADER_LINE);
		lista.add(TableHorizontalElementHeight.HEADER);
		lista.add(TableHorizontalElementHeight.HEADER_LINE);
		for(int i=this.getFirstDyzurnyIdx(page);i<this.getLastDyzurnyIdx(page);i++)
		{
			for(int j=0;j<this.grafik.ileOkresowDyzurow();j++)
			{
				lista.add(TableHorizontalElementHeight.ICELL);
				lista.add(TableHorizontalElementHeight.IHLINE);
			}
			lista.set(lista.size()-1, TableHorizontalElementHeight.HLINE);
		}
		this.tableHeights=lista.toArray(new TableHorizontalElementHeight[0]);
	}
	/**
	 * Metoda wype³niaj¹ca listê elementów pionowych tabeli
	 * @see GrafikPrinter#tableWidths
	 */
	private void createTableWidths(){
		ArrayList<TableVerticalElementWidth> lista=new ArrayList<GrafikPrinter.TableVerticalElementWidth>();
		lista.add(TableVerticalElementWidth.VLINE);
		lista.add(TableVerticalElementWidth.LP_CELL);
		lista.add(TableVerticalElementWidth.VLINE);
		lista.add(TableVerticalElementWidth.NI_CELL);
		lista.add(TableVerticalElementWidth.VLINE);
		lista.add(TableVerticalElementWidth.DG_CELL);
		lista.add(TableVerticalElementWidth.VLINE);
		lista.add(TableVerticalElementWidth.ILG_CELL);
		lista.add(TableVerticalElementWidth.VLINE);
		for(int i=0;i<31;i++)
		{
			lista.add(TableVerticalElementWidth.ICELL);
			lista.add(TableVerticalElementWidth.VLINE);
		}
		lista.add(TableVerticalElementWidth.UWAGI_CELL);
		lista.add(TableVerticalElementWidth.VLINE);
		this.tableWidths=lista.toArray(new TableVerticalElementWidth[0]);
	}
	/**
	 * Metoda rysuj¹ca stopkê strony. (Sporz¹dzi³..Sprawdzwi³...)
	 */
	private void drawFooter(){
		int x=this.getCellX(TableVerticalElementWidth.NI_CELL, 0);
		int y=this.tableY+this.getTableHeight()+this.getFooterHeight();
		int f8x=this.getCellX(TableVerticalElementWidth.ICELL, 8);
		int f21x=this.getCellX(TableVerticalElementWidth.ICELL, 21);
		g.setFont(this.footerFont);
		g.drawString("Sporz¹dzi³...............................................", x, y);
		g.drawString("Sprawdzi³...............................................", f8x, y);
		g.drawString("Zatwierdzi³...............................................",f21x, y);
	}
	/**
	 * Metoda rysuj¹ca nag³owek strony.
	 */
	private void drawHeader(){
		int top1x=this.getCellX(TableVerticalElementWidth.ICELL, 1);
		int top2x=this.getCellX(TableVerticalElementWidth.ICELL, 2);
		int top24x=this.getCellX(TableVerticalElementWidth.ICELL, 24);
		int x=this.x;
		int y=this.y+g.getFontMetrics(this.firmaFont).getHeight();
		g.setFont(this.getFontToWidth(this.firmaFont, this.grafik.getFirma(), top1x-x));
		g.drawString(this.grafik.getFirma(), x, y);
		g.setFont(this.getFontToWidth(this.tytulFont, this.grafik.getTytul(), x+this.getTableWidth()-top2x));
		g.drawString(this.grafik.getTytul(), top2x, y);
		y+=g.getFontMetrics(this.dataFont).getHeight();
		g.setFont(this.getFontToWidth(this.klauzulaFont, this.grafik.getKlauzula(), top1x-x));
		g.drawString(this.grafik.getKlauzula(), x, y);
		g.setFont(this.getFontToWidth(this.grupaFont, this.grafik.getGrupa(), top24x-top2x));
		g.drawString(this.grafik.getGrupa(), top2x, y);
		 AttributedString as = new AttributedString(this.grafik.getMiesiacRok());
		 as.addAttribute(TextAttribute.FONT, dataFont);
		 as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		 g.drawString(as.getIterator(), top24x, y); 
	}
	/**
	 * Metoda rysuj¹ca tabelê dla danej strony.
	 * @param page numer strony
	 */
	private void drawTable(int page){
		this.colorTable();
		this.drawTableHorizontalLines();
		this.drawTableVerticalLines();
		this.fillTableHeader();
		this.fillDyzury(page);
		this.fillOtherInnerCells(page);
		this.fillCells(page);
	}
	/**
	 * Metoda rysuj¹ca liniê poziom¹ o podanych parametrach
	 * @param x wspólrzêdna x punktu pocz¹tkowego
	 * @param y wspólrzêdna y punktu pocz¹tkowego
	 * @param width d³ugoœæ linii
	 * @param size gruboœæ linii
	 */
	private void drawTableHorizontalLine(int x,int y,int width,int size){
		for(int i=0;i<size;i++)g.drawLine(x, y+i, x+width-1, y+i);
	}
	/**
	 * Metoda rysuj¹ca wszystkie linie poziome tabeli
	 */
	private void drawTableHorizontalLines(){
		int x=this.x;
		int y=this.tableY;
		int innerX=this.getInnerHorizonatlLineX();
		int tableWidth=this.getTableWidth();
		int innerWidth=this.getInnerHorizontalLineWidth();
		for(TableHorizontalElementHeight e:this.tableHeights)
		{
			switch(e)
			{
				case IHLINE:
					this.drawTableHorizontalLine(innerX, y, innerWidth, e.height);
					y+=e.height;
					break;
				case HEADER_LINE:
				case HLINE:
					this.drawTableHorizontalLine(x, y, tableWidth, e.height);
				default:
					y+=e.height;
			}
		}
	}
	/**
	 * Metoda rysuj¹ca liniê pionow¹ o zadanych parametrach
	 * @param x wspólrzêdna x punktu pocz¹tkowego
	 * @param y wspólrzêdna y punktu pocz¹tkowego
	 * @param height d³ugoœæ linii
	 * @param size gruboœæ linii
	 */
	private void drawTableVerticalLine(int x,int y,int height,int size){
		for(int i=0;i<size;i++)g.drawLine(x+i, y, x+i, y+height-1);
	}
	/**
	 * Metoda rysuj¹ca wszystkie linie pionowe tabeli.
	 */
	private void drawTableVerticalLines(){
		int x=this.x;
		int y=this.tableY;
		int tableHeight=this.getTableHeight();
		for(TableVerticalElementWidth e:this.tableWidths)
		{
			if(e==TableVerticalElementWidth.VLINE)
				this.drawTableVerticalLine(x, y, tableHeight, e.width);
			x+=e.width;
		}
	}
	/**
	 * Metoda wype³niaj¹ca komórki "Lp.","Nazwisko i Imiê","Uwagi" dla podanej strony
	 * @param page numer strony
	 */
	private void fillCells(int page){
		int height=this.getCellHeight();
		int lpWidth=TableVerticalElementWidth.LP_CELL.width;
		int niWidth=TableVerticalElementWidth.NI_CELL.width;
		int uWidth=TableVerticalElementWidth.UWAGI_CELL.width;
		
		FontMetrics lpFm=g.getFontMetrics(lpFont);
		FontMetrics niFm=g.getFontMetrics(niFont);
		FontMetrics uFm=g.getFontMetrics(uFont);
		
		int lpOffsetY=(height-lpFm.getMaxAscent())/2;
		int uOffsetY=(height-uFm.getMaxAscent())/2;
		
		int lpX=this.getCellX(TableVerticalElementWidth.LP_CELL, 0);
		int niX=this.getCellX(TableVerticalElementWidth.NI_CELL, 0);
		int uX=this.getCellX(TableVerticalElementWidth.UWAGI_CELL, 0);
		
		for(int i=this.getFirstDyzurnyIdx(page),di=0;i<this.getLastDyzurnyIdx(page);i++,di++)
		{
			int y=this.getCellY(TableHorizontalElementHeight.ICELL, di*this.grafik.ileOkresowDyzurow()+1)+height;
			
			g.setFont(lpFont);
			int lpOffsetX=(lpWidth-lpFm.stringWidth(String.valueOf((i+1))+"."))/2;
			g.drawString(String.valueOf((i+1))+".", lpX+lpOffsetX, y-lpOffsetY);
			
			g.setFont(uFont);
			String uStr=String.format(this.doubleFormatString, this.grafik.ileGodzin(i));
			int uOffsetX=(uWidth-uFm.stringWidth(uStr))/2;
			g.drawString(uStr, uX+uOffsetX, y-uOffsetY);
			
			g.setFont(niFont);
			String niStr=this.grafik.getDyzurny(i);
			if(niWidth>niFm.stringWidth(niStr))
			{
				int niOffsetX=(niWidth-niFm.stringWidth(niStr))/2;
				int niOffsetY=(height-niFm.getMaxAscent())/2;
				g.drawString(niStr, niX+niOffsetX, y-niOffsetY);
			}
			else
			{
				String[] tab=niStr.split(" ", 2);
				if(tab.length==1)
				{
					int niOffsetX=(niWidth-niFm.stringWidth(niStr))/2;
					int niOffsetY=(height-niFm.getMaxAscent())/2;
					g.drawString(niStr, niX+niOffsetX, y-niOffsetY);
				}
				else
				{
					niStr=tab[0]+"\n"+tab[1];
					int niOffsetX=(niWidth-niFm.stringWidth(tab[0]))/2;
					int niOffsetY=height/2+niFm.getMaxDescent();
					g.drawString(tab[0], niX+niOffsetX, y-niOffsetY);
					niOffsetX=(niWidth-niFm.stringWidth(tab[1]))/2;
					niOffsetY=height/2-niFm.getMaxAscent();
					g.drawString(tab[1], niX+niOffsetX, y-niOffsetY);
				}
			}
		}
	}
	/**
	 * Metoda wype³niaj¹ca komórki dy¿urów dla podanej strony
	 * @param page numer strony
	 */
	private void fillDyzury(int page){
		int height=TableHorizontalElementHeight.ICELL.height;
		int width=TableVerticalElementWidth.ICELL.width;
		g.setFont(this.xFont);
		FontMetrics fm=g.getFontMetrics();
		int fontHeight=fm.getMaxAscent();
		int offsetY=height-fontHeight;
		int offsetX=(width-fm.stringWidth("X"))/2;
		int cdi=0;
		for(int dyzurnyIdx=this.getFirstDyzurnyIdx(page);dyzurnyIdx<this.getLastDyzurnyIdx(page);dyzurnyIdx++)
		{
			for(int czasDyzuruIdx=0;czasDyzuruIdx<this.grafik.ileOkresowDyzurow();czasDyzuruIdx++)
			{
				int y=this.getCellY(TableHorizontalElementHeight.ICELL, cdi+1)+height-offsetY;
				for(int dzien=1;dzien<=this.grafik.ileDniMiesiaca();dzien++)
				{
					if(this.grafik.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzien))
					{
						int x=this.getCellX(TableVerticalElementWidth.ICELL, dzien)+offsetX;
						g.drawString("X", x, y);
					}
				}
				cdi++;
			}
		}
	}
	/**
	 * Metoda wypeniaj¹ca komórki okresów dy¿urów dla podanej strony
	 * @param page numer strony
	 */
	private void fillOtherInnerCells(int page){
		int height=TableHorizontalElementHeight.ICELL.height;
		int dgWidth=TableVerticalElementWidth.DG_CELL.width;
		int ilgWidth=TableVerticalElementWidth.ILG_CELL.width;
		int dgX=this.getCellX(TableVerticalElementWidth.DG_CELL, 0);
		int ilgX=this.getCellX(TableVerticalElementWidth.ILG_CELL,0);
		
		g.setFont(this.oicFont);
		FontMetrics fm=g.getFontMetrics();
		int fontHeight=fm.getMaxAscent();
		int offsetY=height-fontHeight;
		
		String[] dg=new String[this.grafik.ileOkresowDyzurow()];
		String[] ilg=new String[dg.length];
		int[] dgOffsetX=new int[dg.length];
		int[] ilgOffsetX=new int[dg.length];
		for(int i=0;i<this.grafik.ileOkresowDyzurow();i++)
		{
			dg[i]=this.grafik.getPrzedzialCzasuDyzuru(i);
			ilg[i]=String.format(this.doubleFormatString,this.grafik.getIloscGodzinDyzuru(i));
			dgOffsetX[i]=(dgWidth-fm.stringWidth(dg[i]))/2;
			ilgOffsetX[i]=(ilgWidth-fm.stringWidth(ilg[i]))/2;
		}
		int cdi=1;
		for(int dyzurnyIdx=this.getFirstDyzurnyIdx(page);dyzurnyIdx<this.getLastDyzurnyIdx(page);dyzurnyIdx++)
		{
			for(int czasDyzuruIdx=0;czasDyzuruIdx<this.grafik.ileOkresowDyzurow();czasDyzuruIdx++,cdi++)
			{
				int y=this.getCellY(TableHorizontalElementHeight.ICELL, cdi)+height-offsetY;
				g.drawString(dg[czasDyzuruIdx], dgX+dgOffsetX[czasDyzuruIdx], y);
				g.drawString(ilg[czasDyzuruIdx], ilgX+ilgOffsetX[czasDyzuruIdx], y);
			}
		}	
	}
	/**
	 * Metoda wype³niaj¹ca nag³owek tabeli.
	 */
	private void fillTableHeader(){
		int y=this.getCellY(TableHorizontalElementHeight.HEADER, 0)+TableHorizontalElementHeight.HEADER.height;
		int height=TableHorizontalElementHeight.HEADER.height;
		int width=TableVerticalElementWidth.LP_CELL.width;
		int x=this.getCellX(TableVerticalElementWidth.LP_CELL, 0);
		g.setFont(this.tableHeaderFont);
		FontMetrics fm=g.getFontMetrics();
		int fontHeight=fm.getMaxAscent();
		int offsetY=height-fontHeight;
		int offsetX=(width-fm.stringWidth("Lp."))/2;
		y-=offsetY;
		x+=offsetX;
		g.drawString("Lp.", x,y);
		
		x=this.getCellX(TableVerticalElementWidth.NI_CELL, 0);
		width=TableVerticalElementWidth.NI_CELL.width;
		offsetX=(width-fm.stringWidth("Nazwisko i imiê"))/2;
		x+=offsetX;
		g.drawString("Nazwisko i imiê", x,y);
		
		x=this.getCellX(TableVerticalElementWidth.DG_CELL, 0);
		width=TableVerticalElementWidth.DG_CELL.width;
		offsetX=(width-fm.stringWidth("Dy¿ur w godz."))/2;
		x+=offsetX;
		g.drawString("Dy¿ur w godz.", x,y);
		
		x=this.getCellX(TableVerticalElementWidth.ILG_CELL, 0);
		width=TableVerticalElementWidth.ILG_CELL.width;
		offsetX=(width-fm.stringWidth("il.g."))/2;
		x+=offsetX;
		g.drawString("il.g.", x,y);
		
		
		width=TableVerticalElementWidth.ICELL.width;
		for(int i=1;i<=this.grafik.ileDniMiesiaca();i++)
		{
			x=this.getCellX(TableVerticalElementWidth.ICELL, i);
			offsetX=(width-fm.stringWidth(String.valueOf(i)))/2;
			x+=offsetX;
			g.drawString(String.valueOf(i), x,y);
		}
		
		x=this.getCellX(TableVerticalElementWidth.UWAGI_CELL, 0);
		width=TableVerticalElementWidth.UWAGI_CELL.width;
		offsetX=(width-fm.stringWidth("Uwagi"))/2;
		x+=offsetX;
		g.drawString("Uwagi", x,y);
	}
	/**
	 * @return wysokoœæ komórki dy¿urnego
	 */
	private int getCellHeight(){
		int i=this.grafik.ileOkresowDyzurow();
		return i*TableHorizontalElementHeight.ICELL.height+(i-1)*TableHorizontalElementHeight.IHLINE.height;
	}
	/**
	 * Metoda zwraca wspó³rzêdn¹ x n-tego elementu (n=offset) <code>cell</code> 
	 * @param cell typ elementu
	 * @param offset numer elementu
	 * @return wspó³rzêdna x elementu
	 * @see TableVerticalElementWidth
	 */
	private int getCellX(TableVerticalElementWidth cell,int offset){
		int sum=this.x;
		for(TableVerticalElementWidth e:this.tableWidths) 
		{
			if(e==cell)
				if(cell==TableVerticalElementWidth.ICELL) 
					return sum+(offset-1)*(TableVerticalElementWidth.ICELL.width+TableVerticalElementWidth.VLINE.width);
				else return sum;
			else sum+=e.width;
		}
		return sum;
	}
	/**
	 * Metoda zwraca wspó³rzêdn¹ y n-tego elementu (n=offset) <code>cell</code> 
	 * @param cell typ elementu
	 * @param offset numer elementu
	 * @return wspó³rzêdna y elementu
	 * @see TableHorizontalElementHeight
	 */
	private int getCellY(TableHorizontalElementHeight cell,int offset){
		int sum=this.tableY;
		for(TableHorizontalElementHeight e:this.tableHeights) 
		{
			if(e==cell)
				if(cell==TableHorizontalElementHeight.ICELL) 
				{
					offset--;
					if(offset==0) return sum;
					else sum+=e.height;
				}
				else return sum;
			else sum+=e.height;
		}
		return sum;
	}
	/**
	 * Metoda zwraca indeks pierwszego dy¿urnego na podanej stronie.
	 * @param page numer strony
	 * @return indeks dy¿urnego
	 */
	private int getFirstDyzurnyIdx(int page){
		return page*this.dyzurnyPerPage;
	}
	/**
	 * Metoda skaluje czcionke (w dó³) <code>baseFont</code>, dla podanego ³añcucha znaków i podanej d³ugoœci.
	 * @param baseFont czcionka do wyskalowania
	 * @param string ³ancuch znaków
	 * @param width d³ugoœæ maksymalna
	 * @return wyskalowana czcionka
	 */
	private Font getFontToWidth(Font baseFont,String string,int width){
		if(string==null) string="";
		FontMetrics fm=g.getFontMetrics(baseFont);
		if(width>fm.stringWidth(string)) return baseFont;
		int stringWidth=width;
		Font font=baseFont;
		do
		{
			font=new Font(baseFont.getFontName(), baseFont.getStyle(), font.getSize()-1);
			fm=g.getFontMetrics(font);
			stringWidth=fm.stringWidth(string);
		}
		while(width<=stringWidth);
		return font;
	}
	/**
	 * 
	 * @return wysokoœæ stopki wydruku
	 */
	private int getFooterHeight(){
		return 2*this.g.getFontMetrics(this.footerFont).getHeight();
	}
	/**
	 * 
	 * @return wysokoœæ nag³ówka wydruku
	 */
	private int getHeaderHeight(){
		return g.getFontMetrics(this.firmaFont).getHeight() 
				+ g.getFontMetrics(this.dataFont).getHeight()
				+g.getFontMetrics(this.dataFont).getMaxDescent();
	}
	/**
	 * 
	 * @return wspó³rzêdna x pocz¹tku wewnêtrznych linii poziomych
	 */
	private int getInnerHorizonatlLineX(){
		int sum=this.x;
		for(TableVerticalElementWidth e:this.tableWidths) 
		{
			sum+=e.width;
			if(e==TableVerticalElementWidth.NI_CELL) return sum;
		}
		return sum;
	}
	/**
	 * 
	 * @return d³ugoœæ wewnêtrznej linii poziomej
	 */
	private int getInnerHorizontalLineWidth(){
		int sum=0;
		boolean count=false;
		for(TableVerticalElementWidth e:this.tableWidths) 
		{
			if(e==TableVerticalElementWidth.UWAGI_CELL) return sum;
			if(count)sum+=e.width;
			if(e==TableVerticalElementWidth.NI_CELL) count=true;
		}
		return sum;
	}
	/**
	 * Metoda zwraca indeks ostatniego dy¿urnego +1 na danej stronie.
	 * @param page numer strony
	 * @return indeks ostatniego dy¿urnego +1
	 */
	private int getLastDyzurnyIdx(int page){
		int idx=(page+1)*this.dyzurnyPerPage;
		if(idx>this.grafik.iluDyzurnych()) return this.grafik.iluDyzurnych();
		else return idx;
	}
	/**
	 * 
	 * @return wysokoœæ tabeli
	 */
	private int getTableHeight(){
		int sum=0;
		for(TableHorizontalElementHeight e:this.tableHeights) sum+=e.height;
		return sum;
	}
	/**
	 * 
	 * @return d³ugoœæ tabeli
	 */
	private int getTableWidth(){
		int sum=0;
		for(TableVerticalElementWidth e:this.tableWidths) sum+=e.width;
		return sum;
	}
	
	/**
	 * Metoda tworzy (jeœli jeszcze nie istnieje) okno dialogowe ustawieñ wydruku i zwraca do niego referencjê.
	 * @return referencja do okna dialogowego
	 * @see PrintDialog 
	 */
	protected PrintDialog getPrintDialog() {
		if(this.printDialog==null) this.printDialog=new PrintDialog();
		return this.printDialog;
	}
	/**
	 * @param grafik do wydruku
	 */
	protected void setGrafik(Grafik grafik) {
		this.grafik = grafik;
		this.pages=(int) Math.ceil(this.grafik.iluDyzurnych()/this.dyzurnyPerPage);
	}
	/**
	 * @return iloœæ dy¿urnych na stronê
	 */
	public int getDyzurnyPerPage() {
		return this.dyzurnyPerPage;
	}
	/**
	 * Metoda pokazuje okno ustawieñ wydruku, a po zaakceptowaniu przez u¿ytkownika drukuje grafik.
	 * @param g grafik do wydruku.
	 */
	@Override
	public void print(Grafik g) throws PrinterException {
		this.setGrafik(g);
		this.printerJob.setJobName("Grafik dy¿urów "+this.grafik.getMiesiacRok()+" - "+this.grafik.getGrupa());
		int max=g.iluDyzurnych();
		if(max>MAX_DYZURNY_PER_PAGE) max=MAX_DYZURNY_PER_PAGE;
		this.getPrintDialog().setMaxDyzurnyPerPage(max);
		this.printDialog.setDefaultDyzurnyPerPage();
		if(this.printDialog())
		{
			this.printerJob.setPrintService(this.printDialog.getPrintService());
			this.copies=this.printDialog.getCopies();
			this.printInColor=this.printDialog.isPrintInColor();
			this.setDyzurnyPerPage(this.printDialog.getDyzurnyPerPage());
			this.aset.remove(Chromaticity.class);
			if(this.printInColor) this.aset.add(Chromaticity.COLOR);
			else this.aset.add(Chromaticity.MONOCHROME);
			this.printerJob.setPrintable(this);
			this.printerJob.print(this.aset);
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if(this.grafik==null) return NO_SUCH_PAGE;
		if(pageIndex >=this.copies*this.pages) return NO_SUCH_PAGE;
		int page=pageIndex%this.pages;
		Graphics2D g2d=(Graphics2D) graphics;
		this.g=g2d;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		this.createTableHeights(page);
		this.createTableWidths();
		this.calculateY(pageFormat);
		this.calculateX(pageFormat);
		
		
		this.drawHeader();
		this.drawTable(page);
		this.drawFooter();
		return PAGE_EXISTS;
	}
	/**
	 * Metoda pokazuje okno dialogowe ustawieñ wydruku.
	 * @return true je¿eli u¿ytkownik potwierdzi³ ustawienia, false w przeciwnym wypadku.
	 */
	public boolean printDialog() {
		PrintDialog pd=this.getPrintDialog();
		if(this.parent!=null) pd.setLocationRelativeTo(this.parent);
		pd.setVisible(true);
		return pd.isPrintApproved();
	}
	/**
	 * @param dyzurnyPerPage iloœæ dy¿urnych na stronê do ustawienia
	 */
	public void setDyzurnyPerPage(int dyzurnyPerPage) {
		if(dyzurnyPerPage<MAX_DYZURNY_PER_PAGE)
			this.dyzurnyPerPage = dyzurnyPerPage;
		else this.dyzurnyPerPage=MAX_DYZURNY_PER_PAGE;
		this.pages=(int) Math.ceil(this.grafik.iluDyzurnych()/this.dyzurnyPerPage);
	}

}
