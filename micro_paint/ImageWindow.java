package micro_paint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageWindow extends JFrame {

	public ImageWindow(int width, int height) {
		super("Image Window");
		this.width = width;
		this.height = height;
		imagePanel = new ImagePanel(this);
		
		setBounds(450, 150, width, height);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		add(imagePanel);
		
		setVisible(true);	
	}
	
	
	public void resizeCustom() {
		setSize(width, height);
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	public void setImagePanel(ImagePanel imageFrame) {
		this.imagePanel = imageFrame;
	}
		
	private int width, height;
	private ImagePanel imagePanel;
}
