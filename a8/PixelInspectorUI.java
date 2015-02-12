package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorUI extends JPanel implements ActionListener {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel pixel_info;
	private JButton copyToBrushButton;
	private Pixel sendPixel;
	private FrameView magnifyFrameView;
	private Frame magFrame;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	private JPanel topPanel;
	
	public PixelInspectorUI() {
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		copyToBrushButton = new JButton("Copy to Paint Brush Tool");
		copyToBrushButton.addActionListener(this);
		
		magFrame = new ColorFrame(120, 120);
		magnifyFrameView = new FrameView(magFrame);

		setLayout(new BorderLayout());
		
		topLeftPanel = new JPanel();
		topLeftPanel.setLayout(new GridLayout(4, 1));
		topLeftPanel.add(x_label);
		topLeftPanel.add(y_label);
		topLeftPanel.add(pixel_info);
		topLeftPanel.add(copyToBrushButton);
		topLeftPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		topRightPanel = new JPanel();
		topRightPanel.add(magnifyFrameView);
		topRightPanel.setAlignmentX(RIGHT_ALIGNMENT);
		
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(topLeftPanel, BorderLayout.WEST);
		topPanel.add(topRightPanel, BorderLayout.EAST);
		topPanel.setAlignmentY(TOP_ALIGNMENT);
		
		add(topPanel);
	}
	
	public void setInfo(int x, int y, Pixel p, Frame f) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getGreen(), p.getBlue()));	
		sendPixel = new ColorPixel(p.getRed(), p.getGreen(), p.getBlue());
		updateMagnifyFrame(x, y, p, f);
	}

	private void updateMagnifyFrame(int x, int y, Pixel p, Frame f) {
		if(x < 7){
			x = 7;
		}
		
		if(y < 7){
			y = 7;
		}
		
		if(x > f.getWidth() - 16){
			x = f.getWidth() - 16;
		}
		
		if(y > f.getHeight() - 16){
			y = f.getHeight() - 16;
		}
		
		IndirectFrame magArea = new IndirectFrame(f, x - 7, y - 7, 16, 16);
		
		for(int i = 0; i < magFrame.getWidth(); i++){
			for(int j = 0; j < magFrame.getHeight(); j++){
				int magAreaX = i / 8;
				int magAreaY = j / 8;
				
				magFrame.setPixel(i, j, magArea.getPixel(magAreaX, magAreaY));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BrushPixel b = new BrushPixel();
		b.setPixel(sendPixel);
	}
}
