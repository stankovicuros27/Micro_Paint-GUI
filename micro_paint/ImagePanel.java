package micro_paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.RasterOp;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Scanner;  
import java.io.*;  

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImagePanel extends JPanel {

	public ImagePanel(ImageWindow imageWindow) {
		super();
		this.imageWindow = imageWindow;
		image = new BufferedImage(imageWindow.getWidth(), imageWindow.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		image.getGraphics().setColor(Color.WHITE);
		image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
		layerList = new ArrayList<Layer>();
		selectionList = new ArrayList<Selection>();
	}		
	
	public void addLayer(Layer layer) {
		if (layer != null) {
			if(layer.getOpacity() != 100) {
				int op = layer.getOpacity();
				if (op > 100) op = 100;
				if (op < 0) op = 0;
				
				Runtime runtime = Runtime.getRuntime();
				
				try {
					Process process = runtime.exec(new String[] {MenuWindow.cppPath, 
							"ImageOpacity", layer.getPath(), "" + op, "null"});
					process.waitFor();
					System.out.println(process.exitValue());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			Formatter.loadLayer(layer);
			if (!layer.isOk() || layer.getImage() == null) {
				JOptionPane.showMessageDialog(MenuWindow.getInstance(),
	    			    "Couldn't read layer",
	    			    "Invalid File",
	    			    JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (layer.getX() + layer.getImage().getWidth() > imageWindow.getWidth()) {
				imageWindow.setWidth(layer.getX() + layer.getImage().getWidth());
			}
			if (layer.getY() + layer.getImage().getHeight() > imageWindow.getHeight()) {
				imageWindow.setHeight(layer.getY() + layer.getImage().getHeight());
			}
			imageWindow.resizeCustom();
			image = new BufferedImage(imageWindow.getWidth(), imageWindow.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			image.getGraphics().setColor(Color.WHITE);
			image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
			layerList.add(layer);
		}
		repaint();
	}
	
	public void removeLayerIndex(int ind) {
		if(ind >= 0 && ind < layerList.size())
			layerList.remove(ind);
		changeDimensions();
		repaint();
	}
	
	public void addSelection(int x, int y, int w, int h) {
		if (x + w > image.getWidth() || y + h > image.getHeight()) {
			System.out.println("Cannot create Selection outside of the Image!");
			return;
		}
		
		Selection selection = new Selection(this, x, y, w, h);
		selectionList.add(selection);
	}
	
	public void removeSelectionIndex(int ind) {
		if(ind >= 0 && ind < selectionList.size())
			selectionList.remove(ind);
	}
	
	private void changeDimensions() {
		int maxW = 0;
		int maxH = 0;
		
		for (Layer l : layerList) {
			if (l.getX() + l.getImage().getWidth() > maxW) maxW = l.getX() + l.getImage().getWidth();
			if (l.getY() + l.getImage().getHeight() > maxH) maxH = l.getY() + l.getImage().getHeight();
		}
		imageWindow.setWidth(maxW);
		imageWindow.setHeight(maxH);
		
		imageWindow.resizeCustom();
	}
	
	@Override
    public void paintComponent(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
        for (Layer l : layerList) {
        	if (l.getImage() != null) {
        		image.getGraphics().drawImage(l.getImage(), 0, 0, null);
        		//g.drawImage(l.getImage(), l.getX(), l.getY(), null);
        	}
        }
        g.drawImage(image, 0, 0, null);
    }        
	
	public ArrayList<Layer> getLayerList() {
		return layerList;
	}
	
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ArrayList<Selection> getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(ArrayList<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	private ArrayList<Layer> layerList;
	private ArrayList<Selection> selectionList;
	private ImageWindow imageWindow;
	private BufferedImage image;
}
