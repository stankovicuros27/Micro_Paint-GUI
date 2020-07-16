package micro_paint;

import java.awt.image.BufferedImage;

public class Layer {
	
	public Layer(String path, int x, int y) {
		this.path = path;
		this.x = x;
		this.y = y;
	}
	
	public Layer(String path, int x, int y, int opacity) {
		this.path = path;
		this.x = x;
		this.y = y;
		this.opacity = opacity;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
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
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}



	private BufferedImage image;
	private String path;
	private int opacity = 100;
	private int x, y;
	private boolean ok = false;
}
