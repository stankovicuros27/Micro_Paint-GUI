package micro_paint;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MenuWindow extends JFrame {

	public MenuWindow() {
		super("Menu Window");
		imageWindow = new ImageWindow(600, 600);
		setBounds(150, 300, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addButtons();
		
		setVisible(true);
		setResizable(false);
	}
	
	private void addButtons() {
	
		btnShowImageInfo = new JButton("Show image info");
		btnShowImageInfo.addActionListener(e -> {
			JFrame f = new JFrame();
			f.setLayout(new GridLayout(6, 1));
			JLabel dimensions = new JLabel("Dimensions: " + imageWindow.getWidth() + "w : " + imageWindow.getHeight() + "h");
			JLabel layers = new JLabel("Layers:");
			String layerListString[] = new String[imageWindow.getImagePanel().getLayerList().size()];
			for (int i = 0; i < imageWindow.getImagePanel().getLayerList().size(); i++) {
				Layer l = imageWindow.getImagePanel().getLayerList().get(i);
				layerListString[i] = "Index(" + i 
						+ "), Path(" + l.getPath() + 
						"), X:Y:W:H(" + l.getX() + ":" + l.getY() + ":" + l.getImage().getWidth() 
						+ ":" + l.getImage().getHeight() + ")";
			}
			JList layerList = new JList<String>(layerListString);
			layerList.setVisible(true);
			
			JLabel selections = new JLabel("Selections:");
			String selectionListString[] = new String[imageWindow.getImagePanel().getSelectionList().size()];
			for (int i = 0; i < imageWindow.getImagePanel().getSelectionList().size(); i++) {
				Selection s = imageWindow.getImagePanel().getSelectionList().get(i);
				selectionListString[i] = "Index(" + i 
						+ "), X:Y:W:H(" + s.getX() + ":" + s.getY() + ":" + s.getWidth() 
						+ ":" + s.getHeight() + ")";
			}
			JList selectionList = new JList<String>(selectionListString);
			layerList.setVisible(true);
			
			f.setBounds(505, 505, 300, 500);
			f.add(dimensions);
			f.add(layers);
			f.add(layerList);
			f.add(selections);
			f.add(selectionList);
			f.setVisible(true);
			f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		});
		btnShowImageInfo.setBounds(0, 0, 200, 30);
		
		btnManageImageLayers = new JButton("Manage image layers");
		btnManageImageLayers.addActionListener(e -> {
			JFrame f = new JFrame();
			Object[] options = {"Add new Layer",
                    "Remove existing Layer"};
			
			int n = JOptionPane.showOptionDialog(f, "Select action", "Layer Manager",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							null);
			if (n == JOptionPane.YES_OPTION) {
				JFrame enterLayer = new JFrame();
				JLabel pathL = new JLabel("Enter path:");
				JTextField path = new JTextField();
				JLabel xL = new JLabel("Enter x:");
				JTextField x = new JTextField();
				JLabel yL = new JLabel("Enter y:");
				JTextField y = new JTextField();
				JLabel opL = new JLabel("Enter opacity:");
				JTextField opacity = new JTextField();
				enterLayer.setLayout(new GridLayout(5, 2));
				enterLayer.add(pathL);
				enterLayer.add(path);
				enterLayer.add(xL);
				enterLayer.add(x);
				enterLayer.add(yL);
				enterLayer.add(y);
				enterLayer.add(opL);
				enterLayer.add(opacity);
				
				JButton confirm = new JButton("Confirm");
				confirm.addActionListener(ee -> {
					Layer l = new Layer(path.getText(), 
							Integer.parseInt(x.getText()),
							Integer.parseInt(y.getText()), 
							Integer.parseInt(opacity.getText()));
					//Formatter.loadLayer(l);
					
					imageWindow.getImagePanel().addLayer(l);
					f.dispose();
					enterLayer.dispose();
				});
				
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(ee -> {
					f.dispose();
					enterLayer.dispose();
				});
				
				enterLayer.add(confirm);
				enterLayer.add(cancel);
				
				enterLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				enterLayer.setBounds(500, 500, 300, 300);
				enterLayer.setVisible(true);
				
			} else if (n == JOptionPane.NO_OPTION) {
				JFrame deleteLayer = new JFrame();
				JLabel label = new JLabel("Enter index to delete:");
				JTextField tf = new JTextField();
				JButton btn = new JButton("Delete");
				btn.addActionListener(eee -> {
					imageWindow.getImagePanel().removeLayerIndex(Integer.parseInt(tf.getText()));
					deleteLayer.dispose();
				});
				
				deleteLayer.setLayout(new GridLayout(3, 1));
				deleteLayer.add(label);
				deleteLayer.add(tf);
				deleteLayer.add(btn);
				
				deleteLayer.setBounds(500, 500, 300, 150);
				deleteLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				deleteLayer.setVisible(true);
			}
			
		});
		btnManageImageLayers.setBounds(0, 50, 200, 30);
		
		
		btnManageImageSelections = new JButton("Manage image selections");
		btnManageImageSelections.addActionListener(e -> {
			JFrame f = new JFrame();
			Object[] options = {"Add new Selection",
                    "Remove existing Selection"};
			
			int n = JOptionPane.showOptionDialog(f, "Select action", "Selection Manager",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							null);
			if (n == JOptionPane.YES_OPTION) {
				JFrame enterLayer = new JFrame();
				JLabel xL = new JLabel("Enter x:");
				JTextField x = new JTextField();
				JLabel yL = new JLabel("Enter y:");
				JTextField y = new JTextField();
				JLabel wL = new JLabel("Enter width:");
				JTextField width = new JTextField();
				JLabel hL = new JLabel("Enter height:");
				JTextField height = new JTextField();
				
				enterLayer.setLayout(new GridLayout(5, 2));
				enterLayer.add(xL);
				enterLayer.add(x);
				enterLayer.add(yL);
				enterLayer.add(y);
				enterLayer.add(wL);
				enterLayer.add(width);
				enterLayer.add(hL);
				enterLayer.add(height);
				
				JButton confirm = new JButton("Confirm");
				confirm.addActionListener(ee -> {
					imageWindow.getImagePanel().addSelection(Integer.parseInt(x.getText()), 
							Integer.parseInt(y.getText()), 
							Integer.parseInt(width.getText()), 
							Integer.parseInt(height.getText()));
					f.dispose();
					enterLayer.dispose();
				});
				
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(ee -> {
					f.dispose();
					enterLayer.dispose();
				});
				
				enterLayer.add(confirm);
				enterLayer.add(cancel);
				
				enterLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				enterLayer.setBounds(500, 500, 300, 300);
				enterLayer.setVisible(true);
				
			} else if (n == JOptionPane.NO_OPTION) {
				JFrame deleteLayer = new JFrame();
				JLabel label = new JLabel("Enter index to delete:");
				JTextField tf = new JTextField();
				JButton btn = new JButton("Delete");
				btn.addActionListener(eee -> {
					imageWindow.getImagePanel().removeSelectionIndex(Integer.parseInt(tf.getText()));
					deleteLayer.dispose();
				});
				
				deleteLayer.setLayout(new GridLayout(3, 1));
				deleteLayer.add(label);
				deleteLayer.add(tf);
				deleteLayer.add(btn);
				
				deleteLayer.setBounds(500, 500, 300, 150);
				deleteLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				deleteLayer.setVisible(true);
			}
			
		});
		btnManageImageSelections.setBounds(0, 100, 200, 30);
		
		btnPerformOperations = new JButton("Perform operation");
		btnPerformOperations.addActionListener(e -> {
				
			JFrame f = new JFrame();
			Object[] options = {"Make new composite operation",
                    "Perform existing operation"};
			
			int n = JOptionPane.showOptionDialog(f, "Select action", "Operation Manager",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							null);
			if (n == JOptionPane.YES_OPTION) {
				JFrame enterLayer = new JFrame();
				JLabel numL = new JLabel("Enter number of operations:");
				JTextField num = new JTextField();
				enterLayer.setLayout(new GridLayout(1, 1));
				enterLayer.add(numL);
				enterLayer.add(num);
				
				JButton confirm = new JButton("Confirm");
				confirm.addActionListener(ee -> {
					
					int nn = Integer.parseInt(num.getText());
					
					f.dispose();
					enterLayer.dispose();
					
					JFrame ff = new JFrame();
					int height = 50 * nn;
					ff.setBounds(500, 500, 300, height);
					
					JLabel operationL = new JLabel("OpCode/Operand");
					
					JTextField operations[][] = new JTextField[nn][2];
					for (int i = 0; i < nn; i++) {
						operations[i][0] = new JTextField();
						operations[i][1] = new JTextField();
					}
					
					String opcodes[] = new String[nn];
					Integer operands[] = new Integer[nn];
					
					ff.setLayout(new GridLayout(nn + 2, 2));
					ff.add(operationL);
					ff.add(new JLabel());
					for (int i = 0; i < nn; i++) {
						ff.add(operations[i][0]);
						ff.add(operations[i][1]);
					}
					
					JButton btn = new JButton("Confirm");
					ff.add(btn);
					
					btn.addActionListener(eee -> {
						
						for (int i = 0; i < nn; i++) {
							opcodes[i] = "" + operations[i][0].getText();
							operands[i] = Integer.parseInt(operations[i][1].getText());
						}
						
						Operation op = new Operation();
						for (int i = 0; i < nn; i++) {
							System.out.println(opcodes[i] + " " + operands[i] + " " + op + " " + op.getOperandList());
							op.getOperationList().add(opcodes[i]);
							op.getOperandList().add(operands[i]);
						}
						
						String path = JOptionPane.showInputDialog(ff,"Enter operation path"); 
						Formatter.exportOperation(op, path);
						
						ff.dispose();
					});
					
					ff.setVisible(true);
				});
				
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(ee -> {
					f.dispose();
					enterLayer.dispose();
				});
				
				enterLayer.setLayout(new GridLayout(3, 1));
				enterLayer.add(confirm);
				enterLayer.add(cancel);
				
				enterLayer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				enterLayer.setBounds(500, 500, 450, 120);
				enterLayer.setVisible(true);
				
			} else if (n == JOptionPane.NO_OPTION) {
				JFrame ff = new JFrame();
			    String path = JOptionPane.showInputDialog(ff,"Enter operation path"); 
			    
			    Formatter.exportXML(imageWindow.getImagePanel(), "temp.xml");
				
				Runtime runtime = Runtime.getRuntime();
				
				try {
					Process process = runtime.exec(new String[] {"C:\\Users\\Uros\\source\\repos\\Micro_Paint\\Debug\\Micro_Paint.exe", 
							"temp.xml", path, System.getProperty("user.dir")});
					process.waitFor();
					System.out.println(process.exitValue());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				System.out.println("DONE");
				
				Formatter.importXML(imageWindow.getImagePanel(), "temp.xml");
			}
		});
		btnPerformOperations.setBounds(0, 150, 200, 30);
		
		
		btnSaveImage = new JButton("Save image");
		btnSaveImage.addActionListener(e -> {
			JFrame f = new JFrame();
		    String path = JOptionPane.showInputDialog(f,"Enter image path"); 
		    Formatter.exportImage(imageWindow.getImagePanel().getImage(), path);
		});
		btnSaveImage.setBounds(0, 200, 200, 30);
		
		
		btnSaveProject = new JButton("Load/Save project");
		btnSaveProject.addActionListener(e -> {
			JFrame ff = new JFrame();
			Object[] options = {"Save project",
                    "Load project"};
			
			int n = JOptionPane.showOptionDialog(ff, "Select action", "Selection Manager",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							null);
			if (n == JOptionPane.YES_OPTION) {
				System.out.println("Saving project");
				JFrame f = new JFrame();
			    String path = JOptionPane.showInputDialog(f,"Enter project path"); 
			    Formatter.exportXML(imageWindow.getImagePanel(), path);
			    
			} else if (n == JOptionPane.NO_OPTION) {
				System.out.println("Loading project");
				JFrame f = new JFrame();
			    String path = JOptionPane.showInputDialog(f,"Enter project path"); 
			    Formatter.importXML(imageWindow.getImagePanel(), path);
			}
			
		});
		btnSaveProject.setBounds(0, 250, 200, 30);
		
		
		add(btnShowImageInfo);
		add(btnManageImageLayers);
		add(btnManageImageSelections);
		add(btnPerformOperations);
		add(btnSaveImage);
		add(btnSaveProject);
		
		setLayout(null);
	}
	
	private String name = "Menu Window";
	private int width = 217, height = 320;
	private ImageWindow imageWindow;
	
	private JButton btnShowImageInfo;
	private JButton btnManageImageLayers;
	private JButton btnManageImageSelections;
	private JButton btnPerformOperations;
	private JButton btnSaveImage;
	private JButton btnSaveProject;
}
