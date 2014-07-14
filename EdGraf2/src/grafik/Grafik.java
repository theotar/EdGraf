package grafik;



import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Klasa reprezentuj�ca grafik dy�ur�w.
 * @author Wojciech Pierzchalski
 * @version 1.0
 */

public class Grafik implements Serializable{
	
	/**
	 * Klasa reprezentuj�ca ilo�� godzin maksymlanego odpoczynku w danym tygodniu.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	private static class OdpoczynekTygodniowy extends ZakresTygodnia{
		/**
		 * Ilo�� godzin maksymalnego nieprzerwanego odpoczynku w ciagu tygodnia
		 */
		double iloscGodzin=0;
		/**
		 * Tworzy nowy obiekt OdpoczynekTygodniowy z ilo�ci� godzin ustawion� na 0 
		 * oraz zakresem tygodnia wyliczonym na podstawie daty
		 * @param data - data, na podstawie, kt�rej obliczany jest zakres tygodnia.
		 */
		public OdpoczynekTygodniowy(Date data) {
			super(data);
		}
		/**
		 * Tworzy nowy obiekt OdpoczynekTygodniowy z ilo�ci� godzin ustawion� na 0 
		 * oraz z podanym zakresem tygodnia
		 * @param zt - zakres tygodnia dla obiektu.
		 */
		public OdpoczynekTygodniowy(ZakresTygodnia zt){
			super();
			this.iloscGodzin=0;
			this.koniecTygodnia=zt.koniecTygodnia;
			this.niepelnyOstatniTydzien=zt.niepelnyOstatniTydzien;
			this.niepelnyPierwszyTydzien=zt.niepelnyPierwszyTydzien;
			this.poczatekTygodnia=zt.poczatekTygodnia;
		}
		
	}
	/**
	 * Klasa reprezentuj�ca przedzia� czasowy dy�uru.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public static class CzasDyzuru implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Ilo�� godzin w przedziale czasowym.
		 */
		private double iloscGodzin;
		/**
		 * Przedzia� czasu w formie tekstowej. np '07.00-15.00'
		 */
		private String przedzialCzasu;
		/**
		 * @param przedzialCzasu - tekstowy zapis przedzia�u.
		 * @param iloscGodzin - liczba godzin w przedziale.
		 */
		public CzasDyzuru(String przedzialCzasu, double iloscGodzin) {
			this.przedzialCzasu = przedzialCzasu;
			this.iloscGodzin = iloscGodzin;
		}
		/**
		 * 
		 * @return ilo�� godzin w przedzile czasowym.
		 */
		public double getIloscGodzin() {
			return iloscGodzin;
		}
		/**
		 * 
		 * @return warto�� tekstow� przedzia�u czasu.
		 */
		public String getPrzedzialCzasu() {
			return przedzialCzasu;
		}
		/**
		 * 
		 * @param przedzialCzasu - ustawia podan� warto�� tekstow� przedzia�u czasu.
		 * @param iloscGodzin do ustawienia w przedziale.
		 */
		public void set(String przedzialCzasu,double iloscGodzin){
			this.przedzialCzasu=przedzialCzasu;
			this.iloscGodzin=iloscGodzin;
		}
		/**
		 * 
		 * @param iloscGodzin do ustawienia w przedziale.
		 */
		public void setIloscGodzin(double iloscGodzin) {
			this.iloscGodzin = iloscGodzin;
		}
		/**
		 * 
		 * @param przedzialCzasu - ustawia podan� warto�� tekstow� przedzia�u czasu.
		 */
		public void setPrzedzialCzasu(String przedzialCzasu) {
			this.przedzialCzasu = przedzialCzasu;
		}
		
		@Override
		public String toString() {
			return this.przedzialCzasu+"   "+this.iloscGodzin+" h";
		}
	}
	/**
	 * Klasa reprezentuj�ca dane dy�uru. Zawiera informajce o:<br>
	 * <li>Czy jest dy�ur</li>
	 * <li>Je�li jest dy�ur, to czy nie powoduje b��d�w</li>
	 * <li>Je�li nie ma dy�uru,a by�by, to czy spowodowa�oby to bjaki� b��d</li>
	 * @author Wojciech Pierzchalski
	 *
	 */
	public static class Dyzur implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Zbi�r b��d�w, kt�re powoduje wstawiony dy�ur typu {@link TypBledu}
		 */
		Set<TypBledu> bledy=new HashSet<TypBledu>();
		/**
		 * Czy jest dy�ur?
		 */
		boolean jest=false;
		/**
		 * Zbi�r mo�liwych b��d�w typu {@link TypBledu}, gdyby zosta� wstawiony dy�ur.
		 */
		Set<TypBledu> mozliweBledy=new HashSet<TypBledu>();
		public Dyzur(){
			
		}
		public Dyzur(Dyzur inny,boolean jest) {
			this.jest=jest;
			this.bledy=inny.bledy;
			this.mozliweBledy=inny.mozliweBledy;
		}
		/**
		 * Sprawdza czy dyzur zawiera dany b��d
		 * @param o b��d
		 * @return true je�li zawiera
		 * @see java.util.Set#contains(java.lang.Object)
		 */
		public boolean bledyContains(Object o) {
			return this.bledy.contains(o);
		}
		/**
		 * @return true je�li dyzur nie ma b��d�w
		 * @see java.util.Set#isEmpty()
		 */
		public boolean bledyIsEmpty() {
			return this.bledy.isEmpty();
		}
		public Iterator<TypBledu> bledyIterator(){
			return new Iterator<TypBledu>() {
				private Iterator<TypBledu> i=Dyzur.this.bledy.iterator();
				
				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public TypBledu next() {
					return (TypBledu) i.next();
				}

				@Override
				public void remove() {
				}
			};
		}
		/**
		 * @return liczba b�ed�w
		 * @see java.util.Set#size()
		 */
		public int bledySize() {
			return this.bledy.size();
		}
		public String bledyToHTMLString(){
			StringBuilder sb=new StringBuilder("<html>");
			for(TypBledu blad:this.bledy)
			{
				sb.append(blad.getOpis()+"<br>");
			}
			sb.append("</html>");
			return sb.toString();
		}
		/**
		 * @return the jest
		 */
		public boolean isJest() {
			return this.jest;
		}
		/**
		 * Sprawdza czy dany b��d jest mozliwy
		 * @param o b��d
		 * @return true je�li jest mo�liwy
		 * @see java.util.Set#contains(java.lang.Object)
		 */
		public boolean mozliweBledyContains(Object o) {
			return this.mozliweBledy.contains(o);
		}
		/**
		 * @return true je�li nie ma mozliwych b��d�w
		 * @see java.util.Set#isEmpty()
		 */
		public boolean mozliweBledyisEmpty() {
			return this.mozliweBledy.isEmpty();
		}
		public Iterator<TypBledu> mozliweBledyIterator(){
			return new Iterator<TypBledu>() {
				private Iterator<TypBledu> i=Dyzur.this.mozliweBledy.iterator();
				
				@Override
				public boolean hasNext() {
					return i.hasNext();
				}

				@Override
				public TypBledu next() {
					return (TypBledu) i.next();
				}

				@Override
				public void remove() {
				}
			};
		}
		/**
		 * @return liczba mozliwych b�ed�w
		 * @see java.util.Set#size()
		 */
		public int mozliweBledySize() {
			return this.mozliweBledy.size();
		}
		
	}
	/**
	 * Rodzaje b��d�w jakie mo�e generowa� �le wstaiony dy�ur. 
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public enum TypBledu{
		/**
		 * Pracownik dy�uruje tak, �e danej doby nie ma 11 godzin nieprzerwanego odpoczynku.
		 */
		BLAD_11H("B��d braku 11h nieprzerwanego odpoczunku w ci�gu doby."),
		/**
		 * Pracownik dyzuruje tak, �e w danym tygodniu nie ma 35 godzin nieprzerwanego odpoczynku.
		 */
		BLAD_35H("B��d braku 35h nieprzerwanego odpoczunku w ci�gu tygodnia."),
		/**
		 * Pracownik dy�uruje w czasie pracy tj. w dzie� powszedni w pierwszym okresie dy�uru
		 * @see TypDnia
		 * @see Grafik#okresyDyzurow
		 */
		DYZUR_W_CZASIE_PRACY("B��d pe�nienia dyzuru w czasie pracy."),
		/**
		 * Conajmniej dw�ch pracownik dy�uruje w tym samym okresie tego samego dnia
		 * @see Grafik#okresyDyzurow
		 */
		WIELOKROTNY_DYZUR("B��d pe�nienia dyzuru przez wielu pracownik�w jednocze�nie.");
		
		private final String opis;
		
		private TypBledu(String opis){
			this.opis=opis;
		}
		public String getOpis(){
			return this.opis;
		}
		
	}
	
	/**
	 * Rodzaje dni w grafiku.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public enum TypDnia{
		DZIEN_ENERGETYKA,NIEDZIELA,POWSZEDNI,SOBOTA,SWIETO
	}
	/**
	 * Klasa reprezentuj�ca zakres tygodnia w miesi�cu.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public static class ZakresTygodnia{
		/**
		 * Numer dnia miesi�ca przypadaj�cy w niedziel�
		 */
		int koniecTygodnia=0;
		/**
		 * Je�li dany tydzie� jest ostatnim w miesi�cu to:</br>
		 * Czy ostatni tydzie� miesi�ca jest niepe�ny tj. czy w danym miesi�cu przypada mniej ni� 7 dni tego tygodnia.<br>
		 * <b>Przyk�ad</b><br>
		 * 29 lutego 2012 to �roda, wi�c w lutym przypada tylko 3 dni tego tygodnia, czyli tydzie� jest <b>niepe�ny</b>
		 * <br>
		 * <br>
		 * Mo�na definiowa� to r�wnie� jako:<br>
		 * Je�li dany tydzie� jest ostatnim w miesi�cu, to czy ostatni dzie� tego miesi�ca to niedziela.
		 */
		boolean niepelnyOstatniTydzien=false;
		/**
		 * Je�li dany tydzie� jest pierwszym w miesi�cu to:</br>
		 * Czy pierwszy tydzie� miesi�ca jest niepe�ny tj. czy w danym miesi�cu przypada mniej ni� 7 dni tego tygodnia.<br>
		 * <b>Przyk�ad</b><br>
		 * 1 lutego 2012 to �roda, wi�c w lutym przypada tylko 5 dni tego tygodnia, czyli tydzie� jest <b>niepe�ny</b>
		 * <br>
		 * <br>
		 * Mo�na definiowa� to r�wnie� jako:<br>
		 * Je�li dany tydzie� jest pierwszym w miesi�cu, to czy pierwszy dzie� tego miesi�ca to poniedzia�ek.
		 * 
		 */
		boolean niepelnyPierwszyTydzien=false;
		/**
		 * Numer dnia miesi�ca przypadaj�cy w poniedzia�ek
		 */
		int poczatekTygodnia=0;
		
		/**
		 * Konstruktor domy�lny<br>
		 * Ustawia pola na 0 lub false
		 */
		private ZakresTygodnia(){
			
		}
		/**
		 * 
		 * @param data - data na podstawie, kt�rej zostanie wyliczony zakres tygodnia.
		 */
		public ZakresTygodnia(Date data) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(data);
			int dzienMiesiaca=calendar.get(Calendar.DAY_OF_MONTH);
			int numerDniaTygodnia=calendar.get(Calendar.DAY_OF_WEEK)-1;
			if(numerDniaTygodnia==0) numerDniaTygodnia=7;
			if(dzienMiesiaca-numerDniaTygodnia<0)
			{
				this.poczatekTygodnia=1;
				this.niepelnyPierwszyTydzien=true;
			}
			else
			{
				this.poczatekTygodnia=dzienMiesiaca-numerDniaTygodnia+1;
			}
			if(this.poczatekTygodnia+6<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
			{
				this.koniecTygodnia=this.poczatekTygodnia+6;
			}
			else
			{
				this.koniecTygodnia=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				this.niepelnyOstatniTydzien=true;
			}
			
		}
		/**
		 * @return the koniecTygodnia
		 */
		public int getKoniecTygodnia() {
			return this.koniecTygodnia;
		}
		/**
		 * @return the poczatekTygodnia
		 */
		public int getPoczatekTygodnia() {
			return this.poczatekTygodnia;
		}
		/**
		 * @return the niepelnyOstatniTydzien
		 */
		public boolean isNiepelnyOstatniTydzien() {
			return this.niepelnyOstatniTydzien;
		}
		/**
		 * @return the niepelnyPierwszyTydzien
		 */
		public boolean isNiepelnyPierwszyTydzien() {
			return this.niepelnyPierwszyTydzien;
		}
	
	}
	private static final long serialVersionUID = 1L;
	

	/**
	 * Firma, dla kt�rej robiony jest grafik dyzur�w.
	 */
	private String firma;
	/**
	 * Jednostka organizacyjna, dla kt�ra obowi�zuje grafik dy�ur�w.
	 */
	private String grupa;
	/**
	 * Je�li inteligentne sprawdzanie b��du 35h jest w��czone i {@link #sprawdzajNiepelnyOstatniTydzienNaBlad35h}==true,
	 * to algorytm sprawdzj�cy uznaje, �e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj�ce na nast�pny miesi�c s� wolne od dy�ur�w.
	 * @see Grafik.TypBledu#BLAD_35H 
	 */
	private boolean inteligentneSprawdzanieBledu35h=true;
	/**
	 * Klauzula bezpiecze�stwa, jak� opatrzony jest grafik dy�ur�w.
	 */
	private String klauzula;
	
	/**
	 * Czy maj� by� sprawdzane b��dy
	 * @see Grafik.TypBledu
	 * @see #sprawdzBledy()
	 */
	private boolean sprawdzajBledy=false;
	/**
	 * Czy maj� by� sprawdzane b��dy 35h, je�li ostatni tydzie� miesi�ca jest niepe�ny?
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 */
	private boolean sprawdzajNiepelnyOstatniTydzienNaBlad35h=true;
	/**
	 * Czy maj� by� sprawdzane b��dy 35h, je�li pierwszy tydzie� miesi�ca jest niepe�ny?
	 * @see Grafik.ZakresTygodnia#niepelnyPierwszyTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 */
	private boolean sprawdzajNiepelnyPierwszyTydzienNaBlad35h=false;
	/**
	 * Tytu� grafiku.
	 */
	private String tytul;
	
	
	
	
	/**
	 * Kalendarz grafiku.
	 */
	protected Calendar calendar;
	
	
	/**
	 * Lista dy�urnych.Lista nie mo�e by� pusta
	 */
	protected List<String> dyzurni;
	/**
	 * Lista dy�ur�w dla poszczeg�lnych dy�urnych.
	 */
	protected List<Dyzur[][]> dyzury;
	/**
	 * Lista okres�w dy�ur�w.Lista nie mo�e by� pusta. Czas dy�uru z indeksem 0 odpowiada czasowi pracy w dni powszednie.
	 */
	protected List<CzasDyzuru> okresyDyzurow;
	/**
	 * Domy�lny konstruktor, kt�ry tworzy obiekt grafiku z jednym dy�urnym Janem Kowalskim 
	 * i jednym okresem dy�uru 07.00-15.00
	 */
	protected Grafik(){
		this.firma="";
		this.tytul="";
		this.klauzula="";
		this.grupa="";
		this.calendar=Calendar.getInstance();
	}
	/**
	 * Sprawdza, czy na obecnie ustawiony dzie� w kalendarzu ({@link Grafik#calendar}) przypada dzie� energetyka.  
	 * @return <code>true</code> je�eli jest Dzie� Energetyka.<code>false</false> je�li nie.
	 */
	private boolean isDzienEnergetyka(){
		int m=this.calendar.get(Calendar.MONTH);
		int d=this.calendar.get(Calendar.DAY_OF_MONTH);
		return d==14 && m==Calendar.AUGUST;
	}
	/**
	 * Sprawdza, czy na obecnie ustawiony dzie� w kalendarzu przypada �wi�to wolne od pracy.
	 * @return <code>true</code> je�eli jest �wi�to.<code>false</false> je�li nie.
	 */
	private boolean isSwieto(){
		int mm=this.calendar.get(Calendar.MONTH);
		int dm=this.calendar.get(Calendar.DAY_OF_MONTH);
		int rok=this.calendar.get(Calendar.YEAR);
		
		switch(mm)
		{
			case Calendar.JANUARY:
			if(dm==1||dm==6) return true;
			break;
			case Calendar.MAY:
			if(dm==1||dm==3) return true;
			break;
			case Calendar.AUGUST:
			if(dm==15) return true;
			break;
			case Calendar.NOVEMBER:
			if(dm==1||dm==11) return true;
			break;
			case Calendar.DECEMBER:
			if(dm==25||dm==26) return true;
			break;
		}
		int a=rok%19;
		int b=(int)Math.floor(rok/100.0);
		int c=rok%100;
		int d=(int)Math.floor(b/4.0);
		int e=b%4;
		int f=(int)Math.floor((b+8)/25.0);
		int g=(int)Math.floor((b-f+1)/3.0);
		int h=(19*a+b-d-g+15)%30;
		int i=(int)Math.floor(c/4.0);
		int k=c%4;
		int l=(32+2*e+2*i-h-k)%7;
		int m=(int)Math.floor((a+11*h+22*l)/451.0);
		int p=(h+l-7*m+114)%31;
		int msc=(int)Math.floor((h+l-7*m+114)/31.0);
		
		Calendar cal=Calendar.getInstance();
		cal.set(rok, msc-1, p+1);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String dataStr=df.format(this.calendar.getTime());
		String swietoStr=df.format(cal.getTime());
		if(dataStr.equals(swietoStr)) return true;
		cal.add(Calendar.DAY_OF_MONTH, 1);
		swietoStr=df.format(cal.getTime());
		if(dataStr.equals(swietoStr)) return true;
		cal.add(Calendar.DAY_OF_MONTH, 48);
		swietoStr=df.format(cal.getTime());
		if(dataStr.equals(swietoStr)) return true;
		cal.add(Calendar.DAY_OF_MONTH, 11);
		swietoStr=df.format(cal.getTime());
		if(dataStr.equals(swietoStr)) return true;
		return false;
		
	}
	/**
	 * Sprawdza, ile dany dy�urny ma nieprzerwanego odpoczynku w danym dniu.<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi�cu. 
	 * @param dyzurnyIdx - Indeks dy�urnego
	 * @param dzienMiesiaca - numer dnia miesi�ca.
	 * @return liczba godzin nieprzerwanego odpoczynku
	 * @see Grafik.TypBledu#BLAD_11H
	 * @see Grafik#dyzurni
	 */
	private double maxOdpoczynekDobowy(int dyzurnyIdx,int dzienMiesiaca){
		double max=0;
		double odp=0;
		if(this.isDzienWolny(dzienMiesiaca)&&!this.isDyzur(dyzurnyIdx, 0, dzienMiesiaca))
		{
			odp=this.getIloscGodzinDyzuru(0);
			max=odp;
		}
		for(int czasDyzuruIdx=1;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
		{
			if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca))
			{
				if(odp>max) max=odp;
				odp=0;
			}
			else 
			{
				odp+=this.getCzasDyzuru(czasDyzuruIdx).iloscGodzin;
			}
		}
		if(odp>=max) max=odp;
		return max;
	}
	/**
	 * Sprawdza, ile dany dy�urny ma nieprzerwanego odpoczynku w danym dniu, je�li zak�ada si�, �e w okresie 
	 * <code>czasDyzuruIdx</code> b�dzie pe�ni� dy�ur<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi�cu. 
	 * @param dyzurnyIdx - Indeks dy�urnego
	 * @param czasDyzuruIdx - Indeks okresu dy�uru, w kt�rym zak�ada si�, �e pracownik b�dzie dy�urowa�.
	 * @param dzienMiesiaca - numer dnia miesi�ca.
	 * @return liczba godzin nieprzerwanego odpoczynku
	 * @see Grafik.TypBledu#BLAD_11H
	 * @see Grafik#dyzurni
	 * @see Grafik#okresyDyzurow
	 */
	private double maxOdpoczynekDobowy(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		double max=0;
		double odp=0;
		if(this.isDzienWolny(dzienMiesiaca)&&!this.isDyzur(dyzurnyIdx, 0, dzienMiesiaca)&&czasDyzuruIdx!=0)
		{
			odp=this.getIloscGodzinDyzuru(0);
			max=odp;
		}
		for(int cdi=1;cdi<this.ileOkresowDyzurow();cdi++)
		{
			if(this.isDyzur(dyzurnyIdx, cdi, dzienMiesiaca)||cdi==czasDyzuruIdx)
			{
				if(odp>max) max=odp;
				odp=0;
			}
			else 
			{
				odp+=this.getCzasDyzuru(cdi).iloscGodzin;
			}
		}
		if(odp>=max) max=odp;
		return max;
	}
	/**
	 * Sprawdza, ile dany dy�urny ma nieprzerwanego odpoczynku w danym tygodniu.<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi�cu. 
	 * @param dyzurnyIdx - Indeks dy�urnego
	 * @param dzienMiesiaca - numer dnia miesi�ca,na podstawie, kt�rego okreslony zostanie odpowiedni tydzie�.
	 * @return liczba godzin nieprzerwanego odpoczynku w tygodniu.
	 * @see Grafik.TypBledu#BLAD_35H
	 * @see Grafik#dyzurni
	 * @see Grafik#okresyDyzurow
	 */
	private OdpoczynekTygodniowy maxOdpoczynekTygodniowy(int dyzurnyIdx,int dzienMiesiaca){
		double max=0;
		double odp=0;
		
		this.calendar.set(Calendar.DAY_OF_MONTH, dzienMiesiaca);
		OdpoczynekTygodniowy ot=new OdpoczynekTygodniowy(this.calendar.getTime());
		
		for(int dm=ot.poczatekTygodnia;dm<=ot.koniecTygodnia;dm++)
		{
			for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
			{
				if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dm))
				{
					if(odp>max) max=odp;
					odp=0;
				}
				else
				{
					if(czasDyzuruIdx==0&&!this.isDzienWolny(dm))
					{
						if(odp>max) max=odp;
						odp=0;
					}
					else odp+=this.getIloscGodzinDyzuru(czasDyzuruIdx);
				}
			}
		}
		if(ot.niepelnyOstatniTydzien&&this.inteligentneSprawdzanieBledu35h)
		{
			this.calendar.set(Calendar.DAY_OF_MONTH, ot.koniecTygodnia);
			int numerDniaTygodnia=this.calendar.get(Calendar.DAY_OF_WEEK);
			if(numerDniaTygodnia==Calendar.SATURDAY) odp+=24;
			else if(numerDniaTygodnia==Calendar.FRIDAY) odp+=48;
			else if(numerDniaTygodnia==Calendar.SUNDAY) throw new RuntimeException("Nieoczekiwany b��d niepe�nego tygodnia");
			else if(odp<48) odp=48;
		}
		if(odp>max) max=odp;
		ot.iloscGodzin=max;
		return ot;	
	}
	/**
	 * Sprawdza, ile dany dy�urny ma nieprzerwanego odpoczynku w danym tygodniu, je�li zak�ada si�, �e
	 * w okresie <code>czasDyzuruIdx</code> i dnia <code>dzienMiesiaca</code> pracownik pe�ni dyzur<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi�cu. 
	 * @param dyzurnyIdx - Indeks dy�urnego
	 * @param czasDyzuruIdx - indeks okresu hipotetyczngeo dy�uru.
	 * @param dzienMiesiaca - numer dnia, w kt�rym pe�niony b�dzie hipotetyczny dy�ur.
	 * @param zt - Zakres tygodnia, jaki ma zosta� zbadany.
	 * @return liczba godzin nieprzerwanego odpoczynku w tygodniu.
	 * @see Grafik.TypBledu#BLAD_35H
	 * @see Grafik#dyzurni
	 * @see Grafik#okresyDyzurow
	 */
	private OdpoczynekTygodniowy maxOdpoczynekTygodniowy(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca,ZakresTygodnia zt){
		double max=0;
		double odp=0;
		
		OdpoczynekTygodniowy ot=new OdpoczynekTygodniowy(zt);
		
		for(int dm=zt.poczatekTygodnia;dm<=zt.koniecTygodnia;dm++)
		{
			for(int cdi=0;cdi<this.ileOkresowDyzurow();cdi++)
			{
				if(this.isDyzur(dyzurnyIdx, cdi, dm)||(dm==dzienMiesiaca&&cdi==czasDyzuruIdx))
				{
					if(odp>max) max=odp;
					odp=0;
				}
				else
				{
					if(cdi==0&&!this.isDzienWolny(dm))
					{
						if(odp>max) max=odp;
						odp=0;
					}
					else odp+=this.getIloscGodzinDyzuru(cdi);
				}
			}
		}
		if(zt.niepelnyOstatniTydzien&&this.inteligentneSprawdzanieBledu35h)
		{
			this.calendar.set(Calendar.DAY_OF_MONTH, zt.koniecTygodnia);
			int numerDniaTygodnia=this.calendar.get(Calendar.DAY_OF_WEEK);
			if(numerDniaTygodnia==Calendar.SATURDAY) odp+=24;
			else if(numerDniaTygodnia==Calendar.FRIDAY) odp+=48;
			else if(numerDniaTygodnia==Calendar.SUNDAY) throw new RuntimeException("Nieoczekiwany b��d niepe�nego tygodnia");
			else if(odp<48) odp=48;
		}
		if(odp>max) max=odp;
		ot.iloscGodzin=max;
		return ot;	
	}
	/**
	 * Pomniejsza tablic� dy�ur�w.
	 * 
	 */
	private boolean pomniejszDyzury(){
		if(this.dyzury.get(0).length<2) return false;
		
		for(int i=0;i<this.dyzurni.size();i++)
		{
			Dyzur[][] stareDyzury=this.dyzury.get(i);
			Dyzur[][] noweDyzury=new Dyzur[stareDyzury.length-1][];
			for(int j=0;j<noweDyzury.length;j++)
			{
				noweDyzury[j]=stareDyzury[j];
			}
			this.dyzury.set(i, noweDyzury);
		}
		return true;
	}
	/**
	 * Powi�ksza tablic� dy�ur�w
	 */
	private void powiekszDyzury(){
		for(int i=0;i<this.iluDyzurnych();i++)
		{
			Dyzur[][] stareDyzury=this.dyzury.get(i);
			Dyzur[][] noweDyzury=new Dyzur[stareDyzury.length+1][];
			for(int j=0;j<stareDyzury.length;j++)
			{
				noweDyzury[j]=stareDyzury[j];
			}
			noweDyzury[stareDyzury.length]=new Dyzur[this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
			for(int d=0;d<this.ileDniMiesiaca();d++)
				noweDyzury[stareDyzury.length][d]=new Dyzur();
			this.dyzury.set(i, noweDyzury);
		}
	}
	/**
	 * Tworzy tablic� dy�ur�w o wymiarach:<br>
	 * <code>
	 * [okresyDyzurow.size()][ileDniMiesiaca()]
	 * </code>
	 * @return tablic� dy�ur�w odwzorowuj�c� dy�ury jednego dy�urnego
	 * @see #okresyDyzurow
	 * @see #ileDniMiesiaca()
	 */
	private Dyzur[][] stworzDyzuryDlaJednegoDyzurnego(){
		int iloscOkresowDyzurow=this.ileOkresowDyzurow();
		int iloscDniWMiesiacu=this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Dyzur[][] dyzury= new Dyzur[iloscOkresowDyzurow][iloscDniWMiesiacu];
		for(int i=0;i<iloscOkresowDyzurow;i++)
			for(int j=0;j<iloscDniWMiesiacu;j++)
				dyzury[i][j]=new Dyzur();
		return dyzury;
	}
	/**
	 * Podaje Czas dy�uru wskazanego przez indeks.
	 * @param idx indeks czasu dy�uru
	 * @return czas dy�uru
	 * @throws IllegalArgumentException je�eli idx poza zakresem
	 * @see Grafik.CzasDyzuru
	 */
	protected CzasDyzuru getCzasDyzuru(int idx){
		if(idx<0||idx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dy�uru poza zakresem");
		return this.okresyDyzurow.get(idx);
	}
	/**
	 * Dostosowuje struktur� danych przechowuj�c� dy�ury do nowej liczby okres�w dy�ur�w.
	 */
	protected void nowyRozmiarDyzurow(){
		int stary=this.dyzury.get(0).length;
		int nowy=this.ileOkresowDyzurow();
		if(nowy>stary) this.powiekszDyzury();
		else if(nowy<stary) this.pomniejszDyzury();
	}
	/**
	 * Sprawdza, czy wyst�pi� b��d {@link Grafik.TypBledu#BLAD_11H}<br>
	 * dla danego dy�urnego, danego dnia i w danym okresie dy�uru.
	 * Je�li wyst�pi� b��d dodaje go do zbioru b��d�w dy�uru.<br>
	 * Sprawdza r�wnie� i oznacza mo�liwo�� wyst�pienia tego b��du.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca pe�nionego dy�uru.
	 */
	protected void sprawdzBlad11h(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(!this.isSprawdzajBledy()) return;
			boolean ok;
			if(this.maxOdpoczynekDobowy(dyzurnyIdx, dzienMiesiaca)<11) ok=false;
			else ok=true;
			for(int i=0;i<this.ileOkresowDyzurow();i++)
				if(this.isDyzur(dyzurnyIdx, i, dzienMiesiaca)) 
				{
					if(!ok)
					{
						this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).bledy.add(TypBledu.BLAD_11H);
						this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).mozliweBledy.remove(TypBledu.BLAD_11H);
					}
					else
					{
						this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).bledy.remove(TypBledu.BLAD_11H);
						this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).mozliweBledy.remove(TypBledu.BLAD_11H);
					}		
				}
				else
				{
					this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).bledy.remove(TypBledu.BLAD_11H);
					if(!ok) this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).mozliweBledy.add(TypBledu.BLAD_11H);
					else if(this.maxOdpoczynekDobowy(dyzurnyIdx,i, dzienMiesiaca)<11)
						this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).mozliweBledy.add(TypBledu.BLAD_11H);
					else this.getDyzur(dyzurnyIdx, i, dzienMiesiaca).mozliweBledy.remove(TypBledu.BLAD_11H);
				}
				
	}
	/**
	 * Sprawdza, czy wyst�pi� b��d {@link Grafik.TypBledu#BLAD_35H}<br>
	 * dla danego dy�urnego, danego dnia i w danym okresie dy�uru.
	 * Je�li wyst�pi� b��d dodaje go do zbioru b��d�w dy�uru.<br>
	 * Sprawdza r�wnie� i oznacza mo�liwo�� wyst�pienia tego b��du.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param dzienMiesiaca dzie� miesi�ca pe�nionego dy�uru.
	 * @see #sprawdzajNiepelnyPierwszyTydzienNaBlad35h
	 * @see #sprawdzajNiepelnyOstatniTydzienNaBlad35h
	 * @see #inteligentneSprawdzanieBledu35h
	 */
	protected void sprawdzBlad35h(int dyzurnyIdx,int dzienMiesiaca){
		if(!this.isSprawdzajBledy()) return;
		OdpoczynekTygodniowy ot=this.maxOdpoczynekTygodniowy(dyzurnyIdx, dzienMiesiaca);
		if(!this.sprawdzajNiepelnyPierwszyTydzienNaBlad35h&&ot.niepelnyPierwszyTydzien)
		{
			for(int dm=ot.poczatekTygodnia;dm<=ot.koniecTygodnia;dm++)
				for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
				{
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).bledy.remove(TypBledu.BLAD_35H);
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.remove(TypBledu.BLAD_35H);
				}
		}
		else if(!this.sprawdzajNiepelnyOstatniTydzienNaBlad35h&&ot.niepelnyOstatniTydzien)
		{
			for(int dm=ot.poczatekTygodnia;dm<=ot.koniecTygodnia;dm++)
				for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
				{
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).bledy.remove(TypBledu.BLAD_35H);
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.remove(TypBledu.BLAD_35H);
				}
		}
		else if(ot.iloscGodzin<35)
		{
			for(int dm=ot.poczatekTygodnia;dm<=ot.koniecTygodnia;dm++)
				for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
					if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dm))
					{
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).bledy.add(TypBledu.BLAD_35H);
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.remove(TypBledu.BLAD_35H);
					}
					else 
					{
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).bledy.remove(TypBledu.BLAD_35H);
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.add(TypBledu.BLAD_35H);
					}
		}
		else 
		{
			for(int dm=ot.poczatekTygodnia;dm<=ot.koniecTygodnia;dm++)
				for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
				{
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).bledy.remove(TypBledu.BLAD_35H);
					if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dm))
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.remove(TypBledu.BLAD_35H);
					else if(this.maxOdpoczynekTygodniowy(dyzurnyIdx, czasDyzuruIdx, dm, ot).iloscGodzin<35)
						this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.add(TypBledu.BLAD_35H);
					else this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dm).mozliweBledy.remove(TypBledu.BLAD_35H);
				}
		}
	}
	/**
	 * Sprawdza, czy wyst�pi� b��d {@link Grafik.TypBledu#DYZUR_W_CZASIE_PRACY}<br>
	 * dla danego dy�urnego, danego dnia i w danym okresie dy�uru.
	 * Je�li wyst�pi� b��d dodaje go do zbioru b��d�w dy�uru.<br>
	 * Sprawdza r�wnie� i oznacza mo�liwo�� wyst�pienia tego b��du.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca pe�nionego dy�uru.
	 */
	protected void sprawdzBladPracy(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(!this.isSprawdzajBledy()) return;
		boolean dzienWolny=this.isDzienWolny(dzienMiesiaca);
		if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca))
		{
			if(czasDyzuruIdx!=0)
				this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.DYZUR_W_CZASIE_PRACY);
			else if(dzienWolny)
				this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.DYZUR_W_CZASIE_PRACY);
			else 
			{
				this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.add(TypBledu.DYZUR_W_CZASIE_PRACY);
				this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.remove(TypBledu.DYZUR_W_CZASIE_PRACY);
			}
		}
		else 
		{
			this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.DYZUR_W_CZASIE_PRACY);
			if(!dzienWolny&&czasDyzuruIdx==0)
				this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.add(TypBledu.DYZUR_W_CZASIE_PRACY);
		}
	}
	/**
	 * Sprawdza, czy wyst�pi� b��d {@link Grafik.TypBledu#WIELOKROTNY_DYZUR}<br>
	 * dla danego dy�urnego, danego dnia i w danym okresie dy�uru.
	 * Je�li wyst�pi� b��d dodaje go do zbioru b��d�w dy�uru.<br>
	 * Sprawdza r�wnie� i oznacza mo�liwo�� wyst�pienia tego b��du.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca pe�nionego dy�uru.
	 */
	protected void sprawdzBladWielokrotnegoDyzuru(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(!this.isSprawdzajBledy()) return;
		if(this.iluDyzurnych()<2)
		{
			this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.WIELOKROTNY_DYZUR);
			this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.remove(TypBledu.WIELOKROTNY_DYZUR);
			return;
		}
		if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca))
		{
			boolean jestInny=false;
			for(int i=0;i<this.iluDyzurnych();i++)
			{
				if(i==dyzurnyIdx) continue;
				if(this.isDyzur(i, czasDyzuruIdx, dzienMiesiaca))
				{
					this.getDyzur(i, czasDyzuruIdx, dzienMiesiaca).bledy.add(TypBledu.WIELOKROTNY_DYZUR);
					jestInny=true;
				}
				else this.getDyzur(i, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.add(TypBledu.WIELOKROTNY_DYZUR);
			}
			if(jestInny) this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.add(TypBledu.WIELOKROTNY_DYZUR);
			else this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.WIELOKROTNY_DYZUR);
		}
		else
		{
			this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.remove(TypBledu.WIELOKROTNY_DYZUR);
			this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.remove(TypBledu.WIELOKROTNY_DYZUR);
			for(int i=0;i<this.iluDyzurnych();i++)
			{
				if(this.isDyzur(i, czasDyzuruIdx, dzienMiesiaca))
				{
					this.sprawdzBladWielokrotnegoDyzuru(i, czasDyzuruIdx, dzienMiesiaca);
					return;
				}
				else this.getDyzur(i, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.remove(TypBledu.WIELOKROTNY_DYZUR);
			}
		}
	}
	/**
	 * Sprawdza i oznacza wszystkie b��dy jakie wyst�puj� i mog� wyst�pi� w ca�ym grafiku.
	 * @see Grafik.TypBledu
	 * @see #sprawdzBledyPracy()
	 * @see #sprawdzBledyWielokrotnegoDyzuru()
	 * @see #SprawdzBledy11h()
	 * @see #sprawdzBledy35h()
	 */
	protected void sprawdzBledy(){
		if(!this.isSprawdzajBledy()) return;
		this.sprawdzBledyPracy();
		this.sprawdzBledyWielokrotnegoDyzuru();
		this.SprawdzBledy11h();
		this.sprawdzBledy35h();
	}
	/**
	 * Sprawdza i oznacza wszystkie b�edy, jakie wyst�puja lub mog� wyst�pi� dla dy�uru pe�nionego danego dnia,
	 * w danym okresie, przez danego dy�urnego.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca pe�nionego dy�uru.
	 * @see Grafik.TypBledu
	 * @see #sprawdzBladPracy(int, int, int)
	 * @see #sprawdzBladWielokrotnegoDyzuru(int, int, int)
	 * @see #sprawdzBlad11h(int, int, int)
	 * @see #sprawdzBlad35h(int, int)
	 */
	protected void sprawdzBledy(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(!this.isSprawdzajBledy()) return;
		this.sprawdzBladPracy(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca);
		this.sprawdzBladWielokrotnegoDyzuru(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca);
		this.sprawdzBlad11h(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca);
		this.sprawdzBlad35h(dyzurnyIdx, dzienMiesiaca);
	}
	/**
	 * Sprawdza i oznacza ca�y grafik w poszukiwaniu b��d�w {@link Grafik.TypBledu#BLAD_11H} 
	 * oraz mo�liwo�ci ich wyst�pienia.
	 */
	protected void SprawdzBledy11h(){
		if(!this.isSprawdzajBledy()) return;
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBlad11h(dyzurnyIdx, 0 , dzienMiesiaca);
	}
	/**
	 * Sprawdza i oznacza ca�y grafik w poszukiwaniu b��d�w {@link Grafik.TypBledu#BLAD_35H} 
	 * oraz mo�liwo�ci ich wyst�pienia.
	 * @see #sprawdzajNiepelnyPierwszyTydzienNaBlad35h
	 * @see #sprawdzajNiepelnyOstatniTydzienNaBlad35h
	 * @see #inteligentneSprawdzanieBledu35h
	 */
	protected void sprawdzBledy35h(){
		if(!this.isSprawdzajBledy()) return;
		int dm=1;
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
		{
			dm=1;
			for(dm=1;dm<=this.ileDniMiesiaca();dm+=7)
				this.sprawdzBlad35h(dyzurnyIdx, dm);
			dm-=7;
			if(this.ileDniMiesiaca()-dm>0)
				this.sprawdzBlad35h(dyzurnyIdx, dm+1);	
		}
	}
	
	
	/**
	 * Sprawdza i oznacza ca�y grafik w poszukiwaniu b��d�w {@link Grafik.TypBledu#DYZUR_W_CZASIE_PRACY} 
	 * oraz mo�liwo�ci ich wyst�pienia.
	 */
	protected void sprawdzBledyPracy(){
		if(!this.isSprawdzajBledy()) return;
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBladPracy(dyzurnyIdx, 0 , dzienMiesiaca);
	}
	/**
	 * Sprawdza i oznacza ca�y grafik w poszukiwaniu b��d�w {@link Grafik.TypBledu#WIELOKROTNY_DYZUR} 
	 * oraz mo�liwo�ci ich wyst�pienia.
	 */
	protected void sprawdzBledyWielokrotnegoDyzuru(){
		if(!this.isSprawdzajBledy()) return;
		for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBladWielokrotnegoDyzuru(0, czasDyzuruIdx, dzienMiesiaca);
	}
	/**
	 * Tworzy struktur� danych przechowuj�c� dy�ury.
	 * @see #dyzury
	 */
	protected void stworzDyzury(){
		this.dyzury=new ArrayList<Dyzur[][]>();
		int iloscDyzurnych=this.iluDyzurnych();
		
		for(int i=0;i<iloscDyzurnych;i++)
			this.dyzury.add(this.stworzDyzuryDlaJednegoDyzurnego());
	}
	/**
	 * Czysci wszystkie b��dy i mo�liwe b��dy z grafiku.
	 * @see Grafik.TypBledu
	 */
	protected void wyczyscBledy(){
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
			for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
				for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				{
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.clear();
					this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.clear();
				}
	}
	/**
	 * Dodaje nowy okres dy�uru do okres�w dy�ur�w i sprawdza b��dy.
	 * @param cd nowy okres dy�uru
	 * @see #sprawdzBledy()
	 */
	public void addCzasDyzuru(CzasDyzuru cd){
		this.okresyDyzurow.add(cd);
		this.nowyRozmiarDyzurow();
		this.sprawdzBledy();
		
	}
	/**
	 * Dodaje do grafiku nowego dy�urnego.
	 * @param dyzurny Nazwisko i Imi� nowego dyzurnego
	 */
	public void addDyzurny(String dyzurny){
		this.dyzurni.add(dyzurny);
		this.dyzury.add(this.stworzDyzuryDlaJednegoDyzurnego());
		this.sprawdzBledy();
	}
	/**
	 * @return data grafiku
	 */
	public Date getDate(){
		return this.calendar.getTime();
	}
	/**
	 * Zwraca obiekt dy�uru dla danego dyzurnego, w danym dniu i w danym okresie
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� dy�uru
	 * @return obiekt dy�uru
	 * @see Grafik.Dyzur
	 */
	public Dyzur getDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.dyzury.get(dyzurnyIdx)[czasDyzuruIdx][dzienMiesiaca-1];
	}
	/**
	 * Podaje Naziwkso i Imi� dy�urnego wskazanego przez indeks.
	 * @param idx indeks dy�urnego
	 * @return Naziwsko i Imi� dy�urnego
	 * @throws IllegalArgumentException je�eli idx poza zakresem
	 */
	public String getDyzurny(int idx){
		if(idx<0||idx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy�urnego poza zakresem");
		return this.dyzurni.get(idx);
	}
	/**
	 * Zwraca tablic� dy�ur�w dla danego dyzurnego, danego dnia
	 * @param dyzurnyIdx indeks dyzurnego
	 * @param dzienMiesiaca numer dnia miesi�ca
	 * @return tablic� dy�ur�w
	 */
	public Dyzur[] getDyzury(int dyzurnyIdx,int dzienMiesiaca){
		Dyzur[] tab=new Dyzur[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getDyzur(dyzurnyIdx, i, dzienMiesiaca);
		return tab;
	}
	/**
	 * Zwraca firm�, dla kt�rej zrobiony jest grafik.
	 * @return firma
	 */
	public String getFirma() {
		return firma;
	}
	/**
	 * Zwraca jednostk� organizacyjn�, dla kt�rej zrobiony jest grafik.
	 * @return jednostka organizacyjna.
	 */
	public String getGrupa() {
		return grupa;
	}
	/**
	 * Podaje ilo�� godzin dy�uru w okresie wskazanym przez indeks.
	 * @param idx indeks okresu dy�uru
	 * @return ilo�� godzin dy�uru
	 * @see Grafik.CzasDyzuru#iloscGodzin
	 */
	public double getIloscGodzinDyzuru(int idx){
		return this.getCzasDyzuru(idx).iloscGodzin;
	}
	/**
	 * Zwraca tablic� ilo�ci godzin w okresach dy�ur�w
	 * @return tablica ilo�ci godzin w okresach dyzur�w
	 */
	public Double[] getIlosciGodzinDyzuru(){
		Double[] tab=new Double[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getIloscGodzinDyzuru(i);
		return tab;
	}
	/**
	 * Zwraca klauzul� bezpiecze�stwa jak� opatrzony jest grafik.
	 * @return klauzula
	 */
	public String getKlauzula() {
		return klauzula;
	}
	/**
	 * Podaje aktualny miesi�c i rok grafiku w formacie "MMMM yyyy" klasy {@link SimpleDateFormat}
	 * @return miesi�c i rok
	 * @see SimpleDateFormat#format(Date)
	 */
	public String getMiesiacRok(){
		Date data=this.calendar.getTime();
		SimpleDateFormat df=new SimpleDateFormat("MMMM yyyy");
		return df.format(data).toUpperCase();
	}
	/**
	 * 
	 * @param index indeks okresu dyzuru
	 * @return czas dyzuru 
	 * @see java.util.List#get(int)
	 */
	public CzasDyzuru getOkresDyzuru(int index) {
		return this.okresyDyzurow.get(index);
	}
	/**
	 * Podaje przedzia� czasu dy�uru w okresie wskazanym przez indeks.
	 * @param idx indeks okresu dy�uru
	 * @return tekstowa reprezentacja czasu dy�uru
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public String getPrzedzialCzasuDyzuru(int idx){
		return this.getCzasDyzuru(idx).przedzialCzasu;
	}
	/**
	 * Zwraca tablic� tekstowych reprezentacji przedzia��w czasu dy�uru.
	 * @return tablic� przedzia��w
	 */
	public String[] getPrzedzialyCzasu(){
		String[] tab=new String[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getPrzedzialCzasuDyzuru(i);
		return tab;
	}
	/**
	 * Zwraca tytu� grafiku
	 * @return tytu� grafiku
	 */
	public String getTytul() {
		return tytul;
	}
	/**
	 * Zwraca zakres tygodnia w danym dniu
	 * @param dzienMiesiaca numer dnia miesi�ca
	 * @return zakres tygodnia
	 * @see Grafik.ZakresTygodnia
	 */
	public ZakresTygodnia getZakresTygodnia(int dzienMiesiaca){
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) return null;
		Calendar cal=Calendar.getInstance();
		cal.setTime(this.calendar.getTime());
		cal.set(Calendar.DAY_OF_MONTH, dzienMiesiaca);
		return new ZakresTygodnia(cal.getTime());
	}
	/**
	 * Podaje liczb� dni aktualnego miesi�ca grafiku
	 * @return liczba dni miesi�ca
	 */
	public int ileDniMiesiaca(){
		return this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * Zwraca ile godzin dy�uruje w miesi�cu dy�urny wskazany przez indeks
	 * @param dyzurnyIdx indeks dy�urnego
	 * @return liczba godzin 
	 */
	public double ileGodzin(int dyzurnyIdx){
		if(dyzurnyIdx<0||dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy�urnego poza zakresem");
		double suma=0;
		for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca)) suma+=this.getIloscGodzinDyzuru(czasDyzuruIdx);
		return suma;
	}
	/**
	 * Zwraca liczb� okres�w dy�ur�w w grafiku
	 * @return liczba okres�w
	 */
	public int ileOkresowDyzurow(){
		return this.okresyDyzurow.size();
	}
	/**
	 * Zwraca liczb� dy�urnych w grafiku
	 * @return liczba dyzurnych
	 */
	public int iluDyzurnych(){
		return this.dyzurni.size();
	}
	/**
	 * Sprawdza czy dla danego dy�urnego, danego dnia i w danym okresie dy�ur powoduje b��d.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca
	 * @return <code>true</code> je�eli dy�ur powoduje b��d, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.TypBledu
	 */
	public boolean isBlad(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.size()!=0;
	}
	/**
	 * Sprawdza, czy dany dyzurnego, w danym dniu i w danym okresie pe�ni dy�ur
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� dy�uru
	 * @return <code>true</code> je�li pe�ni, <code>false</code> je�li nie.
	 */
	public boolean isDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(dyzurnyIdx<0 || dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy�urnego poza zakresem");
		if(czasDyzuruIdx<0||czasDyzuruIdx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dyzuru poza zakresem");
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) throw new IllegalArgumentException("Nieporawny numer dnia miesi�ca");
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).jest;
	}
	/**
	 * Sprawdza, czy dany dzie� nie jest {@link Grafik.TypDnia#POWSZEDNI}
	 * @param dzienMiesiaca numer dnia miesi�ca do sprawdzenia
	 * @return <code> {@link #jakiDzien(int)} != POWSZEDNI</code>
	 */
	public boolean isDzienWolny(int dzienMiesiaca){
		return this.jakiDzien(dzienMiesiaca)!=TypDnia.POWSZEDNI;
	}
	/**
	 * Sprawdza, czy inteligentne sprawdzanie b��du 35h jest w��czone<br>
	 * Je�li inteligentne sprawdzanie b��du 35h jest w��czone i 
	 * {@link #isSprawdzajNiepelnyOstatniTydzienNaBlad35h()} zwraca <code>true</code>,
	 * to algorytm sprawdzj�cy uznaje, �e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj�ce na nast�pny miesi�c s� wolne od dy�ur�w.
	 * @return <code> true </code> jesli inteligentne sprawdzanie jest w��czone, <code>false</code> w przeciwnym wypadku.
	 * @see Grafik.TypBledu#BLAD_35H 
	 * @see #sprawdzBlad35h(int, int)
	 */
	public boolean isInteligentneSprawdzanieBledu35h() {
		return inteligentneSprawdzanieBledu35h;
	}
	
	
	/**
	 * Sprawdza czy dla danego dy�urnego, danego dnia i w danym okresie wstawienie dy�uru spowoduje b��d.
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� miesi�ca
	 * @return <code>true</code> je�eli dy�ur powoduje b��d, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.TypBledu
	 */
	public boolean isMozliwyBlad(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.size()!=0;
	}

	/**
	 * Czy grafik sprawdza b��dy
	 * @return <code>true</code> je�li sprawdza, <code>false</code> je�li nie.
	 */
	public boolean isSprawdzajBledy() {
		return this.sprawdzajBledy;
	}

	/**
	 * Sprawdza czy maj� by� sprawdzane b��dy 35h, je�li ostatni tydzie� miesi�ca jest niepe�ny.
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @return <code> true </code> jesli maj� by� sprawdzane, <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isSprawdzajNiepelnyOstatniTydzienNaBlad35h() {
		return sprawdzajNiepelnyOstatniTydzienNaBlad35h;
	}

	/**
	 * Sprawdza czy maj� by� sprawdzane b��dy 35h, je�li pierwszy tydzie� miesi�ca jest niepe�ny.
	 * @see Grafik.ZakresTygodnia#niepelnyPierwszyTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @return <code> true </code> jesli maj� by� sprawdzane, <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isSprawdzajNiepelnyPierwszyTydzienNaBlad35h() {
		return sprawdzajNiepelnyPierwszyTydzienNaBlad35h;
	}

	/**
	 * Podaje jaki dzie� przypada na podany dzie� miesi�ca.
	 * @param dzienMiesiaca dzie� miesi�ca do sprawdzenia
	 * @return typ dnia
	 * @throws IllegalArgumentException je�eli <code> dzienMiesiaca <1 || dzienMiesiaca > ileDniMiesiaca()</code>
	 * @see Grafik.TypDnia
	 */
	public TypDnia jakiDzien(int dzienMiesiaca){
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) throw new IllegalArgumentException("Dzien miesiaca poza zakresem");
		calendar.set(Calendar.DAY_OF_MONTH, dzienMiesiaca);
		int dzienTygodnia = calendar.get(Calendar.DAY_OF_WEEK);
		TypDnia td=TypDnia.POWSZEDNI;
		switch(dzienTygodnia)
		{
			case Calendar.SUNDAY: td=TypDnia.NIEDZIELA;break;
			case Calendar.SATURDAY: td=TypDnia.SOBOTA;break;
			default:td=TypDnia.POWSZEDNI;
		}
		if(this.isDzienEnergetyka()) return TypDnia.DZIEN_ENERGETYKA;
		if(this.isSwieto()) return TypDnia.SWIETO;
		return td;
		
	}

	/**
	 * Usuwa okres dy�uru wskazany przez indeksi sprawdza b��dy
	 * @param idx indeks dy�uru
	 * @return <code>true</code> je�eli usuni�to okres dy�uru, <code>false</code> w przeciwnym wypadku
	 * @see #sprawdzBledy()
	 */
	public boolean removeCzasDyzuru(int idx){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		if(this.ileOkresowDyzurow()<2) return false;
		this.okresyDyzurow.remove(idx);
		this.nowyRozmiarDyzurow();
		this.sprawdzBledy();
		return true;
	}

	/**
	 * Usuwa z grafiku dy�urnego wskazanego przez indeks, pod warunkiem, �e po usuni�ciu zostanie conajmniej 1 dy�urny.
	 * @param idx indeks dy�urnego
	 * @return <code>true</code> je�eli usuni�to dy�urnego, <code>false</code> w przeciwnym wypadku
	 */
	public boolean removeDyzurny(int idx){
		if(idx<0||idx>=this.iluDyzurnych()) return false;
		if(this.iluDyzurnych()<2) return false;
		this.dyzurni.remove(idx);
		this.dyzury.remove(idx);
		this.sprawdzBledy();
		return true;
	}

	/**
	 * Usuwa z grafiku dy�urnego wskazanego przez Imie i Nazwisko, pod warunkiem, �e po usuni�ciu zostanie conajmniej 1 dy�urny.
	 * @param dyzurny  Nazwisko i Imi� dy�urnego
	 * @return <code>true</code> je�eli usuni�to dy�urnego, <code>false</code> w przeciwnym wypadku
	 */
	public boolean removeDyzurny(String dyzurny){
		int idx=this.dyzurni.indexOf(dyzurny);
		return this.removeDyzurny(idx);
	}
	/**
	 * Ustawia nowy czas dy�uru zamiast wskazanego przez indeks i sprawdza b��dy
	 * @param idx indeks okresu dy�uru
	 * @param czasDyzuru nowy okres dy�uru
	 * @return <code>true</code> je�eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see #sprawdzBledy()
	 */
	public boolean setCzasDyzuru(int idx,CzasDyzuru czasDyzuru){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.okresyDyzurow.set(idx, czasDyzuru);
		return true;
	}

	/**
	 * Ustawia nowe paramatery czasu dy�uru wskazanego przez indeks i sprawdza b��dy
	 * @param idx indeks okresu dy�uru
	 * @param przedzialCzasu tekstowa reprezentacja okresu dy�uru
	 * @param iloscGodzin ilo�� godzin dy�uru
	 * @return <code>true</code> je�eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see #sprawdzBledy()
	 */
	public boolean setCzasDyzuru(int idx,String przedzialCzasu,double iloscGodzin){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.getCzasDyzuru(idx).set(przedzialCzasu, iloscGodzin);
		this.sprawdzBledy();
		return true;
	}

	/**
	 * Ustawia dyzur w zale�no�ci od parametru <code>jestDyzur</code> dla danego dyzurnego, w danym dniu i w danym okresie,
	 * a nast�pnie sprawdza b��dy. 
	 * @param dyzurnyIdx indeks dy�urnego
	 * @param czasDyzuruIdx indeks okresu dy�uru
	 * @param dzienMiesiaca dzie� dy�uru
	 * @param jestDyzur czy jest dy�ur
	 * @throws IllegalArgumentException je�eli <code>dyzurnyIdx</code> jest poza zakresem.
	 * @throws IllegalArgumentException je�eli <code>czasDyzuruIdx</code> jest poza zakresem.
	 * @throws IllegalArgumentException je�eli <code>dzienMiesiaca</code> jest poza zakresem.
	 * @see #sprawdzBledy(int, int, int)
	 */
	public void setDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca,boolean jestDyzur){
		if(dyzurnyIdx<0 || dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy�urnego poza zakresem");
		if(czasDyzuruIdx<0||czasDyzuruIdx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dyzuru poza zakresem");
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) throw new IllegalArgumentException("Nieporawny numer dnia miesi�ca");
		this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).jest=jestDyzur;
		this.sprawdzBledy(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca);
		
	}

	/**
	 * Ustawia Naziwsko i Imi� dy�urnemu wskazanemu przez indeks.
	 * @param idx indeks dy�urnego
	 * @param dyzurny nowe Nazwisko i Imi� dy�urnego
	 * @return <code>true</code> je�eli dokonano zmiany, <code>false</code> w przeciwnym wypadku
	 */
	public boolean setDyzurny(int idx,String dyzurny){
		if(idx<0||idx>=this.iluDyzurnych()) return false;
		this.dyzurni.set(idx, dyzurny);
		return true;
	}

	/**
	 * Ustawia firm�, dla kt�rej zrobiony jest grafik.
	 * @param firma nowa nazwa firmy
	 */
	public void setFirma(String firma) {
		this.firma = firma;
	}

	/**
	 * Ustawia jednostk� organizacyjn�, dla kt�rej zrobiony jest grafik.
	 * @param grupa nowa jednostka organizacyjna
	 */
	public void setGrupa(String grupa) {
		this.grupa = grupa;
	}
	/**
	 * Ustawia now�  ilo�� godzin dla okresu wskazanego przez indeks i sprawdza b��dy
	 * @param idx indeks okresu dy�uru
	 * @param iloscGodzin nowa ilo�� godzin
	 * @return <code>true</code> je�eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#iloscGodzin
	 */
	public boolean setIloscGodzinDyzuru(int idx,double iloscGodzin){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.okresyDyzurow.get(idx).setIloscGodzin(iloscGodzin);
		this.sprawdzBledy();
		return true;
	}
	/**
	 * Ustawia, czy inteligentne sprawdzanie b��du 35h jest w��czone<br>
	 * Je�li inteligentne sprawdzanie b��du 35h jest w��czone i 
	 * {@link #isSprawdzajNiepelnyOstatniTydzienNaBlad35h()} zwraca <code>true</code>,
	 * to algorytm sprawdzj�cy uznaje, �e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj�ce na nast�pny miesi�c s� wolne od dy�ur�w.
	 * @param inteligentneSprawdzanieBledu35h czy w��czy� inteligentne sprawdzanie
	 * @see Grafik.TypBledu#BLAD_35H 
	 * @see #sprawdzBlad35h(int, int)
	 */
	public void setInteligentneSprawdzanieBledu35h(
			boolean inteligentneSprawdzanieBledu35h) {
		this.inteligentneSprawdzanieBledu35h = inteligentneSprawdzanieBledu35h;
		this.sprawdzBledy();
	}
	
	/**
	 * Ustawia klauzul� bezpiecze�stwa
	 * @param klauzula nowa klauzula
	 */
	public void setKlauzula(String klauzula) {
		this.klauzula = klauzula;
	}
	/**
	 * Ustawia now� tekstow� reprezentacj� czasu dy�uru dla okresu wskazanego przez indeks
	 * @param idx indeks okresu dy�uru
	 * @param przedzialCzasu nowa warto�� tekstowa przedzia�u czasu dy�uru
	 * @return <code>true</code> je�eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public boolean setPrzedzialCzasuDyzuru(int idx,String przedzialCzasu){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.getCzasDyzuru(idx).setPrzedzialCzasu(przedzialCzasu);
		return true;
	}

	
	
	/** Ustawia nowe tekstowe reprezentacje czasu dy�uru dla wszystkich okres�w, pod warunkiem, 
	 * �e liczba nowych reprezentacji jest taka sama jak starcyh.
	 * @param przedzialy nowe warto�ci tekstowe przedzia��w czasu dy�uru
	 * @return <code>true</code> je�eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public boolean setPrzedzialyCzasuDyzuru(String[] przedzialy){
		if(przedzialy.length!=this.ileOkresowDyzurow()) return false;
		for(int i=0;i<przedzialy.length;i++)
			this.setPrzedzialCzasuDyzuru(i, przedzialy[i]);
		return true;
	}


	/**
	 * Ustawia,czy grafik ma sprawdza� b��dy. Je�li <code>sprawdzajBledy==true</code> b��dy b�d� sprawdzane.<br>
	 * w innym przypadku b��dy nie b�d� sprawdzane, a zapisane informacjao b��dach zostanie wykasowana.
	 * @param sprawdzajBledy czy sprawdza� b��dy.
	 */
	public void setSprawdzajBledy(boolean sprawdzajBledy) {
		this.sprawdzajBledy = sprawdzajBledy;
		if(sprawdzajBledy) this.sprawdzBledy();
		else this.wyczyscBledy();
	}
	/**
	 * Ustawia czy maj� by� sprawdzane b��dy 35h, je�li ostatni tydzie� miesi�ca jest niepe�ny.
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @param sprawdzaj czy sprawdza�
	 */
	public void setSprawdzajNiepelnyOstatniTydzienNaBlad35h(boolean sprawdzaj) {
		this.sprawdzajNiepelnyOstatniTydzienNaBlad35h = sprawdzaj;
		this.sprawdzBledy();
	}
	/**
	 * Ustawia czy maj� by� sprawdzane b��dy 35h, je�li pierwszy tydzie� miesi�ca jest niepe�ny.
	 * @param sprawdzaj czy sprawdza�
	 */
	public void setSprawdzajNiepelnyPierwszyTydzienNaBlad35h(boolean sprawdzaj) {
		this.sprawdzajNiepelnyPierwszyTydzienNaBlad35h = sprawdzaj;
		this.sprawdzBledy();
	}
	/**
	 * Ustawia nowy tutu� grafiku
	 * @param tytul nowy tytu�
	 */
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	/**
	 * Ustawia miesi�c i rok grafiku. Czy�ci list� dy�ur�w. 
	 * @param nowaDataGrafiku nowa data grafiku.
	 * @see #stworzDyzury()
	 */
	public boolean ustawDate(Date nowaDataGrafiku){
		this.calendar.setTime(nowaDataGrafiku);
		this.stworzDyzury();
		this.sprawdzBledy();
		return true;
	}
	/**
	 * Ustawia miesi�c i rok grafiku. Czy�ci list� dy�ur�w.
	 * @param miesiac nowy miesi�c grafiku
	 * @param rok nowy rok grafiku
	 * @throws IllegalArgumentException je�eli <code>miesiac<1 || miesiac>12</code>
	 */
	public boolean ustawDate(int miesiac,int rok){
		if(miesiac<1||miesiac>12) throw new IllegalArgumentException("Miesiac to licbza z zakresu 1-12");
		if(this.calendar.get(Calendar.MONTH)==miesiac-1 && this.calendar.get(Calendar.YEAR)==rok) return false;
		this.calendar.set(Calendar.MONTH, miesiac-1);
		this.calendar.set(Calendar.YEAR, rok);
		this.stworzDyzury();
		this.sprawdzBledy();
		return true;
	}
}
