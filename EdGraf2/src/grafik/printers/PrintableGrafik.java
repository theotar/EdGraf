/**
 * 
 */
package grafik.printers;

import java.awt.print.PrinterException;

import grafik.Grafik;

/**
 * @author Wojciech Pierzchalski
 *
 */
public interface PrintableGrafik {
	public void print(Grafik g) throws PrinterException;
}
