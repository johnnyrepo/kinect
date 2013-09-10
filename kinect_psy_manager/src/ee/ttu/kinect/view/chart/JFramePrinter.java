package ee.ttu.kinect.view.chart;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;
import javax.swing.JFrame;

public class JFramePrinter implements Printable {

	private JFrame frame;
	
	public JFramePrinter(JFrame frame) {
		this.frame = frame;
	}

	public void saveToImageFile(String name) {
		Rectangle rec = frame.getBounds();
		BufferedImage bi = new BufferedImage(rec.width, rec.height, Transparency.TRANSLUCENT);
		frame.paint(bi.getGraphics());
		
		File file = new File(name);
		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printToPaper() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);

		HashPrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
		attrSet.add(new PageRanges(1));
		attrSet.add(new Copies(1));
		attrSet.add(OrientationRequested.LANDSCAPE);
		attrSet.add(MediaSizeName.ISO_A4);
		
		boolean isPrint = job.printDialog(attrSet);
		if (isPrint) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// We have only one page, and 'page'
	    // is zero-based
	    if (pageIndex > 0) {
	         return NO_SUCH_PAGE;
	    }

	    // User (0,0) is typically outside the
	    // imageable area, so we must translate
	    // by the X and Y values in the PageFormat
	    // to avoid clipping.
	    Graphics2D g2d = (Graphics2D)graphics;
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	    // Now we perform our rendering
	    frame.printAll(g2d);

	    // tell the caller that this page is part
	    // of the printed document
	    return PAGE_EXISTS;
	}

}