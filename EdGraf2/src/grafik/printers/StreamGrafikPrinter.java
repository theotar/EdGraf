package grafik.printers;

import java.io.OutputStream;
import java.io.PrintWriter;

import grafik.Grafik;

public class StreamGrafikPrinter implements PrintableGrafik {
	
	private OutputStream out;
	private PrintWriter pw;
	
	public StreamGrafikPrinter(OutputStream o) {
		this.out=o;
		this.pw=new PrintWriter(out);
	}

	protected void printSeparatorLine(){
		pw.println("--------------------------------------------------------------------------------------------------" +
				"----------------------------------------------------");
	}
	@Override
	public void print(Grafik g) {
		//NAGLOWEK 1
		pw.format("%-60s %89s\n",g.getFirma(),g.getTytul());
		
		//NAGLOWEK 2
		pw.format("%-40s %-80s %20s\n", g.getKlauzula(),g.getGrupa(),g.getMiesiacRok());
		this.printSeparatorLine();
		//NAGLOWEK TABELA
		pw.format("%3s %-25s %15s %5s ","Lp.","Nazwisko i Imiê","Dy¿ur w godz.","il.g.");
		int dniMiesiaca=g.ileDniMiesiaca();
		for(int i=0;i<dniMiesiaca;i++) {
			String td="x";
			switch(g.jakiDzien(i+1))
			{
				case POWSZEDNI: td=" ";break;
				case SOBOTA: td="s";break;
				case NIEDZIELA: td="n";break;
				case DZIEN_ENERGETYKA: td="d";break;
				case SWIETO: td="œ";break;
			}
			pw.format("%2s%s",i+1,td);
		}
		for(int i=31-dniMiesiaca;i>0;i--) pw.format("%3s","");
		pw.format("%5s\n","Uwagi");
		//TRESC
		this.printSeparatorLine();
		for(int dyzurnyIdx=0;dyzurnyIdx<g.iluDyzurnych();dyzurnyIdx++){
			for(int czasDyzuruIdx=0;czasDyzuruIdx< g.ileOkresowDyzurow();czasDyzuruIdx++){
				String lp;
				String ni;
				String uwagi;
				if(czasDyzuruIdx==g.ileOkresowDyzurow()/2)
				{
					lp=String.valueOf(dyzurnyIdx+1);
					ni=g.getDyzurny(dyzurnyIdx);
					uwagi=Integer.toString((int)g.ileGodzin(dyzurnyIdx));
				}
				else
				{
					lp="";
					ni="";
					uwagi="";
				}
				pw.format("%3s %-25s %15s %5.0f ",
						lp,ni,g.getPrzedzialCzasuDyzuru(czasDyzuruIdx),g.getIloscGodzinDyzuru(czasDyzuruIdx));
				for(int dzienMiesiacaIdx=1;dzienMiesiacaIdx<=g.ileDniMiesiaca();dzienMiesiacaIdx++){
					
					pw.format("%1s%-2s",
							g.isBlad(dyzurnyIdx, czasDyzuruIdx, dzienMiesiacaIdx)?"!":" ",
							g.isDyzur(dyzurnyIdx, czasDyzuruIdx, dzienMiesiacaIdx)?"X":
								g.isMozliwyBlad(dyzurnyIdx, czasDyzuruIdx, dzienMiesiacaIdx)?" ":"."
							);
				}
				for(int i=31-dniMiesiaca;i>0;i--) pw.format("%3s","");
				pw.format("%5s\n",uwagi);		
			}
			this.printSeparatorLine();
			pw.flush();
		}
		
	}

}
