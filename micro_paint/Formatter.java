package micro_paint;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class Formatter {
	
	
	static void loadLayer(Layer layer) {
		
		String ext = layer.getPath().substring(layer.getPath().length() - 4);
		layer.setImage(null);
		
		// Loading BMP Image
		if (ext.equals(".bmp")) {
			 try {
				 layer.setImage(ImageIO.read(new File(layer.getPath())));
				 BufferedImage temp = new BufferedImage(layer.getImage().getWidth(), layer.getImage().getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
				 temp.getGraphics().drawImage(layer.getImage(), 0, 0, null);
				 layer.setImage(temp);
		     } catch (IOException e) {
		    	 e.printStackTrace();
		     }
			 
		// Loading PAM Image	 
		} else if (ext.equals(".pam")) {
			int headerSize = 0;
			try {
				File file = new File(layer.getPath());   
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters  
				String line;
				
				line = br.readLine();	
				headerSize += line.length() + 1;	// ???
				
				if (!line.equals("P7")) {
					throw new Exception();
				}
				
				int w = 0, h = 0, depth = 0, maxval = 0;
				String tupltype;
				
				String pattern = "([^0-9]*)([0-9]*)";
				Pattern r = Pattern.compile(pattern);
				
				line = br.readLine();
				headerSize += line.length() + 1;
				Matcher m = r.matcher(line);
				
				if (m.find()) {		         
			        w = Integer.parseInt(m.group(2));
			    } else {
			    	throw new Exception();
			    }
				
				line = br.readLine();
				headerSize += line.length() + 1;
				m = r.matcher(line);
				
				if (m.find()) {
					h = Integer.parseInt(m.group(2));
			    } else {
			    	throw new Exception();
			    }
				
				line = br.readLine();
				headerSize += line.length() + 1;
				m = r.matcher(line);
				
				if (m.find( )) {
			        depth = Integer.parseInt(m.group(2));
			    } else {
			    	throw new Exception();
			    }
									
				line = br.readLine();
				headerSize += line.length() + 1;
				m = r.matcher(line);
				
				if (m.find()) {
			        maxval = Integer.parseInt(m.group(2));		         
			    } else {
			        throw new Exception();
			    }
				
				pattern = "TUPLTYPE ([A-Z]*[\u005F]*[A-Z]*)";
				line = br.readLine();
				headerSize += line.length() + 1;
				m = r.matcher(line); 
				
				if (m.find()) {
			        //maxval = Integer.parseInt(m.group(1));
			    } else {
			    	throw new Exception();
			    }
			
				line = br.readLine();
				headerSize += line.length() + 1;		
				
				if (depth == 3) {
					byte pixels[] = new byte[w * h * 3];
					FileInputStream fileInputStream = null;
					fileInputStream = new FileInputStream(file);
					fileInputStream.skip(headerSize);
		            fileInputStream.read(pixels);
		            
					layer.setImage(new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB));
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							byte rr = pixels[(i + j * w) * 3 + 2];
							byte rg = pixels[(i + j * w) * 3 + 1];
							byte rb = pixels[(i + j * w) * 3 ];
							
							int rgb = ((rr << 16) & 0xff0000) | ((rg << 8) & 0xff00) | (rb & 0xff);
							
							layer.getImage().setRGB(i, j, rgb);
						}
					}
					
				}
				else if (depth == 4) {
					
					byte pixels[] = new byte[w * h * 4];
					FileInputStream fileInputStream = null;
					fileInputStream = new FileInputStream(file);
					fileInputStream.skip(headerSize);
		            fileInputStream.read(pixels);
		            
		            layer.setImage(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							byte rr = pixels[(i + j * w) * 4 + 3]; // sve 1
							byte rg = pixels[(i + j * w) * 4 + 2];
							byte rb = pixels[(i + j * w) * 4 + 1];
							byte ra = pixels[(i + j * w) * 4];
							
							int argb = ((ra << 24) & 0xff000000) | ((rr << 16) & 0xff0000) | ((rg << 8) & 0xff00) | ((rb) & 0xff);
							
							layer.getImage().setRGB(i, j, argb);
						}
					}	
				} else {
					throw new Exception();
				}
	
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
		
	}
	
}
