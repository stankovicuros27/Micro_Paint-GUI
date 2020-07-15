package micro_paint;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
				System.out.println(depth);
				if (depth == 3) {
					byte pixels[] = new byte[w * h * 3];
					FileInputStream fileInputStream = null;
					fileInputStream = new FileInputStream(file);
					fileInputStream.skip(headerSize);
		            fileInputStream.read(pixels);
		            
					layer.setImage(new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR));
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							byte rr = pixels[(i + j * w) * 3 + 2];
							byte rg = pixels[(i + j * w) * 3 + 1];
							byte rb = pixels[(i + j * w) * 3 ];
							
							int rgb = ((rb << 16) & 0xff0000) | ((rg << 8) & 0xff00) | (rr & 0xff);
							
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
		            
		            layer.setImage(new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR));
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							byte rr = pixels[(i + j * w) * 4 + 2]; // sve 1
							byte rg = pixels[(i + j * w) * 4 + 1];
							byte rb = pixels[(i + j * w) * 4];
							byte ra = pixels[(i + j * w) * 4 + 3];
							
							int argb = ((ra << 24) & 0xff000000) | ((rb << 16) & 0xff0000) | ((rg << 8) & 0xff00) | ((rr) & 0xff);
							
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
	
	static void exportImage(BufferedImage img, String path) {
		String ext = path.substring(path.length() - 4);
		
		if(ext.equals(".pam")) {
			try {
				FileWriter myWriter = new FileWriter(path);
				myWriter.write("P7\n");
				myWriter.write("WIDTH " + img.getWidth() + "\n");
				myWriter.write("HEIGHT " + img.getHeight() + "\n");
				myWriter.write("DEPTH 4\n");
				myWriter.write("MAXVAL 255\n");
				myWriter.write("TUPLTYPE RGB_ALPHA\n");
				myWriter.write("ENDHDR\n");
				
				for(int i = 0; i < img.getWidth(); i++) {
					for(int j = 0; j < img.getHeight(); j++) {
						int rgb = img.getRGB(i, j);
						Color col = new Color(rgb);
						byte alpha = (byte) (col.getAlpha() & 0xff);
						byte red = (byte) (col.getRed() & 0xff);
						byte green = (byte) (col.getGreen() & 0xff);
						byte blue = (byte) (col.getBlue() & 0xff);
						
						int bgra = (red << 24) | (green << 16) | (blue << 8) | alpha;
						myWriter.write(bgra);
					}
				}
				myWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Node createLayer(Document doc, Layer lay) {
        Element layer = doc.createElement("Layer");
        layer.setAttribute("Path", lay.getPath());
        return layer;
    }
	
	private static Node createSelection(Document doc, Selection sel) {
        Element selection = doc.createElement("Selection");
        selection.setAttribute("x", "" + sel.getX());
        selection.setAttribute("y", "" + sel.getY());
        selection.setAttribute("w", "" + sel.getWidth());
        selection.setAttribute("h", "" + sel.getHeight());
        return selection;
    }
	
	static void exportXML(ImagePanel imagePanel, String path) {
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.newDocument();
	        
	        Element root = doc.createElement("Root");
	        doc.appendChild(root);   
	        doc.setXmlStandalone(true);

	        Element layerList = doc.createElement("Layer_List");
	        layerList.setAttribute("Layer_Count", "" + imagePanel.getLayerList().size());	        
	        root.appendChild(layerList);
	        
	        Element selectionList = doc.createElement("Selection_List");
	        selectionList.setAttribute("Selection_Count", "" + imagePanel.getSelectionList().size());
	        root.appendChild(selectionList);
	        
	        
	        for (Layer l : imagePanel.getLayerList()) {
	        	layerList.appendChild(createLayer(doc, l));
	        }
	        
	        for (Selection l : imagePanel.getSelectionList()) {
	        	selectionList.appendChild(createSelection(doc, l));
	        }

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transf = transformerFactory.newTransformer();
	        
	        //transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transf.setOutputProperty(OutputKeys.INDENT, "yes");
	        transf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        //transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        
	        DOMSource source = new DOMSource(doc);

	        File myFile = new File(path);

	        StreamResult file = new StreamResult(myFile);

	        transf.transform(source, file);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void importXML(ImagePanel imagePanel, String path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path);
			
			NodeList layerList = doc.getElementsByTagName("Layer");
			for (int i = 0; i < layerList.getLength(); i++) {
				Node layNode = layerList.item(i);
				if (layNode.getNodeType() == Node.ELEMENT_NODE) {
					Element lay = (Element) layNode;
					String pathLay = lay.getAttribute("Path");
					imagePanel.addLayer(new Layer(pathLay, 0, 0));
				}
			}
			
			NodeList selectionList = doc.getElementsByTagName("Selection");
			for (int i = 0; i < selectionList.getLength(); i++) {
				Node selectionNode = selectionList.item(i);
				if (selectionNode.getNodeType() == Node.ELEMENT_NODE) {
					Element node = (Element) selectionNode;
					int x = Integer.parseInt(node.getAttribute("x"));
					int y = Integer.parseInt(node.getAttribute("y"));
					int w = Integer.parseInt(node.getAttribute("w"));
					int h = Integer.parseInt(node.getAttribute("h"));
					imagePanel.addSelection(x, y, w, h);
				}
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
