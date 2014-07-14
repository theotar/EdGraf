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
 * Klasa reprezentuj¹ca grafik dy¿urów.
 * @author Wojciech Pierzchalski
 * @version 1.0
 */

public class Grafik implements Serializable{
	
	/**
	 * Klasa reprezentuj¹ca iloœæ godzin maksymlanego odpoczynku w danym tygodniu.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	private static class OdpoczynekTygodniowy extends ZakresTygodnia{
		/**
		 * Iloœæ godzin maksymalnego nieprzerwanego odpoczynku w ciagu tygodnia
		 */
		double iloscGodzin=0;
		/**
		 * Tworzy nowy obiekt OdpoczynekTygodniowy z iloœci¹ godzin ustawion¹ na 0 
		 * oraz zakresem tygodnia wyliczonym na podstawie daty
		 * @param data - data, na podstawie, której obliczany jest zakres tygodnia.
		 */
		public OdpoczynekTygodniowy(Date data) {
			super(data);
		}
		/**
		 * Tworzy nowy obiekt OdpoczynekTygodniowy z iloœci¹ godzin ustawion¹ na 0 
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
	 * Klasa reprezentuj¹ca przedzia³ czasowy dy¿uru.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public static class CzasDyzuru implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Iloœæ godzin w przedziale czasowym.
		 */
		private double iloscGodzin;
		/**
		 * Przedzia³ czasu w formie tekstowej. np '07.00-15.00'
		 */
		private String przedzialCzasu;
		/**
		 * @param przedzialCzasu - tekstowy zapis przedzia³u.
		 * @param iloscGodzin - liczba godzin w przedziale.
		 */
		public CzasDyzuru(String przedzialCzasu, double iloscGodzin) {
			this.przedzialCzasu = przedzialCzasu;
			this.iloscGodzin = iloscGodzin;
		}
		/**
		 * 
		 * @return iloœæ godzin w przedzile czasowym.
		 */
		public double getIloscGodzin() {
			return iloscGodzin;
		}
		/**
		 * 
		 * @return wartoœæ tekstow¹ przedzia³u czasu.
		 */
		public String getPrzedzialCzasu() {
			return przedzialCzasu;
		}
		/**
		 * 
		 * @param przedzialCzasu - ustawia podan¹ wartoœæ tekstow¹ przedzia³u czasu.
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
		 * @param przedzialCzasu - ustawia podan¹ wartoœæ tekstow¹ przedzia³u czasu.
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
	 * Klasa reprezentuj¹ca dane dy¿uru. Zawiera informajce o:<br>
	 * <li>Czy jest dy¿ur</li>
	 * <li>Jeœli jest dy¿ur, to czy nie powoduje b³êdów</li>
	 * <li>Jeœli nie ma dy¿uru,a by³by, to czy spowodowa³oby to bjakiœ b³¹d</li>
	 * @author Wojciech Pierzchalski
	 *
	 */
	public static class Dyzur implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Zbiór b³êdów, które powoduje wstawiony dy¿ur typu {@link TypBledu}
		 */
		Set<TypBledu> bledy=new HashSet<TypBledu>();
		/**
		 * Czy jest dy¿ur?
		 */
		boolean jest=false;
		/**
		 * Zbiór mo¿liwych b³êdów typu {@link TypBledu}, gdyby zosta³ wstawiony dy¿ur.
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
		 * Sprawdza czy dyzur zawiera dany b³¹d
		 * @param o b³¹d
		 * @return true jeœli zawiera
		 * @see java.util.Set#contains(java.lang.Object)
		 */
		public boolean bledyContains(Object o) {
			return this.bledy.contains(o);
		}
		/**
		 * @return true jeœli dyzur nie ma b³êdów
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
		 * @return liczba b³edów
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
		 * Sprawdza czy dany b³¹d jest mozliwy
		 * @param o b³¹d
		 * @return true jeœli jest mo¿liwy
		 * @see java.util.Set#contains(java.lang.Object)
		 */
		public boolean mozliweBledyContains(Object o) {
			return this.mozliweBledy.contains(o);
		}
		/**
		 * @return true jeœli nie ma mozliwych b³êdów
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
		 * @return liczba mozliwych b³edów
		 * @see java.util.Set#size()
		 */
		public int mozliweBledySize() {
			return this.mozliweBledy.size();
		}
		
	}
	/**
	 * Rodzaje b³êdów jakie mo¿e generowaæ ¿le wstaiony dy¿ur. 
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public enum TypBledu{
		/**
		 * Pracownik dy¿uruje tak, ¿e danej doby nie ma 11 godzin nieprzerwanego odpoczynku.
		 */
		BLAD_11H("B³¹d braku 11h nieprzerwanego odpoczunku w ci¹gu doby."),
		/**
		 * Pracownik dyzuruje tak, ¿e w danym tygodniu nie ma 35 godzin nieprzerwanego odpoczynku.
		 */
		BLAD_35H("B³¹d braku 35h nieprzerwanego odpoczunku w ci¹gu tygodnia."),
		/**
		 * Pracownik dy¿uruje w czasie pracy tj. w dzieñ powszedni w pierwszym okresie dy¿uru
		 * @see TypDnia
		 * @see Grafik#okresyDyzurow
		 */
		DYZUR_W_CZASIE_PRACY("B³¹d pe³nienia dyzuru w czasie pracy."),
		/**
		 * Conajmniej dwóch pracownik dy¿uruje w tym samym okresie tego samego dnia
		 * @see Grafik#okresyDyzurow
		 */
		WIELOKROTNY_DYZUR("B³¹d pe³nienia dyzuru przez wielu pracowników jednoczeœnie.");
		
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
	 * Klasa reprezentuj¹ca zakres tygodnia w miesi¹cu.
	 * @author Wojciech Pierzchalski
	 * @version 1.0
	 */
	public static class ZakresTygodnia{
		/**
		 * Numer dnia miesi¹ca przypadaj¹cy w niedzielê
		 */
		int koniecTygodnia=0;
		/**
		 * Jeœli dany tydzieñ jest ostatnim w miesi¹cu to:</br>
		 * Czy ostatni tydzieñ miesi¹ca jest niepe³ny tj. czy w danym miesi¹cu przypada mniej ni¿ 7 dni tego tygodnia.<br>
		 * <b>Przyk³ad</b><br>
		 * 29 lutego 2012 to œroda, wiêc w lutym przypada tylko 3 dni tego tygodnia, czyli tydzieñ jest <b>niepe³ny</b>
		 * <br>
		 * <br>
		 * Mo¿na definiowaæ to równie¿ jako:<br>
		 * Jeœli dany tydzieñ jest ostatnim w miesi¹cu, to czy ostatni dzieñ tego miesi¹ca to niedziela.
		 */
		boolean niepelnyOstatniTydzien=false;
		/**
		 * Jeœli dany tydzieñ jest pierwszym w miesi¹cu to:</br>
		 * Czy pierwszy tydzieñ miesi¹ca jest niepe³ny tj. czy w danym miesi¹cu przypada mniej ni¿ 7 dni tego tygodnia.<br>
		 * <b>Przyk³ad</b><br>
		 * 1 lutego 2012 to œroda, wiêc w lutym przypada tylko 5 dni tego tygodnia, czyli tydzieñ jest <b>niepe³ny</b>
		 * <br>
		 * <br>
		 * Mo¿na definiowaæ to równie¿ jako:<br>
		 * Jeœli dany tydzieñ jest pierwszym w miesi¹cu, to czy pierwszy dzieñ tego miesi¹ca to poniedzia³ek.
		 * 
		 */
		boolean niepelnyPierwszyTydzien=false;
		/**
		 * Numer dnia miesi¹ca przypadaj¹cy w poniedzia³ek
		 */
		int poczatekTygodnia=0;
		
		/**
		 * Konstruktor domyœlny<br>
		 * Ustawia pola na 0 lub false
		 */
		private ZakresTygodnia(){
			
		}
		/**
		 * 
		 * @param data - data na podstawie, której zostanie wyliczony zakres tygodnia.
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
	 * Firma, dla której robiony jest grafik dyzurów.
	 */
	private String firma;
	/**
	 * Jednostka organizacyjna, dla która obowi¹zuje grafik dy¿urów.
	 */
	private String grupa;
	/**
	 * Jeœli inteligentne sprawdzanie b³êdu 35h jest w³¹czone i {@link #sprawdzajNiepelnyOstatniTydzienNaBlad35h}==true,
	 * to algorytm sprawdzj¹cy uznaje, ¿e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj¹ce na nastêpny miesi¹c s¹ wolne od dy¿urów.
	 * @see Grafik.TypBledu#BLAD_35H 
	 */
	private boolean inteligentneSprawdzanieBledu35h=true;
	/**
	 * Klauzula bezpieczeñstwa, jak¹ opatrzony jest grafik dy¿urów.
	 */
	private String klauzula;
	
	/**
	 * Czy maj¹ byæ sprawdzane b³êdy
	 * @see Grafik.TypBledu
	 * @see #sprawdzBledy()
	 */
	private boolean sprawdzajBledy=false;
	/**
	 * Czy maj¹ byæ sprawdzane b³êdy 35h, jeœli ostatni tydzieñ miesi¹ca jest niepe³ny?
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 */
	private boolean sprawdzajNiepelnyOstatniTydzienNaBlad35h=true;
	/**
	 * Czy maj¹ byæ sprawdzane b³êdy 35h, jeœli pierwszy tydzieñ miesi¹ca jest niepe³ny?
	 * @see Grafik.ZakresTygodnia#niepelnyPierwszyTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 */
	private boolean sprawdzajNiepelnyPierwszyTydzienNaBlad35h=false;
	/**
	 * Tytu³ grafiku.
	 */
	private String tytul;
	
	
	
	
	/**
	 * Kalendarz grafiku.
	 */
	protected Calendar calendar;
	
	
	/**
	 * Lista dy¿urnych.Lista nie mo¿e byæ pusta
	 */
	protected List<String> dyzurni;
	/**
	 * Lista dy¿urów dla poszczególnych dy¿urnych.
	 */
	protected List<Dyzur[][]> dyzury;
	/**
	 * Lista okresów dy¿urów.Lista nie mo¿e byæ pusta. Czas dy¿uru z indeksem 0 odpowiada czasowi pracy w dni powszednie.
	 */
	protected List<CzasDyzuru> okresyDyzurow;
	/**
	 * Domyœlny konstruktor, który tworzy obiekt grafiku z jednym dy¿urnym Janem Kowalskim 
	 * i jednym okresem dy¿uru 07.00-15.00
	 */
	protected Grafik(){
		this.firma="";
		this.tytul="";
		this.klauzula="";
		this.grupa="";
		this.calendar=Calendar.getInstance();
	}
	/**
	 * Sprawdza, czy na obecnie ustawiony dzieñ w kalendarzu ({@link Grafik#calendar}) przypada dzieñ energetyka.  
	 * @return <code>true</code> je¿eli jest Dzieñ Energetyka.<code>false</false> jeœli nie.
	 */
	private boolean isDzienEnergetyka(){
		int m=this.calendar.get(Calendar.MONTH);
		int d=this.calendar.get(Calendar.DAY_OF_MONTH);
		return d==14 && m==Calendar.AUGUST;
	}
	/**
	 * Sprawdza, czy na obecnie ustawiony dzieñ w kalendarzu przypada œwiêto wolne od pracy.
	 * @return <code>true</code> je¿eli jest œwiêto.<code>false</false> jeœli nie.
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
	 * Sprawdza, ile dany dy¿urny ma nieprzerwanego odpoczynku w danym dniu.<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi¹cu. 
	 * @param dyzurnyIdx - Indeks dy¿urnego
	 * @param dzienMiesiaca - numer dnia miesi¹ca.
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
	 * Sprawdza, ile dany dy¿urny ma nieprzerwanego odpoczynku w danym dniu, jeœli zak³ada siê, ¿e w okresie 
	 * <code>czasDyzuruIdx</code> bêdzie pe³ni³ dy¿ur<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi¹cu. 
	 * @param dyzurnyIdx - Indeks dy¿urnego
	 * @param czasDyzuruIdx - Indeks okresu dy¿uru, w którym zak³ada siê, ¿e pracownik bêdzie dy¿urowa³.
	 * @param dzienMiesiaca - numer dnia miesi¹ca.
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
	 * Sprawdza, ile dany dy¿urny ma nieprzerwanego odpoczynku w danym tygodniu.<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi¹cu. 
	 * @param dyzurnyIdx - Indeks dy¿urnego
	 * @param dzienMiesiaca - numer dnia miesi¹ca,na podstawie, którego okreslony zostanie odpowiedni tydzieñ.
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
			else if(numerDniaTygodnia==Calendar.SUNDAY) throw new RuntimeException("Nieoczekiwany b³¹d niepe³nego tygodnia");
			else if(odp<48) odp=48;
		}
		if(odp>max) max=odp;
		ot.iloscGodzin=max;
		return ot;	
	}
	/**
	 * Sprawdza, ile dany dy¿urny ma nieprzerwanego odpoczynku w danym tygodniu, jeœli zak³ada siê, ¿e
	 * w okresie <code>czasDyzuruIdx</code> i dnia <code>dzienMiesiaca</code> pracownik pe³ni dyzur<br><br>
	 *  0 < <code>dzienMiesiaca</code> <= Liczba dni w miesi¹cu. 
	 * @param dyzurnyIdx - Indeks dy¿urnego
	 * @param czasDyzuruIdx - indeks okresu hipotetyczngeo dy¿uru.
	 * @param dzienMiesiaca - numer dnia, w którym pe³niony bêdzie hipotetyczny dy¿ur.
	 * @param zt - Zakres tygodnia, jaki ma zostaæ zbadany.
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
			else if(numerDniaTygodnia==Calendar.SUNDAY) throw new RuntimeException("Nieoczekiwany b³¹d niepe³nego tygodnia");
			else if(odp<48) odp=48;
		}
		if(odp>max) max=odp;
		ot.iloscGodzin=max;
		return ot;	
	}
	/**
	 * Pomniejsza tablicê dy¿urów.
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
	 * Powiêksza tablicê dy¿urów
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
	 * Tworzy tablicê dy¿urów o wymiarach:<br>
	 * <code>
	 * [okresyDyzurow.size()][ileDniMiesiaca()]
	 * </code>
	 * @return tablicê dy¿urów odwzorowuj¹c¹ dy¿ury jednego dy¿urnego
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
	 * Podaje Czas dy¿uru wskazanego przez indeks.
	 * @param idx indeks czasu dy¿uru
	 * @return czas dy¿uru
	 * @throws IllegalArgumentException je¿eli idx poza zakresem
	 * @see Grafik.CzasDyzuru
	 */
	protected CzasDyzuru getCzasDyzuru(int idx){
		if(idx<0||idx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dy¿uru poza zakresem");
		return this.okresyDyzurow.get(idx);
	}
	/**
	 * Dostosowuje strukturê danych przechowuj¹c¹ dy¿ury do nowej liczby okresów dy¿urów.
	 */
	protected void nowyRozmiarDyzurow(){
		int stary=this.dyzury.get(0).length;
		int nowy=this.ileOkresowDyzurow();
		if(nowy>stary) this.powiekszDyzury();
		else if(nowy<stary) this.pomniejszDyzury();
	}
	/**
	 * Sprawdza, czy wyst¹pi³ b³¹d {@link Grafik.TypBledu#BLAD_11H}<br>
	 * dla danego dy¿urnego, danego dnia i w danym okresie dy¿uru.
	 * Jeœli wyst¹pi³ b³¹d dodaje go do zbioru b³êdów dy¿uru.<br>
	 * Sprawdza równie¿ i oznacza mo¿liwoœæ wyst¹pienia tego b³êdu.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca pe³nionego dy¿uru.
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
	 * Sprawdza, czy wyst¹pi³ b³¹d {@link Grafik.TypBledu#BLAD_35H}<br>
	 * dla danego dy¿urnego, danego dnia i w danym okresie dy¿uru.
	 * Jeœli wyst¹pi³ b³¹d dodaje go do zbioru b³êdów dy¿uru.<br>
	 * Sprawdza równie¿ i oznacza mo¿liwoœæ wyst¹pienia tego b³êdu.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param dzienMiesiaca dzieñ miesi¹ca pe³nionego dy¿uru.
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
	 * Sprawdza, czy wyst¹pi³ b³¹d {@link Grafik.TypBledu#DYZUR_W_CZASIE_PRACY}<br>
	 * dla danego dy¿urnego, danego dnia i w danym okresie dy¿uru.
	 * Jeœli wyst¹pi³ b³¹d dodaje go do zbioru b³êdów dy¿uru.<br>
	 * Sprawdza równie¿ i oznacza mo¿liwoœæ wyst¹pienia tego b³êdu.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca pe³nionego dy¿uru.
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
	 * Sprawdza, czy wyst¹pi³ b³¹d {@link Grafik.TypBledu#WIELOKROTNY_DYZUR}<br>
	 * dla danego dy¿urnego, danego dnia i w danym okresie dy¿uru.
	 * Jeœli wyst¹pi³ b³¹d dodaje go do zbioru b³êdów dy¿uru.<br>
	 * Sprawdza równie¿ i oznacza mo¿liwoœæ wyst¹pienia tego b³êdu.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca pe³nionego dy¿uru.
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
	 * Sprawdza i oznacza wszystkie b³êdy jakie wystêpuj¹ i mog¹ wyst¹piæ w ca³ym grafiku.
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
	 * Sprawdza i oznacza wszystkie b³edy, jakie wystêpuja lub mog¹ wyst¹piæ dla dy¿uru pe³nionego danego dnia,
	 * w danym okresie, przez danego dy¿urnego.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca pe³nionego dy¿uru.
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
	 * Sprawdza i oznacza ca³y grafik w poszukiwaniu b³êdów {@link Grafik.TypBledu#BLAD_11H} 
	 * oraz mo¿liwoœci ich wyst¹pienia.
	 */
	protected void SprawdzBledy11h(){
		if(!this.isSprawdzajBledy()) return;
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBlad11h(dyzurnyIdx, 0 , dzienMiesiaca);
	}
	/**
	 * Sprawdza i oznacza ca³y grafik w poszukiwaniu b³êdów {@link Grafik.TypBledu#BLAD_35H} 
	 * oraz mo¿liwoœci ich wyst¹pienia.
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
	 * Sprawdza i oznacza ca³y grafik w poszukiwaniu b³êdów {@link Grafik.TypBledu#DYZUR_W_CZASIE_PRACY} 
	 * oraz mo¿liwoœci ich wyst¹pienia.
	 */
	protected void sprawdzBledyPracy(){
		if(!this.isSprawdzajBledy()) return;
		for(int dyzurnyIdx=0;dyzurnyIdx<this.iluDyzurnych();dyzurnyIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBladPracy(dyzurnyIdx, 0 , dzienMiesiaca);
	}
	/**
	 * Sprawdza i oznacza ca³y grafik w poszukiwaniu b³êdów {@link Grafik.TypBledu#WIELOKROTNY_DYZUR} 
	 * oraz mo¿liwoœci ich wyst¹pienia.
	 */
	protected void sprawdzBledyWielokrotnegoDyzuru(){
		if(!this.isSprawdzajBledy()) return;
		for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				this.sprawdzBladWielokrotnegoDyzuru(0, czasDyzuruIdx, dzienMiesiaca);
	}
	/**
	 * Tworzy strukturê danych przechowuj¹c¹ dy¿ury.
	 * @see #dyzury
	 */
	protected void stworzDyzury(){
		this.dyzury=new ArrayList<Dyzur[][]>();
		int iloscDyzurnych=this.iluDyzurnych();
		
		for(int i=0;i<iloscDyzurnych;i++)
			this.dyzury.add(this.stworzDyzuryDlaJednegoDyzurnego());
	}
	/**
	 * Czysci wszystkie b³êdy i mo¿liwe b³êdy z grafiku.
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
	 * Dodaje nowy okres dy¿uru do okresów dy¿urów i sprawdza b³êdy.
	 * @param cd nowy okres dy¿uru
	 * @see #sprawdzBledy()
	 */
	public void addCzasDyzuru(CzasDyzuru cd){
		this.okresyDyzurow.add(cd);
		this.nowyRozmiarDyzurow();
		this.sprawdzBledy();
		
	}
	/**
	 * Dodaje do grafiku nowego dy¿urnego.
	 * @param dyzurny Nazwisko i Imiê nowego dyzurnego
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
	 * Zwraca obiekt dy¿uru dla danego dyzurnego, w danym dniu i w danym okresie
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ dy¿uru
	 * @return obiekt dy¿uru
	 * @see Grafik.Dyzur
	 */
	public Dyzur getDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.dyzury.get(dyzurnyIdx)[czasDyzuruIdx][dzienMiesiaca-1];
	}
	/**
	 * Podaje Naziwkso i Imiê dy¿urnego wskazanego przez indeks.
	 * @param idx indeks dy¿urnego
	 * @return Naziwsko i Imiê dy¿urnego
	 * @throws IllegalArgumentException je¿eli idx poza zakresem
	 */
	public String getDyzurny(int idx){
		if(idx<0||idx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy¿urnego poza zakresem");
		return this.dyzurni.get(idx);
	}
	/**
	 * Zwraca tablicê dy¿urów dla danego dyzurnego, danego dnia
	 * @param dyzurnyIdx indeks dyzurnego
	 * @param dzienMiesiaca numer dnia miesi¹ca
	 * @return tablicê dy¿urów
	 */
	public Dyzur[] getDyzury(int dyzurnyIdx,int dzienMiesiaca){
		Dyzur[] tab=new Dyzur[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getDyzur(dyzurnyIdx, i, dzienMiesiaca);
		return tab;
	}
	/**
	 * Zwraca firmê, dla której zrobiony jest grafik.
	 * @return firma
	 */
	public String getFirma() {
		return firma;
	}
	/**
	 * Zwraca jednostkê organizacyjn¹, dla której zrobiony jest grafik.
	 * @return jednostka organizacyjna.
	 */
	public String getGrupa() {
		return grupa;
	}
	/**
	 * Podaje iloœæ godzin dy¿uru w okresie wskazanym przez indeks.
	 * @param idx indeks okresu dy¿uru
	 * @return iloœæ godzin dy¿uru
	 * @see Grafik.CzasDyzuru#iloscGodzin
	 */
	public double getIloscGodzinDyzuru(int idx){
		return this.getCzasDyzuru(idx).iloscGodzin;
	}
	/**
	 * Zwraca tablicê iloœci godzin w okresach dy¿urów
	 * @return tablica iloœci godzin w okresach dyzurów
	 */
	public Double[] getIlosciGodzinDyzuru(){
		Double[] tab=new Double[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getIloscGodzinDyzuru(i);
		return tab;
	}
	/**
	 * Zwraca klauzulê bezpieczeñstwa jak¹ opatrzony jest grafik.
	 * @return klauzula
	 */
	public String getKlauzula() {
		return klauzula;
	}
	/**
	 * Podaje aktualny miesi¹c i rok grafiku w formacie "MMMM yyyy" klasy {@link SimpleDateFormat}
	 * @return miesi¹c i rok
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
	 * Podaje przedzia³ czasu dy¿uru w okresie wskazanym przez indeks.
	 * @param idx indeks okresu dy¿uru
	 * @return tekstowa reprezentacja czasu dy¿uru
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public String getPrzedzialCzasuDyzuru(int idx){
		return this.getCzasDyzuru(idx).przedzialCzasu;
	}
	/**
	 * Zwraca tablicê tekstowych reprezentacji przedzia³ów czasu dy¿uru.
	 * @return tablicê przedzia³ów
	 */
	public String[] getPrzedzialyCzasu(){
		String[] tab=new String[this.ileOkresowDyzurow()];
		for(int i=0;i<tab.length;i++)
			tab[i]=this.getPrzedzialCzasuDyzuru(i);
		return tab;
	}
	/**
	 * Zwraca tytu³ grafiku
	 * @return tytu³ grafiku
	 */
	public String getTytul() {
		return tytul;
	}
	/**
	 * Zwraca zakres tygodnia w danym dniu
	 * @param dzienMiesiaca numer dnia miesi¹ca
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
	 * Podaje liczbê dni aktualnego miesi¹ca grafiku
	 * @return liczba dni miesi¹ca
	 */
	public int ileDniMiesiaca(){
		return this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * Zwraca ile godzin dy¿uruje w miesi¹cu dy¿urny wskazany przez indeks
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @return liczba godzin 
	 */
	public double ileGodzin(int dyzurnyIdx){
		if(dyzurnyIdx<0||dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy¿urnego poza zakresem");
		double suma=0;
		for(int czasDyzuruIdx=0;czasDyzuruIdx<this.ileOkresowDyzurow();czasDyzuruIdx++)
			for(int dzienMiesiaca=1;dzienMiesiaca<=this.ileDniMiesiaca();dzienMiesiaca++)
				if(this.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca)) suma+=this.getIloscGodzinDyzuru(czasDyzuruIdx);
		return suma;
	}
	/**
	 * Zwraca liczbê okresów dy¿urów w grafiku
	 * @return liczba okresów
	 */
	public int ileOkresowDyzurow(){
		return this.okresyDyzurow.size();
	}
	/**
	 * Zwraca liczbê dy¿urnych w grafiku
	 * @return liczba dyzurnych
	 */
	public int iluDyzurnych(){
		return this.dyzurni.size();
	}
	/**
	 * Sprawdza czy dla danego dy¿urnego, danego dnia i w danym okresie dy¿ur powoduje b³¹d.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca
	 * @return <code>true</code> je¿eli dy¿ur powoduje b³¹d, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.TypBledu
	 */
	public boolean isBlad(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).bledy.size()!=0;
	}
	/**
	 * Sprawdza, czy dany dyzurnego, w danym dniu i w danym okresie pe³ni dy¿ur
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ dy¿uru
	 * @return <code>true</code> jeœli pe³ni, <code>false</code> jeœli nie.
	 */
	public boolean isDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		if(dyzurnyIdx<0 || dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy¿urnego poza zakresem");
		if(czasDyzuruIdx<0||czasDyzuruIdx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dyzuru poza zakresem");
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) throw new IllegalArgumentException("Nieporawny numer dnia miesi¹ca");
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).jest;
	}
	/**
	 * Sprawdza, czy dany dzieñ nie jest {@link Grafik.TypDnia#POWSZEDNI}
	 * @param dzienMiesiaca numer dnia miesi¹ca do sprawdzenia
	 * @return <code> {@link #jakiDzien(int)} != POWSZEDNI</code>
	 */
	public boolean isDzienWolny(int dzienMiesiaca){
		return this.jakiDzien(dzienMiesiaca)!=TypDnia.POWSZEDNI;
	}
	/**
	 * Sprawdza, czy inteligentne sprawdzanie b³êdu 35h jest w³¹czone<br>
	 * Jeœli inteligentne sprawdzanie b³êdu 35h jest w³¹czone i 
	 * {@link #isSprawdzajNiepelnyOstatniTydzienNaBlad35h()} zwraca <code>true</code>,
	 * to algorytm sprawdzj¹cy uznaje, ¿e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj¹ce na nastêpny miesi¹c s¹ wolne od dy¿urów.
	 * @return <code> true </code> jesli inteligentne sprawdzanie jest w³¹czone, <code>false</code> w przeciwnym wypadku.
	 * @see Grafik.TypBledu#BLAD_35H 
	 * @see #sprawdzBlad35h(int, int)
	 */
	public boolean isInteligentneSprawdzanieBledu35h() {
		return inteligentneSprawdzanieBledu35h;
	}
	
	
	/**
	 * Sprawdza czy dla danego dy¿urnego, danego dnia i w danym okresie wstawienie dy¿uru spowoduje b³¹d.
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ miesi¹ca
	 * @return <code>true</code> je¿eli dy¿ur powoduje b³¹d, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.TypBledu
	 */
	public boolean isMozliwyBlad(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca){
		return this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).mozliweBledy.size()!=0;
	}

	/**
	 * Czy grafik sprawdza b³êdy
	 * @return <code>true</code> jeœli sprawdza, <code>false</code> jeœli nie.
	 */
	public boolean isSprawdzajBledy() {
		return this.sprawdzajBledy;
	}

	/**
	 * Sprawdza czy maj¹ byæ sprawdzane b³êdy 35h, jeœli ostatni tydzieñ miesi¹ca jest niepe³ny.
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @return <code> true </code> jesli maj¹ byæ sprawdzane, <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isSprawdzajNiepelnyOstatniTydzienNaBlad35h() {
		return sprawdzajNiepelnyOstatniTydzienNaBlad35h;
	}

	/**
	 * Sprawdza czy maj¹ byæ sprawdzane b³êdy 35h, jeœli pierwszy tydzieñ miesi¹ca jest niepe³ny.
	 * @see Grafik.ZakresTygodnia#niepelnyPierwszyTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @return <code> true </code> jesli maj¹ byæ sprawdzane, <code>false</code> w przeciwnym wypadku.
	 */
	public boolean isSprawdzajNiepelnyPierwszyTydzienNaBlad35h() {
		return sprawdzajNiepelnyPierwszyTydzienNaBlad35h;
	}

	/**
	 * Podaje jaki dzieñ przypada na podany dzieñ miesi¹ca.
	 * @param dzienMiesiaca dzieñ miesi¹ca do sprawdzenia
	 * @return typ dnia
	 * @throws IllegalArgumentException je¿eli <code> dzienMiesiaca <1 || dzienMiesiaca > ileDniMiesiaca()</code>
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
	 * Usuwa okres dy¿uru wskazany przez indeksi sprawdza b³êdy
	 * @param idx indeks dy¿uru
	 * @return <code>true</code> je¿eli usuniêto okres dy¿uru, <code>false</code> w przeciwnym wypadku
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
	 * Usuwa z grafiku dy¿urnego wskazanego przez indeks, pod warunkiem, ¿e po usuniêciu zostanie conajmniej 1 dy¿urny.
	 * @param idx indeks dy¿urnego
	 * @return <code>true</code> je¿eli usuniêto dy¿urnego, <code>false</code> w przeciwnym wypadku
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
	 * Usuwa z grafiku dy¿urnego wskazanego przez Imie i Nazwisko, pod warunkiem, ¿e po usuniêciu zostanie conajmniej 1 dy¿urny.
	 * @param dyzurny  Nazwisko i Imiê dy¿urnego
	 * @return <code>true</code> je¿eli usuniêto dy¿urnego, <code>false</code> w przeciwnym wypadku
	 */
	public boolean removeDyzurny(String dyzurny){
		int idx=this.dyzurni.indexOf(dyzurny);
		return this.removeDyzurny(idx);
	}
	/**
	 * Ustawia nowy czas dy¿uru zamiast wskazanego przez indeks i sprawdza b³êdy
	 * @param idx indeks okresu dy¿uru
	 * @param czasDyzuru nowy okres dy¿uru
	 * @return <code>true</code> je¿eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see #sprawdzBledy()
	 */
	public boolean setCzasDyzuru(int idx,CzasDyzuru czasDyzuru){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.okresyDyzurow.set(idx, czasDyzuru);
		return true;
	}

	/**
	 * Ustawia nowe paramatery czasu dy¿uru wskazanego przez indeks i sprawdza b³êdy
	 * @param idx indeks okresu dy¿uru
	 * @param przedzialCzasu tekstowa reprezentacja okresu dy¿uru
	 * @param iloscGodzin iloœæ godzin dy¿uru
	 * @return <code>true</code> je¿eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see #sprawdzBledy()
	 */
	public boolean setCzasDyzuru(int idx,String przedzialCzasu,double iloscGodzin){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.getCzasDyzuru(idx).set(przedzialCzasu, iloscGodzin);
		this.sprawdzBledy();
		return true;
	}

	/**
	 * Ustawia dyzur w zale¿noœci od parametru <code>jestDyzur</code> dla danego dyzurnego, w danym dniu i w danym okresie,
	 * a nastêpnie sprawdza b³êdy. 
	 * @param dyzurnyIdx indeks dy¿urnego
	 * @param czasDyzuruIdx indeks okresu dy¿uru
	 * @param dzienMiesiaca dzieñ dy¿uru
	 * @param jestDyzur czy jest dy¿ur
	 * @throws IllegalArgumentException je¿eli <code>dyzurnyIdx</code> jest poza zakresem.
	 * @throws IllegalArgumentException je¿eli <code>czasDyzuruIdx</code> jest poza zakresem.
	 * @throws IllegalArgumentException je¿eli <code>dzienMiesiaca</code> jest poza zakresem.
	 * @see #sprawdzBledy(int, int, int)
	 */
	public void setDyzur(int dyzurnyIdx,int czasDyzuruIdx,int dzienMiesiaca,boolean jestDyzur){
		if(dyzurnyIdx<0 || dyzurnyIdx>=this.iluDyzurnych()) throw new IllegalArgumentException("Index dy¿urnego poza zakresem");
		if(czasDyzuruIdx<0||czasDyzuruIdx>=this.ileOkresowDyzurow()) throw new IllegalArgumentException("Index czasu dyzuru poza zakresem");
		if(dzienMiesiaca<1||dzienMiesiaca>this.ileDniMiesiaca()) throw new IllegalArgumentException("Nieporawny numer dnia miesi¹ca");
		this.getDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca).jest=jestDyzur;
		this.sprawdzBledy(dyzurnyIdx, czasDyzuruIdx, dzienMiesiaca);
		
	}

	/**
	 * Ustawia Naziwsko i Imiê dy¿urnemu wskazanemu przez indeks.
	 * @param idx indeks dy¿urnego
	 * @param dyzurny nowe Nazwisko i Imiê dy¿urnego
	 * @return <code>true</code> je¿eli dokonano zmiany, <code>false</code> w przeciwnym wypadku
	 */
	public boolean setDyzurny(int idx,String dyzurny){
		if(idx<0||idx>=this.iluDyzurnych()) return false;
		this.dyzurni.set(idx, dyzurny);
		return true;
	}

	/**
	 * Ustawia firmê, dla której zrobiony jest grafik.
	 * @param firma nowa nazwa firmy
	 */
	public void setFirma(String firma) {
		this.firma = firma;
	}

	/**
	 * Ustawia jednostkê organizacyjn¹, dla której zrobiony jest grafik.
	 * @param grupa nowa jednostka organizacyjna
	 */
	public void setGrupa(String grupa) {
		this.grupa = grupa;
	}
	/**
	 * Ustawia now¹  iloœæ godzin dla okresu wskazanego przez indeks i sprawdza b³êdy
	 * @param idx indeks okresu dy¿uru
	 * @param iloscGodzin nowa iloœæ godzin
	 * @return <code>true</code> je¿eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#iloscGodzin
	 */
	public boolean setIloscGodzinDyzuru(int idx,double iloscGodzin){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.okresyDyzurow.get(idx).setIloscGodzin(iloscGodzin);
		this.sprawdzBledy();
		return true;
	}
	/**
	 * Ustawia, czy inteligentne sprawdzanie b³êdu 35h jest w³¹czone<br>
	 * Jeœli inteligentne sprawdzanie b³êdu 35h jest w³¹czone i 
	 * {@link #isSprawdzajNiepelnyOstatniTydzienNaBlad35h()} zwraca <code>true</code>,
	 * to algorytm sprawdzj¹cy uznaje, ¿e dni weekendowe ostatniego tygodnia grafiku 
	 * przypadaj¹ce na nastêpny miesi¹c s¹ wolne od dy¿urów.
	 * @param inteligentneSprawdzanieBledu35h czy w³¹czyæ inteligentne sprawdzanie
	 * @see Grafik.TypBledu#BLAD_35H 
	 * @see #sprawdzBlad35h(int, int)
	 */
	public void setInteligentneSprawdzanieBledu35h(
			boolean inteligentneSprawdzanieBledu35h) {
		this.inteligentneSprawdzanieBledu35h = inteligentneSprawdzanieBledu35h;
		this.sprawdzBledy();
	}
	
	/**
	 * Ustawia klauzulê bezpieczeñstwa
	 * @param klauzula nowa klauzula
	 */
	public void setKlauzula(String klauzula) {
		this.klauzula = klauzula;
	}
	/**
	 * Ustawia now¹ tekstow¹ reprezentacjê czasu dy¿uru dla okresu wskazanego przez indeks
	 * @param idx indeks okresu dy¿uru
	 * @param przedzialCzasu nowa wartoœæ tekstowa przedzia³u czasu dy¿uru
	 * @return <code>true</code> je¿eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public boolean setPrzedzialCzasuDyzuru(int idx,String przedzialCzasu){
		if(idx<0||idx>=this.ileOkresowDyzurow()) return false;
		this.getCzasDyzuru(idx).setPrzedzialCzasu(przedzialCzasu);
		return true;
	}

	
	
	/** Ustawia nowe tekstowe reprezentacje czasu dy¿uru dla wszystkich okresów, pod warunkiem, 
	 * ¿e liczba nowych reprezentacji jest taka sama jak starcyh.
	 * @param przedzialy nowe wartoœci tekstowe przedzia³ów czasu dy¿uru
	 * @return <code>true</code> je¿eli dokoanno zmian, <code>false</code> w przeciwnym wypadku
	 * @see Grafik.CzasDyzuru#przedzialCzasu
	 */
	public boolean setPrzedzialyCzasuDyzuru(String[] przedzialy){
		if(przedzialy.length!=this.ileOkresowDyzurow()) return false;
		for(int i=0;i<przedzialy.length;i++)
			this.setPrzedzialCzasuDyzuru(i, przedzialy[i]);
		return true;
	}


	/**
	 * Ustawia,czy grafik ma sprawdzaæ b³êdy. Jeœli <code>sprawdzajBledy==true</code> b³êdy bêd¹ sprawdzane.<br>
	 * w innym przypadku b³êdy nie bêd¹ sprawdzane, a zapisane informacjao b³êdach zostanie wykasowana.
	 * @param sprawdzajBledy czy sprawdzaæ b³êdy.
	 */
	public void setSprawdzajBledy(boolean sprawdzajBledy) {
		this.sprawdzajBledy = sprawdzajBledy;
		if(sprawdzajBledy) this.sprawdzBledy();
		else this.wyczyscBledy();
	}
	/**
	 * Ustawia czy maj¹ byæ sprawdzane b³êdy 35h, jeœli ostatni tydzieñ miesi¹ca jest niepe³ny.
	 * @see Grafik.ZakresTygodnia#niepelnyOstatniTydzien
	 * @see Grafik.TypBledu#BLAD_35H
	 * @param sprawdzaj czy sprawdzaæ
	 */
	public void setSprawdzajNiepelnyOstatniTydzienNaBlad35h(boolean sprawdzaj) {
		this.sprawdzajNiepelnyOstatniTydzienNaBlad35h = sprawdzaj;
		this.sprawdzBledy();
	}
	/**
	 * Ustawia czy maj¹ byæ sprawdzane b³êdy 35h, jeœli pierwszy tydzieñ miesi¹ca jest niepe³ny.
	 * @param sprawdzaj czy sprawdzaæ
	 */
	public void setSprawdzajNiepelnyPierwszyTydzienNaBlad35h(boolean sprawdzaj) {
		this.sprawdzajNiepelnyPierwszyTydzienNaBlad35h = sprawdzaj;
		this.sprawdzBledy();
	}
	/**
	 * Ustawia nowy tutu³ grafiku
	 * @param tytul nowy tytu³
	 */
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	/**
	 * Ustawia miesi¹c i rok grafiku. Czyœci listê dy¿urów. 
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
	 * Ustawia miesi¹c i rok grafiku. Czyœci listê dy¿urów.
	 * @param miesiac nowy miesi¹c grafiku
	 * @param rok nowy rok grafiku
	 * @throws IllegalArgumentException je¿eli <code>miesiac<1 || miesiac>12</code>
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
