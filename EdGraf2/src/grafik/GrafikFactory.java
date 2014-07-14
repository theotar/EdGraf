/**
 * 
 */
package grafik;

import grafik.Grafik.CzasDyzuru;
import gui.EdGraf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


/**
 * Fabryka grafików
 * @author Wojciech Pierzchalski
 * 
 */
public class GrafikFactory {
	/**
	 * Domyslne okresy dy¿urów:<Br>
	 * 07.00-15.00<BR>
	 * 15.00-20.00<BR>
	 * 20.00-02.00<BR>
	 * 02.00-07.00<BR>
	 */
	private static final ArrayList<CzasDyzuru> DEFAULT_OKRESY=new ArrayList<Grafik.CzasDyzuru>();
	static
	{
		DEFAULT_OKRESY.add(new CzasDyzuru("07.00-15.00", 8));
		DEFAULT_OKRESY.add(new CzasDyzuru("15.00-20.00", 5));
		DEFAULT_OKRESY.add(new CzasDyzuru("20.00-02.00", 6));
		DEFAULT_OKRESY.add(new CzasDyzuru("02.00-07.00", 5));
	}
	/**
	 * Metoda tworzy listê dy¿urnych na podstawie ustawieñ programu EdGraf
	 * @param settings ustawienia
	 * @return lista dy¿urnych.
	 * @see EdGraf#getSettings()
	 */
	private static List<String> getDyzurniFromSettings(Properties settings){
		ArrayList<String> lista=new ArrayList<String>();
		int i=0;
		String dyzurny;
		do
		{
			dyzurny=settings.getProperty("dyzurny."+i++);
			if(dyzurny!=null) lista.add(dyzurny);
		}
		while(dyzurny!=null);
		return lista;
	}
	/**
	 * Metoda tworzy listê okresów dy¿urów na podstawie ustawieñ programu EdGraf
	 * @param settings ustawienia
	 * @return lista okresów.
	 * @see EdGraf#getSettings()
	 * @see Grafik.CzasDyzuru
	 */
	private static List<CzasDyzuru> getOkresyDyzuruFromSettings(Properties settings){
		ArrayList<CzasDyzuru> lista=new ArrayList<CzasDyzuru>();
		int i=0;
		String cdStr;
		do
		{
			cdStr=settings.getProperty("czasDyzuru."+i++);
			if(cdStr!=null) 
			{
				String[] tab=cdStr.split(";");
				lista.add(new CzasDyzuru(tab[0], Double.parseDouble(tab[1])));
			}
		}
		while(cdStr!=null);
		return lista;
	}
	/**
	 * Metoda zwraca podstawowy grafik z jednym dy¿urnym Janem Kowalskim 
	 * i jednym okresem dy¿uru 07.00-15.00 na obecny miesi¹c
	 * @return obiekt grafiku
	 */
	public static Grafik getGrafik(){
		Grafik g=new Grafik();
		g.dyzurni=new ArrayList<String>();
		g.dyzurni.add("Jan Kowalski");
		g.okresyDyzurow=DEFAULT_OKRESY;
		g.stworzDyzury();
		g.setSprawdzajBledy(true);
		return g;
	}
	/**
	 * Funkcja odczytuj¹ca obiekt grafiku z pliku programu EdGraf v1 (*.grf)
	 * @param file plik grafiku
	 * @return obiekt grafiku lub null w razie b³edu
	 * @throws IOException
	 */
	public static Grafik getGrafikFromEdgraf1(File file) throws IOException{
		FileInputStream fis=new FileInputStream(file);
		InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
		BufferedReader in=new BufferedReader(isr);
		String line;
		Grafik g=new Grafik();
		g.okresyDyzurow=DEFAULT_OKRESY;
		boolean values=false;
		int dyzurnyIdx=0;
		while((line=in.readLine())!=null)
		{
			if(values)
			{
				if(!line.contains("=")) break;
				String[] tab=line.split("=");
				Scanner skaner=new Scanner(tab[0]);
				int col=skaner.nextInt();
				int row=skaner.nextInt();
				skaner.close();
				if(row==0)
				{
					switch(col)
					{
						case 0:
							g.setFirma(tab[1].substring(3));
							break;
						case 5:
							g.setTytul(tab[1].substring(3));
							break;
					}
				}
				else if(row==1)
				{
					switch(col)
					{
						case 0:
							g.setKlauzula(tab[1].substring(3));
							break;
						case 5:
							g.setGrupa(tab[1].substring(3));
							break;
						case 27:
							skaner=new Scanner(tab[1].substring(3));
							String mStr=skaner.next();
							
							int rok=skaner.nextInt();
							skaner.close();
							int miesiac=-1;
							if(mStr.equalsIgnoreCase("Styczeñ")) miesiac=1;
							else if(mStr.equalsIgnoreCase("Luty")) miesiac=2;
							else if(mStr.equalsIgnoreCase("Marzec")) miesiac=3;
							else if(mStr.equalsIgnoreCase("kwiecieñ")) miesiac=4;
							else if(mStr.equalsIgnoreCase("Maj")) miesiac=5;
							else if(mStr.equalsIgnoreCase("Czerwiec")) miesiac=6;
							else if(mStr.equalsIgnoreCase("Lipiec")) miesiac=7;
							else if(mStr.equalsIgnoreCase("Sierpieñ")) miesiac=8;
							else if(mStr.equalsIgnoreCase("Wrzesieñ")) miesiac=9;
							else if(mStr.equalsIgnoreCase("PaŸdziernik")) miesiac=10;
							else if(mStr.equalsIgnoreCase("Listopad")) miesiac=11;
							else if(mStr.equalsIgnoreCase("Grudzieñ")) miesiac=12;
							g.ustawDate(miesiac, rok);
							break;
					}
				}
				else if(row>2+g.iluDyzurnych()*4) break;
				else if(row>2)
				{
					if(col==1)
					{
						g.dyzurni.set(dyzurnyIdx++,tab[1].substring(3));
					}
					else
					{
						if(tab[1].contains("X"))
						{
							int cdi=(row-3)-4*(dyzurnyIdx-1);
							int dzien=col-3;
							g.setDyzur(dyzurnyIdx-1, cdi, dzien, true);
						}
					}
				}
			}
			if(line.contains("AbsoluteRowCount"))
			{
				String[] tab=line.split("=");
				int iluDyzurnych=Integer.parseInt(tab[1]);
				iluDyzurnych-=5;
				iluDyzurnych/=4;
				g.dyzurni=new ArrayList<String>(iluDyzurnych);
				while(iluDyzurnych>0)
				{
					g.dyzurni.add("");
					iluDyzurnych--;
				}
				g.stworzDyzury();
				continue;
			}
			if(line.contains("[Value]"))
			{
				values=true;
				continue;
			}
			
		}
		if(g!=null) g.setSprawdzajBledy(true);
		return  g;
	}
	/**
	 * Metoda wczytuje grafik z pliku okreœlonego przez œcie¿ke path
	 * @param path œcie¿ka do pliku
	 * @return obiekt grafiku lub null w razie b³êdu
	 */
	public static Grafik getGrafikFromFile(String path){
		if(path.endsWith(".grf"))
		{
			try {
				return GrafikFactory.getGrafikFromEdgraf1(new File(path));
			} catch (IOException e) {
				return null;
			}
		}
		else if(path.endsWith(".gdf"))
		{
			try {
				ObjectInputStream oi=new ObjectInputStream(new FileInputStream(new File(path)));
				Grafik g=(Grafik) oi.readObject();
				oi.close();
				return g;
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				return null;
			} catch (ClassNotFoundException e) {
				return null;
			}
		}
		else return null;
	}
	/**
	 * Funkcja tworzy nowy grafik dy¿urów na podstawie ustawieñ programu EdGraf
	 * @param props ustawienia EdGraf
	 * @return Obiekt grafiku lub null w razie niepowodzenia
	 * @see EdGraf#getSettings()
	 */
	public static Grafik getGrafikFromProperties(Properties props){
		Grafik g=new Grafik();
		g.dyzurni= GrafikFactory.getDyzurniFromSettings(props);
		if(g.dyzurni.isEmpty()) return null;
		g.okresyDyzurow=getOkresyDyzuruFromSettings(props);
		if(g.okresyDyzurow.isEmpty()) g.okresyDyzurow=DEFAULT_OKRESY;
		int offset=Integer.parseInt(props.getProperty("monthOffset","0"));
		g.calendar.add(Calendar.MONTH, offset);
		g.stworzDyzury();	
		g.setFirma(props.getProperty("firma"));
		g.setGrupa(props.getProperty("grupa"));
		g.setTytul(props.getProperty("tytul"));
		g.setKlauzula(props.getProperty("klauzula"));
		g.setSprawdzajBledy(true);
		return g;
	}
	/**
	 * Funkcja tworzy nowy grafik dy¿urów na podstawie ustawieñ programu EdGraf wczytanych z pliku settings
	 * @param settings plik z ustawieniami EdGraf
	 * @return Obiekt grafiku lub null w razie niepowodzenia
	 * @see EdGraf#getSettings()
	 */
	public static Grafik getGrafikFromSettingsFile(File settings){
		Properties props=new Properties();
		try {
			FileInputStream fin=new FileInputStream(settings);
			props.load(fin);
			fin.close();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return GrafikFactory.getGrafikFromProperties(props);
	}
	/**
	 * Metoda zwraca podstawowy grafik z jednym dy¿urnym Janem Kowalskim 
	 * i jednym okresem dy¿uru 07.00-15.00 na nastêpny miesi¹c
	 * @return obiekt grafiku
	 */
	public static Grafik getGrafikNextMonth(){
		Grafik g=new Grafik();
		g.dyzurni=new ArrayList<String>();
		g.dyzurni.add("Jan Kowalski");
		g.okresyDyzurow=DEFAULT_OKRESY;
		g.calendar.add(Calendar.MONTH, 1);
		g.stworzDyzury();
		g.setSprawdzajBledy(true);
		return g;
	}
}
