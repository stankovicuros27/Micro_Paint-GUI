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
		layerList = new ArrayList<Layer>();
	}		
	
	public void addLayer(Layer layer) {
		if (layer != null) {
			Formatter.loadLayer(layer);
			if (layer.getX() + layer.getImage().getWidth() > imageWindow.getWidth()) {
				imageWindow.setWidth(layer.getX() + layer.getImage().getWidth());
			}
			if (layer.getY() + layer.getImage().getHeight() > imageWindow.getHeight()) {
				imageWindow.setHeight(layer.getY() + layer.getImage().getHeight());
			}
			imageWindow.resizeCustom();
			layerList.add(layer);
		}
		repaint();
	}
	
	@Override
    public void paintComponent(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
        for (Layer l : layerList) {
        	if (l.getImage() != null) {
        		g.drawImage(l.getImage(), l.getX(), l.getY(), null);
        	}
        }
    }        
	
	public ArrayList<Layer> getLayerList() {
		return layerList;
	}
	
	private ArrayList<Layer> layerList;
	private ImageWindow imageWindow;
}
