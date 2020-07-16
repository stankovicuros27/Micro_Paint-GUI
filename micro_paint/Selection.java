package micro_paint;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Selection {

	public Selection(ImagePanel imagePanel, int x, int y, int width, int height) {
		super();
		this.imagePanel = imagePanel;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	public void setImagePanel(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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



	private ImagePanel imagePanel;
	private int x, y, width, height;
}
