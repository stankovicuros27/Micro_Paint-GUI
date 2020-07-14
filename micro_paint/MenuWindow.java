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
			
			f.setBounds(505, 505, 300, 500);
			f.add(dimensions);
			f.add(layers);
			f.add(layerList);
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
				
			}
			
		});
		btnManageImageLayers.setBounds(0, 50, 200, 30);
		
		
		btnManageImageSelections = new JButton("Manage image selections");
		btnManageImageSelections.setBounds(0, 100, 200, 30);
		
		
		btnPerformOperations = new JButton("Perform operation");
		btnPerformOperations.setBounds(0, 150, 200, 30);
		
		
		btnSaveImage = new JButton("Save image");
		btnSaveImage.setBounds(0, 200, 200, 30);
		
		
		btnSaveProject = new JButton("Save project");
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
	private int width = 220, height = 280;
	private ImageWindow imageWindow;
	
	private JButton btnShowImageInfo;
	private JButton btnManageImageLayers;
	private JButton btnManageImageSelections;
	private JButton btnPerformOperations;
	private JButton btnSaveImage;
	private JButton btnSaveProject;
}
